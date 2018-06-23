@echo off

set PROD_PATH=../../../../../http-game-server/server/s0/webapps/demo/WEB-INF/classes
set op=%cd%

echo Updating from SVN...
cd %PROD_PATH%
svn up
cd %op%

echo Current Dir: %op%
echo PROD_PATH:%PROD_PATH%
echo Rsyncing...

rsync -avc --delete --chmod u+rwx ../out/production/http-game-server/* %PROD_PATH%

echo Submitting to SVN...
cd %PROD_PATH%/
svn add ./ --force
svn ci --editor-cmd notepad.exe

cd %op%

pause
:end
