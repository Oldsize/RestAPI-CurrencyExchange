package com.example.restapi.servlets;

import com.example.restapi.dao.CurrencyDAO;
import com.example.restapi.mapper.CurrencyMapper;
import com.example.restapi.mapper.StringMapper;
import com.example.restapi.model.Currency;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet(name = "GetCurrencyServlet", value = "/currency/*")
public class GetCurrencyServlet extends HttpServlet {

    private final CurrencyMapper currencyMapper = new CurrencyMapper();

    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String pathInfo = req.getPathInfo().substring(1).toUpperCase();
        PrintWriter out = resp.getWriter();
        StringMapper stringMapper = new StringMapper();
        if (!pathInfo.matches("^[A-Z]{3}$")) {
            String message = "Передан неправильный формат валюты.";
            out.print(stringMapper.mapFromStringtoJSON(message));
            resp.setStatus(404);
            return;
        }
        CurrencyDAO currencyDAO = CurrencyDAO.getInstance();
        try {
            Currency currencyModel = currencyDAO.getCurrencyByCodeQuery(pathInfo);
            if (currencyModel == null) {
                String message = "Валюта не найдена.";
                out.print(stringMapper.mapFromStringtoJSON(message));
                resp.setStatus(404);
                return;
            }
            String jsonResponse = currencyMapper.mapFromModeltoJSON(currencyModel);
            out.print(jsonResponse);
            out.flush();
        } catch (SQLException e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}