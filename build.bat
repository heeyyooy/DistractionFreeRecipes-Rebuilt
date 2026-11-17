@echo off
call "%~dp0gradlew.bat" clean build %*
exit /b %ERRORLEVEL%
