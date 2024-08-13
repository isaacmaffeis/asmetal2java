# Asmetal2java

[![Maven CI](https://github.com/isaacmaffeis/asmetal2java/actions/workflows/maven.yml/badge.svg)](https://github.com/isaacmaffeis/asmetal2java/actions/workflows/maven.yml)
[![Docker CI](https://github.com/isaacmaffeis/asmetal2java/actions/workflows/docker-image.yml/badge.svg)](https://github.com/isaacmaffeis/asmetal2java/actions/workflows/docker-image.yml)

Asmetal2java is a tool that automatically generates a Java file from an Abstract State Machine (ASM)
specification written in Asmeta. This allows developers and researchers to quickly convert ASM 
models into Java code, facilitating the integration of formal specifications into software 
development processes.

## Origins and Contributions
This project was originally developed as part of the Asmeta repository.
The majority of the work, including the core functionalities and foundational code,
was done by the Asmeta team.
You can find the original project and more information about their work in the:
[asmetal2java](https://github.com/asmeta/asmeta/tree/master/code/experimental/asmetal2java).


## Asmeta Framework
Asmetal2java is part of the larger Asmeta (ASMETA: Abstract State Machines Environment for 
Modeling and Analysis) framework, which is an environment for the formal modeling and analysis of
systems using Abstract State Machines (ASMs).

The [Asmeta](https://github.com/asmeta/asmeta/tree/master) framework provides a comprehensive suite
of tools for creating, simulating, verifying, and analyzing ASM models.


## How to Start:

### Cloning the Repository
1. Open PowerShell or your preferred terminal and clone the repository:
    ```shell
    git clone https://github.com/isaacmaffeis/asmetal2java.git
    ```

2. Navigate into the project directory:
    ```shell
    cd ./asmetal2java
    ```

### Using Maven

3. Package the core projects to compile the necessary components:
    ```shell
    cd ./core
    mvn clean package
    ```

4. Generate and install the executable JAR file with all dependencies included:
     ```shell
     cd ../asmetal2java_codegen
     mvn clean install
     ```

5. Start the application by running the generated JAR file:
    ```shell
    java -jar ./target/asmetal2java_codegen-0.0.1-SNAPSHOT-jar-with-dependencies.jar
     ```

6. Customize execution with additional options:
    ```shell
    java -jar ./target/asmetal2java_codegen-0.0.1-SNAPSHOT-jar-with-dependencies.jar -input <input> -output <output> -D<property=value>
    ```
    - `-input` : The ASM input file (required)

    - `-output` : The output folder (optional, defaults to `./output/`)

    - `-D <property=value>` : Additional properties (optional, see `-help` for more info)


7. Example of a use case:
    ```shell
    java -jar ./target/asmetal2java_codegen-0.0.1-SNAPSHOT-jar-with-dependencies.jar -input "examples/RegistroDiCassa.asm" -output "../asmetal2java_examples/src/" -Dformatter=true
     ```

### Using Docker

3. Open Docker Desktop and build the Docker image:
    ```shell
    docker build -t asmetal2java .
    ```

4. Run the application within a Docker container:
    ```shell
    docker run --rm asmetal2java
    ```

5. Customize execution by passing options:
    ```shell
    docker run --rm -v "$(pwd)/<input>:/app/<input>" -v "$(pwd)/asmetal2java_codegen/<output>:/app/asmetal2java_codegen/<output>" asmetal2java -input <input> -output <output> -D<property=value>
    ```

    - `-v "$(pwd)/<input>:/app/<input>"` : Maps the input file from the host to the container (required)

    - `-v "$(pwd)/<output>:/app/asmetal2java_codegen/<output>"` : Maps the output folder from the host to the container (required, use `<output>` as `output` for the default path)

    - `-input` : The ASM input file (required)

    - `-output` : Output folder (optional, defaults to `./output/`)

    - `-D <property=value>` : Additional properties (optional, see `-help` for more info)

6. Example of a use case:
    ```shell
    docker run --rm -v "$(pwd)/asmetal2java_codegen/examples:/app/asmetal2java_codegen/examples" -v "$(pwd)/asmetal2java_codegen/output:/app/asmetal2java_codegen/output" asmetal2java -input "examples/RegistroDiCassa.asm" -output "output"
    ```
