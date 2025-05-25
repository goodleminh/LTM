@echo off
set TOMCAT_VERSION=8.5.34
set TOMCAT_DIR=tomcat
set TOMCAT_ARCHIVE=apache-tomcat-%TOMCAT_VERSION%.zip
set TOMCAT_URL=https://downloads.apache.org/tomcat/tomcat-8/v%TOMCAT_VERSION%/bin/apache-tomcat-%TOMCAT_VERSION%.zip

if not exist %TOMCAT_DIR%\bin\startup.bat (
    echo Downloading Tomcat %TOMCAT_VERSION%...
    powershell -Command "Invoke-WebRequest '%TOMCAT_URL%' -OutFile '%TOMCAT_ARCHIVE%'"
    powershell -Command "Expand-Archive '%TOMCAT_ARCHIVE%' -DestinationPath '.'"
    move apache-tomcat-%TOMCAT_VERSION% %TOMCAT_DIR%
    del %TOMCAT_ARCHIVE%
    echo Tomcat installed to %TOMCAT_DIR%
) else (
    echo Tomcat already exists in %TOMCAT_DIR%
)
 