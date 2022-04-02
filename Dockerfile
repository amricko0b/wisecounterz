FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG VERSION
COPY build/libs/wisecounterz-${VERSION}.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]