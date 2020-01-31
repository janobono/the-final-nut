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
janobono/spring-boot-nut-chocolate   latest              2df1fab068fd        12 seconds ago       185MB
janobono/quarkus-nut-chocolate       latest              3fbbdffc3176        About a minute ago   179MB
```


### memory consumption

```shell script
docker stats
```

```
CONTAINER ID        NAME                                            CPU %               MEM USAGE / LIMIT     MEM %               NET I/O             BLOCK I/O           PIDS
22d681e84bd3        the-two-nuts-with-chocolate_quarkus-nut_1       1.92%               308.6MiB / 7.677GiB   3.93%               12.6kB / 8.42kB     0B / 0B             29
dfa6ec0b8eb4        the-two-nuts-with-chocolate_spring-boot-nut_1   1.95%               452.3MiB / 7.677GiB   5.75%               52.4kB / 45.7kB     0B / 0B             36

```


## results

1. quarkus
1. springboot
