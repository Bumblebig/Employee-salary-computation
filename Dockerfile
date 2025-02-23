# Use an official Java runtime as a parent image
FROM eclipse-temurin:17-jdk

# Set the working directory in the container
WORKDIR /app

# Copy Maven wrapper and source code
COPY . .

# Build the application inside the container
RUN ./mvnw clean package -DskipTests

# Copy the built JAR file to the final container setup
CMD ["java", "-jar", "target/*.jar"]