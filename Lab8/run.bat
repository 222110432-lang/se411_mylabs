@echo off
setlocal
cd /d "%~dp0"
where java >nul 2>nul
if errorlevel 1 (
    echo Java was not found. Install JDK 17 or newer and add it to PATH.
    pause
    exit /b 1
)
where mvn >nul 2>nul
if errorlevel 1 (
    echo Maven was not found. Install Maven and add its bin folder to PATH.
    echo You can also import pom.xml in your IDE and run clean package exec:java.
    pause
    exit /b 1
)
call mvn clean package exec:java
set "lab_exit_code=%errorlevel%"
echo.
pause
exit /b %lab_exit_code%
