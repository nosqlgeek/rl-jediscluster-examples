#!/bin/bash
mvn clean
mvn install
scp target/jedis-example-1.0-SNAPSHOT.jar nosqlgeek@ubuntu-server:/home/nosqlgeek/
ssh nosqlgeek@ubuntu-server 'docker cp /home/nosqlgeek/jedis-example-1.0-SNAPSHOT.jar fd30e77bf0bf:/tmp/jedis-example-1.0-SNAPSHOT.jar'
