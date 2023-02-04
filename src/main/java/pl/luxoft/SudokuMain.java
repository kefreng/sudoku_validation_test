package pl.luxoft;

import java.io.*;
import java.util.List;
import java.util.Scanner;

public class SudokuMain {
    public static void main(String[] args) {
        String filePath = (args != null && args.length > 0) ? args[0] : "";
        Sudoku sudoku = new Sudoku();
        try {
            boolean result = sudoku.validateSudoku(filePath);
            if (result) {
                System.out.println("0 (VALID)");
            } else {
                System.out.println("-1 (INVALID)");
            }
        }catch(SudokuException ex){
            System.out.println("-1 (INVALID) " + ex.getMessage());
        }


    }


}
