# Use Amazon Corretto 21 as base image
FROM amazoncorretto:21

# Set working directory
WORKDIR /app

# Copy Gradle Wrapper and build files
COPY gradlew build.gradle settings.gradle /app/
COPY gradle /app/gradle

# Copy application source code
COPY src /app/src

# Give permission to gradlew
RUN chmod +x ./gradlew

# Build the project (skip tests)
RUN ./gradlew build -x test --no-daemon

# Set the JAR file name (Change name if needed)
ARG JAR_FILE=build/libs/profile_service-0.0.1-SNAPSHOT.jar

# Optional: Check JAR file exists
RUN ls -l $JAR_FILE

# Expose application port
EXPOSE 8083

# Run the Spring Boot application
CMD ["java", "-jar", "build/libs/profile_service-0.0.1-SNAPSHOT.jar"]

