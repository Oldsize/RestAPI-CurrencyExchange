package com.example.restapi.servlets;

import com.example.restapi.dao.CurrencyDAO;
import com.example.restapi.dao.CurrencyExchangeDAO;
import com.example.restapi.exceptions.DAOException;
import com.example.restapi.exceptions.DatabaseNotFoundException;
import com.example.restapi.mapper.StringMapper;
import com.example.restapi.model.Currency;
import com.example.restapi.model.ExchangeRate;
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
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "allExchanges", value = "/exchanges")
public class AllExchangesServlet extends HttpServlet {

    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        CurrencyExchangeDAO currencyExchangeDAO = CurrencyExchangeDAO.getInstance();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        CurrencyDAO currencyDAO = CurrencyDAO.getInstance();
        StringMapper stringMapper = new StringMapper();
        List<ExchangeRate> jsonList = new ArrayList<>();
        try {
            currencyExchangeDAO.getAllExchangesQuery(resultSet -> {
                try {
                    while (resultSet.next()) {
                        Currency baseCurrency = currencyDAO.getCurrencyByIdQuery(resultSet.getString("BaseCurrencyId"));
                        Currency targetCurrency = currencyDAO.getCurrencyByIdQuery(resultSet.getString("TargetCurrencyId"));
                        Long id = resultSet.getLong("ID");
                        BigDecimal rate = resultSet.getBigDecimal("Rate");
                        ExchangeRate exchangeRate = new ExchangeRate();
                        exchangeRate.setId(id);
                        exchangeRate.setBaseCurrency(baseCurrency);
                        exchangeRate.setTargetCurrency(targetCurrency);
                        exchangeRate.setRate(rate);
                        jsonList.add(exchangeRate);
                    }
                } catch (SQLException | DatabaseNotFoundException | DAOException e) {
                    throw new RuntimeException();
                }
            });
        } catch (DAOException | SQLException | DatabaseNotFoundException e) {
            String message = "Error processing request";
            out.print(stringMapper.mapFromStringtoJSON(message));
            response.setStatus(500);
        }
        String jsonString = gson.toJson(jsonList);
        out.print(jsonString);
        out.flush();
    }
}