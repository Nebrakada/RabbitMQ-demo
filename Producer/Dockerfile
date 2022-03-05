FROM openjdk:11
WORKDIR ./app
EXPOSE 8081
ADD ./target/app.jar app.jar
ENTRYPOINT ["java", "-jar", "--enable-preview", "app.jar"]
