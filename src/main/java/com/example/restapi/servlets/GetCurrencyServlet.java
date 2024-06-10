package com.example.restapi.servlets;

import com.example.restapi.dao.CurrencyDAO;
import com.example.restapi.exceptions.CurrencyNotFoundException;
import com.example.restapi.mapper.CurrencyMapper;
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
        String pathInfo = req.getPathInfo().substring(1).toUpperCase();
        if (!pathInfo.matches("^[A-Z]{3}$")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid currency code format");
            return;
        }

        CurrencyDAO currencyDAO = CurrencyDAO.getInstance();
        try {
            Currency currencyModel = currencyDAO.getCurrencyByCodeQuery(pathInfo);
            if (currencyModel == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Currency not found");
            }

            String jsonResponse = currencyMapper.mapFromModeltoJSON(currencyModel);
            resp.setContentType("application/json");
            PrintWriter out = resp.getWriter();
            out.print(jsonResponse);
            out.flush();
        } catch (SQLException e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (CurrencyNotFoundException e) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Currency not found");
        }
    }
}