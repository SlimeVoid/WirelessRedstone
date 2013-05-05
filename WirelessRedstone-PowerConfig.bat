rem @echo off

set programdir="C:\Programming"
set packagedir="%programdir%\Packages"
set repodir="%programdir%\Repositories"
set forgedir="%repodir%\MinecraftForge"
set mcpdir="%forgedir%\mcp"
cd %mcpdir%
set wirelessredstone="%repodir%\WirelessRedstone-FML"
set powerconfig="%wirelessredstone%\addons\powerconfig"

if not exist %wirelessredstone% GOTO :WRFAIL
if exist %powerconfig% GOTO :WIRE
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
xcopy "%powerconfig%\PowerConfig-common\*.*" "%mcpdir%\src\minecraft" /S
xcopy "%powerconfig%\PowerConfig-client\*.*" "%mcpdir%\src\minecraft" /S
pause
call %mcpdir%\recompile.bat
call %mcpdir%\reobfuscate.bat
echo Recompile and Reobf Completed Successfully
pause

:REPACKAGE
if not exist "%mcpdir%\reobf" GOTO :WRFAIL
if exist "%packagedir%\WirelessRedstone-PowerConfig" (
del "%packagedir%\WirelessRedstone-PowerConfig\*.*" /S /Q
rmdir "%packagedir%\WirelessRedstone-PowerConfig" /S /Q
)
mkdir "%packagedir%\WirelessRedstone-PowerConfig\wirelessredstone\addon"
xcopy "%mcpdir%\reobf\minecraft\wirelessredstone\addon\*.*" "%packagedir%\WirelessRedstone-PowerConfig\wirelessredstone\addon\" /S
xcopy "%powerconfig%\PowerConfig-Resources\*.*" "%packagedir%\WirelessRedstone-PowerConfig\" /S
echo "Power Configurator Packaged Successfully
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
echo Could not compile Wireless Redstone - PowerConfig
pause
GOTO :EOF

:WRCOMPLETE
echo Wireless Redstone - PowerConfig completed compile successfully
pause
GOTO :EOF

:EOF