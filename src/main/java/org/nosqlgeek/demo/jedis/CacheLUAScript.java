package org.nosqlgeek.demo.jedis;


import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Show how a LUA script can be loaded in clustered mode
 */
public class CacheLUAScript {


    public static void main(String[] cargs) {


        Set<HostAndPort> nodes = new HashSet<HostAndPort>();
        nodes.add(new HostAndPort("ubuntu-server", 7000));

        JedisCluster jedis = new JedisCluster(nodes);

        // A script
        String script = "return KEYS[1] .. \":\" .. ARGV[1];";


        List<String> keys  = new ArrayList<String>();
        keys.add("hello");
        List<String> args = new ArrayList<String>();
        keys.add("world");

        ScriptResponse resp = evalsha(jedis, script, "-1", keys, args);
        ScriptResponse resp2 = evalsha(jedis, script, resp.getSha(), keys, args);


    }

    private static ScriptResponse evalsha(JedisCluster jedis, String script, String sha, List<String> keys, List<String> args) {

        //Let's assume that at least one key was passed
        String sampleKey = keys.get(0);

        if (jedis.scriptExists(sha, sampleKey)) {

            //Execute the cached script
            System.out.println("Executing the cached script ...");
            return new ScriptResponse(jedis.evalsha(sha, keys, args), sha);


        } else {

            System.out.println("Loading the script before executing ...");

            //Load the script to the shard and execute it
            String newSha = jedis.scriptLoad(script, sampleKey);

            return new ScriptResponse(jedis.evalsha(sha, keys, args), newSha);
        }

    }

}
