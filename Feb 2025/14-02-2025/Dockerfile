# Use OpenJDK as the base image
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy your Java file to the container
COPY EmpManagementApp.java /app/

# Copy the PostgreSQL JDBC driver into the container
COPY postgresql-42.7.5.jar /app/postgresql.jar

# Compile the Java file
RUN javac -d . EmpManagementApp.java

# Expose the application port (change if needed)
EXPOSE 8080

# Run the application
CMD ["java", "-cp", "/app:/app/postgresql.jar", "EmpManagementApp"]
