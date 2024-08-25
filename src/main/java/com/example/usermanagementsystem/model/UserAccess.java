package com.example.usermanagementsystem.model;

import java.util.HashSet;
import java.util.Set;

public class UserAccess {

    private int userId;
    private Set<String> resources;

    public UserAccess(int userId) {
        this.userId = userId;
        this.resources = new HashSet<>();
    }

    public int getUserId() {
        return userId;
    }

    public Set<String> getResources() {
        return resources;
    }
}

