FROM openjdk:17-slim

WORKDIR /app

VOLUME ["/app/asmetal2java_asmegen/input"]
VOLUME ["/app/asmetal2java_asmegen/output"]

COPY ./asmetal2java_asmgen/target/asmetal2java_asmgen-0.0.1-SNAPSHOT-jar-with-dependencies.jar /app/asmetal2java_asmgen-0.0.1-SNAPSHOT-jar-with-dependencies.jar

ENTRYPOINT ["java", "-jar", "asmetal2java_asmgen-0.0.1-SNAPSHOT-jar-with-dependencies.jar"]
