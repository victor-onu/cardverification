package com.victor.cardverification.controllerTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.victor.cardverification.controllers.CardVerificationController;
import com.victor.cardverification.dto.CardResponse;
import com.victor.cardverification.dto.HitResponse;
import com.victor.cardverification.dto.Payload;
import com.victor.cardverification.exception.CustomException;
import com.victor.cardverification.services.CardVerificationService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.servlet.Filter;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = CardVerificationController.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
public class CardVerificationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    CardVerificationService cardVerificationService;

    @BeforeAll
    public void setup() {
    }

    @Test
    void verifyCard_ShouldBeSuccessful() throws Exception {
        CardResponse cardResponse = new CardResponse(true, new Payload("scheme", "type", "bank"));
        String cardNumber = "45717360";
        given(cardVerificationService.getCardDetails(anyString())).willReturn(cardResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/card-scheme/verify/{cardNumber}", cardNumber))
                .andExpect(status().isOk());
    }

    @Test
    void verifyCard_ShouldThrowException() throws Exception{
        String cardNumber = "45717360";
        given(cardVerificationService.getCardDetails(anyString())).willThrow(new CustomException("Details not found", HttpStatus.NOT_FOUND));
        mockMvc.perform(MockMvcRequestBuilders.get("/card-scheme/verify/{cardNumber}", cardNumber))
                .andExpect(status().isNotFound());
    }

    @Test
    void hitCount_ShouldCountSuccessfully() throws Exception{
        given(cardVerificationService.countHits(1, 3)).willReturn(new HitResponse());
        mockMvc.perform(MockMvcRequestBuilders.get("/card-scheme/stats?start=1&limit=3"))
                .andExpect(status().isOk());
    }

    @Test
    void hitCount_ShouldThrowException() throws Exception{
        given(cardVerificationService.countHits(1, 3)).willThrow(
                new CustomException("the limit in your url is lesser than the size of hit count", HttpStatus.NOT_FOUND));
        mockMvc.perform(MockMvcRequestBuilders.get("/card-scheme/stats?start=1&limit=3"))
                .andExpect(status().isNotFound());
    }

}
