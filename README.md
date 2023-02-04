# Sudoku validator

Create a command line tool (running on jvm) for validating a standard 9x9 Sudoku puzzle:

Command line: **validate.bat puzzleName.txt**

File format: csv format each line representing a row e.g.:

| sudoku            |
| ----------------- |
| 1,2,3,4,5,6,7,8,9 |
| 1,2,3,4,5,6,7,8,9 |
| 1,2,3,4,5,6,7,8,9 |
| 1,2,3,4,5,6,7,8,9 |
| 1,2,3,4,5,6,7,8,9 |
| 1,2,3,4,5,6,7,8,9 |
| 1,2,3,4,5,6,7,8,9 |
| 1,2,3,4,5,6,7,8,9 |
| 1,2,3,4,5,6,7,8,9 |

The program should return 0 (VALID) or non-zero (INVALID) value with an error text on stdout (in case of
an invalid solution or file).

There should be unit tests covering a range of error conditions and the project should be maven or
gradle based.

It should be possible to unpack the code from a zip, generate test report, build it and use a batch file to
call the code from a packaged jar.


How to run it:<br />
1) Download the project as Zip file <br />
2) Unzip the project <br />
3) Open a console and go to the same path where you unzipped the file <br />
4) Run the following command: **validate.bat puzzleName.txt** <br />
* There are some files to test the program: <br /><br />
* validSudoku.txt <br />
* invalidSudoku.txt <br />
