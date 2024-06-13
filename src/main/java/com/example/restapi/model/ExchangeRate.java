package com.example.restapi.model;

import com.google.gson.annotations.SerializedName;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public class ExchangeRate {

    @SerializedName("id")
    private Long id;
    @NotNull
    @SerializedName("baseCurrency")
    private Currency baseCurrency;
    @NotNull
    @SerializedName("targetCurrency")
    private Currency targetCurrency;
    @NotEmpty
    @SerializedName("rate")
    private BigDecimal rate;

    public ExchangeRate() {}

    public ExchangeRate(Long id, Currency baseCurrency, Currency targetCurrency, BigDecimal rate) {
        this.id = id;
        this.baseCurrency = baseCurrency;
        this.targetCurrency = targetCurrency;
        this.rate = rate;
    }

    public ExchangeRate(Currency baseCurrency, Currency targetCurrency, BigDecimal rate) {
        this(null, baseCurrency, targetCurrency, rate);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ExchangeRate)) return false;

        ExchangeRate that = (ExchangeRate) o;

        if (!id.equals(that.id)) return false;
        if (!baseCurrency.equals(that.baseCurrency)) return false;
        if (!targetCurrency.equals(that.targetCurrency)) return false;
        return rate.equals(that.rate);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + baseCurrency.hashCode();
        result = 31 * result + targetCurrency.hashCode();
        result = 31 * result + rate.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "ExchangeRate{" +
                "id=" + id +
                ", baseCurrency=" + baseCurrency +
                ", targetCurrency=" + targetCurrency +
                ", rate=" + rate +
                '}';
    }
}