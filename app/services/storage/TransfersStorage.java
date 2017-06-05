package services.storage;

import models.domain.Account;
import models.domain.User;

import javax.annotation.Nonnull;
import java.util.Optional;

public interface TransfersStorage {

    void put(@Nonnull Account account, @Nonnull User user);

    Optional<Account> getUserAccountByEmail(@Nonnull String email);

    void updateAccounts(@Nonnull Account from, @Nonnull Account to);
}
