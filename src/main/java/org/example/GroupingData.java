package org.example;

import java.util.HashMap;

public class GroupingData {

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

}
