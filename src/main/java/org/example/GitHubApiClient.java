package org.example;

import org.json.JSONArray;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class GitHubApiClient {
    private final HttpClient httpClient;
    String defaultURL = "https://api.github.com/users/<username>/events";
    int statusCode = 404;

    public GitHubApiClient() {
        this.httpClient = HttpClient.newHttpClient();
    }

    public String fetchUserEvents(String username) {

        String urlGitHub = defaultURL.replace("<username>", username);
        /*
        Request build
         */
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(urlGitHub))
                .GET()
                .build();

        /*
        Response build
         */
        try {
            HttpResponse<String> response = httpClient.send(
                request, HttpResponse.BodyHandlers.ofString());
                statusCode = response.statusCode();
                return response.body();
        } catch (IOException | InterruptedException e) {
            System.out.println(statusCode);
            e.printStackTrace();
        }
        return "";
    }
}
