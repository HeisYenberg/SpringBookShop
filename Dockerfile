FROM openjdk:8-jdk-alpine
WORKDIR /app
COPY target/SpringBookShop-1.0-SNAPSHOT.jar app.jar
COPY public/logo.png public/logo.png
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
