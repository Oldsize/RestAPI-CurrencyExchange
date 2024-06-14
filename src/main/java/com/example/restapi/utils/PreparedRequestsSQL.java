package com.example.restapi.utils;

public class PreparedRequestsSQL {

    private static final String SELECT_CURRENCY_BY_ID_SQL = """
            SELECT * FROM currencies 
            WHERE ID = ?""";

    public String getSELECT_CURRENCY_BY_ID_SQL() {
        return SELECT_CURRENCY_BY_ID_SQL;
    }

    private static final String SELECT_ALL_EXCHANGES_SQL = """
            SELECT * FROM exchangeRates""";

    public String getSELECT_ALL_EXCHANGES_SQL() {
        return SELECT_ALL_EXCHANGES_SQL;
    }

    private static final String SELECT_CURRENCY_EXCHANGE_SQL = """
            SELECT * FROM exchangeRates WHERE BaseCurrencyId = ?
            AND TargetCurrencyId = ?""";

    public String getFIND_CURRENCY_EXCHANGE_SQL() {
        return SELECT_CURRENCY_EXCHANGE_SQL;
    }

    private static final String ADD_NEW_CURRENCY_SQL = """
            INSERT INTO currencies (Code, FullName, Sign)
            VALUES (?, ?, ?)""";

    public String getADD_NEW_CURRENCY_SQL() {
        return ADD_NEW_CURRENCY_SQL;
    }

    private static final String ADD_NEW_EXCHANGE_SQL = """
            INSERT INTO exchangeRates (BaseCurrencyId, TargetCurrencyId, Rate) 
            VALUES (?, ?, ?)""";

    public String getADD_NEW_EXCHANGE_SQL() {
        return ADD_NEW_EXCHANGE_SQL;
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

    private static final String UPDATE_RATE_SQL = """
            UPDATE exchangeRates
            SET Rate = ?
            WHERE ID = ?;""";

    public String getUPDATE_RATE_SQL() {
        return UPDATE_RATE_SQL;
    }

    private static final String SELECT_EXCHANGE_BY_ID_SQL = """
            SELECT ID, BaseCurrencyId, TargetCurrencyId, Rate FROM exchangeRates
            WHERE ID = ?""";

    public String getSELECT_EXCHANGE_BY_ID_SQL() {
        return getSELECT_EXCHANGE_BY_ID_SQL();
    }
}
