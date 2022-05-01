FROM adoptopenjdk/openjdk11:latest

RUN mkdir -p /app

WORKDIR /app

COPY target/konsign-api-0.0.1-SNAPSHOT.jar /app

CMD ["java", "-jar", "konsign-api-0.0.1-SNAPSHOT.jar"]