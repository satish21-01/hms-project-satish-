# Base image
FROM eclipse-temurin:17-jdk

# Jar copy
COPY target/user-service-0.0.1-SNAPSHOT.jar app.jar

# Port expose
EXPOSE 8081

# Run app
ENTRYPOINT ["java","-jar","/app.jar"]