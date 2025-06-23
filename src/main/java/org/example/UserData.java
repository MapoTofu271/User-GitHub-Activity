package org.example;

import org.json.JSONObject;

public class UserData {
    private long id;
    private String type;

    private Repo repo;

    public void setType(String type) {
        this.type = type;
    }
    public String getType() {
        return type;
    }
    public void setRepo(Repo repo) {
        this.repo = repo;
    }
    public Repo getRepo() {
        return repo;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
