FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY build/libs/ApiGatewayService-v.0.1.jar /app

EXPOSE 80:80

CMD ["java", "-jar", "ApiGatewayService-v.0.1.jar"]

