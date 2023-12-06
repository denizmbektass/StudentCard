FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY ApiGatewayService/build/libs/ApiGatewayService-v.0.1.jar /app/gateway
COPY AuthMicroService/build/libs/AuthMicroService-v.0.1.jar /app/auth
COPY CardMicroService/build/libs/CardMicroService-v.0.1.jar /app/card
COPY MailService/build/libs/MailService-v.0.1.jar /app/mail
COPY UserMicroService/build/libs/UserMicroService-v.0.1.jar /app/user

EXPOSE 80:80

CMD ["java", "-jar", "ApiGatewayService-v.0.1.jar"]

