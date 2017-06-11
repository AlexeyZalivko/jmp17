package exchange.domain;

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
}
