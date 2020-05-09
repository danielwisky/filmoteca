FROM adoptopenjdk/openjdk13:ubi
VOLUME /tmp
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "-Dspring.data.mongodb.uri=mongodb://mongodb:27017/filmoteca", "/app.jar"]