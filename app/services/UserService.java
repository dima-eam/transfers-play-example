package services;

import models.domain.Account;
import models.domain.User;

import java.math.BigDecimal;

/**
 * Service to manage users in the system.
 */
public interface UserService {

    /**
     * Creates user and account
     *
     * @param user           user data
     * @param initialBalance account initial balance
     * @return account
     */
    Account create(User user, BigDecimal initialBalance);
}
