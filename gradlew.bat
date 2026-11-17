@echo off
setlocal EnableExtensions

rem Lightweight Windows bootstrapper for the project's pinned Gradle version.
rem It downloads Gradle into the user's .gradle cache; no system-wide install is needed.
set "GRADLE_VERSION=9.2.1"
set "GRADLE_CACHE=%USERPROFILE%\.gradle\bootstrap"
set "GRADLE_HOME=%GRADLE_CACHE%\gradle-%GRADLE_VERSION%"
set "GRADLE_EXE=%GRADLE_HOME%\bin\gradle.bat"

if not exist "%GRADLE_EXE%" (
    echo [DFR] Gradle %GRADLE_VERSION% was not found. Downloading the official distribution...
    if not exist "%GRADLE_CACHE%" mkdir "%GRADLE_CACHE%"
    if errorlevel 1 exit /b 1

    powershell.exe -NoLogo -NoProfile -ExecutionPolicy Bypass -Command ^
      "$ErrorActionPreference='Stop'; $version='9.2.1'; $root=Join-Path $env:USERPROFILE '.gradle\bootstrap'; $archive=Join-Path $root ('gradle-' + $version + '-bin.zip'); $url='https://services.gradle.org/distributions/gradle-' + $version + '-bin.zip'; $expected='72f44c9f8ebcb1af43838f45ee5c4aa9c5444898b3468ab3f4af7b6076c5bc3f'; if (Test-Path $archive) { Remove-Item -LiteralPath $archive -Force }; Invoke-WebRequest -Uri $url -OutFile $archive; $actual=(Get-FileHash -LiteralPath $archive -Algorithm SHA256).Hash.ToLowerInvariant(); if ($actual -ne $expected) { Remove-Item -LiteralPath $archive -Force; throw ('Gradle archive SHA-256 mismatch. Expected ' + $expected + ', received ' + $actual) }; Expand-Archive -LiteralPath $archive -DestinationPath $root -Force;"
    if errorlevel 1 (
        echo [DFR] Gradle bootstrap failed. Check your internet connection and try again.
        exit /b 1
    )
)

if not exist "%GRADLE_EXE%" (
    echo [DFR] Gradle executable was not created: "%GRADLE_EXE%"
    exit /b 1
)

call "%GRADLE_EXE%" %*
exit /b %ERRORLEVEL%
