package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;

public class EventMapFromApi {
    public UserData[] eventMapper(String response){

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        UserData[] userData = null;
        try {
            userData = objectMapper.readValue(response, UserData[].class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return userData;
    }

    /*
    Process data:
        Grouping same name repo
        Grouping same action in the same repo
        Count same action in the repo

        hashmap<name, Hashmap<action, count>>
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
