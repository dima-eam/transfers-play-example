package services.storage;

import models.domain.Account;
import models.domain.User;
import models.domain.TransferDetails;

import javax.annotation.Nonnull;
import java.math.BigDecimal;
import java.util.Optional;

public interface TransfersStorage {

    void put(@Nonnull Account account, @Nonnull User user);

    Optional<User> getUserByEmail(@Nonnull String email);

    TransferDetails transfer(@Nonnull User payer, @Nonnull User payee, @Nonnull BigDecimal sum);
}
