FROM eclipse-temurin:17-jdk-alpine as builder

RUN mkdir /src

WORKDIR /src
COPY . .
RUN ./gradlew clean build

# ----------------------------------------------

FROM eclipse-temurin:17-jre-alpine as pod

ENV PORT=8080
ENV PROFILE=dev

RUN adduser -D nonroot && mkdir -p /home/nonroot/app

USER nonroot

WORKDIR /home/nonroot/app

COPY --from=builder /src/target/konsign-api-0.0.1-SNAPSHOT.jar /home/nonroot/app

CMD ["java", "-Dspring.profiles.actives=dev", "-jar", "konsign-api-0.0.1-SNAPSHOT.jar"]