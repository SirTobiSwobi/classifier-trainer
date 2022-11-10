FROM maven:3.8.6-eclipse-temurin-11 AS build
ENV version=1.0.8
MAINTAINER Tobias Eljasik-Swoboda ${version}
COPY src /home/app/src
COPY pom.xml /home/app
COPY classifier-trainer.yml /home/app/classifier-trainer.yml
RUN mvn -f /home/app/pom.xml clean package

FROM openjdk:20
ENV version=1.0.8
COPY --from=build /home/app/target/classifier-trainer-${version}-SNAPSHOT.jar /usr/local/lib/classifier.jar
COPY --from=build /home/app/classifier-trainer.yml /usr/local/lib/classifier-trainer.yml
EXPOSE 8080
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "/usr/local/lib/classifier.jar", "server", "/usr/local/lib/classifier-trainer.yml"]
