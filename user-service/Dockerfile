# Use Java 17 image
FROM eclipse-temurin:17

# Set container working directory
WORKDIR /app

# Copy generated jar file
COPY target/user-service-0.0.1-SNAPSHOT.jar app.jar

# Expose Spring Boot port
EXPOSE 8081

# Start Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]