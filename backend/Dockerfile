FROM openjdk:8-jdk-alpine

COPY ./ /opt/app/

WORKDIR opt/app

RUN ./gradlew clean build

ENTRYPOINT["java", "-jar", "backend-0.0.1-SNAPSHOT.jar"]
