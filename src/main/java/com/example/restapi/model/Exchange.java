package com.example.restapi.model;

import com.google.gson.annotations.SerializedName;
import java.math.BigDecimal;

public class Exchange {

    @SerializedName("baseCurrency")
    private Currency baseCurrency;
    @SerializedName("targetCurrency")
    private Currency targetCurrency;
    @SerializedName("rate")
    private BigDecimal rate;
    @SerializedName("amount")
    private BigDecimal amount;
    @SerializedName("convertedAmount")
    private BigDecimal convertedAmount;

    public Exchange(Currency baseCurrency,
                    Currency targetCurrency,
                    BigDecimal rate,
                    BigDecimal amount,
                    BigDecimal convertedAmount) {
        this.baseCurrency = baseCurrency;
        this.targetCurrency = targetCurrency;
        this.rate = rate;
        this.amount = amount;
        this.convertedAmount = convertedAmount;
    }

    public Currency getBaseCurrency() {
        return baseCurrency;
    }

    public void setBaseCurrency(Currency baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public Currency getTargetCurrency() {
        return targetCurrency;
    }

    public void setTargetCurrency(Currency targetCurrency) {
        this.targetCurrency = targetCurrency;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getConvertedAmount() {
        return convertedAmount;
    }

    public void setConvertedAmount(BigDecimal convertedAmount) {
        this.convertedAmount = convertedAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Exchange)) return false;

        Exchange exchange = (Exchange) o;

        if (!baseCurrency.equals(exchange.baseCurrency)) return false;
        if (!targetCurrency.equals(exchange.targetCurrency)) return false;
        if (!rate.equals(exchange.rate)) return false;
        if (!amount.equals(exchange.amount)) return false;
        return convertedAmount.equals(exchange.convertedAmount);
    }

    @Override
    public int hashCode() {
        int result = baseCurrency.hashCode();
        result = 31 * result + targetCurrency.hashCode();
        result = 31 * result + rate.hashCode();
        result = 31 * result + amount.hashCode();
        result = 31 * result + convertedAmount.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Exchange{" +
                "baseCurrency=" + baseCurrency +
                ", targetCurrency=" + targetCurrency +
                ", rate=" + rate +
                ", amount=" + amount +
                ", convertedAmount=" + convertedAmount +
                '}';
    }
}