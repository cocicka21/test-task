FROM maven:latest AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM openjdk:21-jdk-slim
WORKDIR /app
COPY --from=build /app/target/testtask*.jar /app/testtask.jar
EXPOSE 8090
ENTRYPOINT ["java", "-jar", "/app/testtask.jar"]