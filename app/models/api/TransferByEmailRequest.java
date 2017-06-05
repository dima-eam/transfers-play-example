package models.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.annotation.Nonnull;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * Request to transfer money from one user account to another by email.
 */
public class TransferByEmailRequest {

    /**
     * Payer email.
     */
    @Nonnull
    private final String payerEmail;
    /**
     * Payee email.
     */
    @Nonnull
    private final String payeeEmail;
    /**
     * Sum to transfer.
     */
    @Nonnull
    private final BigDecimal sum;

    public TransferByEmailRequest(@Nonnull @JsonProperty("payerEmail") String payerEmail,
                                  @Nonnull @JsonProperty("payeeEmail") String payeeEmail,
                                  @Nonnull @JsonProperty("sum") BigDecimal sum) {
        this.payerEmail = Objects.requireNonNull(payerEmail, "payerEmail");
        this.payeeEmail = Objects.requireNonNull(payeeEmail, "payeeEmail");
        this.sum = Objects.requireNonNull(sum, "sum");
    }

    @Nonnull
    @JsonProperty("payerEmail")
    public String getPayerEmail() {
        return payerEmail;
    }

    @Nonnull
    @JsonProperty("payeeEmail")
    public String getPayeeEmail() {
        return payeeEmail;
    }

    @Nonnull
    @JsonProperty("sum")
    public BigDecimal getSum() {
        return sum;
    }

    @Override
    public java.lang.String toString() {
        return "TransferByEmailRequest{" +
                "payerEmail=" + payerEmail +
                "payeeEmail=" + payeeEmail +
                ", sum=" + sum +
                '}';
    }
}
