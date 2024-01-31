#!/bin/bash

gnome-terminal --tab --title="ApiGatewayService" -- ./gradlew :ApiGatewayService:bootRun -Dspring.profiles.active=local
gnome-terminal --tab --title="AuthMicroService" -- ./gradlew :AuthMicroService:bootRun -Dspring.profiles.active=local
gnome-terminal --tab --title="CardMicroService" -- ./gradlew :CardMicroService:bootRun -Dspring.profiles.active=local
gnome-terminal --tab --title="UserMicroService" -- ./gradlew :UserMicroService:bootRun -Dspring.profiles.active=local
gnome-terminal --tab --title="MailService" -- ./gradlew :MailService:bootRun -Dspring.profiles.active=local
