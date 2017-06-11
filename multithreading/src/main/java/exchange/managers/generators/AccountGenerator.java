package exchange.managers.generators;

import com.google.common.collect.Lists;
import exchange.domain.Account;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Random;

/**
 * Created by alex on 2017-06-11.
 */
public class AccountGenerator {

    private static String ACCOUNT_PREFIX = "2625";

    public static List<Account> generateAccounts() {
        final Random rnd = new Random();
        final int size = rnd.nextInt(400) + 50;
        final List<Account> accounts = Lists.newArrayListWithCapacity(size);

        for (int i = 0; i < size; i++) {
            final Account account = new Account();
            account.setAccountNumber(generateAccountNumber(i, rnd));
            account.setAmountByn(getAmount(rnd.nextDouble() * 5000));
            account.setAmountRub(getAmount(rnd.nextDouble() * 6000));
            account.setAmountUsd(getAmount(rnd.nextDouble() * 7000));
            accounts.add(account);
        }
        return accounts;
    }

    private static BigDecimal getAmount(final double d){
        final DecimalFormat df = new DecimalFormat("#.##");
        return new BigDecimal(df.format(d));
    }

    private static String generateAccountNumber(final int i, final Random rnd) {
        return String.format("%s%d%d", ACCOUNT_PREFIX, rnd.nextInt(99999), i);
    }
}
