# State 1
FROM gradle:9.1.0-jdk17 AS builder
WORKDIR /app

COPY --chown=gradle:gradle . /app
RUN gradle bootJar --no-daemon

# State 2
FROM eclipse-temurin:17-jre-alpine-3.22
WORKDIR /app
COPY --from=builder /app/build/libs/demo-0.0.1-SNAPSHOT.jar /app/app.jar
EXPOSE 8080
RUN apk add --no-cache curl

ENTRYPOINT [ "java", "-jar", "app.jar" ]