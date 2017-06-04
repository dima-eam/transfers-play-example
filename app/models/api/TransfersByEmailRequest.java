package models.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.annotation.Nonnull;
import java.math.BigDecimal;
import java.util.Objects;

public class TransfersByEmailRequest {

    @Nonnull
    private final String email;
    @Nonnull
    private final BigDecimal sum;

    public TransfersByEmailRequest(@Nonnull @JsonProperty("email") String email,
                                   @Nonnull @JsonProperty("sum") BigDecimal sum) {
        this.email = Objects.requireNonNull(email, "email");
        this.sum = Objects.requireNonNull(sum, "sum");
    }

    @Nonnull
    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    @Nonnull
    @JsonProperty("sum")
    public BigDecimal getSum() {
        return sum;
    }

    @Override
    public java.lang.String toString() {
        return "TransfersByEmailRequest{" +
                "email=" + email +
                ", sum=" + sum +
                '}';
    }
}
