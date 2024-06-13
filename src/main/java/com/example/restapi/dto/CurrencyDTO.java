package com.example.restapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;


@AllArgsConstructor
@Data
public class CurrencyDTO {
    String code;
    String fullName;
    String sign;
}
