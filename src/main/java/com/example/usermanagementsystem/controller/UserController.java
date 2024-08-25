package com.example.usermanagementsystem.controller;

import com.example.usermanagementsystem.service.AccessService;
import com.example.usermanagementsystem.model.UserInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final AccessService accessService;

    public UserController(AccessService accessService) {
        this.accessService = accessService;
    }

    @GetMapping("/{resource}")
    public ResponseEntity<String> checkUserAccess(@PathVariable String resource, @RequestAttribute("userInfo") UserInfo userInfo) {
        if (accessService.hasAccess(userInfo.getUserId(), resource)) {
            return ResponseEntity.ok("Access granted to resource: " + resource);
        } else {
            return ResponseEntity.status(403).body("Access denied to resource: " + resource);
        }
    }
}


