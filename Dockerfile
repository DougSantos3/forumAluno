FROM eclipse-temurin:17-jdk
EXPOSE 8080
COPY target/forum-0.0.1-SNAPSHOT.jar forum.jar
ENTRYPOINT ["sh", "-c", "java -jar forum.jar --spring.profiles.active=dev"]
