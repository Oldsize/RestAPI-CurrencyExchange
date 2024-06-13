package com.example.restapi.dto;

import com.example.restapi.model.Currency;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ExchangeRateDTO {
    Long id;
    Currency baseCurrency;
    Currency targetCurrency;
    BigDecimal rate;
    Long amount;
    BigDecimal convertedAmount;
}
