FROM eclipse-temurin:17-jdk-alpine as builder

RUN mkdir /src

WORKDIR /src
COPY . .
RUN ./mvnw clean package

# ----------------------------------------------

FROM eclipse-temurin:17-jre-alpine as pod

RUN adduser -D nonroot

USER nonroot

RUN mkdir -p /home/nonroot/app

WORKDIR /home/nonroot/app

COPY --from=builder /src/target/konsign-api-0.0.1-SNAPSHOT.jar /home/nonroot/app

CMD ["java", "-Dspring.profiles.active=dev", "-jar", "konsign-api-0.0.1-SNAPSHOT.jar"]