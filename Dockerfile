FROM openjdk:21-jdk-slim

WORKDIR /testingdemostracion

COPY target/*.jar app.jar

# Expose the port your Spring Boot app runs on (default 8080)
EXPOSE 8080

# Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]
