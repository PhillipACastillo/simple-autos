#FROM adoptopenjdk/openjdk11:alpine-jre
#
#WORKDIR /app
#
#COPY . .
#
#RUN ./gradlew build -v --stacktrace
#
#EXPOSE 8080
#
## /app/gradle/wrapper/gradle-wrapper.jar
#
#CMD java -jar $(find /app -type f -name "*.jar")

FROM openjdk:8-jdk-alpine
WORKDIR /app
COPY build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
EXPOSE 8080
