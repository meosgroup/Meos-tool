package vn.com.nam.loda.model;

import com.google.gson.annotations.SerializedName;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import vn.com.nam.loda.service.API;

public class User {
    @SerializedName("username")
    private String username;
    @SerializedName("password")
    private String password;

    private String token;

    public User(){

    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String login() throws UnirestException {
        token = "Bearer "+API.login(username,password).getObject().getString("token");
        return token;
    }
    public JsonNode vote(String idOrUrl) throws UnirestException {
        return API.vote(token,idOrUrl);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
