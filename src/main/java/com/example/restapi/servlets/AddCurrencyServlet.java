package com.example.restapi.servlets;

import com.example.restapi.dao.CurrencyDAO;
import com.example.restapi.dto.CurrencyDTO;
import com.example.restapi.mapper.CurrencyMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "addCurrency", value = "/add/currency/*")
public class AddCurrencyServlet extends HttpServlet {

    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String parameterCode = req.getParameter("code");
        String parameterFullName = req.getParameter("fullname");
        String parameterSign = req.getParameter("sign");
        if (parameterSign == null || parameterCode == null || parameterFullName == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing parameters");
            return;
        } else if (!parameterCode.matches("^(?!.*(.).*\\1)[A-Z]{3}$") ||
                !parameterFullName.matches("^(?=[a-zA-Z\\s]{1,30}$)(?=(?:.*[A-Z]){1,3})([a-zA-Z\\s]+)$") ||
                !parameterSign.matches("^[A-Z\\p{Punct}]$")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid parameters format");
            return;
        } else {
            PrintWriter out = resp.getWriter();
            CurrencyDAO currencyDAO = CurrencyDAO.getInstance();
            CurrencyDTO currencyDTO = new CurrencyDTO(parameterCode, parameterFullName, parameterSign);
            currencyDAO.addCurrencyQuery(currencyDTO);
            CurrencyMapper currencyMapper = new CurrencyMapper();
            out.print(currencyMapper.mapFromDTOtoJSON(currencyDTO));
            out.flush();
        }
    }
}