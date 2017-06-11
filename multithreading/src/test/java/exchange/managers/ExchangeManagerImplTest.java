package exchange.managers;

import exchange.dao.CurrencyRate;
import exchange.dao.CurrencyRateImpl;
import exchange.domain.Account;
import exchange.utils.CurrencyUtils;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by alex on 2017-06-11.
 */
public class ExchangeManagerImplTest {

    private static Account account;

    private ExchangeManager exchangeManager = new ExchangeManagerImpl();
    private CurrencyRate currencyRate = new CurrencyRateImpl();

    private MathContext mathContext = new MathContext(2, RoundingMode.HALF_UP);

    @Before
    public void init() {
        account = new Account();
        account.setAccountNumber("");
        account.setAmountByn(BigDecimal.valueOf(120.56));
        account.setAmountRub(BigDecimal.valueOf(73.33));
        account.setAmountUsd(BigDecimal.valueOf(28.51));
    }

    @Test
    public void convertByn2Rub() throws Exception {
        final BigDecimal amount = BigDecimal.valueOf(7);
        final Account result = exchangeManager.convert(account, CurrencyUtils.BYN, CurrencyUtils.RUB, amount);
        assertNotNull(result);

        final BigDecimal usdAmount = amount.divide(currencyRate.getRateByCurrency(CurrencyUtils.BYN), mathContext);
        final BigDecimal bynAmount = account.getAmountByn().subtract(amount);
        final BigDecimal rubAmount = account.getAmountRub().add(currencyRate.getRateByCurrency(CurrencyUtils.RUB).multiply(usdAmount, mathContext));

        assertEquals(result.getAmountUsd(), account.getAmountUsd());
        assertEquals(result.getAmountByn(), bynAmount);
        assertEquals(result.getAmountRub(), rubAmount);
    }

    @Test
    public void convertByn2Usd() throws Exception {
        System.out.println(account);
        final BigDecimal amount = BigDecimal.valueOf(2.33);
        final Account result = exchangeManager.convert(account, CurrencyUtils.BYN, CurrencyUtils.USD, amount);

        final BigDecimal usdAmount = amount.divide(currencyRate.getRateByCurrency(CurrencyUtils.BYN), mathContext);
        final BigDecimal bynAmount = account.getAmountByn().subtract(amount);

        assertEquals(result.getAmountUsd(), account.getAmountUsd().add(usdAmount));
        assertEquals(result.getAmountByn(), bynAmount);
        assertEquals(result.getAmountRub(), account.getAmountRub());

        assertNotNull(result);
        System.out.println(result);
    }

}