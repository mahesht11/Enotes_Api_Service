# Use a base image with Java 11
FROM adoptopenjdk:11-jre-hotspot

# Set the working directory
LABEL maintainer ="mahitech"
# Copy the JAR file to the container
COPY target/Enotes_Api_Service-0.0.1-SNAPSHOT.jar Enotes_Api_Service-0.0.1-SNAPSHOT.jar
# Expose the port that your Spring Boot application listens on (default is 8080)
EXPOSE 8088
# Define the command to run your application
CMD ["java", "-jar", "/Enotes_Api_Service-0.0.1-SNAPSHOT.jar"]
