package com.example.restapi.servlets;

import com.example.restapi.dao.CurrencyExchangeDAO;
import com.example.restapi.dto.ExchangeRateDTO;
import com.example.restapi.mapper.ExchangeMapper;
import com.example.restapi.mapper.StringMapper;
import com.example.restapi.service.ExchangeService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "getExchangeAmont", value = "/exchange-amount/*")
public class GetExchangeAmountServlet extends HttpServlet {
    @SneakyThrows
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String from = request.getParameter("from");
        String to = request.getParameter("to");
        String amount = request.getParameter("amount");
        StringMapper stringMapper = new StringMapper();
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        ExchangeMapper exchangeMapper = new ExchangeMapper();
        CurrencyExchangeDAO currencyExchangeDAO = CurrencyExchangeDAO.getInstance();
        ExchangeService exchangeService = ExchangeService.getInstance();
        ExchangeRateDTO exchangeDTO = exchangeService.currencyAmountExchange(from, to, Long.valueOf(amount));
        if (exchangeDTO == null) {
            String message = "Не удалось найти обменный курс.";
            out.print(stringMapper.mapFromStringtoJSON(message));
            response.setStatus(404);
        } else
            out.print(exchangeMapper.mapFromExchangeRateDTOtoSON(exchangeDTO));
        out.flush();
    }
}
