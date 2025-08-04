@echo off
echo Démarrage du projet Spring Boot...
cd /d %~dp0
call mvn spring-boot:run
pause
