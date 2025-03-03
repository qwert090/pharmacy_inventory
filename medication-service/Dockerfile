# medication-service/Dockerfile
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/medication-service-0.0.1-SNAPSHOT.jar medication-service.jar
ENTRYPOINT ["java", "-jar", "/medication-service.jar"]