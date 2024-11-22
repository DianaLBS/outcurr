FROM maven:3.8.8-eclipse-temurin-17 AS build

WORKDIR /app

COPY . .

RUN mvn -N io.takari:maven:wrapper

RUN chmod +x mvnw

RUN mvn clean install -DskipTests

FROM eclipse-temurin:17-jre

WORKDIR /app

COPY --from=build /app/outcome-curr-mgmt/target/*.jar /app/outcome-curr-mgmt.jar

EXPOSE 9092

CMD ["java", "-jar", "/app/outcurr-app.jar"]