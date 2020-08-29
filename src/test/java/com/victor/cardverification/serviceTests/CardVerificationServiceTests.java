package com.victor.cardverification.serviceTests;

import com.victor.cardverification.Pojos.Bank;
import com.victor.cardverification.Pojos.CardDetails;
import com.victor.cardverification.dto.CardResponse;
import com.victor.cardverification.dto.Payload;
import com.victor.cardverification.exception.CustomException;
import com.victor.cardverification.services.CardVerificationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CardVerificationServiceTests {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private CardDetails cardDetails;

    @Mock
    private Bank bank;

    @Mock
    private Payload payload;

    @InjectMocks
    private CardVerificationService cardVerificationService;

//    @Test
//    void getCardDetails_ShouldBeSuccessful() throws Exception{
//        String cardNumber = "45717360";
//        CardDetails cardDetails1 = new CardDetails();
//        cardDetails1.setBrand("brand");
//        cardDetails1.setBank(new Bank());
//        when(restTemplate.getForObject("https://lookup.binlist.net/" + cardNumber, CardDetails.class)).thenReturn(cardDetails);
//        CardResponse cardResponse = cardVerificationService.getCardDetails(cardNumber);
//        CardDetails cardDetails = restTemplate.getForObject("https://lookup.binlist.net/" + cardNumber, CardDetails.class);
//        assertThat(cardResponse).isNull();
//    }

    @Test
    void  getCardDetails_ShouldThrowException() throws Exception{
        String cardNumber = "45717360";
        Exception exception = assertThrows(CustomException.class, () -> {
            cardVerificationService.getCardDetails(cardNumber);
        });
        String expectedError = "Details not found";
        String actualMessage = exception.getLocalizedMessage();
        assertTrue(actualMessage.contains(expectedError));
    }

}
