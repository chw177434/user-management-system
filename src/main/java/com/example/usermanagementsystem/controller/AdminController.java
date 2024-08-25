package com.example.usermanagementsystem.controller;

import com.example.usermanagementsystem.model.AccessRequest;
import com.example.usermanagementsystem.service.AccessService;
import com.example.usermanagementsystem.model.UserInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AccessService accessService;

    public AdminController(AccessService accessService) {
        this.accessService = accessService;
    }

    @PostMapping("/addUser")
    public ResponseEntity<String> addUserAccess(@RequestBody AccessRequest accessRequest, @RequestAttribute("userInfo") UserInfo userInfo) {
        if (!userInfo.getRole().equals("admin")) {
            return ResponseEntity.status(403).body("Access denied: Only admins can add user access.");
        }
        accessService.addAccess(accessRequest);
        return ResponseEntity.ok("Access added successfully.");
    }
}


