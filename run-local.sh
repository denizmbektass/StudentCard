#!/bin/bash

gnome-terminal -- ./gradlew :ApiGatewayService:bootRun -Dspring.profiles.active=local
gnome-terminal -- ./gradlew :AuthMicroService:bootRun -Dspring.profiles.active=local
gnome-terminal -- ./gradlew :CardMicroService:bootRun -Dspring.profiles.active=local
gnome-terminal -- ./gradlew :UserMicroService:bootRun -Dspring.profiles.active=local
gnome-terminal -- ./gradlew :MailService:bootRun -Dspring.profiles.active=local
