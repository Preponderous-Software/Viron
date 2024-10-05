FROM ubuntu:20.04

# Install Java 21 and Maven
RUN apt-get update
RUN apt-get install -y openjdk-21-jdk
RUN apt-get install -y maven

# Set the working directory
WORKDIR /app

# Copy src and pom.xml to the container
COPY src /app/src
COPY pom.xml /app

# Build the project
RUN mvn clean package

# Run the jar file
CMD ["java", "-jar", "target/viron-0.4.0-SNAPSHOT.jar"]