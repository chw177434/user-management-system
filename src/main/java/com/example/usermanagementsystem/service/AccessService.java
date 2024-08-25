package com.example.usermanagementsystem.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.usermanagementsystem.model.AccessRequest;
import com.example.usermanagementsystem.model.UserAccess;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class AccessService {

    private final Map<Integer, UserAccess> accessMap = new HashMap<>();
    private final File file = new File("access.json");

    public AccessService() {
        loadAccessData();
    }

    public void addAccess(AccessRequest request) {
        UserAccess userAccess = accessMap.getOrDefault(request.getUserId(), new UserAccess(request.getUserId()));
        userAccess.getResources().addAll(request.getEndpoint());
        accessMap.put(request.getUserId(), userAccess);
        saveAccessData();
    }

    public boolean hasAccess(int userId, String resource) {
        UserAccess userAccess = accessMap.get(userId);
        return userAccess != null && userAccess.getResources().contains(resource);
    }

    private void loadAccessData() {
        if (file.exists()) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                UserAccess[] accesses = mapper.readValue(file, UserAccess[].class);
                for (UserAccess access : accesses) {
                    accessMap.put(access.getUserId(), access);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveAccessData() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(file, accessMap.values());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}





