package pl.luxoft;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;

import java.io.*;
import java.lang.reflect.Array;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Sudoku {

    public static Integer SUDOKU_ROWS = 9;
    public static Integer SUDOKU_COLUMNS = 9;

    List<Integer> validRow, validColumn, valid3x3Square;

    public Sudoku() {
        validRow = validColumn = valid3x3Square = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
    }


    public boolean validateSudoku(String filePath) {
        List<List<Integer>> matrix = getListOfData(filePath);
        return validateDataSudoku(matrix);
    }

    private List<List<Integer>> getListOfData(String filePath){
        File file = this.readAndGetFile(filePath);
        List<String> list = this.getListStringFromFile(file);
        return this.convertDataToListOfListInteger(list);
    }


    public File readAndGetFile(String fileName) {
        File file = null;
        if (fileName != null && !fileName.isEmpty()) {
            file = new File(fileName);
        } else {
            throw new SudokuException("File name cannot be empty.");
        }

        return returnFileIfExists(file);
    }

    public File returnFileIfExists(File file) {
        if (!file.exists()) {
            throw new SudokuException("File doesn't exists.");
        }
        return file;
    }


    public List<String> getListStringFromFile(File file) {
        List<String> list = new ArrayList<>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            String row;
            while ((row = br.readLine()) != null) {
                list.add(row);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (list.isEmpty()) {
            throw new SudokuException("File has no data.");
        }

        return list;
    }

    public List<List<Integer>> convertDataToListOfListInteger(List<String> rows) {
        if (isRowsDataValid(rows)) {
            List<List<Integer>> result = new ArrayList<>();
            for (String row : rows) {
                List<String> lRows = Arrays.asList(row.split(","));
                result.add(lRows.stream().map(Integer::parseInt).collect(Collectors.toList()));
            }
            return result;
        }
        return new ArrayList<>();
    }

    private boolean validateDataSudoku(List<List<Integer>> matrix) {
        boolean validRows = validateRows(matrix);
        boolean validColumns = validateColumns(matrix);
        boolean valid3x3Squares = validate3x3Squares(matrix);

        return validRows && validColumns && valid3x3Squares;
    }

    private boolean validateRows(List<List<Integer>> matrix) {
        return (validateAmountRows(matrix) && validateRepeatedNumbersRows(matrix));
    }

    private boolean validateColumns(List<List<Integer>> matrix) {
        return (validateAmountColumns(matrix) && validateRepeatedNumbersColumns(matrix));
    }

    private boolean validate3x3Squares(List<List<Integer>> matrix) {
        for (int row = 0, column = 0; row < 9; row += 3, column += 3) {
            if (!valid3x3Square(matrix, row, column)) {
                return false;
            }
        }
        return true;
    }

    private boolean validateAmountRows(List<List<Integer>> matrix) {
        return getAmountRowsFromMatrix(matrix) == SUDOKU_ROWS;
    }

    private boolean validateRepeatedNumbersRows(List<List<Integer>> matrix) {
        for (int row = 0; row < SUDOKU_ROWS; row++) {
            if (rowHasRepeatedNumbers(matrix.get(row))) {
                throw new SudokuException("Row " + row + " has repeated numbers : " + Arrays.toString(matrix.get(row).toArray()));
            }
        }
        return true;
    }

    private boolean validateAmountColumns(List<List<Integer>> matrix) {
        return getAmountColumnsFromList(matrix) == SUDOKU_COLUMNS;
    }

    private boolean validateRepeatedNumbersColumns(List<List<Integer>> matrix) {
        for (int column = 0; column < SUDOKU_ROWS; column++) {
            List<Integer> listColumn = getColumnByIndexFromMatrixData(matrix, column);
            if (columnHasRepeatedNumbers(listColumn)) {
                return false;
            }
        }
        return true;
    }

    public boolean valid3x3Square(List<List<Integer>> matrix, int x, int y) {
        List<Integer> list3x3Square = get3x3SquareAsList(matrix, x, y);
        Set<Integer> cleanList = removeRepeatedElements(list3x3Square);

        if (cleanList.size() < list3x3Square.size()) {
            throw new SudokuException("Square has repeated values: " + Arrays.toString(list3x3Square.toArray()));
        }
        if (!list3x3Square.containsAll(valid3x3Square) || !valid3x3Square.containsAll(list3x3Square)) {
            throw new SudokuException("Square " + Arrays.toString(list3x3Square.toArray()) + " is invalid. Only numbers from 1 to 9 are allowed.");
        }
        return true;
    }


    public boolean isRowsDataValid(List<String> rows) {
        int rowNumber = 1;
        for (String row : rows) {
            if (!validRowData(row.trim().replaceAll("\\s+", ""))) {
                throw new SudokuException("Row " + rowNumber + " contains characters different to commas or different to numbers [1..9]. Line: " + row);
            }
            rowNumber++;
        }
        return true;
    }

    private boolean validRowData(String row) {
        String regex = "^[1-9]*(,[1-9]*)*$";
        Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);

        Matcher matcher = p.matcher(row);

        return matcher.find();

    }

    public int getAmountColumnsFromList(List<List<Integer>> matrix) {
        for (int i = 0; i < matrix.size(); i++) {
            List<Integer> column = getColumnByIndexFromMatrixData(matrix, i);
        }

        return SUDOKU_COLUMNS;
    }

    public int getAmountRowsFromMatrix(List<List<Integer>> matrix) {
        int rows = matrix.size();
        if (rows != SUDOKU_ROWS) {
            throw new SudokuException("File has " + rows + " rows instead " + SUDOKU_ROWS + " rows");
        }
        return rows;
    }


    public List<Integer> getRowByIndexFromMatrixData(List<List<Integer>> matrix, int indexRow) {
        return matrix.get(indexRow);
    }

    public List<Integer> getColumnByIndexFromMatrixData(List<List<Integer>> matrix, int indexColumn) {
        List<Integer> column = new ArrayList<>();
        for (List<Integer> row : matrix) {
            int rowNumber = 1;
            try {
                column.add(row.get(indexColumn));
            } catch (Exception ex) {
                throw new SudokuException("Row " + rowNumber + " has " + row.size() + " columns.");
            }
            rowNumber++;
        }

        return column;
    }

    public boolean rowHasRepeatedNumbers(List<Integer> rowElements) {
        Set<Integer> setList = removeRepeatedElements(rowElements);
        return setList.size() < rowElements.size();
    }

    public boolean columnHasRepeatedNumbers(List<Integer> columnElements) {
        Set<Integer> setList = removeRepeatedElements(columnElements);
        return setList.size() < columnElements.size();
    }

    private Set<Integer> removeRepeatedElements(List<Integer> list) {
        return new HashSet<Integer>(list);
    }


    private List<Integer> get3x3SquareAsList(List<List<Integer>> matrix, int row, int column) {
        List<Integer> list3x3Square = new ArrayList<>();
        for (int i = row; i < row + 3; i++) {
            for (int j = column; j < column + 3; j++) {
                list3x3Square.add(matrix.get(i).get(j));
            }
        }
        return list3x3Square;
    }


}
