FROM maven:3.9-eclipse-temurin-25 AS build
WORKDIR /app
COPY backend/pom.xml .
COPY backend/src ./src
RUN mvn clean package -DskipTests

FROM amazoncorretto:25-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar banco-backend.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "/app/banco-backend.jar"]