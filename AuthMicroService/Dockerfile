FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY build/libs/AuthMicroService-v.0.1.jar /app

ENV SPRING_PROFILES_ACTIVE=prod

EXPOSE 4040:4040

CMD ["java", "-jar", "AuthMicroService-v.0.1.jar"]

