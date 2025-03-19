# SPE Assignment

Nitin Kumar Rajesh:: _MT2024103_

Github link: <https://github.com/nitin-rajesh/CalcOps>

## Module 1: Calculator webpage

The calculator has a UI built with HTML/CSS and uses JavaScript to compute. It supports a 8 operators with inline expressions.
_Operators_
- Addition [+]
- Subtraction [-]
- Multiplication [*]
- Division [/]
- Exponent [^]
- Square root [sqrt()]
- Natural log [ln()]

![Calculator screenshot](https://i.ibb.co/sJqPvgZp/Screenshot-2025-02-28-at-9-30-43-PM.png)

## Module 2: Springboot web server

The calculator is hosted as a Java SpringBoot web server. This returns the calculator app as a HTML page. All dependencies are managed by Maven.

![Spring boot start screenshot](https://i.ibb.co/fVwFrnqS/Screenshot-2025-02-28-at-9-37-21-PM.png)

## Module 3: Testing with Selenium and JUnit

Test cases are written with JUnit. User interface is tested with Selenium. A total of 3 test cases check server load, calculations, and UI elements functioning. Automated test cases are executed using Chromedriver in Selenium.

Test cases are as follows:
1. Assert if calculator class in web page has loaded
2. Perform 4 arithmatic operations and validate the result
3. Write an arithmatic expression using on display keypad, validate answer

![Selenium test screenshot](https://i.ibb.co/yBsR9Rzt/Screenshot-2025-02-28-at-9-43-47-PM.png)

## Module 4: Maven build for executable .jar

A cross platform executable jar is compiled using the following Maven command -

```
% mvn clean package
```
The above command creates a cross-platform executable, `target/calicos-0.0.1-SNAPSHOT.jar`. Automated testing is also completed through this process.

The .jar file can be executed directly -
```
java -jar target/calicos-0.0.1-SNAPSHOT.jar
```

## Module 5: Dockerized application

After packing, the .jar file can be hosted in a Docker container. 
To dockerize the application, a Dockerfile is first created in the root directory

_Dockerfile contents_
```
FROM openjdk:22-jdk-slim
MAINTAINER github.com/nitin-rajesh
COPY target/calicos-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 6789
```

The commands are executed to create a docker image
```
% docker build --tag=docker-calicos-jar:latest .
```

The image can be viewed in terminal:
```
% docker images
REPOSITORY           TAG       IMAGE ID       CREATED       SIZE
docker-java-jar      latest    c2cef126ec74   2 days ago    793MB
docker-calicos-jar   latest    1c0304a71e0a   2 days ago    793MB
ubuntu               latest    72297848456d   4 weeks ago   117MB
jenkins/jenkins      lts       dc56634cc8fa   7 weeks ago   779MB
```

Once the Docker image is compiled, it can be started as a container with the image ID:

```
% docker run -d -p 8080:6789 1c0304a71e0at
```

The running container can be viewed in terminal:

```
% docker container ls 
CONTAINER ID   IMAGE          COMMAND                CREATED         STATUS         PORTS                    NAMES
e8b1de1faee5   1c0304a71e0a   "java -jar /app.jar"   9 seconds ago   Up 7 seconds   0.0.0.0:8080->6789/tcp   nostalgic_davinci
```
It can be accessed using the url <http://localhost:8080/calci> on the local machine, or by replacing 'localhost' with the IP address on another machine in the network.

Command to find the IP address:

```
% sudo docker container inspect e8b1de1faee5 | grep -i IPAddress
    "SecondaryIPAddresses": null,
    "IPAddress": "172.17.0.2",
        "IPAddress": "172.17.0.2",
```

**Ngrok hosting**: Furthermore, the link can be hosted as a public url using Ngrock

## Module 6: Jenkins pipeline

The above tasks to deploy  dockerized application can be performed a Jenkins pipeline. The following Groovy pipeline script enables the tasks:

_Groovy pipeline contents_
```
pipeline {
    agent any

    stages {
        stage('Clone Repository') {
            steps {
                git 'https://github.com/nitin-rajesh/CalcOps'
            }
        }

        stage('Build with Maven') {
            steps {
                sh 'mvn clean package'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build --tag=docker-calicos:latest .'
            }
        }

        stage('Run Docker Container') {
            steps {
                sh 'docker run -d -p 8080:6789 docker-calicos:latest'
            }
        }
    }
}
```

## Module 7: Ansible script

A Dockerized .jar is an efficient runtime when compared to a full Ubuntu server. If the need arises to perform the above tasks on multiple remote machines, then an Ansible playbook can be used to achieve the result.

_Ansible playboook .yml contents_
```
---
- name: Automate tasks using Ansible
  hosts: localhost
  tasks:
    - name: Clone the Git repository
      git:
        repo: 'https://github.com/nitin-rajesh/CalcOps'
        dest: /tmp/repo
        version: HEAD

    - name: Run Maven clean and package
      command: mvn clean package
      args:
        chdir: /tmp/repo

    - name: Build Docker image
      command: docker build -t docker-calicos:latest .
      args:
        chdir: /tmp/repo

    - name: Run Docker container
      command: docker run -d -p 8080:6789 docker-calicos:latest
```

## Summary

The Calculator has been developed successfully with the following tech stack for Dev and Ops tasks-

|Tasks|Tech stack|
|--|--|
|Calci code| HTML/CSS/JS|
|Web server|Java SpringBoot|
|Testing|JUnit + Selenium|
|Deployment|Mvn packaged .jar|
|Runtime|Dockerized app server|
|Build automation|Jenkins pipeline|
|Remote server config|Ansible|
