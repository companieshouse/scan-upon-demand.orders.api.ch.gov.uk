package uk.gov.companieshouse.missingimagedelivery.orders.api.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
class HealthcheckControllerIntegrationTest {

    @InjectMocks
    private HealthcheckController controller;

    @Test
    @DisplayName("Successfully returns health status")
    public void returnHealthStatusSuccessfully() throws Exception {
        final ResponseEntity<Void> response = controller.getHealthCheck();

        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }
}
