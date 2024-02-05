FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY build/libs/ApiGatewayService-v.0.1.jar /app

ENV SPRING_PROFILES_ACTIVE=prod
ENV KEYSTORE_JKS_PATH=/cert/keystore.jks
ENV KEYSTORE_PASSWORD=123456

EXPOSE 443:443

CMD ["java", "-jar", "ApiGatewayService-v.0.1.jar"]

