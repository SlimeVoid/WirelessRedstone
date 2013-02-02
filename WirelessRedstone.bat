@echo off

set mcpdir="C:\Programming\mcp"
cd %mcpdir%
set repodir="C:\Programming\Repositories"
set wirelessredstone="%repodir%\WirelessRedstone-FML"

if exist %wirelessredstone% GOTO :WIRE
GOTO :WRFAIL

:WIRE
if exist %mcpdir%\src GOTO :COPYSRC
GOTO :WRFAIL

:COPYSRC
if not exist "%mcpdir%\src-work" GOTO :CREATESRC
GOTO :WRFAIL

:CREATESRC
mkdir "%mcpdir%\src-copy"
xcopy "%mcpdir%\src\*.*" "%mcpdir%\src-copy\" /S
if exist "%mcpdir%\src-copy" GOTO :COPYWR
GOTO :WRFAIL

:COPYWR
xcopy "%wirelessredstone%\WR-common\*.*" "%mcpdir%\src\minecraft" /S
xcopy "%wirelessredstone%\WR-client\*.*" "%mcpdir%\src\minecraft" /S
pause
call %mcpdir%\recompile.bat
call %mcpdir%\reobfuscate.bat
pause
ren "%mcpdir%\src" src-old
echo Recompiled Source folder renamed
pause
ren "%mcpdir%\src-copy" src
echo Original Source folder restored
pause
del "%mcpdir%\src-old" /S /Q
echo Recompiled Source folder removed
if exist "%mcpdir%\src-old" rmdir "%mcpdir%\src-old" /S /Q
GOTO :WRCOMPLETE

:WRFAIL
echo Could not compile Wireless Redstone
pause
GOTO :EOF

:WRCOMPLETE
echo Wireless Redstone completed compile successfully
pause
GOTO :EOF

:EOF