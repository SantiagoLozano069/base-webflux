FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
RUN ./mvnw dependency:go-offline -B
COPY src ./src
RUN ./mvnw package
EXPOSE 8081
ENTRYPOINT ["java","-jar","target/security-gateway-0.0.1-SNAPSHOT.jar"]