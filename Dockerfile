FROM gradle:6.9-jdk11-alpine AS build
RUN mkdir /workspace
WORKDIR /workspace
COPY settings.gradle build.gradle ./
COPY src ./src

RUN gradle assemble

WORKDIR /workspace/build/libs
EXPOSE 8080
ENTRYPOINT ["java","-jar","fruitseller-0.0.1-SNAPSHOT.jar"]