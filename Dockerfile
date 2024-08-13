FROM openjdk:17-slim

# Install Maven
RUN apt-get update && apt-get install -y maven

# Working directory for Maven
WORKDIR /app

VOLUME ["/app/asmetal2java_codegen/input"]
VOLUME ["/app/asmetal2java_codegen/output"]

COPY core/ /app/core/
COPY asmetal2java_examples /app/asmetal2java_examples

RUN cd /app/core && mvn install

COPY ./asmetal2java_codegen /app/asmetal2java_codegen

RUN cd /app/asmetal2java_codegen && mvn clean package

# Set the working directory for execution
WORKDIR /app/asmetal2java_codegen

ENTRYPOINT ["java", "-jar", "target/asmetal2java_codegen-0.0.1-SNAPSHOT-jar-with-dependencies.jar"]