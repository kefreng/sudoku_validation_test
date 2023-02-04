package pl.luxoft;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class SudokuTest {

    public static Integer SUDOKU_ROWS = 9;
    public static Integer SUDOKU_COLUMNS = 9;
    public static String BASE_PATH_RESOURCE = "src/test/resources/";

    @Test
    public void testReadAndGetFile() {
        //arrange
        String filePath = BASE_PATH_RESOURCE + "puzzleName.txt";
        Sudoku sudoku = new Sudoku();
        //act
        File file = sudoku.readAndGetFile(filePath);
        //assert
        assertTrue(file.exists());
        assertTrue(file != null);
    }

    @Test
    public void testReadAndGetFileWhenPathIsEmpty() {
        //arrange
        String filePath = "";
        Sudoku sudoku = new Sudoku();
        //act
        SudokuException exception = assertThrows(SudokuException.class, () -> sudoku.readAndGetFile(filePath));
        //assert
        assertEquals("File name cannot be empty.", exception.getMessage());
    }

    @Test
    public void testReadFileDoesntExists() {
        //arrange
        String filePath = BASE_PATH_RESOURCE + "puzzleName_NoExistsFile.txt";
        Sudoku sudoku = new Sudoku();
        //act
        SudokuException exception = assertThrows(SudokuException.class, () -> sudoku.readAndGetFile(filePath));

        //assert
        assertEquals("File doesn't exists.", exception.getMessage());
    }


    @Test
    public void testValidateFileHasData() {
        //arrange
        String filePath = BASE_PATH_RESOURCE + "puzzleName.txt";
        Sudoku sudoku = new Sudoku();
        //act
        File file = sudoku.readAndGetFile(filePath);
        List<String> list = sudoku.getListStringFromFile(file);
        //assert
        assertFalse(list.isEmpty());
    }

    @Test
    public void testValidateFileHasntData() {
        //arrange
        String filePath = BASE_PATH_RESOURCE + "puzzleNameEmpty.txt";
        Sudoku sudoku = new Sudoku();
        //act
        File file = sudoku.readAndGetFile(filePath);
        SudokuException exception = assertThrows(SudokuException.class, () -> sudoku.getListStringFromFile(file));

        //assert
        assertEquals("File has no data.", exception.getMessage());
    }



    @Test
    public void testIfRowDataFromFileAreJustNumbersAndCommas() {
        //arrange
        String filePath = BASE_PATH_RESOURCE + "puzzleName.txt";
        Sudoku sudoku = new Sudoku();
        //act
        File file = sudoku.readAndGetFile(filePath);
        List<String> list = sudoku.getListStringFromFile(file);
        boolean isValidData = false;
        isValidData = sudoku.isRowsDataValid(list);

        //assert
        assertTrue(isValidData);
    }

    @Test
    public void testIfRowDataFromFileArentNumbersAndCommas() {
        //arrange
        String filePath = BASE_PATH_RESOURCE + "puzzleNameInvalidData.txt";
        Sudoku sudoku = new Sudoku();
        //act
        File file = sudoku.readAndGetFile(filePath);
        List<String> list = sudoku.getListStringFromFile(file);
        SudokuException exception = assertThrows(SudokuException.class, () -> sudoku.isRowsDataValid(list));

        //assert
        assertEquals("Row 4 contains characters different to commas or different to numbers [1..9]. Line: 1,2,3,4,5,6a,7,8,9", exception.getMessage());

    }

    @Test
    public void testValidateFileHas9Rows() {
        //arrange
        String filePath = BASE_PATH_RESOURCE + "puzzleName.txt";
        Sudoku sudoku = new Sudoku();
        //act
        File file = sudoku.readAndGetFile(filePath);
        List<String> list = sudoku.getListStringFromFile(file);
        //assert
        assertFalse(list.isEmpty());
        assertTrue(list.size() == SUDOKU_ROWS);
    }

    @Test
    public void testValidateFileHasnt9Rows() {
        //arrange
        String filePath = BASE_PATH_RESOURCE + "puzzleNameInvalidRows.txt";
        Sudoku sudoku = new Sudoku();
        //act
        File file = sudoku.readAndGetFile(filePath);
        List<String> list = sudoku.getListStringFromFile(file);
        List<List<Integer>> intList = sudoku.convertDataToListOfListInteger(list);
        SudokuException exception = assertThrows(SudokuException.class, () -> sudoku.getAmountRowsFromMatrix(intList));

        //assert
        assertEquals("File has 7 rows instead " + SUDOKU_ROWS + " rows", exception.getMessage());
    }

    @Test
    public void testValidateFileHas9Columns() {
        //arrange
        String filePath = BASE_PATH_RESOURCE + "puzzleName.txt";
        Sudoku sudoku = new Sudoku();
        //act
        File file = sudoku.readAndGetFile(filePath);
        List<String> list = sudoku.getListStringFromFile(file);
        List<List<Integer>> intList = sudoku.convertDataToListOfListInteger(list);
        int columns = 0;
        columns = sudoku.getAmountColumnsFromList(intList);

        //assert
        assertFalse(list.isEmpty());
        assertTrue(columns == SUDOKU_COLUMNS);
    }

    @Test
    public void testValidateFileHasnt9Columns() {
        //arrange
        String filePath = BASE_PATH_RESOURCE + "puzzleNameInvalidColumns.txt";
        Sudoku sudoku = new Sudoku();
        //act
        File file = sudoku.readAndGetFile(filePath);
        List<String> list = sudoku.getListStringFromFile(file);
        List<List<Integer>> intList = sudoku.convertDataToListOfListInteger(list);
        SudokuException exception = assertThrows(SudokuException.class, () -> sudoku.getAmountColumnsFromList(intList));

        //assert
        assertEquals("Row 1 has 8 columns.", exception.getMessage());

    }


    @Test
    public void testConvertDataToListOfListInteger(){
        //arrange
        String filePath = BASE_PATH_RESOURCE + "puzzleName.txt";
        Sudoku sudoku = new Sudoku();
        //act
        File file = sudoku.readAndGetFile(filePath);
        List<String> list = sudoku.getListStringFromFile(file);
        List<List<Integer>> intList = sudoku.convertDataToListOfListInteger(list);
        //assert
        assertFalse(intList.isEmpty());
        assertTrue(intList.size() == SUDOKU_ROWS);
        for(int i=0; i< intList.size(); i++){
            assertTrue(intList.get(i).size() == SUDOKU_COLUMNS);
        }

    }

    @Test
    public void testGetSudokuRowByIndex(){
        //arrange
        String filePath = BASE_PATH_RESOURCE + "puzzleName.txt";
        Sudoku sudoku = new Sudoku();
        //act
        File file = sudoku.readAndGetFile(filePath);
        List<String> list = sudoku.getListStringFromFile(file);
        List<List<Integer>> intList = sudoku.convertDataToListOfListInteger(list);
        List<Integer> rowElements = sudoku.getRowByIndexFromMatrixData(intList, 0);
        //assert
        assertFalse(rowElements.isEmpty());
        assertTrue(rowElements.size() == SUDOKU_COLUMNS);
    }

    @Test
    public void testGetSudokuColumnByIndex(){
        //arrange
        String filePath = BASE_PATH_RESOURCE + "puzzleName.txt";
        Sudoku sudoku = new Sudoku();
        //act
        File file = sudoku.readAndGetFile(filePath);
        List<String> list = sudoku.getListStringFromFile(file);
        List<List<Integer>> intList = sudoku.convertDataToListOfListInteger(list);
        List<Integer> columnElements = sudoku.getColumnByIndexFromMatrixData(intList, 0);
        //assert
        assertFalse(columnElements.isEmpty());
        assertTrue(columnElements.size() == SUDOKU_ROWS);
    }

    @Test
    public void testRowHasntRepeatedNumbers() {
        //arrange
        String filePath = BASE_PATH_RESOURCE + "puzzleName.txt";
        Sudoku sudoku = new Sudoku();
        //act
        File file = sudoku.readAndGetFile(filePath);
        List<String> list = sudoku.getListStringFromFile(file);
        List<List<Integer>> intList = sudoku.convertDataToListOfListInteger(list);
        List<Integer> rowElements = sudoku.getRowByIndexFromMatrixData(intList, 0);
        boolean hasRepeatedNumber = sudoku.rowHasRepeatedNumbers(rowElements);
        //assert
        assertFalse(hasRepeatedNumber);
    }

    @Test
    public void testRowHasRepeatedNumbers() {
        //arrange
        String filePath = BASE_PATH_RESOURCE + "puzzleNameRepeatedElements.txt";
        Sudoku sudoku = new Sudoku();
        //act
        File file = sudoku.readAndGetFile(filePath);
        List<String> list = sudoku.getListStringFromFile(file);
        List<List<Integer>> intList = sudoku.convertDataToListOfListInteger(list);
        List<Integer> rowElements = sudoku.getRowByIndexFromMatrixData(intList, 0);
        boolean hasRepeatedNumber = sudoku.rowHasRepeatedNumbers(rowElements);
        //assert
        assertTrue(hasRepeatedNumber);
    }

    @Test
    public void testColumnHasntRepeatedNumbers() {
        //arrange
        String filePath = BASE_PATH_RESOURCE + "puzzleName.txt";
        Sudoku sudoku = new Sudoku();
        //act
        File file = sudoku.readAndGetFile(filePath);
        List<String> list = sudoku.getListStringFromFile(file);
        List<List<Integer>> intList = sudoku.convertDataToListOfListInteger(list);
        List<Integer> columnElements = sudoku.getColumnByIndexFromMatrixData(intList, 0);
        boolean hasntRepeatedNumbers = sudoku.columnHasRepeatedNumbers(columnElements);
        //assert
        assertFalse(hasntRepeatedNumbers);
    }

    @Test
    public void testColumnHasRepeatedNumbers() {
        //arrange
        String filePath = BASE_PATH_RESOURCE + "puzzleNameRepeatedElements.txt";
        Sudoku sudoku = new Sudoku();
        //act
        File file = sudoku.readAndGetFile(filePath);
        List<String> list = sudoku.getListStringFromFile(file);
        List<List<Integer>> intList = sudoku.convertDataToListOfListInteger(list);
        List<Integer> columnElements = sudoku.getColumnByIndexFromMatrixData(intList, 0);
        boolean hasRepeatedNumbers = sudoku.columnHasRepeatedNumbers(columnElements);
        //assert
        assertTrue(hasRepeatedNumbers);
    }

    @Test
    public void testValidate3x3Square() {
        //arrange
        String filePath = BASE_PATH_RESOURCE + "validSudoku.txt";
        Sudoku sudoku = new Sudoku();
        //act
        File file = sudoku.readAndGetFile(filePath);
        List<String> list = sudoku.getListStringFromFile(file);
        List<List<Integer>> intList = sudoku.convertDataToListOfListInteger(list);
        boolean valid3x3Square = sudoku.valid3x3Square(intList, 0,0);
        //assert
        assertTrue(valid3x3Square);
    }

    @Test
    public void testInvalid3x3Square() {
        //arrange
        String filePath = BASE_PATH_RESOURCE + "invalidSudoku.txt";
        Sudoku sudoku = new Sudoku();
        //act
        File file = sudoku.readAndGetFile(filePath);
        List<String> list = sudoku.getListStringFromFile(file);
        List<List<Integer>> intList = sudoku.convertDataToListOfListInteger(list);
        SudokuException exception = assertThrows(SudokuException.class, () -> sudoku.valid3x3Square(intList, 3,0));

        //assert
        assertEquals("Square has repeated values: [2, 1, 4, 3, 6, 5, 1, 9, 7]", exception.getMessage());

    }

    @Test
    public void testInvalid3x3SquareNumbersNotAllowed() {
        //arrange
        String filePath = BASE_PATH_RESOURCE + "validSudoku2.txt";
        Sudoku sudoku = new Sudoku();
        //act
        File file = sudoku.readAndGetFile(filePath);
        List<String> list = sudoku.getListStringFromFile(file);
        List<List<Integer>> intList = sudoku.convertDataToListOfListInteger(list);
        SudokuException exception = assertThrows(SudokuException.class, () -> sudoku.valid3x3Square(intList, 3,0));

        //assert
        assertEquals("Square [12, 11, 14, 13, 16, 15, 18, 19, 17] is invalid. Only numbers from 1 to 9 are allowed.", exception.getMessage());

    }

    @Test
    public void testValidSudoku(){
        //arrange
        String filePath = BASE_PATH_RESOURCE + "validSudoku.txt";
        Sudoku sudoku = new Sudoku();
        //act
        boolean validSudoku = sudoku.validateSudoku(filePath);

        //assert
        assertTrue(validSudoku);
    }

    @Test
    public void testInvalidSudoku(){
        //arrange
        String filePath = BASE_PATH_RESOURCE + "invalidSudoku.txt";
        Sudoku sudoku = new Sudoku();
        //act
        SudokuException exception = assertThrows(SudokuException.class, () -> sudoku.validateSudoku(filePath));

        //assert
        assertEquals("Row 5 has repeated numbers : [1, 9, 7, 2, 1, 4, 3, 6, 5]", exception.getMessage());
    }


}