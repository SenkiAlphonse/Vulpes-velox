FROM openjdk:8-slim
WORKDIR /application
COPY build/libs/velox*.jar /application/app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]