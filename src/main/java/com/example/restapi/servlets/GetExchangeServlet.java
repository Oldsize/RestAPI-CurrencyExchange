package com.example.restapi.servlets;

import com.example.restapi.dao.CurrencyDAO;
import com.example.restapi.dto.ExchangeDTO;
import com.example.restapi.mapper.ExchangeMapper;
import com.example.restapi.mapper.StringMapper;
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
public class GetExchangeServlet extends HttpServlet {
    @SneakyThrows
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ExchangeMapper exchangeMapper = new ExchangeMapper();
        ExchangeService exchangeService = ExchangeService.getInstance();
        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        StringMapper stringMapper = new StringMapper();
        String pathInfo = req.getPathInfo().substring(1).toUpperCase();
        if (!pathInfo.matches("^[A-Z]{6}$")) {
            String message = "Неправильные параметры поиска обмена валют.";
            out.print(stringMapper.mapFromStringtoJSON(message));
            resp.setStatus(404);
        }
        CurrencyDAO currencyDAO = CurrencyDAO.getInstance();
        String baseCurrencyCode = pathInfo.substring(0, 3);
        String targetCurrencyCode = pathInfo.substring(3);
        ExchangeDTO exchangeDTO = exchangeService.currencyExchange(baseCurrencyCode, targetCurrencyCode);
        if (exchangeDTO == null) {
            String message = "Обменный курс не найден.";
            out.print(stringMapper.mapFromStringtoJSON(message));
            resp.setStatus(404);
        }
        Currency baseCurrency = currencyDAO.getCurrencyByCodeQuery(baseCurrencyCode);
        Currency targetCurrency = currencyDAO.getCurrencyByCodeQuery(targetCurrencyCode);
        ExchangeRate exchange = new ExchangeRate();
        exchange.setId(exchangeDTO.getId());
        exchange.setRate(exchangeDTO.getRate());
        exchange.setBaseCurrency(baseCurrency);
        exchange.setTargetCurrency(targetCurrency);
        if (exchange.getId() == null || exchange.getRate() == null || exchange.getBaseCurrency() == null || exchange.getTargetCurrency() == null) {
            String message = "Обменный курс не найден.";
            out.print(stringMapper.mapFromStringtoJSON(message));
            resp.setStatus(404);
        }
        String json = exchangeMapper.mapFromModeltoJSON(exchange);
        resp.setCharacterEncoding("UTF-8");
        out.print(json);
        out.flush();
    }
}