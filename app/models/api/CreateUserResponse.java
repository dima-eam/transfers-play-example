package models.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import models.domain.Account;
import models.domain.User;

import javax.annotation.Nonnull;
import java.util.Objects;

/**
 * Response with user and account data.
 */
public class CreateUserResponse {

    /**
     * User's data.
     */
    @Nonnull
    private final User user;
    /**
     * User's account.
     */
    @Nonnull
    private final Account account;

    public CreateUserResponse(@Nonnull @JsonProperty("user") User user,
                              @Nonnull @JsonProperty("account") Account account) {
        this.user = Objects.requireNonNull(user, "user");
        this.account = Objects.requireNonNull(account, "account");
    }

    @Nonnull
    @JsonProperty("user")
    public User getUser() {
        return user;
    }

    @Nonnull
    @JsonProperty("account")
    public Account getAccount() {
        return account;
    }

    @Override
    public String toString() {
        return "CreateUserResponse{" +
                "user=" + user +
                ", account=" + account +
                '}';
    }
}
