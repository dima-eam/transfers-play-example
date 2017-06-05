package services;

import models.domain.TransferDetails;

import javax.annotation.Nonnull;
import java.math.BigDecimal;

/**
 * Service to transfer money between users.
 */
public interface TransfersService {

    /**
     * Transfers money from one user to another by email
     *
     * @param payerEmail payer email
     * @param payeeEmail payee email
     * @param sum        transfer sum
     * @return details of transfer
     */
    TransferDetails transfer(@Nonnull String payerEmail, @Nonnull String payeeEmail, @Nonnull BigDecimal sum);
}
