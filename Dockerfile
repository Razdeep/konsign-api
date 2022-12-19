FROM eclipse-temurin:17-jdk-alpine as builder

RUN mkdir /src

WORKDIR /src

COPY . .

RUN ./mvnw clean package

FROM eclipse-temurin:17-jdk-alpine

RUN mkdir -p /app

WORKDIR /app

COPY --from=builder /src/target/konsign-api-0.0.1-SNAPSHOT.jar /app

CMD ["java", "-Dspring.profiles.active=dev", "-jar", "konsign-api-0.0.1-SNAPSHOT.jar"]