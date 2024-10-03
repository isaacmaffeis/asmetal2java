FROM openjdk:17-slim

WORKDIR /app

RUN mkdir -p /app/input
RUN mkdir -p /app/output

VOLUME ["/app/asmetal2java_asmegen/input"]
VOLUME ["/app/asmetal2java_asmegen/output"]

ARG JAR_FILE
COPY ${JAR_FILE} /app/asmetal2java_asmgen-0.0.1-SNAPSHOT-jar-with-dependencies.jar

ENTRYPOINT ["java", "-jar", "app/asmetal2java_asmgen-0.0.1-SNAPSHOT-jar-with-dependencies.jar"]
