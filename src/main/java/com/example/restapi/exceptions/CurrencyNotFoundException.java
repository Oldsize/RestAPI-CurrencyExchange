package com.example.restapi.exceptions;

public class CurrencyNotFoundException extends Exception {
    public CurrencyNotFoundException() {
        super("Currency Not Found.");
    }
}
