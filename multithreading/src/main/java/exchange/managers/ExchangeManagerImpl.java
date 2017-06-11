package exchange.managers;

import exchange.dao.AccountManager;
import exchange.dao.AccountManagerImpl;
import exchange.dao.CurrencyRate;
import exchange.dao.CurrencyRateImpl;
import exchange.domain.Account;
import exchange.utils.CurrencyUtils;

import java.math.BigDecimal;

/**
 * Created by alex on 2017-06-11.
 */
public class ExchangeManagerImpl implements ExchangeManager {

    private AccountManager accountManager = new AccountManagerImpl();
    private CurrencyRate currencyRate = new CurrencyRateImpl();

    @Override
    public Account convert(final Account account, final CurrencyUtils currency, final BigDecimal amount) {

        return null;
    }
}
