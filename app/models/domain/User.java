package models.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.annotation.Nonnull;
import java.util.Objects;

/**
 * Simple user entity. No validation provided, sorry.
 */
public class User {

    /**
     * User's email
     */
    @Nonnull
    private final String email;
    /**
     * User's first name
     */
    @Nonnull
    private final String firstName;
    /**
     * User's last name
     */
    @Nonnull
    private final String lastName;

    public User(@Nonnull @JsonProperty("email") String email,
                @Nonnull @JsonProperty("firstName") String firstName,
                @Nonnull @JsonProperty("lastName") String lastName) {
        this.email = Objects.requireNonNull(email, "email");
        this.firstName = Objects.requireNonNull(firstName, "firstName");
        this.lastName = Objects.requireNonNull(lastName, "lastName");
    }

    @Nonnull
    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    @Nonnull
    @JsonProperty("firstName")
    public String getFirstName() {
        return firstName;
    }

    @Nonnull
    @JsonProperty("lastName")
    public String getLastName() {
        return lastName;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
