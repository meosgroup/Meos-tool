package vn.com.nam.loda.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class VoteTool {

    public interface VoteListener {
        public void onVoteResponse(JsonNode response, int num);
    }


    public static int numThread = 0;
    List<User> listUser;
    VoteListener listener;

    public VoteTool() {
        listUser = new ArrayList<User>();
    }

    public VoteTool(List<User> list) {
        listUser = list;
    }

    public void addListener(VoteListener listener) {
        this.listener = listener;
    }

    public void automaticVote(String idOrUrl, long timeBetweenRequest) throws UnirestException, InterruptedException {
        for (int i=0;i<listUser.size();i++) {
            JsonNode jsonNode = listUser.get(i).vote(idOrUrl);
            if (listener != null) listener.onVoteResponse(jsonNode, i+1);
            Thread.sleep(timeBetweenRequest);
        }
    }

}
