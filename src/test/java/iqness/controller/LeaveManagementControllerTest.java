package iqness.controller;


import iqness.request.LeaveManagementRequest;
import iqness.request.LoginRequest;
import iqness.request.UserCreateRequest;
import iqness.request.UserPasswordResetRequest;
import iqness.service.LeaveManagementService;
import iqness.service.UserService;
import iqness.vo.LoginVO;
import iqness.vo.ResponseVO;
import iqness.vo.UsersVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class LeaveManagementControllerTest {
    @InjectMocks
    LeaveManagementController leaveManagementController;

    @Mock
    LeaveManagementService leaveManagementService;
    private static final UUID ID = UUID.randomUUID();
    private final ObjectMapper objectMapper = new ObjectMapper();

    private MockMvc mockMvc;
    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(leaveManagementController).build();
    }

    @Test
    void createLeaveManagement() throws Exception{
        var leaveManagementRequest = LeaveManagementRequest.builder().build();
        ResultActions resultActions = mockMvc.perform(
                        post("/leavemanagement/create").content(objectMapper.writeValueAsString(leaveManagementRequest)).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
        ResponseVO response =
                objectMapper.readValue(
                        resultActions.andReturn().getResponse().getContentAsString(), ResponseVO.class);
        assertEquals(200, response.getStatus());
        assertEquals("success", response.getMessage());
    }

}
