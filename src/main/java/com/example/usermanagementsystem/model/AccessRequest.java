package com.example.usermanagementsystem.model;

import java.util.List;

public class AccessRequest {

    private int userId;
    private List<String> endpoint;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<String> getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(List<String> endpoint) {
        this.endpoint = endpoint;
    }
}


