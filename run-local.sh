#!/bin/bash

# Function to execute a command in a tab
run_command_in_tab() {
    gnome-terminal --tab --title="$1" -- bash -c "$2; echo Press Enter to close this tab; read"
}

# Run commands in tabs
run_command_in_tab "ApiGatewayService" "./gradlew --warning-mode=all --stacktrace :ApiGatewayService:bootRun -Dspring.profiles.active=local"
run_command_in_tab "AuthMicroService" "./gradlew --warning-mode=all --stacktrace :AuthMicroService:bootRun -Dspring.profiles.active=local"
run_command_in_tab "CardMicroService" "./gradlew --warning-mode=all --stacktrace :CardMicroService:bootRun -Dspring.profiles.active=local"
run_command_in_tab "UserMicroService" "./gradlew --warning-mode=all --stacktrace :UserMicroService:bootRun -Dspring.profiles.active=local"
run_command_in_tab "MailService" "./gradlew --warning-mode=all --stacktrace :MailService:bootRun -Dspring.profiles.active=local"
