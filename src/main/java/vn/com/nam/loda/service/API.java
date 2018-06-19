package vn.com.nam.loda.service;

import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class API {
    public static JsonNode login(String username, String password) throws UnirestException {
        return Unirest.post("https://login.ereka.vn/api/authenticate")
                .header("content-type","application/json")
                .body("{\n" +
                        "  \"username\": \""+username+"\",\n" +
                        "  \"password\": \""+password+"\"\n" +
                        "}")
                .asJson()
                .getBody();
    }

    public static JsonNode vote(String token,String id) throws UnirestException {
        System.out.println(id);
        return Unirest.post("https://v2.api.ereka.vn/content/posts/"+id+"/vote")
                .header("content-type","application/json")
                .header("authorization",token)
                .asJson()
                .getBody();
    }
}
