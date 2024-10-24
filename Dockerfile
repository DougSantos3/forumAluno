FROM eclipse-temurin:17-jdk
EXPOSE 8080
ADD /target/forum-0.0.1-SNAPSHOT.jar forum.jar
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -XX:+UseContainerSupport -Xmx300m -Xss512k -XX:CICompilerCount=2 -Dserver.port=$PORT -Dspring.profiles.active=prod -jar forum.jar"]

