# SparkFun
A collection of test projects for learning Spark. Don't expect anything good here.

# Requirements
- Spark 2.10
- Java 1.8.x
- Docker

# Setup
1. docker pull mongo
1. docker run -p 127.0.0.1:27017:27017 --name mongo-test  -d mongo:latest
1. docker ps
    ```
    CONTAINER ID        IMAGE               COMMAND                  CREATED             STATUS              PORTS                        NAMES
    efb4f645eaaf        mongo:latest        "docker-entrypoint.s…"   3 minutes ago       Up 3 minutes        127.0.0.1:27017->27017/tcp   mongo-test
    ```
1. docker cp resources/zip.json efb4f645eaaf:/tmp
1. docker exec -it efb4f645eaaf mongoimport --db large --collection zips /tmp/zips.json
    ```
    2020-10-15T15:03:56.640+0000	connected to: mongodb://localhost/
    2020-10-15T15:03:57.235+0000	29353 document(s) imported successfully. 0 document(s) failed to import.
    ```
1. mvn clean install
1. cd mongo/target
1. java -jar mongo-1.0-SNAPSHOT.jar

![Hooray!](https://media.giphy.com/media/11sBLVxNs7v6WA/giphy.gif)