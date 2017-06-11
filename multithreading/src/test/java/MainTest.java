import exchange.dao.AccountManager;
import exchange.dao.AccountManagerImpl;
import exchange.domain.Account;
import exchange.exceptions.BussinessException;
import exchange.managers.ExchangeManager;
import exchange.managers.ExchangeManagerImpl;
import exchange.managers.generators.AccountGenerator;
import exchange.utils.CurrencyUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

/**
 * Created by alex on 2017-06-11.
 */
public class MainTest {
    private Logger log = Logger.getLogger(MainTest.class.getName());

    private AccountManager accountManager = new AccountManagerImpl();
    private ExchangeManager exchangeManager = new ExchangeManagerImpl();

    private static List<Account> accounts = null;

    private Object monitor = new Object();

    @BeforeClass
    public static void init() {
        accounts = AccountGenerator.generateAccounts();
    }

    @Test
    public void multithreadTest() throws BussinessException {

        for (Account account : accounts) {
            accountManager.saveAmount(account);
        }

        ExecutorService executorService = Executors.newFixedThreadPool(10);

        final Random rnd = new Random();

        final Runnable runnable1 = () -> {
            for (int i = 0; i < accounts.size(); i += 3) {
                final CurrencyUtils fromCurrency = getCurrencyByRnadomNumber(rnd.nextInt(3));
                final CurrencyUtils toCurrency = getCurrencyByRnadomNumber(rnd.nextInt(3));
                final BigDecimal amount = getAmount(rnd.nextDouble() * 6000);
                log.info(Thread.currentThread().getName() + "; from: " + fromCurrency + "; to: " + toCurrency + "; with amount: " + amount);

                synchronized (monitor) {
                    Account account = null;
                    try {
                        account = accountManager.getAccount(accounts.get(i).getAccountNumber());
                    } catch (BussinessException e) {
                        log.warning("Error while load account: " + e.getMessage());
                    }
                    log.info(Thread.currentThread().getName() + "; account (before): " + account);

                    final Account resultAccount = exchangeManager.convert(account, fromCurrency, toCurrency, amount);

                    log.info(Thread.currentThread().getName() + "; account (after): " + account);
                    try {
                        accountManager.saveAmount(resultAccount);
                    } catch (BussinessException e) {
                        log.warning("Error while save account: " + e.getMessage());
                    }
                }
            }
        };

        final Runnable runnable2 = () -> {
            for (int i = 0; i < accounts.size(); i += 2) {
                final CurrencyUtils fromCurrency = getCurrencyByRnadomNumber(rnd.nextInt(3));
                final CurrencyUtils toCurrency = getCurrencyByRnadomNumber(rnd.nextInt(3));
                final BigDecimal amount = getAmount(rnd.nextDouble() * 6000);
                log.info(Thread.currentThread().getName() + "; from: " + fromCurrency + "; to: " + toCurrency + "; with amount: " + amount);

                synchronized (monitor) {
                    Account account = null;
                    try {
                        account = accountManager.getAccount(accounts.get(i).getAccountNumber());
                    } catch (BussinessException e) {
                        log.warning("Error while load account: " + e.getMessage());
                    }
                    log.info(Thread.currentThread().getName() + "; account (before): " + account);

                    final Account resultAccount = exchangeManager.convert(account, fromCurrency, toCurrency, amount);

                    log.info(Thread.currentThread().getName() + "; account (after): " + account);
                    try {
                        accountManager.saveAmount(resultAccount);
                    } catch (BussinessException e) {
                        log.warning("Error while save account: " + e.getMessage());
                    }
                }
            }
        };

        executorService.submit(runnable1);
        executorService.submit(runnable2);
    }

    private static BigDecimal getAmount(final double d) {
        final DecimalFormat df = new DecimalFormat("#.##");
        return new BigDecimal(df.format(d));
    }

    private CurrencyUtils getCurrencyByRnadomNumber(final int num) {
        switch (num) {
            case 0: {
                return CurrencyUtils.BYN;
            }
            case 1: {
                return CurrencyUtils.RUB;
            }
            case 3: {
                return CurrencyUtils.USD;
            }
            default: {
                return CurrencyUtils.USD;
            }
        }
    }

}
