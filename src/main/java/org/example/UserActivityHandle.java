package org.example;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserActivityHandle {
    String defaultURL = "https://api.github.com/users/<username>/events";
    final String username;
    List<String> userActivity = new ArrayList<>();

    public UserActivityHandle(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    /*
    default url
    https://api.github.com/users/<username>/events : String
        - replace <username> with username var
    Fetch data using HttpURLConncection
     */
    public void URLConnect(String username) throws IOException, InterruptedException {
        String userGitHubURL = defaultURL.replace("<username>", username);

        /*
        HttpClient build
         */
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(userGitHubURL))
                .GET()
                .build();

        /*
        Response build
         */
        HttpResponse<String> response = client.send(
                request, HttpResponse.BodyHandlers.ofString());

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        UserData[] userData = objectMapper.readValue(response.body(), UserData[].class);


        /* for(UserData userData1 : userData) {
            JSONObject userJSON = new JSONObject();
            userJSON.put("id", userData1.getId());
            userJSON.put("type", userData1.getType());
            userJSON.put("repo", userData1.getRepo().getName());
            data.put(userJSON);
            //System.out.println(objectMapper.writeValueAsString(userData1));
        }
        System.out.println(data.toString(4));
        */
        HashMap<String, HashMap<String, Integer>> result = dataHandle(userData);
        print(result);

    }

    /*
    Process data:
        Grouping same name repo
        Grouping same action in the same repo
        Count same action in the repo

        hashmap<name, Hash<action, count>>
        root --- action1
             --- action2
             --- action3


        name
            action
                count
     */


    public HashMap<String, HashMap<String, Integer>> dataHandle(UserData[] data) {
        HashMap<String, HashMap<String, Integer>> repoMap = new HashMap<>();
        for (UserData datum : data) {
            String repo = datum.getRepo().getName();
            String type = datum.getType();
            repoMap.putIfAbsent(repo, new HashMap<>());
            HashMap<String, Integer> repoEvents = repoMap.get(repo);
            repoEvents.put(type, repoEvents.getOrDefault(type, 0) + 1);
        }
            return repoMap;
    }

    public void print(HashMap<String, HashMap<String, Integer>> repoMap) {
        for(String key : repoMap.keySet()) {
            System.out.println("In " + key + ":");
            HashMap<String, Integer> repoEvents = repoMap.get(key);
            for(String eventType : repoEvents.keySet()) {
                System.out.println("  " + eventType + ": " + repoEvents.get(eventType));
            }
        }
    }

}
