package com.example.restapi.utils;

public class PreparedRequestsSQL {

    private static final String SELECT_CURRENCY_BY_ID_SQL = """
            SELECT * FROM currencies WHERE ID = ?""";

    public String getSELECT_CURRENCY_BY_ID_SQL() {
        return SELECT_CURRENCY_BY_ID_SQL;
    }

    private static final String SELECT_ALL_EXCHANGES_SQL = """
            SELECT * FROM exchangeRates""";

    public String getSELECT_ALL_EXCHANGES_SQL() {
        return SELECT_ALL_EXCHANGES_SQL;
    }

    private static final String FIND_CURRENCY_EXCHANGE_SQL = """
            SELECT * FROM exchangeRates WHERE BaseCurrencyId = ? AND TargetCurrencyId = ?""";

    public String getFIND_CURRENCY_EXCHANGE_SQL() {
        return FIND_CURRENCY_EXCHANGE_SQL;
    }

    private static final String ADD_NEW_CURRENCY_SQL = """
            INSERT INTO currencies (Code, FullName, Sign)
            VALUES (?, ?, ?)""";

    public String getADD_NEW_CURRENCY_SQL() {
        return ADD_NEW_CURRENCY_SQL;
    }

    private static final String SELECT_ID_BY_CODE_SQL = """
            SELECT ID FROM currencies
            WHERE Code = ?""";

    public String getSELECT_ID_BY_CODE_SQL() {
        return SELECT_ID_BY_CODE_SQL;
    }

    private static final String SELECT_CURRENCY_BY_CODE_SQL = """
            SELECT * FROM currencies
            WHERE Code = ?""";

    public String getSELECT_CURRENCY_BY_CODE_SQL() {
        return SELECT_CURRENCY_BY_CODE_SQL;
    }

    private static final String SELECT_ALL_CURRENCIES_SQL = """
            SELECT * FROM currencies""";

    public String getSELECT_ALL_CURRENCIES_SQL() {
        return SELECT_ALL_CURRENCIES_SQL;
    }

    private static final String EXCHANGE_CURRENCIES_JOIN_SQL = "SELECT " +
            "exchangeRates.ID, " +
            "baseCurrency.ID as baseCurrencyId, " +
            "baseCurrency.FullName as baseCurrencyName, " +
            "baseCurrency.Code as baseCurrencyCode, " +
            "baseCurrency.Sign as baseCurrencySign, " +
            "targetCurrency.ID as targetCurrencyId, " +
            "targetCurrency.FullName as targetCurrencyName, " +
            "targetCurrency.Code as targetCurrencyCode, " +
            "targetCurrency.Sign as targetCurrencySign, " +
            "exchangeRates.Rate " +
            "FROM " +
            "exchangeRates " +
            "JOIN currencies as baseCurrency ON exchangeRates.BaseCurrencyId = baseCurrency.ID " +
            "JOIN currencies as targetCurrency ON exchangeRates.TargetCurrencyId = targetCurrency.ID " +
            "WHERE " +
            "baseCurrency.Code = ? AND targetCurrency.Code = ?;";

    public String getEXCHANGE_CURRENCIES_JOIN_SQL() {
        return EXCHANGE_CURRENCIES_JOIN_SQL;
    }
}
