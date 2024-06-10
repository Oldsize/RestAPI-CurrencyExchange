package com.example.restapi.servlets;

import com.example.restapi.dao.CurrencyDAO;
import com.example.restapi.dto.ExchangeDTO;
import com.example.restapi.mapper.ExchangeMapper;
import com.example.restapi.model.Currency;
import com.example.restapi.model.ExchangeRate;
import com.example.restapi.service.ExchangeService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;

import java.io.IOException;
import java.io.PrintWriter;


@WebServlet(name = "getExchange", value = "/exchange/*")
public class GetCurrencyExchangeServlet extends HttpServlet {
    @SneakyThrows
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ExchangeService exchangeService = ExchangeService.getInstance();
        String pathInfo = req.getPathInfo().substring(1);
        if (!pathInfo.matches("^[A-Z]{6}$")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
        CurrencyDAO currencyDAO = CurrencyDAO.getInstance();
        String baseCurrencyCode = pathInfo.substring(0, 3);
        String targetCurrencyCode = pathInfo.substring(3);
        ExchangeDTO exchangeDTO = exchangeService.currencyExchange(baseCurrencyCode, targetCurrencyCode);
        ExchangeMapper exchangeMapper = new ExchangeMapper();
        Currency baseCurrency = currencyDAO.getCurrencyByCodeQuery(baseCurrencyCode);
        Currency targetCurrency = currencyDAO.getCurrencyByCodeQuery(targetCurrencyCode);
        ExchangeRate exchange = new ExchangeRate();
        exchange.setId(exchangeDTO.getId());
        exchange.setRate(exchangeDTO.getRate());
        exchange.setBaseCurrency(baseCurrency);
        exchange.setTargetCurrency(targetCurrency);
        String json = exchangeMapper.mapFromModeltoJSON(exchange);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        out.print(json);
        out.flush();
    }
}
