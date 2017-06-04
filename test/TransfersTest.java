import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import play.libs.ws.WS;
import play.libs.ws.WSClient;
import play.libs.ws.WSResponse;
import play.mvc.Http;
import play.test.WithServer;

import java.util.concurrent.CompletionStage;

public class TransfersTest extends WithServer {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Test
    public void testInServer() throws Exception {
        int timeout = 5000;
        String url = "http://localhost:" + this.testServer.port() + "/transfers/api/createUser";
        try (WSClient ws = WS.newClient(this.testServer.port())) {
            CompletionStage<WSResponse> stage = ws.url(url).get();
            WSResponse response = stage.toCompletableFuture().get();
            Assert.assertEquals(Http.Status.OK, response.getStatus());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

//    @Test
//    public void shouldCreateUser() throws IOException {
//        Http.RequestBuilder request = new Http.RequestBuilder()
//                .method(Helpers.POST)
//                .uri(routes.UserController.create().url())
//                .bodyJson(OBJECT_MAPPER.readTree(getClass().getResourceAsStream("createUser.json")));
//
//        Result result = Helpers.route(request);
//        Assert.assertEquals(Http.Status.OK, result.status());
//
//        CreateUserResponse response = OBJECT_MAPPER.readValue(Helpers.contentAsString(result), CreateUserResponse.class);
//
//        User user = response.getUser();
//        Assert.assertEquals(user.getEmail(), "test@mail.com");
//        Assert.assertEquals(user.getFirstName(), "tester");
//        Assert.assertEquals(user.getLastName(), "test");
//
//        Account account = response.getAccount();
//        Assert.assertNotNull(account);
//    }
//
//    @Test
//    public void shouldDepositByEmail() throws IOException {
//        Http.RequestBuilder request = new Http.RequestBuilder()
//                .method(Helpers.POST)
//                .uri(controllers.transfers.routes.TransfersController.depositByEmail().url())
//                .bodyJson(OBJECT_MAPPER.readTree(getClass().getResourceAsStream("depositByEmail.json")));
//
//        Result result = Helpers.route(request);
//        Assert.assertEquals(Http.Status.OK, result.status());
//
//        CreateUserResponse response = OBJECT_MAPPER.readValue(Helpers.contentAsString(result), CreateUserResponse.class);
//
//        User user = response.getUser();
//        Assert.assertEquals(user.getEmail(), "test@mail.com");
//        Assert.assertEquals(user.getFirstName(), "tester");
//        Assert.assertEquals(user.getLastName(), "test");
//
//        Account account = response.getAccount();
//        Assert.assertNotNull(account);
//    }
//
//    @Override
//    protected Application provideApplication() {
//        return new GuiceApplicationBuilder()
//                .configure("play.http.router", "router.Routes")
//                .build();
//    }

}
