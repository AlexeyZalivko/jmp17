package exchange.dao;

import exchange.utils.CurrencyUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

/**
 * Created by alex on 2017-06-11.
 */
public interface CurrencyRate {
    Map<CurrencyUtils, BigDecimal> getRates() throws IOException;
}
