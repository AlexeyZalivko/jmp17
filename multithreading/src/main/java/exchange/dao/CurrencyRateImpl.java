package exchange.dao;

import exchange.utils.CurrencyUtils;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Map;
import java.util.Random;

import static exchange.utils.CurrencyUtils.getCurrency;
import static exchange.utils.PathUtils.UTF_8;
import static exchange.utils.PathUtils.getFilePath;
import static java.util.stream.Collectors.toMap;

/**
 * Created by alex on 2017-06-11.
 */
public class CurrencyRateImpl implements CurrencyRate {
    public static final String ACCOUNTS_DELIMITER = "; ";
    public static final String PARAMS_DELIMITER = ": ";

    @Override
    public synchronized Map<CurrencyUtils, BigDecimal> getRates() throws IOException {
        final File file = new File(getFilePath("currency"));
        if (!file.exists()) {
            FileUtils.writeStringToFile(file, generateCurrencyRates(), UTF_8);
        }

        return getRates(FileUtils.readFileToString(file, UTF_8));
    }

    private String generateCurrencyRates() {
        final Random rnd = new Random();
        return String.format("BYN: %f; RUB: %f; ", rnd.nextDouble() * 2, rnd.nextDouble() * 60);
    }

    private Map<CurrencyUtils, BigDecimal> getRates(final String rates) {
        return Arrays.asList(rates.split(ACCOUNTS_DELIMITER))
                .stream()
                .collect(toMap(
                        x -> getCurrency(x.split(PARAMS_DELIMITER)[0]),
                        x -> new BigDecimal(x.split(PARAMS_DELIMITER)[1])
                ));
    }
}
