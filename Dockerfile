FROM openjdk:13-alpine

VOLUME /tmp

WORKDIR /

COPY build/libs/Autos-API-0.0.1-SNAPSHOT.jar springweb.jar

CMD java -jar springweb.jar
