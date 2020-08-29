package com.victor.cardverification.dto;

import lombok.Data;

import java.util.LinkedHashMap;

@Data
public class HitResponse {
    private boolean success;
    private Integer start;
    private Integer limit;
    private Integer size;
    private HitPayload payload;
}