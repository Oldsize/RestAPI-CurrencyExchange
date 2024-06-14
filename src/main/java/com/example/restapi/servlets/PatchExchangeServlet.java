package com.example.restapi.servlets;

import com.example.restapi.dao.CurrencyExchangeDAO;
import com.example.restapi.mapper.ExchangeMapper;
import com.example.restapi.mapper.StringMapper;
import com.example.restapi.model.ExchangeRate;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "patchExchange", value = "/patch/exchange/*")
public class PatchExchangeServlet extends HttpServlet {
    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CurrencyExchangeDAO currencyExchangeDAO = CurrencyExchangeDAO.getInstance();
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        StringMapper stringMapper = new StringMapper();
        ExchangeMapper exchangeMapper = new ExchangeMapper();
        String idParam = req.getParameter("id");
        String rateParam = req.getParameter("rate");
        if (idParam == null || rateParam == null) {
            String message = "Недостаток параметров.";
            out.print(stringMapper.mapFromStringtoJSON(message));
            resp.setStatus(400);
        }
        ExchangeRate exchangeRate = currencyExchangeDAO.updateExchangeRateQuery(idParam, rateParam);
        if (exchangeRate == null) {
            String message = "Не удалось обновить обменный курс.";
            out.print(stringMapper.mapFromStringtoJSON(message));
            resp.setStatus(500);
        }
        String JSON = exchangeMapper.mapFromModeltoJSON(exchangeRate);
        out.print(JSON);
        out.flush();
    }
}
