package exchange.managers;

import exchange.dao.CurrencyRate;
import exchange.dao.CurrencyRateImpl;
import exchange.domain.Account;
import exchange.utils.CurrencyUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.logging.Logger;

/**
 * Created by alex on 2017-06-11.
 */
public class ExchangeManagerImpl implements ExchangeManager {

    private Logger log = Logger.getLogger(ExchangeManagerImpl.class.getName());

    private CurrencyRate currencyRate = new CurrencyRateImpl();

    @Override
    public synchronized Account convert(final Account account, final CurrencyUtils fromCurrency, final CurrencyUtils toCurrency, final BigDecimal amount) {
        final BigDecimal originalAmount = getCurrencyAmount(account, fromCurrency);
        if (originalAmount == null) {
            return account;
        }

        BigDecimal validAmount = amount;
        final BigDecimal fromAmount = account.getAmountByCurrrencyType(fromCurrency);
        if (fromAmount == null || fromAmount.compareTo(amount) == -1) {
            log.warning("Amount to exchange (" + fromAmount + ")is less than account amount (" + amount + "). I'll change only " + fromAmount);
            validAmount = fromAmount;
        }

        BigDecimal toRate = null;
        BigDecimal fromRate = null;
        try {
            toRate = currencyRate.getRateByCurrency(toCurrency);
            fromRate = currencyRate.getRateByCurrency(fromCurrency);
        } catch (IOException e) {
            log.warning("Rate isn't found: " + toCurrency.toString());
            return account;
        }

        if (toRate == null || fromRate == null) {
            log.warning("One of rates is null");
            return account;
        }

        final MathContext mathContext = new MathContext(2, RoundingMode.HALF_UP);
        BigDecimal toAmount = null;
        try {
            BigDecimal usdValue = validAmount.divide(currencyRate.getRateByCurrency(fromCurrency), mathContext);
            toAmount = usdValue.multiply(currencyRate.getRateByCurrency(toCurrency), mathContext);
        } catch (IOException e) {
            log.warning("Error convert amount: " + toCurrency.toString());
            return account;
        }

        final Account resultAccount = new Account(account.getAccountNumber(), account.getAmountUsd(), account.getAmountByn(), account.getAmountRub());
        if (toAmount != null) {
            setAmount(resultAccount, fromAmount.subtract(validAmount), fromCurrency);
            setAmount(resultAccount, account.getAmountByCurrrencyType(toCurrency).add(toAmount), toCurrency);
        }

        return resultAccount;
    }

    private Account setAmount(final Account account, final BigDecimal newAmount, final CurrencyUtils currency) {
        switch (currency) {
            case BYN: {
                account.setAmountByn(newAmount);
                break;
            }
            case RUB: {
                account.setAmountRub(newAmount);
                break;
            }
            case USD: {
                account.setAmountUsd(newAmount);
                break;
            }
        }
        return account;
    }

    private BigDecimal getCurrencyAmount(final Account account, final CurrencyUtils currency) {
        switch (currency) {
            case BYN: {
                return account.getAmountByn();
            }
            case RUB: {
                return account.getAmountRub();
            }
            case USD: {
                return account.getAmountUsd();
            }
            default: {
                log.warning("Account type " + currency.toString() + " isn't find.");
                return null;
            }
        }
    }
}
