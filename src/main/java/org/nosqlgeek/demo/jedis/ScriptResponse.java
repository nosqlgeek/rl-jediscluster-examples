package org.nosqlgeek.demo.jedis;

/**
 * The script response, a bit wrapped in order to return the SHA
 */
public class ScriptResponse {

    private Object response;
    private String sha;


    public ScriptResponse(Object resp, String sha) {

        this.response = resp;
        this.sha = sha;
    }

    public Object getResponse() {
        return response;
    }

    public String getSha() {
        return sha;
    }
}
