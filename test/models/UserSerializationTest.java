package models;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.junit.Assert;
import org.junit.Test;
import play.libs.Json;

import java.io.IOException;

public class UserSerializationTest {

    @Test
    public void shouldSuccessSerialize() {
        User user = new User("email", "firstName", "lastName");
        JsonNode jsonNode = Json.toJson(user);
        EqualsBuilder.reflectionEquals(user, Json.fromJson(jsonNode, User.class));
    }

    @Test
    public void shouldThrowNullPointerException() throws IOException {
        JsonNode jsonNode = new ObjectMapper().readTree("{\"email\":\"email\",\"firstName\":\"firstName\"}");
        try {
            Json.fromJson(jsonNode, User.class);
            Assert.fail("Should throw an exception");
        } catch (RuntimeException e) { // easy and terrible
            Assert.assertEquals(e.getCause().getCause().getClass(), NullPointerException.class);
        }
    }
}