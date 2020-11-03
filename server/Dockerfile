FROM openjdk:14-alpine

ARG CLIENT_URL
ENV CLIENT_URL $CLIENT_URL

COPY build/libs /server/
WORKDIR /server

RUN cp /server/server-*-all.jar /server.jar

EXPOSE 8080
CMD ["java", "-Dcom.sun.management.jmxremote", "-Xmx128m", "-jar", "/server.jar"]
