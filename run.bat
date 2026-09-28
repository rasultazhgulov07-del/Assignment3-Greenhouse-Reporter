@echo off
mvn -q -DskipTests package
java -cp target\greenhouse-reporter-1.0.0.jar kz.edu.greenhouse.App
pause
