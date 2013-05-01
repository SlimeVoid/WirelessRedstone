@echo off

set mcpdir="C:\Programming\Repositories\MinecraftForge\mcp"
cd %mcpdir%
set repodir="C:\Programming\Repositories"
set slimevoidlib="%repodir%\EurysCore-FML"
set wirelessredstone="%repodir%\WirelessRedstone-FML"
set slimevoid="%wirelessredstone%\addons\slimevoid"

if not exist %wirelessredstone% GOTO :WRFAIL
if exist %slimevoid% GOTO :WIRE
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
xcopy "%slimevoidlib%\SV-common\*.*" "%mcpdir%\src\minecraft" /S
xcopy "%slimevoid%\SlimeVoid-common\*.*" "%mcpdir%\src\minecraft" /S
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
echo Could not compile Wireless Redstone - SlimeVoid
pause
GOTO :EOF

:WRCOMPLETE
echo Wireless Redstone - SlimeVoid completed compile successfully
pause
GOTO :EOF

:EOF