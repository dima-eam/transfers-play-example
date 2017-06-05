package controllers.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import models.api.CreateUserResponse;
import models.api.ErrorResponse;
import models.domain.Account;
import models.domain.User;
import org.junit.Assert;
import org.junit.Test;
import play.Application;
import play.inject.guice.GuiceApplicationBuilder;
import play.mvc.Http;
import play.mvc.Result;
import play.test.Helpers;
import play.test.WithApplication;

/**
 * Just a couple of tests of {@link UserController} behavior. No http using, only application context.
 */
public class UserControllerTest extends WithApplication {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Test
    public void shouldCreateUser() throws Exception {
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(Helpers.POST)
                .uri(controllers.user.routes.UserController.create().url())
                .bodyJson(OBJECT_MAPPER.readTree(getClass().getResourceAsStream("createUserRequest.json")));

        Result result = Helpers.route(request);
        Assert.assertEquals(Http.Status.OK, result.status());

        CreateUserResponse response = OBJECT_MAPPER.readValue(Helpers.contentAsString(result), CreateUserResponse.class);

        User user = response.getUser();
        Assert.assertEquals(user.getEmail(), "test@mail.com");
        Assert.assertEquals(user.getFirstName(), "tester");
        Assert.assertEquals(user.getLastName(), "test");

        Account account = response.getAccount();
        Assert.assertNotNull(account);
    }

    @Test
    public void shouldReturnBadRequest() throws Exception {
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(Helpers.POST)
                .uri(routes.UserController.create().url())
                .bodyText("");

        Result result = Helpers.route(request);
        Assert.assertEquals(Http.Status.BAD_REQUEST, result.status());

        ErrorResponse errorResponse = OBJECT_MAPPER.readValue(Helpers.contentAsString(result), ErrorResponse.class);
        Assert.assertEquals("JSON body expected", errorResponse.getMessage());
    }

    @Override
    protected Application provideApplication() {
        return new GuiceApplicationBuilder()
                .configure("play.http.router", "router.Routes")
                .build();
    }

}
