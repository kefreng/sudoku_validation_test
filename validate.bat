@echo off
mvn clean package -q exec:java -Dexec.mainClass="pl.luxoft.SudokuMain" -Dexec.args="%1"
pause