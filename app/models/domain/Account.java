package models.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.annotation.Nonnull;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

/**
 * Simple account representation. Uses {@link UUID} as account number representation.
 * No account number validation provided, sorry.
 */
public final class Account {

    /**
     * Account number.
     */
    @Nonnull
    private final String number;
    /**
     * Account balance.
     */
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

    public static Account generate(BigDecimal initialBalance) {
        return new Account(UUID.randomUUID().toString(), initialBalance);
    }

    public Account deposit(@Nonnull BigDecimal sum) {
        return new Account(this.number, this.balance.add(sum));
    }

    public Account withdraw(@Nonnull BigDecimal sum) {
        return new Account(this.number, this.balance.subtract(sum));
    }

    @Override
    public String toString() {
        return "Account{" +
                "number='" + number + '\'' +
                "balance='" + balance + '\'' +
                '}';
    }
}
