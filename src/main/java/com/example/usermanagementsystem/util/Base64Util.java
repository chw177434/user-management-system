package com.example.usermanagementsystem.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.usermanagementsystem.model.UserInfo;

import java.io.IOException;

public class Base64Util {

    public static UserInfo parseUserInfo(String json) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(json, UserInfo.class);
        } catch (IOException e) {
            return new UserInfo(); // 返回默认值
        }
    }
}



