FROM java
ENV version=1.0.2
MAINTAINER Tobias Eljasik-Swoboda ${version}
EXPOSE 8080/tcp
EXPOSE 8081/tcp
ADD ./target/classifier-trainer-${version}-SNAPSHOT.jar /opt/classifier-trainer/target/classifier-trainer-${version}-SNAPSHOT.jar
ADD ./classifier-trainer.yml /opt/classifier-trainer/target/classifier-trainer.yml
RUN java -jar /opt/classifier-trainer/target/classifier-trainer-${version}-SNAPSHOT.jar server /opt/classifier-trainer/target/classifier-trainer.yml