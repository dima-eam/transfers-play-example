package services;

import models.domain.Account;
import models.domain.TransferDetails;
import services.storage.TransfersStorage;

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
    public TransferDetails transfer(String payerEmail, String payeeEmail, BigDecimal sum) {
        return transfersStorage.getUserAccountByEmail(payerEmail)
                .map(from -> processPayer(from, payeeEmail, sum)
                ).orElseThrow(() -> new IllegalArgumentException("Payer account not found"));
    }

    private TransferDetails processPayer(Account from, String payeeEmail, BigDecimal sum) {
        return transfersStorage.getUserAccountByEmail(payeeEmail)
                .map(to -> transfer(from, to, sum))
                .orElseThrow(() -> new IllegalArgumentException("Payee account not found"));
    }

    private TransferDetails transfer(Account from, Account to, BigDecimal sum) {
        Account payerAcc = from.withdraw(sum);
        if (payerAcc.getBalance().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Insufficient funds, try smaller sum");
        }
        Account payeeAcc = to.deposit(sum);
        transfersStorage.updateAccounts(payerAcc, payeeAcc);

        return new TransferDetails(payerAcc.getBalance(), payeeAcc.getBalance());
    }

}
