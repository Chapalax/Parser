FROM openjdk:17
WORKDIR /app/scrapper
COPY scrapper/target/scrapper-1.0-SNAPSHOT.jar scrapper.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "scrapper.jar"]