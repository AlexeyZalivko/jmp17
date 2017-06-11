package exchange.dao;

import exchange.utils.CurrencyUtils;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by alex on 2017-06-11.
 */
public class CurrencyRateTest {

    private CurrencyRate currencyRate = new CurrencyRateImpl();

    @Test
    public void getRatesTest() throws IOException {
        Map<CurrencyUtils, BigDecimal> rates = currencyRate.getRates();

        assertNotNull(rates);
        assertNotNull(rates.get(CurrencyUtils.BYN));
        assertNotNull(rates.get(CurrencyUtils.RUB));
        assertNull(rates.get(CurrencyUtils.USD));
    }
}
