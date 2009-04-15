@setlocal
@echo off

rem Should be modified based on local settings
set ANT_HOME=C:\apache-ant-1.6.2

set JAVA_HOME=C:\jdk1.5.0_14
echo java home: %JAVA_HOME%

set ANT_OPTS=-Xmx512m

set PATH=%ANT_HOME%\bin;%JAVA_HOME%/bin;
%ANT_HOME%\bin\ant %*

@echo on
@endlocal