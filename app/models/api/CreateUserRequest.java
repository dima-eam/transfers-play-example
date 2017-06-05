package models.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import models.domain.User;

import javax.annotation.Nonnull;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * Request with the data to create user and account in system.
 */
public class CreateUserRequest {

    /**
     * User's data.
     */
    @Nonnull
    private final User user;
    /**
     * Account initial balance.
     */
    @Nonnull
    private final BigDecimal initialBalance;

    public CreateUserRequest(@Nonnull @JsonProperty("user") User user,
                             @Nonnull @JsonProperty("initialBalance") BigDecimal initialBalance) {
        this.user = Objects.requireNonNull(user, "user");
        this.initialBalance = Objects.requireNonNull(initialBalance, "initialBalance");
    }

    @Nonnull
    @JsonProperty("user")
    public User getUser() {
        return user;
    }

    @Nonnull
    @JsonProperty("initialBalance")
    public BigDecimal getInitialBalance() {
        return initialBalance;
    }

    @Override
    public String toString() {
        return "CreateUserResponse{" +
                "user=" + user +
                ", initialBalance=" + initialBalance +
                '}';
    }
}
