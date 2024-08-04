# Use the official OpenJDK 17 base image
FROM openjdk:17-jdk

# Set the working directory
WORKDIR /app

# Copy the JAR file into the container
COPY build/libs/*.jar app.jar

# Create the necessary directory for application.yml
RUN mkdir -p /app/src/main/resources

# Define a build argument for application.yml content
ARG APPLICATION_YAML

# Create the application.yml file from the build argument
RUN echo "$APPLICATION_YAML" > /app/src/main/resources/application.yml

# Run the application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
