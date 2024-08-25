package com.example.usermanagementsystem;

import com.example.usermanagementsystem.controller.UserController;
import com.example.usermanagementsystem.model.UserInfo;
import com.example.usermanagementsystem.service.AccessService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTests {

    @InjectMocks
    private UserController userController;

    @Mock
    private AccessService accessService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void testCheckUserAccessGranted() throws Exception {
        // 模拟用户信息
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(1);
        userInfo.setRole("user");

        // 设置AccessService的行为
        when(accessService.hasAccess(1, "resource")).thenReturn(true);

        // 执行请求并验证响应
        mockMvc.perform(get("/user/resource")
                        .header("Authorization", "Base64EncodedUserInfo")
                        .requestAttr("userInfo", userInfo))
                .andExpect(status().isOk())
                .andExpect(content().string("Access granted to resource: resource"));
    }

    @Test
    void testCheckUserAccessDenied() throws Exception {
        // 模拟用户信息
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(1);
        userInfo.setRole("user");

        // 设置AccessService的行为
        when(accessService.hasAccess(1, "resource")).thenReturn(false);

        // 执行请求并验证响应
        mockMvc.perform(get("/user/resource")
                        .header("Authorization", "Base64EncodedUserInfo")
                        .requestAttr("userInfo", userInfo))
                .andExpect(status().isForbidden())
                .andExpect(content().string("Access denied to resource: resource"));
    }
}

