package models.domain;

import java.math.BigDecimal;

/**
 * Internal data structure with details of a transaction.
 */
public class TransferDetails {

    /**
     * Payer balance after the transfer.
     */
    private final BigDecimal payerBalance;
    /**
     * Payee balance after the transfer.
     */
    private final BigDecimal payeeBalance;

    public TransferDetails(BigDecimal payerBalance, BigDecimal payeeBalance) {
        this.payerBalance = payerBalance;
        this.payeeBalance = payeeBalance;
    }

    public BigDecimal getPayerBalance() {
        return payerBalance;
    }

    public BigDecimal getPayeeBalance() {
        return payeeBalance;
    }
}
