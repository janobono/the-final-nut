# the-final-nut
Quarkus, GraalVM, Svelte, Docker simple application.

After [the-two-nuts-with-chocolate](https://github.com/janobono/the-two-nuts-with-chocolate) I decide to create simple 
[Quarkus](https://quarkus.io/) native application example. Backend is same like in **the-two-nuts-with-chocolate**, 
frontend is svelte sapper application.


## tech stack

Database implementation - [Postgres](https://www.postgresql.org/)

Database migration - [Flyway](https://flywaydb.org/)

Messaging platform - [Kafka](https://kafka.apache.org/)


## build

- GraalVM 
  - `gu install native-image`
  - `sudo apt-get install libz-dev`
- maven
- docker

```shell script
mvn clean install -Pdocker
```


## run

In root directory

```shell script
docker-compose up
```


### first start

Kafka topic is not created at first start so we have to create it:
```shell script
docker exec -it the-final-nut_kafka_1 kafka-topics.sh --create --zookeeper zookeeper:2181 --replication-factor 1 --partitions 1 --topic nut
```

Then we have to start service:
```shell script
docker container start the-final-nut_backend_1
```
or
```shell script
docker-compose down
docker-compose up 
```


### endpoints

- [backend](http://127.0.0.1:8081/user/)
- [frontend](http://127.0.0.1:8080)


## measuring


### image size

```shell script
docker image ls
```

```
REPOSITORY                           TAG                 IMAGE ID            CREATED              SIZE
```


### memory consumption

```shell script
docker stats
```

```
CONTAINER ID        NAME                                            CPU %               MEM USAGE / LIMIT     MEM %               NET I/O             BLOCK I/O           PIDS
```


## results

