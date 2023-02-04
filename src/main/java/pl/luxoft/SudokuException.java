package pl.luxoft;

public class SudokuException extends RuntimeException {
    public SudokuException(String errorMessage) {
        super(errorMessage);
    }
}
