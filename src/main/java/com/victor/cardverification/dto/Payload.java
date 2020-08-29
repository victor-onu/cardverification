package com.victor.cardverification.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Payload {
    private String scheme;
    private String type;
    private String bank;
}
