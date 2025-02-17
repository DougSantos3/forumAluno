FROM maven:3.9.9-eclipse-temurin-21 AS builder
WORKDIR /app
COPY . /app
RUN mvn clean package -DskipTests
FROM  eclipse-temurin:21-jre AS runner
WORKDIR /app
COPY --from=builder /app/target/forum-0.0.1-SNAPSHOT.jar /app/forum.jar
CMD ["java", "-Dspring.profiles.active=dev", "-jar", "/app/forum.jar"]


