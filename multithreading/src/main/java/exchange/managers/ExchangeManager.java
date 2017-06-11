package exchange.managers;

import exchange.domain.Account;
import exchange.utils.CurrencyUtils;

import java.math.BigDecimal;

/**
 * Created by alex on 2017-06-11.
 */
public interface ExchangeManager {
    Account convert(Account account, CurrencyUtils fromCurrency, CurrencyUtils toCurrency, BigDecimal amount);
}
