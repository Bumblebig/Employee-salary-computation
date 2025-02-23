# Use an official OpenJDK runtime as a parent image
FROM eclipse-temurin:17-jdk

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven build output (JAR file) into the container
COPY target/*.jar app.jar

# Expose port 8080 (for local testing)
EXPOSE 8080

# Command to run the Spring Boot app
CMD ["java", "-jar", "app.jar"]
