@echo off
start cmd /k ".\gradlew.bat :ApiGatewayService:bootRun -Dspring.profiles.active=local"
start cmd /k ".\gradlew.bat :AuthMicroService:bootRun -Dspring.profiles.active=local"
start cmd /k ".\gradlew.bat :CardMicroService:bootRun -Dspring.profiles.active=local"
start cmd /k ".\gradlew.bat :UserMicroService:bootRun -Dspring.profiles.active=local"
start cmd /k ".\gradlew.bat :MailService:bootRun -Dspring.profiles.active=local"
