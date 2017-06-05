package models.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import models.domain.TransferDetails;

import javax.annotation.Nonnull;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * Response with the transfer result data.
 */
public class TransfersResponse {

    /**
     * Payer balance after the transfer.
     */
    @Nonnull
    private final BigDecimal payerBalance;
    /**
     * Payee balance after the transfer.
     */
    @Nonnull
    private final BigDecimal payeeBalance;

    private TransfersResponse(@Nonnull @JsonProperty("payerBalance") BigDecimal payerBalance,
                              @Nonnull @JsonProperty("payeeBalance") BigDecimal payeeBalance) {
        this.payerBalance = Objects.requireNonNull(payerBalance, "payerBalance");
        this.payeeBalance = Objects.requireNonNull(payeeBalance, "payeeBalance");
    }

    public static TransfersResponse of(@Nonnull TransferDetails details) {
        return new TransfersResponse(details.getPayerBalance(), details.getPayeeBalance());
    }

    @Nonnull
    @JsonProperty("payerBalance")
    public BigDecimal getPayerBalance() {
        return payerBalance;
    }

    @Nonnull
    @JsonProperty("payeeBalance")
    public BigDecimal getPayeeBalance() {
        return payeeBalance;
    }

    @Override
    public String toString() {
        return "TransferByEmailRequest{" +
                ", payerBalance=" + payerBalance +
                ", payeeBalance=" + payeeBalance +
                '}';
    }
}
