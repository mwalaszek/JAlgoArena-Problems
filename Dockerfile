FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD /build/libs/jalgoarena-problems-1.0.36.jar app.jar
EXPOSE 5002
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=dev","-jar","/app.jar"]