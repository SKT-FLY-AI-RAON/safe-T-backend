# Use the official OpenJDK 17 base image
FROM openjdk:17-jdk

# Set the working directory
WORKDIR /app

# Copy the JAR file into the container
COPY build/libs/*.jar app.jar

# Copy the application.yml configuration file into the container
# Make sure to copy it to the correct location
COPY application.yml /app/src/main/resources/application.yml

# Run the application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
