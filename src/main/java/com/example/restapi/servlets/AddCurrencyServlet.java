package com.example.restapi.servlets;

import com.example.restapi.dao.CurrencyDAO;
import com.example.restapi.dto.CurrencyDTO;
import com.example.restapi.mapper.CurrencyMapper;
import com.example.restapi.mapper.StringMapper;
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
        PrintWriter out = resp.getWriter();
        CurrencyMapper currencyMapper = new CurrencyMapper();
        String parameterCode = req.getParameter("code");
        String parameterFullName = req.getParameter("fullname");
        resp.setContentType("application/json");
        String parameterSign = req.getParameter("sign");
        StringMapper stringMapper = new StringMapper();
        if (parameterSign == null || parameterCode == null || parameterFullName == null) {
            String message = "Недостаток параметров.";
            out.println(stringMapper.mapFromStringtoJSON(message));
            resp.setStatus(400);
            return;
        } else if (!parameterCode.matches("^(?!.*(.).*\\1)[A-Z]{3}$") ||
                !parameterFullName.matches("^(?=[a-zA-Z\\s]{1,30}$)(?=(?:.*[A-Z]){1,3})([a-zA-Z\\s]+)$") ||
                !parameterSign.matches("^[A-Z\\p{Punct}]$")) {
            String message = "Переданы неподходящие параметры валюты.";
            out.println(stringMapper.mapFromStringtoJSON(message));
            resp.setStatus(400);
            return;
        } else {
            CurrencyDAO currencyDAO = CurrencyDAO.getInstance();
            CurrencyDTO currencyDTO = new CurrencyDTO(parameterCode, parameterFullName, parameterSign);
            currencyDAO.addCurrencyQuery(currencyDTO);
            out.print(currencyMapper.mapFromDTOtoJSON(currencyDTO));
            out.flush();
        }
    }
}