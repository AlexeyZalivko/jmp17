package exchange.dao;

import exchange.domain.Account;
import exchange.exceptions.BussinessException;
import org.apache.commons.lang3.StringUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertNotNull;

/**
 * Created by alex on 2017-06-11.
 */
public class AccountManagerTest {

    public static final String ACCOUNT_NUMBER = "26250012531";
    public static final BigDecimal AMOUNT_RUB = new BigDecimal(13.56);
    public static final BigDecimal AMOUNT_BYN = new BigDecimal(52.12);
    public static final BigDecimal AMOUNT_USD = new BigDecimal(12.1);

    private static Account account = new Account();

    private AccountManager manager = new AccountManagerImpl();

    @BeforeClass
    public static void init() {
        account.setAccountNumber(ACCOUNT_NUMBER);
        account.setAmountByn(AMOUNT_BYN);
        account.setAmountRub(AMOUNT_RUB);
        account.setAmountUsd(AMOUNT_USD);
    }

    @Test
    public void saveAccountTest() throws BussinessException {
        manager.saveAmount(account);
        final Account readedAccount = manager.getAccount(ACCOUNT_NUMBER);
        assertNotNull(readedAccount);
    }

    @Test(expected = BussinessException.class)
    public void saveAccountNullAccountNumberTest() throws BussinessException {
        manager.saveAmount(null);
    }

    @Test(expected = BussinessException.class)
    public void saveAccountEmptyAccountNumberTest() throws BussinessException {
        manager.saveAmount(new Account());
    }

    @Test(expected = BussinessException.class)
    public void readAccountWithNullAccountNumberTest() throws BussinessException {
        manager.getAccount(null);
    }

    @Test(expected = BussinessException.class)
    public void readAccountWithEmptyAccountNumberTest() throws BussinessException {
        manager.getAccount(StringUtils.EMPTY);
    }
}
