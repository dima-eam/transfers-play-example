package models.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.annotation.Nonnull;
import java.util.Objects;

/**
 * Simple error message.
 */
public class ErrorResponse {

    /**
     * Error message.
     */
    @Nonnull
    private final String message;

    private ErrorResponse(@Nonnull @JsonProperty("message") String message) {
        this.message = Objects.requireNonNull(message, "message");
    }

    public static ErrorResponse of(@Nonnull String message) {
        return new ErrorResponse(message);
    }

    @Nonnull
    @JsonProperty("message")
    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "ErrorResponse{" +
                "message='" + message + '\'' +
                '}';
    }
}
