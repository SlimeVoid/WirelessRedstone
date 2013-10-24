@echo off

set programdir=%CD%\..\..
set packagedir=%programdir%\Packages
set repodir=%programdir%\Git
set forgedir=%programdir%\Forge
set mcpdir=%forgedir%\mcp
cd %mcpdir%
set wirelessredstone=%repodir%\WirelessRedstone-FML
set eastereggs=%wirelessredstone%\EasterEggs

if not exist %wirelessredstone% GOTO :WRFAIL
if exist %eastereggs% GOTO :WIRE
GOTO :WRFAIL

:WIRE
if exist %mcpdir%\src GOTO :COPYSRC
GOTO :WRFAIL

:COPYSRC
if not exist "%mcpdir%\src-work" GOTO :CREATESRC
GOTO :WRFAIL

:CREATESRC
mkdir "%mcpdir%\src-work"
xcopy "%mcpdir%\src\*.*" "%mcpdir%\src-work\" /S
if exist "%mcpdir%\src-work" GOTO :COPYWR
GOTO :WRFAIL

:COPYWR
xcopy "%wirelessredstone%\WR-common\*.*" "%mcpdir%\src\minecraft" /S
xcopy "%wirelessredstone%\WR-client\*.*" "%mcpdir%\src\minecraft" /S
xcopy "%eastereggs%\HNY-common\*.*" "%mcpdir%\src\minecraft" /S
pause
call %mcpdir%\recompile.bat
call %mcpdir%\reobfuscate.bat
pause
ren "%mcpdir%\src" src-old
echo Recompiled Source folder renamed
pause
ren "%mcpdir%\src-work" src
echo Original Source folder restored
pause
del "%mcpdir%\src-old" /S /Q
echo Recompiled Source folder removed
if exist "%mcpdir%\src-old" rmdir "%mcpdir%\src-old" /S /Q
GOTO :WRCOMPLETE

:WRFAIL
echo Could not compile Wireless Redstone - Remote
pause
GOTO :EOF

:WRCOMPLETE
echo Wireless Redstone - Remote completed compile successfully
pause
GOTO :EOF

:EOF