# Asmetal2java
[![Static Badge](https://img.shields.io/badge/asmeta-main_repository-black?style=social&logo=github&link=https%3A%2F%2Fgithub.com%2Fasmeta%2Fasmeta)](https://github.com/asmeta/asmeta)
[![Build and Push CI Pipeline](https://github.com/isaacmaffeis/asmetal2java/actions/workflows/maven-docker-pipeline.yml/badge.svg)](https://github.com/isaacmaffeis/asmetal2java/actions/workflows/maven-docker-pipeline.yml)

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

## Project Overview

This repository contains two executable projects:

- [asmetal2java_codegen](#Asmetal2java_codegen) (Main Project)
- [asmetal2java_asmgen](#Asmetal2java_asmgen) (Extension Project)

# Asmetal2java_codegen

The main project, **asmetal2java_codegen**, is responsible for translating an Abstract State Machine (ASM) into Java code. 
It generates an executable class named <asmFileName>_Exe that allows users to interact with the ASM step by step. 
This feature provides a hands-on approach to work with ASMs, enabling users to manually follow the execution of the state machine at each step.

## How to Start:

### Using Maven
1. Open PowerShell or your preferred terminal and clone the repository:
    ```shell
    git clone https://github.com/isaacmaffeis/asmetal2java.git
    ```

2. Navigate into the project directory:
    ```shell
    cd ./asmetal2java
    ```

3. Install the project and generate the executable JAR:
    ```shell
    mvn clean install
    ```

4. Start the application by running the generated JAR file:
    ```shell
    java -jar ./asmetal2java_codegen/target/asmetal2java_codegen-0.0.1-SNAPSHOT-jar-with-dependencies.jar
     ```

5. Customize execution with additional options:
    ```shell
    java -jar ./asmetal2java_codegen/target/asmetal2java_codegen-0.0.1-SNAPSHOT-jar-with-dependencies.jar -input <input> -output <output> -D<property=value>
    ```
    - `-input` : The ASM input file (required)

    - `-output` : The output folder (optional, defaults to `./output/`)

    - `-D <property=value>` : Additional translator options
      
     | Option             | Argument Type     | Description |
     |--------------------|-------------------|-------------|
     | `-input`           | String (required) | Path to the ASM input file. |
     | `-output`          | String (optional) | Specifies the output folder. Defaults to `./output/`. |  
     | `-Dformatter`      |  boolean (optional)       | whether the generated code should be formatted. |
     | `-DshuffleRandom` | boolean (optional) |  whether a random shuffle should be applied. |
     | `-DoptimizeSeqMacroRule` | boolean (optional) | whether to optimize the sequence macro rule. |


7. Example of a use case:
    ```shell
    java -jar ./asmetal2java_asmgen/target/asmetal2java_codegen-0.0.1-SNAPSHOT-jar-with-dependencies.jar -input "examples/RegistroDiCassa.asm" -output "../asmetal2java_examples/src/" -Dformatter=true
     ```

### Using Docker

1. Open Docker Desktop and pull the Docker image:
    ```shell
    docker pull isaacmaffeis/asmetal2java_codegen:latest
    ```

2. Run the application (print the help message) within a Docker container:
    ```shell
    docker run --rm isaacmaffeis/asmetal2java_codegen
    ```

3. Mount the volumes and run:
    ```shell
    docker run --rm -v "$(pwd)/<input>:/app/input" -v "$(pwd)/<output>:/app/output" isaacmaffeis/asmetal2java_codegen -input <input> -output <output> -D<property=value>
    ```

    - `-v "$(pwd)/<input>:/app/input"` : Maps the input file from the host to the container (required)

    - `-v "$(pwd)/<output>:/app/output"` : Maps the output folder from the host to the container (required)

4. Example of a use case:
   Inside the root directory `./amsetal2java/` run:
    ```shell
    docker run --rm -v "$(pwd)/asmetal2java_codegen/examples:/app/input" -v "$(pwd)/asmetal2java_codegen/output:/app/output" isaacmaffeis/asmetal2java_codegen -input "input/RegistroDiCassa.asm" -output "output"
    ```
# Asmetal2java_asmgen

The extension project, **asmetal2java_asmgen**, extends asmetal2java_codegen by generating a class named <asmFileName>_ASM. 
Unlike the executable class generated by asmetal2java_codegen, which requires user interaction to step through the Abstract State Machine (ASM) execution, 
the _ASM class produced by asmetal2java_asmgen is designed for automated processes without the need for manual interactions.

## How to Start:

### Using Maven
1. Open PowerShell or your preferred terminal and clone the repository:
    ```shell
    git clone https://github.com/isaacmaffeis/asmetal2java.git
    ```

2. Navigate into the project directory:
    ```shell
    cd ./asmetal2java
    ```
    
3. Install the project and generate the executable JAR:
    ```shell
    mvn clean install
    ```

4. Start the application by running the generated JAR file:
    ```shell
    java -jar ./asmetal2java_asmgen/target/asmetal2java_asmgen-0.0.1-SNAPSHOT-jar-with-dependencies.jar
     ```

5. Customize execution with additional options:
    ```shell
    java -jar ./asmetal2java_asmgen/target/asmetal2java_asmgen-0.0.1-SNAPSHOT-jar-with-dependencies.jar -input <input> -output <output> -D<property=value>
    ```
    - `-input` : The ASM input file (required)

    - `-output` : The output folder (optional, defaults to `./output/`)

    - `-D <property=value>` : Additional translator options
      
     | Option             | Argument Type     | Description |
     |--------------------|-------------------|-------------|
     | `-input`           | String (required) | Path to the ASM input file. |
     | `-output`          | String (optional) | Specifies the output folder. Defaults to `./output/`. |  
     | `-Dformatter`      |  boolean (optional)       | whether the generated code should be formatted. |
     | `-DshuffleRandom` | boolean (optional) |  whether a random shuffle should be applied. |
     | `-DoptimizeSeqMacroRule` | boolean (optional) | whether to optimize the sequence macro rule. |
     | `-finalState` | String (optional) | Final state condition of the ASM |

7. Example of a use case:
    ```shell
    java -jar ./asmetal2java_asmgen/target/asmetal2java_asmgen-0.0.1-SNAPSHOT-jar-with-dependencies.jar -input "examples/RegistroDiCassav4.asm" -output "../asmetal2java_examples/src/" -Dformatter=true -finalState state>=5:statoCassa==Stati.CHIUSO
     ```

### Using Docker

1. Open Docker Desktop and pull the Docker image:
    ```shell
    docker pull isaacmaffeis/asmetal2java_asmgen:latest
    ```

2. Run the application (print the help message) within a Docker container:
    ```shell
    docker run --rm isaacmaffeis/asmetal2java_asmgen
    ```

3. Mount the volumes and run:
    ```shell
    docker run --rm -v "$(pwd)/<input>:/app/input" -v "$(pwd)/<output>:/app/output" isaacmaffeis/asmetal2java_asmgen -input <input> -output <output> -D<property=value>
    ```

    - `-v "$(pwd)/<input>:/app/input"` : Maps the input file from the host to the container (required)

    - `-v "$(pwd)/<output>:/app/output"` : Maps the output folder from the host to the container (required)

4. Example of a use case:
   Inside the root directory `./amsetal2java/` run:
    ```shell
    docker run --rm -v "$(pwd)/asmetal2java_asmgen/examples:/app/input" -v "$(pwd)/asmetal2java_asmgen/output:/app/output" isaacmaffeis/asmetal2java_asmgen -input "input/RegistroDiCassav4.asm" -output "output"
    ```
