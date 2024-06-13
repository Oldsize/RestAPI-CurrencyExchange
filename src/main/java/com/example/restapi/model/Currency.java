package com.example.restapi.model;

import com.google.gson.annotations.SerializedName;
import jakarta.validation.constraints.NotNull;

public class Currency {
    @SerializedName("id")
    private Long id;
    @NotNull
    @SerializedName("code")
    private String code;
    @NotNull
    @SerializedName("name")
    private String fullName;
    @NotNull
    @SerializedName("sign")
    private String sign;

    public Currency() {

    }

    public Currency(Long id, String code, String fullName, String sign) {
        this.id = id;
        this.code = code;
        this.fullName = fullName;
        this.sign = sign;
    }

    public Currency(String code, String fullName, String sign) {
        this(null, code, fullName, sign);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Currency)) return false;

        Currency currency = (Currency) o;

        if (!id.equals(currency.id)) return false;
        if (!code.equals(currency.code)) return false;
        if (!fullName.equals(currency.fullName)) return false;
        return sign.equals(currency.sign);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + code.hashCode();
        result = 31 * result + fullName.hashCode();
        result = 31 * result + sign.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Currency{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", fullName='" + fullName + '\'' +
                ", sign='" + sign + '\'' +
                '}';
    }
}
