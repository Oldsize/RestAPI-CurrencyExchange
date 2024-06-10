package com.example.restapi.mapper;

import com.example.restapi.dto.ExchangeDTO;
import com.example.restapi.exceptions.MappingException;
import com.example.restapi.model.ExchangeRate;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ExchangeMapper {
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();


    public String mapFromDtoToJSON(ExchangeDTO exchangeDTO) throws MappingException {
        try {
            return gson.toJson(exchangeDTO);
        } catch (Exception e) {
            throw new MappingException();
        }
    }

    public String mapFromModeltoJSON(ExchangeRate exchangeModel) throws MappingException {
        try {
            return gson.toJson(exchangeModel);
        } catch (Exception e) {
            throw new MappingException();
        }
    }
}