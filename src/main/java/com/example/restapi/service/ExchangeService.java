package com.example.restapi.service;

import com.example.restapi.dao.CurrencyExchangeDAO;
import com.example.restapi.dto.ExchangeDTO;
import com.example.restapi.dto.ExchangeRateDTO;
import com.example.restapi.exceptions.DAOException;
import com.example.restapi.exceptions.DatabaseNotFoundException;
import com.example.restapi.model.ExchangeRate;
import com.example.restapi.utils.PreparedRequestsSQL;
import java.math.BigDecimal;
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

    public ExchangeRateDTO currencyAmountExchange(String baseCurrencyCode,
                                                  String targetCurrencyCode,
                                                  Long amount) throws DAOException, SQLException, DatabaseNotFoundException {
        if (!baseCurrencyCode.matches("^[a-zA-Z]{3}$") || !targetCurrencyCode.matches("^[a-zA-Z]{3}$"))
            return null;
        else if (amount == null)
            return null;
        ExchangeRate exchangeRate = currencyExchangeDAO.getExchangeByCodeQuery(baseCurrencyCode, targetCurrencyCode);
        if (exchangeRate == null)
            return null;
        BigDecimal amountBigDecimal = new BigDecimal(amount);
        BigDecimal convertedAmount = amountBigDecimal.multiply(exchangeRate.getRate());
        ExchangeRateDTO exchangeRateDTO = new ExchangeRateDTO(exchangeRate.getId(),
                                                              exchangeRate.getBaseCurrency(),
                                                              exchangeRate.getTargetCurrency(),
                                                              exchangeRate.getRate(),
                                                              amount,
                                                              convertedAmount);
        return exchangeRateDTO;
    }

    public ExchangeDTO currencyExchange(String baseCurrencyCode, String targetCurrencyCode) throws DAOException, DatabaseNotFoundException, SQLException {
        ExchangeRate exchangeModel = currencyExchangeDAO.getExchangeByCodeQuery(baseCurrencyCode, targetCurrencyCode);
        if (exchangeModel == null)
            return null;

        ExchangeDTO exchangeDTO = new ExchangeDTO(exchangeModel.getId(),
                    exchangeModel.getBaseCurrency().getId(),
                    exchangeModel.getTargetCurrency().getId(),
                    exchangeModel.getRate());
        return exchangeDTO;
    }
}