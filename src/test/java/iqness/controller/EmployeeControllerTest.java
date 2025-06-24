package iqness.controller;


import iqness.request.EmployeeCreateRequest;
import iqness.service.EmployeeService;
import iqness.vo.EmployeeVO;
import iqness.vo.PaginatedResponseVOAndCount;
import iqness.vo.ResponseVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class EmployeeControllerTest {

//    @InjectMocks
//    EmployeeController employeeController;
//
//    @Mock
//    EmployeeService employeeService;
//    private static final UUID ID = UUID.randomUUID();
//    private final ObjectMapper objectMapper = new ObjectMapper();
//
//    private MockMvc mockMvc;
//    @BeforeEach
//    public void setup() {
//        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
//    }
//
//
//    @Test
//    void createEmployee() throws Exception{
//        var employeeRequest = EmployeeCreateRequest.builder().build();
//        ResultActions resultActions = mockMvc.perform(
//                        post("/employee/create").content(objectMapper.writeValueAsString(employeeRequest)).contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk());
//        ResponseVO response =
//                objectMapper.readValue(
//                        resultActions.andReturn().getResponse().getContentAsString(), ResponseVO.class);
//        assertEquals(200, response.getStatus());
//        assertEquals("success", response.getMessage());
//    }
//
//
//    @Test
////    void listEmployeeTest() throws Exception {
////        UUID MOCK_SERVICE_ID = UUID.fromString("78dc4634-2d6d-473c-b978-6060d776d27a");
////        var employeeVO =
////                Collections.singletonList(EmployeeVO.builder().employeeId(ID).build());
////        var paginatedResponseVOAndCount = new PaginatedResponseVOAndCount<>(1, employeeVO);
////        Mockito.when(
////                        employeeService.listEmployee(
////                                Mockito.any(), Mockito.any(), Mockito.any()))
////                .thenReturn(paginatedResponseVOAndCount);
////        ResultActions resultActions =
////                mockMvc
////                        .perform(
////                                get("/employee/list")
////                                        .param("search", String.valueOf(MOCK_SERVICE_ID))
////                                        .param("offset", "1")
////                                        .param("limit", "10"))
////                        .andDo(print())
////                        .andExpect(status().isOk());
////        var response =
////                objectMapper.readValue(
////                        resultActions.andReturn().getResponse().getContentAsString(), ResponseVO.class);
////        assertEquals(200, response.getStatus());
////        assertEquals("success", response.getMessage());
////        assertEquals(1, response.getTotalCount());
////    }
//
//    @Test
//    void getEmployeeByIdTest() throws Exception {
//        EmployeeVO employeeVO = getResponse();
//        when(employeeService.getEmployeeById(Mockito.any())).thenReturn(employeeVO);
//        ResultActions resultActions =
//                mockMvc
//                        .perform(get("/employee/" + ID + "/details"))
//                        .andDo(print())
//                        .andExpect(status().isOk());
//        ResponseVO response =
//                objectMapper.readValue(
//                        resultActions.andReturn().getResponse().getContentAsString(), ResponseVO.class);
//        assertEquals(200, response.getStatus());
//        assertEquals("success", response.getMessage());
//    }
//    private static EmployeeVO getResponse() {
//        return EmployeeVO.builder().employeeId(ID).build();
//    }
//    @Test
//    void updateEmployeeTest() throws Exception {
//        EmployeeCreateRequest request =
//                EmployeeCreateRequest.builder().employeeName("ealias").build();
//        EmployeeVO employeeVO = getResponse();
//        when(employeeService.updateEmployee(Mockito.any(), Mockito.any()))
//                .thenReturn(employeeVO);
//        ResultActions resultActions =
//                mockMvc
//                        .perform(
//                                put("/employee/" + ID + "/update")
//                                        .content(objectMapper.writeValueAsString(request))
//                                        .contentType(MediaType.APPLICATION_JSON))
//                        .andDo(print())
//                        .andExpect(status().isOk());
//        ResponseVO response =
//                objectMapper.readValue(
//                        resultActions.andReturn().getResponse().getContentAsString(), ResponseVO.class);
//        assertEquals(200, response.getStatus());
//        assertEquals("success", response.getMessage());
//    }
//
//    @Test
//    void deleteEmployeeTest() throws Exception {
//        Mockito.doNothing().when(employeeService).deleteEmployee(Mockito.any());
//        ResultActions resultActions =
//                mockMvc
//                        .perform(delete("/employee/" + ID + "/delete"))
//                        .andDo(print())
//                        .andExpect(status().isOk());
//        ResponseVO response =
//                objectMapper.readValue(
//                        resultActions.andReturn().getResponse().getContentAsString(), ResponseVO.class);
//        assertEquals(200, response.getStatus());
//        assertEquals("success", response.getMessage());
//    }

}
