package models.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.annotation.Nonnull;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public final class Account {

    @Nonnull
    private final String number;

    @Nonnull
    private final BigDecimal balance;

    private Account(@Nonnull @JsonProperty("number") String number,
                    @Nonnull @JsonProperty("balance") BigDecimal balance) {
        this.number = Objects.requireNonNull(number, "number");
        this.balance = Objects.requireNonNull(balance, "balance");
    }

    @Nonnull
    @JsonProperty("number")
    public String getNumber() {
        return number;
    }

    @Nonnull
    @JsonProperty("balance")
    public BigDecimal getBalance() {
        return balance;
    }

    public static Account generate() {
        return new Account(UUID.randomUUID().toString(), BigDecimal.ZERO);
    }

    public Account deposit(@Nonnull BigDecimal sum) {
        return new Account(this.number, this.balance.add(sum));
    }

    public Account withdraw(@Nonnull BigDecimal sum) {
        BigDecimal newSum = this.balance.subtract(sum);
        if (newSum.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Insufficient funds, try smaller sum");
        }
        return new Account(this.number, newSum);
    }

    @Override
    public String toString() {
        return "Account{" +
                "number='" + number + '\'' +
                '}';
    }
}
