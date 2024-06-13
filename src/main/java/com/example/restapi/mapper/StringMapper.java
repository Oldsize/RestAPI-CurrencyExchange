package com.example.restapi.mapper;

import com.example.restapi.exceptions.MappingException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.Map;

public class StringMapper  {
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();
    public String mapFromStringtoJSON(String message) throws MappingException {
        String json = gson.toJson(Map.of("message", message));
        return json;
    }
}
