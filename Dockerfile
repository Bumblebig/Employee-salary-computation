# Use an official OpenJDK runtime as a parent image
FROM eclipse-temurin:17-jdk

# Set the working directory inside the container
WORKDIR /app

# Copy all files into the container
COPY . .

# Ensure the Maven wrapper is executable
RUN chmod +x ./mvnw

# Build the application inside the container, skipping tests
RUN ./mvnw clean package -DskipTests

# Expose port 8080 (for local testing)
EXPOSE 8080

# Run the Spring Boot application
CMD ["java", "-jar", "target/employee-salary-0.0.1-SNAPSHOT.jar"]