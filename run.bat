@echo off
echo ========================================
echo   LIBRARY MANAGEMENT SYSTEM
echo ========================================
echo.
echo Choose an option:
echo 1. Run Complete Demo (All Features)
echo 2. Run Interactive System (Menu-based)
echo.
set /p choice="Enter your choice (1 or 2): "

if "%choice%"=="1" (
    echo.
    echo Running Complete Demo...
    echo.
    cd ..
    java library.Library
    cd library
) else if "%choice%"=="2" (
    echo.
    echo Starting Interactive System...
    echo.
    cd ..
    java library.InteractiveLibrary
    cd library
) else (
    echo Invalid choice!
)

pause
