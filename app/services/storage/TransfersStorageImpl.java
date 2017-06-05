package services.storage;

import models.domain.Account;
import models.domain.User;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Singleton;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Quick and dirty in-memory storage. Uses the simplest syncronization to provide update atomicity.
 * Should be evolved to H2.
 */
@Singleton
public class TransfersStorageImpl implements TransfersStorage {

    private final Map<String, User> userStorage = new ConcurrentHashMap<>();
    private final Map<String, Account> accountStorage = new ConcurrentHashMap<>();
    private final Map<String, String> emailToAccountStorage = new ConcurrentHashMap<>();

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
    public Optional<Account> getUserAccountByEmail(@Nonnull String email) {
        Objects.requireNonNull(email, "email");

        return Optional.ofNullable(userStorage.get(email))
                .map(this::getAccountByUser);
    }

    @Override
    public void updateAccounts(@Nonnull Account from, @Nonnull Account to) {
        Objects.requireNonNull(from, "from");
        Objects.requireNonNull(to, "to");

        synchronized (this) {
            accountStorage.replace(from.getNumber(), from);
            accountStorage.replace(to.getNumber(), to);
        }
    }

    @Nullable
    private Account getAccountByUser(User user) {
        return Optional.ofNullable(emailToAccountStorage.get(user.getEmail()))
                .map(accountStorage::get)
                .orElse(null);
    }

}
