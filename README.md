# the-final-nut
Quarkus, GraalVM, Svelte, Docker simple application.

After [the-two-nuts-with-chocolate](https://github.com/janobono/the-two-nuts-with-chocolate) I decided to create simple 
[Quarkus](https://quarkus.io/) native application example. Backend is same like in **the-two-nuts-with-chocolate**, 
frontend is svelte sapper application.


## tech stack

Database implementation - [Postgres](https://www.postgresql.org/)

Database migration - [Flyway](https://flywaydb.org/)

Messaging platform - [Kafka](https://kafka.apache.org/)


## build

- GraalVM CE 19.3.1 
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

I didn't configure any proxy. You have to add
```
127.0.0.1    backend
``` 
to your `/etc/hosts`


### endpoints

- [backend](http://127.0.0.1:8080/user/)
- [frontend](http://127.0.0.1)


## measuring


### image size

```shell script
docker image ls
```

```
REPOSITORY                    TAG                 IMAGE ID            CREATED              SIZE
janobono/final-nut-frontend   latest              c3723e6b45d0        31 seconds ago       93.2MB
janobono/final-nut-backend    latest              3200130d38f9        About a minute ago   218MB
```


### memory consumption

```shell script
docker stats
```

```
CONTAINER ID        NAME                        CPU %               MEM USAGE / LIMIT     MEM %               NET I/O             BLOCK I/O           PIDS
5048787efe8c        the-final-nut_backend_1     0.38%               33.48MiB / 7.677GiB   0.43%               15.4kB / 13.2kB     0B / 0B             16
```


## results

In my opinion there is no real argument for GraalVM native build now.
 
- build time **11:38 min** vs **57 s**
- image size grow up from 179MB to 218MB as a base `debian:10-slim` was used
- memory usage **308.6MiB** vs **33.48MiB**

I didn't write any native test so this should be next effort. Memory consumption is really impressive but if you haven't
resource problems the price for this result should be really big.
