package com.example.restapi.servlets;

import com.example.restapi.dao.CurrencyExchangeDAO;
import com.example.restapi.mapper.ExchangeMapper;
import com.example.restapi.mapper.StringMapper;
import com.example.restapi.model.ExchangeRate;
import com.example.restapi.utils.PreparedRequestsSQL;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "addExchange", value = "/add/exchange/*")
public class AddExchangeServlet extends HttpServlet {
    @SneakyThrows
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StringMapper stringMapper = new StringMapper();
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        PreparedRequestsSQL preparedRequestsSQL = new PreparedRequestsSQL();
        ExchangeMapper exchangeMapper = new ExchangeMapper();
        CurrencyExchangeDAO currencyExchangeDAO = CurrencyExchangeDAO.getInstance();
        String baseCurrencyCode = request.getParameter("base_currency_code");
        String targetCurrencyCode = request.getParameter("target_currency_code");
        String rate = request.getParameter("rate");
        if (baseCurrencyCode == null || targetCurrencyCode == null || rate == null) {
            String message = "Недостаток аргументов.";
            out.print(stringMapper.mapFromStringtoJSON(message));
            response.setStatus(400);
        }
        ExchangeRate exchangeRate = currencyExchangeDAO.addExchangeQuery(baseCurrencyCode, targetCurrencyCode, rate);
        out.print(exchangeMapper.mapFromModeltoJSON(exchangeRate));
        out.flush();
    }
}