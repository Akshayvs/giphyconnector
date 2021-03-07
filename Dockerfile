FROM openjdk:14
ADD target/SoFi-giphy-connector.jar SoFi-giphy-connector.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "SoFi-giphy-connector.jar"]