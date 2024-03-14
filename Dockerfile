# Step 1: Use the official Maven image with Java 17 to build our application
FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

RUN mvn package -DskipTests

FROM openjdk:17-slim
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
