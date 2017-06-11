package exchange.domain;

import exchange.utils.CurrencyUtils;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by alex on 2017-06-11.
 */
@Data
public class Account {
    private String accountNumber;
    private BigDecimal amountUsd;
    private BigDecimal amountByn;
    private BigDecimal amountRub;

    public Account() {
    }

    public Account(final String accountNumber, final BigDecimal amountUsd, final BigDecimal amountByn, final BigDecimal amountRub) {
        this.accountNumber = accountNumber;
        this.amountUsd = amountUsd;
        this.amountByn = amountByn;
        this.amountRub = amountRub;
    }

    public BigDecimal getAmountByCurrrencyType(CurrencyUtils currency) {
        switch (currency) {
            case BYN: {
                return getAmountByn();
            }
            case RUB: {
                return getAmountRub();
            }
            case USD: {
                return getAmountUsd();
            }
            default: {
                return null;
            }
        }
    }
}
