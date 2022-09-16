FROM openjdk:18
MAINTAINER johnson-matoke
EXPOSE 8080
COPY target/KenyaCountiesDatasetApi-0.0.1-SNAPSHOT.jar springboot-docker-kenya-counties-dataset-api.jar
ENTRYPOINT ["java","-jar","springboot-docker-kenya-counties-dataset-api.jar"]