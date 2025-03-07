@echo off
setlocal

echo Compilating...
javac -d bin src/dev/mic/minesweeper/*.java
if %ERRORLEVEL% NEQ 0 (
    echo Compilation error.
    exit /b %ERRORLEVEL%
)

echo JAR creation...
jar cfe Minesweeper.jar dev.mic.minesweeper.Main -C bin .
if %ERRORLEVEL% NEQ 0 (
    echo Error during JAR creation.
    exit /b %ERRORLEVEL%
)

echo Build success!
echo To execute the program, run: java -jar Minesweeper.jar {rows} {cols} {nMines}

pause >nul
endlocal
