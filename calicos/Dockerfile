FROM openjdk:22-jdk-slim
MAINTAINER github.com/nitin-rajesh
COPY target/calicos-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 6789