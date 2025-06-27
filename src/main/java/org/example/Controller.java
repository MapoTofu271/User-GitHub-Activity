package org.example;

import java.util.HashMap;
import java.util.Objects;

public class Controller {
    private final String username;
    private final GitHubApiClient gitHubApiClient;
    private final EventMapFromApi eventMapFromApi;
    private final GroupingData groupingData;
    private HashMap<String, HashMap<String, Integer>> finalData;

    public Controller(String username, GitHubApiClient gitHubApiClient,
                      EventMapFromApi eventMapFromApi, GroupingData groupingData) {
        this.username = Objects.requireNonNull(username);
        this.gitHubApiClient = Objects.requireNonNull(gitHubApiClient);
        this.eventMapFromApi = Objects.requireNonNull(eventMapFromApi);
        this.groupingData = Objects.requireNonNull(groupingData);
        this.finalData = new HashMap<>();
    }

    public void process() {
        try {
            String apiData = gitHubApiClient.fetchUserEvents(username);
            UserData[] events = eventMapFromApi.eventMapper(apiData);
            this.finalData = groupingData.dataHandle(events != null ? events : new UserData[0]);
        } catch (Exception e) {
            System.err.println("Processing failed: " + e.getMessage());
            this.finalData = new HashMap<>();
        }
    }

    public void print() {
        if (finalData.isEmpty()) {
            System.out.println("No events found for " + username);
            return;
        }

        finalData.forEach((repo, events) -> {
            System.out.println("Repository: " + repo);
            events.forEach((type, count) ->
                    System.out.printf("  %-20s %d events%n", type + ":", count));
        });
    }
}