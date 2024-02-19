# syntax=docker/dockerfile:1

FROM openjdk:latest
WORKDIR /app
COPY build/libs/server-0.0.1-SNAPSHOT.jar ./
CMD java -jar server-0.0.1-SNAPSHOT.jar