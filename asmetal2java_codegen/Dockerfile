FROM openjdk:17-alpine

WORKDIR /app

RUN mkdir -p /app/input
RUN mkdir -p /app/output

VOLUME ["/app/input"]
VOLUME ["/app/output"]

COPY ./target/asmeta.asmetal2java_codegen-0.0.1-SNAPSHOT-jar-with-dependencies.jar /app/asmeta.asmetal2java_codegen-0.0.1-SNAPSHOT-jar-with-dependencies.jar

ENTRYPOINT ["java", "-jar", "asmeta.asmetal2java_codegen-0.0.1-SNAPSHOT-jar-with-dependencies.jar"]
