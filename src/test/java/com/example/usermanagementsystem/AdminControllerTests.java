package com.example.usermanagementsystem;

import com.example.usermanagementsystem.controller.AdminController;
import com.example.usermanagementsystem.service.AccessService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.usermanagementsystem.model.AccessRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import java.util.Base64;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

@WebMvcTest(AdminController.class)
class AdminControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AccessService accessService;


    @Test
    public void testAddUserAccessAsNonAdmin() throws Exception {
        // 创建 AccessRequest 对象
        AccessRequest accessRequest = new AccessRequest();
        accessRequest.setUserId(11);
        accessRequest.setEndpoint(List.of("/some/endpoint"));

        // Base64 编码的 UserInfo 数据
        String userInfoJson = "{\"userId\":\"11\",\"role\":\"user\"}";
        String encodedUserInfo = Base64.getEncoder().encodeToString(userInfoJson.getBytes());

        // Mock AccessService behavior
        doNothing().when(accessService).addAccess(any(AccessRequest.class));

        // 执行测试请求
        mockMvc.perform(MockMvcRequestBuilders.post("/admin/addUser")
                        .header("Authorization", encodedUserInfo)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accessRequest)))
                .andExpect(MockMvcResultMatchers.status().isForbidden()); // 预期返回 403 状态码
    }

    @Test
    public void testAddUserAccessAsAdmin() throws Exception {
        // 创建 AccessRequest 对象
        AccessRequest accessRequest = new AccessRequest();
        accessRequest.setUserId(22);
        accessRequest.setEndpoint(List.of("/some/endpoint"));

        // Base64 编码的 UserInfo 数据
        String userInfoJson = "{\"userId\":\"22\",\"role\":\"admin\"}";
        String encodedUserInfo = Base64.getEncoder().encodeToString(userInfoJson.getBytes());

        // Mock AccessService behavior
        doNothing().when(accessService).addAccess(any(AccessRequest.class));

        // 执行测试请求
        mockMvc.perform(MockMvcRequestBuilders.post("/admin/addUser")
                        .header("Authorization", encodedUserInfo)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accessRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk()); // 预期返回 200 状态码
    }

}

