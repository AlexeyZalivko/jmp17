package exchange.utils;

/**
 * Created by alex on 2017-06-11.
 */
public enum CurrencyUtils {
    BYN("BYN"),
    RUB("RUB"),
    USD("USD");

    CurrencyUtils(final String currency) {
        this.currency = currency;
    }

    public static CurrencyUtils getCurrency(final String currency) {
        return CurrencyUtils.valueOf(currency);
    }

    private String currency;
}
