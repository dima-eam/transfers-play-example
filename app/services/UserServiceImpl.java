package services;

import models.domain.Account;
import models.domain.User;
import services.storage.TransfersStorage;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.math.BigDecimal;

/**
 * Implementation of user management service. Generates account and store account and user.
 */
@Singleton
public class UserServiceImpl implements UserService {

    /**
     * {@link TransfersStorage} to store the data
     */
    private final TransfersStorage transfersStorage;

    @Inject
    public UserServiceImpl(TransfersStorage transfersStorage) {
        this.transfersStorage = transfersStorage;
    }

    /**
     * Creates user and account and stores the data.
     *
     * @param user           user's data
     * @param initialBalance account initial balance
     * @return account
     */
    @Override
    public Account create(User user, BigDecimal initialBalance) {
        Account account = Account.generate(initialBalance);
        transfersStorage.put(account, user);
        return account;
    }

}
