package services;

import services.storage.TransfersStorage;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.math.BigDecimal;
import java.util.function.BiFunction;

@Singleton
public class TransfersServiceImpl implements TransfersService {

    private final TransfersStorage transfersStorage;

    @Inject
    public TransfersServiceImpl(TransfersStorage transfersStorage) {
        this.transfersStorage = transfersStorage;
    }

    @Override
    public BigDecimal depositByEmail(@Nonnull String email, @Nonnull BigDecimal sum) {
        return process(email, sum, transfersStorage::depositByEmail);
    }

    @Override
    public BigDecimal withdrawByEmail(@Nonnull String email, @Nonnull BigDecimal sum) {
        return process(email, sum, transfersStorage::withdrawByEmail);
    }

    private BigDecimal process(@Nonnull String email, @Nonnull BigDecimal sum,
                               BiFunction<String, BigDecimal, BigDecimal> process) {
        return transfersStorage.getUserByEmail(email)
                .map(user -> process.apply(user.getEmail(), sum))
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }
}
