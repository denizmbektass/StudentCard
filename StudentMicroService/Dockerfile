FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY build/libs/UserMicroService-v.0.1.jar /app

ENV SPRING_PROFILES_ACTIVE=prod

EXPOSE 4041:4041

CMD ["java", "-jar", "UserMicroService-v.0.1.jar"]

