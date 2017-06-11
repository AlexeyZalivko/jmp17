package exchange.dao;

import exchange.domain.Account;
import exchange.exceptions.BussinessException;
import exchange.utils.CurrencyUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Map;
import java.util.logging.Logger;

import static exchange.utils.CurrencyUtils.*;
import static exchange.utils.PathUtils.*;
import static java.util.stream.Collectors.toMap;

/**
 * Created by alex on 2017-06-11.
 */
public class AccountManagerImpl implements AccountManager {

    public static final String ACCOUNTS_DELIMITER = "; ";
    public static final String PARAMS_DELIMITER = ": ";

    final Logger log = Logger.getLogger(AccountManagerImpl.class.getName());

    public synchronized Account getAccount(final String accountNumber) throws BussinessException {
        if (StringUtils.isEmpty(accountNumber)) {
            throw new BussinessException("Account number is null or empty: " + accountNumber);
        }
        try {
            return getAccountByString(FileUtils.readFileToString(new File(getFilePath(accountNumber)), UTF_8));
        } catch (IOException e) {
            log.warning("Can't load account: " + e.getMessage());
            throw new BussinessException(e.getMessage(), e);
        }
    }

    public synchronized void saveAmount(final Account account) throws BussinessException {
        if (account == null || StringUtils.isEmpty(account.getAccountNumber())) {
            throw new BussinessException("Account is null");
        }

        final File file = new File(getFilePath(account.getAccountNumber()));

        if (file.exists()) {
            FileUtils.deleteQuietly(file);
        }

        try {
            FileUtils.writeStringToFile(file, account2String(account), UTF_8);
        } catch (IOException e) {
            log.warning("Can't save account: " + e.getMessage());
            throw new BussinessException(e.getMessage(), e);
        }
    }

    private Account getAccountByString(final String text) {
        final Account account = new Account();
        final Map<CurrencyUtils, String> accounts = Arrays.asList(text.split(ACCOUNTS_DELIMITER))
                .stream()
                .collect(toMap(
                        x -> getCurrency(x.split(PARAMS_DELIMITER)[0]),
                        x -> x.split(PARAMS_DELIMITER)[1]
                ));

        account.setAccountNumber(text);
        account.setAmountByn(new BigDecimal(accounts.get(BYN)));
        account.setAmountRub(new BigDecimal(accounts.get(RUB)));
        account.setAmountUsd(new BigDecimal(accounts.get(USD)));

        return account;
    }

    private String account2String(final Account account) {
        final NumberFormat nf = NumberFormat.getInstance();
        nf.setMinimumFractionDigits(0);
        nf.setMaximumFractionDigits(2);
        return String.format("%s: %s; %s: %s; %s: %s; ",
                BYN, nf.format(account.getAmountByn()),
                RUB, nf.format(account.getAmountRub()),
                USD, nf.format(account.getAmountUsd()));
    }
}
