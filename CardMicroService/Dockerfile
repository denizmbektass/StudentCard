FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY build/libs/CardMicroService-v.0.1.jar /app

ENV SPRING_PROFILES_ACTIVE=prod

EXPOSE 4042:4042

CMD ["java", "-jar", "CardMicroService-v.0.1.jar"]

