package models.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.annotation.Nonnull;
import java.math.BigDecimal;
import java.util.Objects;

public class TransfersResponse {

    @Nonnull
    private final BigDecimal balance;

    private TransfersResponse(@Nonnull BigDecimal balance) {
        this.balance = Objects.requireNonNull(balance, "balance");
    }

    public static TransfersResponse of(@Nonnull BigDecimal sum) {
        return new TransfersResponse(sum);
    }

    @Nonnull
    @JsonProperty("balance")
    public BigDecimal getBalance() {
        return balance;
    }

    @Override
    public String toString() {
        return "TransfersByEmailRequest{" +
                ", balance=" + balance +
                '}';
    }
}
