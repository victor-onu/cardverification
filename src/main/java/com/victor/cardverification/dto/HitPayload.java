package com.victor.cardverification.dto;

import lombok.Data;

import java.util.LinkedHashMap;

@Data
public class HitPayload {
    private LinkedHashMap<String, Integer> payload = new LinkedHashMap<String, Integer>();
}
