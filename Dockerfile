FROM openjdk:8-jdk-alpine

VOLUME /tmp

EXPOSE 8080/tcp

ARG JAR_FILE=build/libs/webflux-social-network-1.0.0-SNAPSHOT.jar

ADD ${JAR_FILE} app.jar

HEALTHCHECK --interval=1m --timeout=3s CMD curl -f http://localhost:8080/health || exit 1

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]