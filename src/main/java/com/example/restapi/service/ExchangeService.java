package com.example.restapi.service;

import com.example.restapi.dao.CurrencyExchangeDAO;
import com.example.restapi.dto.ExchangeDTO;
import com.example.restapi.exceptions.CurrencyNotFoundException;
import com.example.restapi.exceptions.DAOException;
import com.example.restapi.exceptions.DatabaseNotFoundException;
import com.example.restapi.model.ExchangeRate;
import com.example.restapi.utils.PreparedRequestsSQL;

import java.sql.SQLException;
import java.util.logging.Logger;

public class ExchangeService {
    private static final Logger LOGGER = Logger.getLogger(ExchangeService.class.getName());
    private final CurrencyExchangeDAO currencyExchangeDAO;

    PreparedRequestsSQL preparedRequestsSQL = new PreparedRequestsSQL();

    private static final ExchangeService INSTANCE = new ExchangeService();

    private ExchangeService() {
        currencyExchangeDAO = CurrencyExchangeDAO.getInstance();
    }

    public static ExchangeService getInstance() {
        return INSTANCE;
    }



    public ExchangeDTO currencyExchange(String baseCurrencyCode, String targetCurrencyCode) throws DAOException, DatabaseNotFoundException, CurrencyNotFoundException, SQLException {
        ExchangeRate exchangeModel = currencyExchangeDAO.getExchangeByCodeQuery(baseCurrencyCode, targetCurrencyCode);
        ExchangeDTO exchangeDTO = new ExchangeDTO (exchangeModel.getId(),
                                                   exchangeModel.getBaseCurrency().getId(),
                                                   exchangeModel.getTargetCurrency().getId(),
                                                   exchangeModel.getRate());
        return exchangeDTO;
    }
}