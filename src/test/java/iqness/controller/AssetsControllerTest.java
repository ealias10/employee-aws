package iqness.controller;
import iqness.request.EmployeeCreateRequest;
import iqness.service.AssetsService;
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
public class AssetsControllerTest {

    @InjectMocks
    AssetsController assetsController;

    @Mock
    AssetsService assetsService;
    private static final UUID ID = UUID.randomUUID();
    private final ObjectMapper objectMapper = new ObjectMapper();

    private MockMvc mockMvc;
    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(assetsController).build();
    }
}
