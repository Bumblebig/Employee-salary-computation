# Use an official OpenJDK runtime as a parent image
# FROM eclipse-temurin:17-jdk
FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN ./mvn clean package -DskipTests

FROM openjdk:17.0.1-jdk-slim
COPY --from=build /target/employee-salary-0.0.1-SNAPSHOT.jar employee-salary.jar
EXPOSE 8080
ENTRYPOINT ("java", "-jar", "employee-salary.jar")

# # Set the working directory inside the container
# WORKDIR /app

# # Copy all files into the container
# COPY . .

# # Ensure the Maven wrapper is executable
# RUN chmod +x ./mvnw

# # Build the application inside the container, skipping tests

# # Expose port 8080 (for local testing)
# EXPOSE 8080

# # Run the Spring Boot application
# CMD ["java", "-jar", "target/employee-salary-0.0.1-SNAPSHOT.jar"]