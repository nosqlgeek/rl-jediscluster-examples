# Jedis Cluster Examples

Some examples for using Jedis in clustered mode.

## How to run

* Create a fat JAR file

```
mvn install
```

* Execute the JAR

```
java -cp jedis-example-1.0-SNAPSHOT.jar <Test> 
``` 

## Executing a cached LUA script

* org.nosqlgeek.demo.jedis.CacheLUAScript

```
java -cp jedis-example-1.0-SNAPSHOT.jar org.nosqlgeek.demo.jedis.CacheLUAScript
```

