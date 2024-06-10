package com.example.restapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ExchangeDTO {
    Long id;
    Long baseCurrencyId;
    Long targetCurrencyId;
    BigDecimal rate;
}
