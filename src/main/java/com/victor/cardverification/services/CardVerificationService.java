package com.victor.cardverification.services;

import com.victor.cardverification.Pojos.CardDetails;
import com.victor.cardverification.dto.CardResponse;
import com.victor.cardverification.dto.HitPayload;
import com.victor.cardverification.dto.HitResponse;
import com.victor.cardverification.dto.Payload;
import com.victor.cardverification.exception.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

@Service
public class CardVerificationService {

    @Autowired
    private RestTemplate restTemplate;

    private LinkedHashMap<String, Integer> hits = new LinkedHashMap<>();

    public CardResponse getCardDetails( String cardNumber) {
        CardDetails cardDetails = restTemplate.getForObject("https://lookup.binlist.net/" + cardNumber, CardDetails.class);
        if (cardDetails == null){
            throw new CustomException("Details not found", HttpStatus.NOT_FOUND);
        }
        CardResponse cardResponse = new CardResponse();
        cardResponse.setSuccess(true);
        Payload payload = new Payload();
        payload.setBank(cardDetails.getBank().getName());
        payload.setScheme(cardDetails.getScheme());
        payload.setType(cardDetails.getType());
        cardResponse.setPayload(payload);
        countHit(cardNumber);
        return cardResponse;
    }

    public void countHit(String cardNumber){
        Integer count;
        if (hits.containsKey(cardNumber)){
            count = hits.get(cardNumber);
            hits.replace(cardNumber, count, count+1);
        }else
        hits.put(cardNumber, 1);
        System.out.println(hits);
    }


    public HitResponse countHits(Integer start, Integer limit) {
        Set<Map.Entry<String, Integer>> hitSet = hits.entrySet();
        LinkedHashMap<String, Integer> payloadHit = new LinkedHashMap<>();
        System.out.println(hitSet);
        try {
            for (int i = 0; i < limit ; i++) {
                payloadHit.put(
                        ((new ArrayList<Map.Entry<String, Integer>>(hitSet)).get(i)).getKey(),
                        ((new ArrayList<Map.Entry<String, Integer>>(hitSet)).get(i)).getValue()
                );
            }
        } catch (Throwable exception){
            throw new CustomException("the limit in your url is lesser than the size of hit count", HttpStatus.NOT_FOUND);
        }
        System.out.println(payloadHit);
        HitResponse hitResponse = new HitResponse();
        hitResponse.setStart(start);
        hitResponse.setLimit(limit);
        hitResponse.setSize(hits.size());
        HitPayload hitPayload = new HitPayload();
        hitPayload.setPayload(payloadHit);
        hitResponse.setPayload(hitPayload);
        hitResponse.setSuccess(true);
        return hitResponse;
    }
}
