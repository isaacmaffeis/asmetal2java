FROM openjdk:17-slim

WORKDIR /app

RUN mkdir -p /app/input
RUN mkdir -p /app/output

VOLUME ["/app/input"]
VOLUME ["/app/output"]

COPY ./target/asmetal2java_asmgen-0.0.1-SNAPSHOT-jar-with-dependencies.jar /app/asmetal2java_asmgen-0.0.1-SNAPSHOT-jar-with-dependencies.jar

ENTRYPOINT ["java", "-jar", "asmetal2java_asmgen-0.0.1-SNAPSHOT-jar-with-dependencies.jar"]
