import com.fasterxml.jackson.databind.ObjectMapper;
import controllers.user.routes;
import models.Account;
import models.User;
import models.api.CreateUserResponse;
import org.junit.Assert;
import org.junit.Test;
import play.Application;
import play.inject.guice.GuiceApplicationBuilder;
import play.mvc.Http;
import play.mvc.Result;
import play.test.Helpers;
import play.test.WithApplication;

import java.io.IOException;

public class UserControllerTest extends WithApplication {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Test
    public void shouldCreateUser() throws IOException {
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(Helpers.POST)
                .uri(controllers.user.routes.UserController.create().url())
                .bodyJson(OBJECT_MAPPER.readTree(getClass().getResourceAsStream("createUser.json")));

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
    public void shouldDepositByEmail() throws IOException {
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(Helpers.POST)
                .uri(controllers.transfers.routes.TransfersController.depositByEmail().url())
                .bodyJson(OBJECT_MAPPER.readTree(getClass().getResourceAsStream("depositByEmail.json")));

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
    public void shouldReturnBadRequest() throws IOException {
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(Helpers.POST)
                .uri(routes.UserController.create().url())
                .bodyText("");

        Result result = Helpers.route(request);
        Assert.assertEquals(Http.Status.BAD_REQUEST, result.status());
    }

    @Override
    protected Application provideApplication() {
        return new GuiceApplicationBuilder()
                .configure("play.http.router", "router.Routes")
                .build();
    }

}
