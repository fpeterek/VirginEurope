FROM openjdk:13

MAINTAINER fpeterek@seznam.cz

WORKDIR /fpeterek/virgineurope/

COPY build.gradle gradle.properties gradlew settings.gradle ./
COPY src/ src/
COPY gradle/ gradle/

RUN pwd
RUN ls
RUN ls gradle
RUN ls src
RUN ./gradlew build
RUN mv build/libs/VirginEurope-1.0.jar virgineurope.jar

ENTRYPOINT java -jar ./virgineurope.jar
