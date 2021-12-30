FROM openjdk:11
VOLUME /tmp
ADD target/*.jar /app.jar
ENTRYPOINT [ "java", "-jar", "/app.jar", "--server.port=8081" ]
