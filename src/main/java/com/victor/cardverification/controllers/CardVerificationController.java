package com.victor.cardverification.controllers;

import com.victor.cardverification.Pojos.CardDetails;
import com.victor.cardverification.apiresponse.ApiResponse;
import com.victor.cardverification.dto.CardResponse;
import com.victor.cardverification.dto.HitResponse;
import com.victor.cardverification.services.CardVerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("card-scheme/")
public class CardVerificationController {

    private final CardVerificationService cardVerificationService;

    @Autowired
    public CardVerificationController(CardVerificationService cardVerificationService) {
        this.cardVerificationService = cardVerificationService;
    }

    @GetMapping("/verify/{cardNumber}")
    public ResponseEntity<ApiResponse<CardResponse>> verifyCard(@PathVariable String cardNumber){
        CardResponse cardResponse = cardVerificationService.getCardDetails(cardNumber);
        ApiResponse<CardResponse> response = new ApiResponse<>(HttpStatus.OK);
        response.setMessage("Card retrieved successfully");
        response.setData(cardResponse);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/stats")
    public HitResponse hitCount(@RequestParam(value = "start") Integer start, @RequestParam(value = "limit") Integer limit){
    HitResponse hitResponse = cardVerificationService.countHits(start, limit);
    return hitResponse;
    }


}
