package exchange.dao;

import exchange.domain.Account;
import exchange.exceptions.BussinessException;

import java.math.BigDecimal;

/**
 * Created by alex on 2017-06-11.
 */
public interface AccountManager {

    Account getAccount(final String accountNumber) throws BussinessException;

    void saveAmount(final Account account) throws BussinessException;
}
