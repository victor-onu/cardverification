package com.victor.cardverification.Pojos;

import lombok.Data;

@Data
public class CardDetails {
    private Number number;
    private String scheme;
    private String type;
    private String brand;
    private boolean prepaid;
    private Country country;
    private Bank bank;

}
