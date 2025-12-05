@echo off
echo Compiling Library Management System...
javac library/*.java
if %errorlevel% neq 0 (
    echo Compilation failed!
    pause
    exit /b %errorlevel%
)

echo.
echo Starting Library Management System Demo...
echo.
java library.Library
pause
