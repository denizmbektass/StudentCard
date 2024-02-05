FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY build/libs/AuthMicroService-v.0.1.jar /app

ENV SPRING_PROFILES_ACTIVE=prod
ENV ADMIN_PWD=prod
ENV EMPLOYMENT_PWD=prod
ENV INTERNSHIP_PWD=prod
ENV EDUCATION_PWD=prod
ENV WORKSHOP_PWD=prod

EXPOSE 4040:4040

CMD ["java", "-jar", "AuthMicroService-v.0.1.jar"]

