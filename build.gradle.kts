plugins {
    id("org.springframework.boot") version "2.7.4"
    id("io.spring.dependency-management") version "1.1.6"
    kotlin("jvm") version "1.9.24" apply false // remove if not using Kotlin code
    id("java")
}

group = "com.razdeep"
version = "0.0.1-SNAPSHOT"
description = "konsign-api"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("io.jsonwebtoken:jjwt:0.9.1")
    implementation("javax.xml.bind:jaxb-api:2.4.0-b180830.0359")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("mysql:mysql-connector-java:8.0.30")
    implementation("com.google.code.gson:gson:2.10")

    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    implementation("org.mapstruct:mapstruct:1.5.3.Final")
    annotationProcessor("org.mapstruct:mapstruct-processor:1.5.3.Final")

    implementation("org.springframework.boot:spring-boot-starter-actuator:3.0.1")
    implementation("org.springdoc:springdoc-openapi-ui:1.6.14")

    implementation("org.springframework.boot:spring-boot-starter-data-redis:3.0.6")
    implementation("redis.clients:jedis:3.9.0")
    implementation("org.springframework.boot:spring-boot-starter-cache:2.4.3")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.15.0")

    runtimeOnly("io.micrometer:micrometer-registry-prometheus")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.testcontainers:testcontainers:1.17.6")
    testImplementation("org.testcontainers:mysql:1.17.6")
}

tasks.test {
    useJUnitPlatform()
}
