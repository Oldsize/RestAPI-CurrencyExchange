package com.example.restapi.servlets;

import com.example.restapi.dao.CurrencyDAO;
import com.example.restapi.exceptions.DatabaseNotFoundException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "allCurrenciesServlet", value = "/currencies")
public class AllCurrenciesServlet extends HttpServlet {

    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        CurrencyDAO currenciesDAO = CurrencyDAO.getInstance();
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        List<Map<String, String>> jsonList = new ArrayList<>();
        try {
            currenciesDAO.getAllCurrenciesQuery(resultSet -> {
                try {
                    while (resultSet.next()) {
                        Map<String, String> json = new LinkedHashMap<>();
                        json.put("ID", resultSet.getString("ID"));
                        json.put("Code", resultSet.getString("Code"));
                        json.put("FullName", resultSet.getString("FullName"));
                        json.put("Sign", resultSet.getString("Sign"));
                        jsonList.add(json);
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (SQLException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error accessing database");
            return;
        } catch (DatabaseNotFoundException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error processing request");
        }
        String jsonString = gson.toJson(jsonList);
        out.print(jsonString);
        out.flush();
    }
}