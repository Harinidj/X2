# Use OpenJDK 17 (Eclipse Temurin) as base image
FROM eclipse-temurin:17-jdk

# Set working directory
WORKDIR /app

# Copy jar file from target folder
COPY target/*.jar app.jar

# Expose Spring Boot default port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
