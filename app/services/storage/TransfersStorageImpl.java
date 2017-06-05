package services.storage;

import models.domain.Account;
import models.domain.TransferDetails;
import models.domain.User;

import javax.annotation.Nonnull;
import javax.inject.Singleton;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Quick and dirty in-memory storage.
 * Should be evolved to H2.
 */
@Singleton
public class TransfersStorageImpl implements TransfersStorage {

    private Map<String, User> userStorage = new ConcurrentHashMap<>();
    private Map<String, Account> accountStorage = new ConcurrentHashMap<>();
    private Map<String, String> emailToAccountStorage = new ConcurrentHashMap<>();

    @Override
    public void put(@Nonnull Account account, @Nonnull User user) {
        Objects.requireNonNull(account, "account");
        Objects.requireNonNull(user, "user");

        synchronized (this) {
            userStorage.putIfAbsent(user.getEmail(), user);
            accountStorage.putIfAbsent(account.getNumber(), account);
            emailToAccountStorage.putIfAbsent(user.getEmail(), account.getNumber());
        }
    }

    @Override
    public Optional<User> getUserByEmail(@Nonnull String email) {
        return Optional.ofNullable(userStorage.get(email));
    }

    @Override
    public TransferDetails transfer(@Nonnull User payer, @Nonnull User payee, @Nonnull BigDecimal sum) {
        return null;
    }

    private BigDecimal deposit(@Nonnull String account, @Nonnull BigDecimal sum) {
        return Optional.ofNullable(accountStorage.get(account))
                .map(acc -> accountStorage.compute(account, (k, v) -> v.deposit(sum)).getBalance())
                .orElseThrow(() -> new IllegalStateException("Account not found"));
    }

    private BigDecimal withdraw(@Nonnull String account, @Nonnull BigDecimal sum) {
        return Optional.ofNullable(accountStorage.get(account))
                .map(acc -> accountStorage.compute(account, (k, v) -> v.withdraw(sum)).getBalance())
                .orElseThrow(() -> new IllegalStateException("Account not found"));
    }
}
