package com.example.restapi.dao;

import com.example.restapi.exceptions.DAOException;
import com.example.restapi.exceptions.DatabaseNotFoundException;
import com.example.restapi.model.Currency;
import com.example.restapi.model.ExchangeRate;
import com.example.restapi.utils.ConnectionManager;
import com.example.restapi.utils.PreparedRequestsSQL;
import java.sql.*;
import java.util.function.Consumer;

public class CurrencyExchangeDAO {
    private CurrencyExchangeDAO() {
    }

    CurrencyDAO currencyDAO = CurrencyDAO.getInstance();
    private static final CurrencyExchangeDAO INSTANCE;

    static {
        INSTANCE = new CurrencyExchangeDAO();
    }

    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.out.println("Failed to load JDBC driver.");
        }
    }

    public static CurrencyExchangeDAO getInstance() {
        return INSTANCE;
    }

    private ConnectionManager connectionManager = ConnectionManager.getInstance();

    PreparedRequestsSQL preparedRequestsSQL = new PreparedRequestsSQL();

    {
        try (Connection connection = connectionManager.getConnection()) {
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (DatabaseNotFoundException e) {
            e.printStackTrace();
        }
    }


    public ExchangeRate addExchangeQuery(String baseCurrencyCode, String targetCurrencyCode, String rate) throws DAOException, SQLException, DatabaseNotFoundException {
        String baseCurrencyId = currencyDAO.getCurrencyIdByCodeQuery(baseCurrencyCode);
        String targetCurrencyId = currencyDAO.getCurrencyIdByCodeQuery(targetCurrencyCode);
        if (!baseCurrencyCode.matches("^[a-zA-Z]{3}$") || !targetCurrencyCode.matches("^[a-zA-Z]{3}$"))
            throw new IllegalArgumentException();
         else if (!rate.matches("^\\d{1,3}\\.\\d{3,4}$"))
            throw new IllegalArgumentException();
         else {
            try (Connection connection = connectionManager.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(preparedRequestsSQL.getADD_NEW_EXCHANGE_SQL())) {
                preparedStatement.setString(1, baseCurrencyId);
                preparedStatement.setString(2, targetCurrencyId);
                preparedStatement.setString(3, rate);
                ResultSet addedExchange = preparedStatement.executeQuery();
                Currency baseCurrency = currencyDAO.getCurrencyByCodeQuery(baseCurrencyCode);
                Currency targetCurrency = currencyDAO.getCurrencyByCodeQuery(targetCurrencyCode);
                ExchangeRate exchangeRate = new ExchangeRate();
                exchangeRate.setBaseCurrency(baseCurrency);
                exchangeRate.setTargetCurrency(targetCurrency);
                exchangeRate.setRate(addedExchange.getBigDecimal("Rate"));
                return exchangeRate;
            }
        }
    }

    public ExchangeRate getExchangeByCodeQuery(String baseCurrencyCode, String targetCurrencyCode) throws DAOException, DatabaseNotFoundException, SQLException {
        try (Connection connection = connectionManager.getConnection()) {
            Currency baseCurrency = currencyDAO.getCurrencyByCodeQuery(baseCurrencyCode);
            if (baseCurrency == null)
                return null;

            Long baseCurrencyId = baseCurrency.getId();
            Currency targetCurrency = currencyDAO.getCurrencyByCodeQuery(targetCurrencyCode);
            if (targetCurrency == null)
                return null;

            Long targetCurrencyId = targetCurrency.getId();

            try (PreparedStatement queryFindExchange = connection.prepareStatement(preparedRequestsSQL.getFIND_CURRENCY_EXCHANGE_SQL())) {
                queryFindExchange.setLong(1, baseCurrencyId);
                queryFindExchange.setLong(2, targetCurrencyId);
                ResultSet exchangeCurrenciesResultSet = queryFindExchange.executeQuery();
                ExchangeRate exchange = new ExchangeRate();
                exchange.setId(exchangeCurrenciesResultSet.getLong("ID"));
                exchange.setBaseCurrency(baseCurrency);
                exchange.setTargetCurrency(targetCurrency);
                exchange.setRate(exchangeCurrenciesResultSet.getBigDecimal("Rate"));
                return exchange;
            }
        }
    }

    public void getAllExchangesQuery(Consumer<ResultSet> consumer) throws DAOException, SQLException, DatabaseNotFoundException {
        try (Connection connection = connectionManager.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(preparedRequestsSQL.getSELECT_ALL_EXCHANGES_SQL())) {
            consumer.accept(resultSet);
        }
    }
}
