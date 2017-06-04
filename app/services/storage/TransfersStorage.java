package services.storage;

import models.Account;
import models.User;

import javax.annotation.Nonnull;
import java.math.BigDecimal;
import java.util.Optional;

public interface TransfersStorage {

    void put(@Nonnull Account account, @Nonnull User user);

    Optional<User> getUserByEmail(@Nonnull String email);

    BigDecimal depositByEmail(@Nonnull String email, @Nonnull BigDecimal sum);

    BigDecimal withdrawByEmail(@Nonnull String email, @Nonnull BigDecimal sum);
}
