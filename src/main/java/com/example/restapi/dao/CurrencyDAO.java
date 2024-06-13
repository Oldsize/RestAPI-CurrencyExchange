package com.example.restapi.dao;

import com.example.restapi.exceptions.DAOException;
import com.example.restapi.exceptions.DatabaseNotFoundException;
import com.example.restapi.dto.CurrencyDTO;
import com.example.restapi.model.Currency;
import com.example.restapi.utils.ConnectionManager;
import com.example.restapi.utils.PreparedRequestsSQL;

import java.sql.*;
import java.util.function.Consumer;

public class CurrencyDAO {
    private CurrencyDAO() {
    }

    ConnectionManager connectionManager = ConnectionManager.getInstance();

    private static final CurrencyDAO INSTANCE = new CurrencyDAO();

    public static CurrencyDAO getInstance() {
        return INSTANCE;
    }

    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.out.println("Failed to load JDBC driver.");
        }
    }

    PreparedRequestsSQL preparedRequestsSQL = new PreparedRequestsSQL();

    public Currency getCurrencyByCodeQuery(String code) throws SQLException, DatabaseNotFoundException, DAOException {
        String sql = preparedRequestsSQL.getSELECT_CURRENCY_BY_CODE_SQL();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, code);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Long currencyId = resultSet.getLong("ID");
                    String currencyCode = resultSet.getString("Code");
                    String fullName = resultSet.getString("FullName");
                    String sign = resultSet.getString("Sign");
                    return new Currency(currencyId, currencyCode, fullName, sign);
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new DAOException();
        }
    }

    public void getAllCurrenciesQuery(Consumer<ResultSet> consumer) throws SQLException, DatabaseNotFoundException, DAOException {
        try (Connection connection = connectionManager.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(preparedRequestsSQL.getSELECT_ALL_CURRENCIES_SQL())) {
            consumer.accept(resultSet);
        }
    }

    public String getCurrencyIdByCodeQuery(String code) throws DAOException, SQLException, DatabaseNotFoundException {
        if (code == null || code.isEmpty()) {
            throw new IllegalArgumentException("Code cannot be null or empty");
        } else if (!code.matches("^[A-Za-z]{3}$")) {
            throw new IllegalArgumentException("Code contains invalid characters");
        } else {
            try (Connection connection = connectionManager.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(preparedRequestsSQL.getSELECT_ID_BY_CODE_SQL())) {
                preparedStatement.setString(1, code);
                ResultSet resultSet = preparedStatement.executeQuery();
                String currencyId = resultSet.getString("ID");
                return currencyId;
            }
        }
    }

    public void executeUpdate(String query) throws SQLException, DAOException, DatabaseNotFoundException {
        try (Connection connection = connectionManager.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            throw new DAOException();
        }
    }

    public Currency getCurrencyByIdQuery(String id) throws SQLException, DatabaseNotFoundException, DAOException {
        if (!id.matches("^(?:[1-9]?[0-9]|0)$"))
            throw new IllegalArgumentException();

        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(preparedRequestsSQL.getSELECT_CURRENCY_BY_ID_SQL())) {
            preparedStatement.setString(1, id);
            ResultSet currencyResultSet = preparedStatement.executeQuery();
            Currency currency = new Currency();
            currency.setId(currencyResultSet.getLong("ID"));
            currency.setCode(currencyResultSet.getString("Code"));
            currency.setFullName(currencyResultSet.getString("FullName"));
            currency.setSign(currencyResultSet.getString("Sign"));
            return currency;
        } catch (SQLException e) {
            throw new DAOException();
        }
    }

    public void addCurrencyQuery(CurrencyDTO currencyDTO) throws DatabaseNotFoundException, DAOException, SQLException {
        try (Connection connection = connectionManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(preparedRequestsSQL.getADD_NEW_CURRENCY_SQL());
            preparedStatement.setString(1, currencyDTO.getCode());
            preparedStatement.setString(2, currencyDTO.getFullName());
            preparedStatement.setString(3, currencyDTO.getSign());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DAOException();
        }
    }
}
