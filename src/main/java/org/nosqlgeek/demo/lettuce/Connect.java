package org.nosqlgeek.demo.lettuce;

import io.lettuce.core.RedisURI;
import io.lettuce.core.cluster.RedisClusterClient;
import io.lettuce.core.cluster.api.sync.RedisAdvancedClusterCommands;
import org.nosqlgeek.demo.Constants;
import org.nosqlgeek.demo.Test;


/**
 * Connect to Redis Enterprise via Lettuce Cluster
 */
public class Connect {

    /**
     * Test if we can connect and add an item
     *
     * @param args
     */
    public static void main(String[] args) {

       RedisURI redisUri = RedisURI.Builder.redis(Constants.HOST, Constants.PORT).build();
       RedisClusterClient clusterClient = RedisClusterClient.create(redisUri);
       RedisAdvancedClusterCommands<String, String> client = clusterClient.connect().sync();

       String result = client.set("hello", "world");
       System.out.println("set = " + result);
       Test.assertEquals("OK", result);

       result = client.get("hello");
       System.out.println("get = " + result);
       Test.assertEquals("world", result);
    }

}
