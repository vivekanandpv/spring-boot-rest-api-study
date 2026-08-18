# syntax=docker/dockerfile:1

# Build and test the application with Maven; the dependency cache survives rebuilds.
FROM maven:3.9-eclipse-temurin-25-alpine AS build

WORKDIR /build

COPY pom.xml .
RUN --mount=type=cache,target=/root/.m2 \
    mvn --batch-mode dependency:go-offline

COPY src ./src
RUN --mount=type=cache,target=/root/.m2 \
    mvn --batch-mode package

# Run the packaged application from a smaller JRE-only image as an unprivileged user.
FROM eclipse-temurin:25-jre-alpine AS runtime

WORKDIR /app

RUN addgroup -S app && adduser -S -G app app

COPY --from=build --chown=app:app /build/target/*.jar app.jar

EXPOSE 8080

USER app

ENTRYPOINT ["java", "-XX:MaxRAMPercentage=75.0", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]
