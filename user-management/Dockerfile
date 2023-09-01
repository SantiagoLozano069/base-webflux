FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
RUN ./mvnw dependency:go-offline -B
COPY src ./src
RUN ./mvnw package
EXPOSE 8080
ENTRYPOINT ["java","-jar","target/user-management-0.0.1-SNAPSHOT.jar"]