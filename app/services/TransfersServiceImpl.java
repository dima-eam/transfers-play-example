package services;

import models.domain.TransferDetails;
import models.domain.User;
import services.storage.TransfersStorage;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.math.BigDecimal;

/**
 * Implementation of service to transfer money between users. Checks for payer and payee
 * are presented in the system and process the transfer.
 */
@Singleton
public class TransfersServiceImpl implements TransfersService {

    /**
     * {@link TransfersStorage} to manipulate the data, stored in the system.
     */
    private final TransfersStorage transfersStorage;

    @Inject
    public TransfersServiceImpl(TransfersStorage transfersStorage) {
        this.transfersStorage = transfersStorage;
    }

    @Override
    public TransferDetails transfer(@Nonnull String payerEmail, @Nonnull String payeeEmail, @Nonnull BigDecimal sum) {
        return transfersStorage.getUserByEmail(payerEmail)
                .map(payer -> processPayer(payer, payeeEmail, sum)
                ).orElseThrow(() -> new IllegalArgumentException("Payer not found"));
    }

    private TransferDetails processPayer(User payer, @Nonnull String payeeEmail, @Nonnull BigDecimal sum) {
        return transfersStorage.getUserByEmail(payeeEmail)
                .map(payee -> transfersStorage.transfer(payer, payee, sum))
                .orElseThrow(() -> new IllegalArgumentException("Payee not found"));
    }

}
