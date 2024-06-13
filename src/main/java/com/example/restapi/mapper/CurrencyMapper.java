package com.example.restapi.mapper;

import com.example.restapi.dto.CurrencyDTO;
import com.example.restapi.exceptions.MappingException;
import com.example.restapi.model.Currency;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class CurrencyMapper {
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public String mapFromDTOtoJSON(CurrencyDTO currencyModel) throws MappingException {
        try {
            return gson.toJson(currencyModel);
        } catch (Exception e) {
            throw new MappingException();
        }
    }
    public String mapFromModeltoJSON(Currency currencyModel) throws MappingException {
        try {
            return gson.toJson(currencyModel);
        } catch (Exception e) {
            throw new MappingException();
        }
    }
}