package exchange.managers.generators;

import exchange.domain.Account;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by alex on 2017-06-11.
 */
public class AccountGeneratorTest {
    @Test
    public void generateAccountsTest() throws Exception {
        final List<Account> accounts = AccountGenerator.generateAccounts();
        assertNotNull(accounts);
        assertTrue(accounts.size() > 0);
    }

}