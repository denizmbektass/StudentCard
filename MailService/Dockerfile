FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY build/libs/MailService-v.0.1.jar /app

EXPOSE 4043:4043

CMD ["java", "-jar", "MailService-v.0.1.jar"]

