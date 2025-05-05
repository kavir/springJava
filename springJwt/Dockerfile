# -------- Stage 1: Build the Spring Boot App --------
    FROM maven:3.8.5-openjdk-17 AS builder

    WORKDIR /app
    COPY . .
    
    RUN mvn clean package -DskipTests
    
    # -------- Stage 2: Run the App --------
    FROM openjdk:17-jdk-slim
    
    WORKDIR /app
    COPY --from=builder /app/target/springJwt-0.0.1-SNAPSHOT.jar app.jar
    
    # Optional: confirm JAR is copied correctly
    RUN ls -l /app
    
    EXPOSE 8080
    
    CMD ["java", "-jar", "app.jar"]
    
    

# # Use an official Java runtime as a base image
# FROM openjdk:17-jdk-slim

# # Set the working directory inside the container
# WORKDIR /app

# # Copy the JAR file into the container
# COPY target/springJwt-0.0.1-SNAPSHOT.jar app.jar

# # Expose port 8080 to allow external access
# EXPOSE 8080

# # Command to run the Spring Boot application
# CMD ["java", "-jar", "app.jar"]
