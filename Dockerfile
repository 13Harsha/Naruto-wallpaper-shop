# Use an official OpenJDK 8 runtime as a parent image
FROM openjdk:8-jdk-alpine

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file and static resources to the container
COPY target/*.jar app.jar
# Add the necessary static resources
COPY /src/main/resources/static/images /app/static/images

# Set permissions for static files to 777
RUN chmod -R 777 /app/static/images

# Expose the application port
EXPOSE 8081

# Run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]