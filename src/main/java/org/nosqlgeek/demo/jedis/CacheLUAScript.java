package org.nosqlgeek.demo.jedis;


import org.nosqlgeek.demo.Constants;
import org.nosqlgeek.demo.Test;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.exceptions.JedisNoScriptException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Show how a LUA script can be loaded in clustered mode
 */
public class CacheLUAScript {


    /**
     * Entry point
     *
     * @param cargs
     */
    public static void main(String[] cargs) {


        Set<HostAndPort> nodes = new HashSet<HostAndPort>();
        nodes.add(new HostAndPort(Constants.HOST, Constants.PORT));

        JedisCluster jedis = new JedisCluster(nodes);

        // A script
        String script = "return KEYS[1] .. \":\" .. ARGV[1];";


        List<String> keys  = new ArrayList<String>();
        List<String> args = new ArrayList<String>();
        keys.add("hello");
        args.add("world");

        //Execute without any script
        ScriptResponse resp = evalsha(jedis, script, "i-am-not-there-yet", keys, args);
        System.out.println(resp.getResponse().toString());

        ScriptResponse resp2 = evalsha(jedis, script, resp.getSha(), keys, args);
        System.out.println(resp2.getResponse().toString());


    }

    /**
     * Helper method for caching a script on-demand
     *
     * @param jedis
     * @param script
     * @param sha
     * @param keys
     * @param args
     * @return
     */
    private static ScriptResponse evalsha(JedisCluster jedis, String script, String sha, List<String> keys, List<String> args) {

        //Let's assume that at least one key was passed
        String sampleKey = keys.get(0);


        try {

            System.out.println("Executing the cached script ...");
            return new ScriptResponse(jedis.evalsha(sha, keys, args), sha);

        } catch (JedisNoScriptException e) {

            System.out.println("WARN: " + e.toString());

            //Load the script to the shard and execute it
            System.out.println("Loading script ...");

            String newSha = jedis.scriptLoad(script, sampleKey);
            System.out.println("sha = " + newSha);


            //Quick check
            boolean exists = jedis.scriptExists(newSha, sampleKey);
            System.out.println("exists = " + exists);
            Test.assertEquals(true, exists);

            return new ScriptResponse(jedis.evalsha(newSha, keys, args), newSha);
        }


    }

}
