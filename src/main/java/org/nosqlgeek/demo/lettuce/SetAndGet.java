package org.nosqlgeek.demo.lettuce;

import io.lettuce.core.RedisURI;
import io.lettuce.core.cluster.RedisClusterClient;
import io.lettuce.core.cluster.api.sync.RedisAdvancedClusterCommands;
import org.nosqlgeek.demo.Constants;
import org.nosqlgeek.demo.Test;

/**
 * Perform several sets and gets
 */
public class SetAndGet {

    /**
     * Test if we can connect and add an item
     *
     * @param args
     */
    public static void main(String[] args) {

        RedisURI redisUri = RedisURI.Builder.redis(Constants.HOST, Constants.PORT).build();
        RedisClusterClient clusterClient = RedisClusterClient.create(redisUri);
        RedisAdvancedClusterCommands<String, String> client = clusterClient.connect().sync();


        String result = "";

        for (int i = 0; i < 1000; i++) {

            result = client.set("hello:" + i , "world:" + i);
            Test.assertEquals("OK", result);
        }


        for (int i = 0; i < 1000; i++) {

            result = client.get("hello:" + i);
            Test.assertEquals("world:" + i, result);
            System.out.println(result);

        }
    }

}
