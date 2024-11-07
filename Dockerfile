# Use a base image with Java 11
FROM adoptopenjdk:11-jre-hotspot

# Set the working directory
WORKDIR /app
# Copy the JAR file to the container
COPY target/Enotes_Api_Service-0.0.1-SNAPSHOT.jar app.jar
# Expose the port that your Spring Boot application listens on (default is 8080)
EXPOSE 8088
# Define the command to run your application
CMD ["java", "-jar", "app.jar"]