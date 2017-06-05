package controllers.transfers;

import com.fasterxml.jackson.databind.ObjectMapper;
import models.api.CreateUserResponse;
import models.api.ErrorResponse;
import models.api.TransfersResponse;
import models.domain.Account;
import models.domain.User;
import org.junit.*;
import org.junit.runners.MethodSorters;
import play.Application;
import play.libs.Json;
import play.libs.ws.WS;
import play.libs.ws.WSClient;
import play.libs.ws.WSResponse;
import play.mvc.Http;
import play.test.Helpers;
import play.test.TestServer;

import java.math.BigDecimal;
import java.util.concurrent.CompletionStage;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TransfersTest {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static TestServer testServer;

    @Test
    public void t01_shouldCreateUser() throws Exception {
        String url = "http://localhost:" + testServer.port() + "/transfers/api/createUser";

        try (WSClient ws = WS.newClient(testServer.port())) {
            CompletionStage<WSResponse> stage = ws.url(url)
                    .post(OBJECT_MAPPER.readTree(getClass().getResourceAsStream("createUserRequest.json")));
            WSResponse response = stage.toCompletableFuture().get();
            Assert.assertEquals(Http.Status.OK, response.getStatus());

            CreateUserResponse createUserResponse = Json.fromJson(response.asJson(), CreateUserResponse.class);

            User user = createUserResponse.getUser();
            Assert.assertEquals(user.getEmail(), "test@mail.com");
            Assert.assertEquals(user.getFirstName(), "tester");
            Assert.assertEquals(user.getLastName(), "test");

            Account account = createUserResponse.getAccount();
            Assert.assertNotNull(account);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void t02_shouldDepositByEmail() throws Exception {
        String url = "http://localhost:" + testServer.port() + "/transfers/api/transferByEmail";

        try (WSClient ws = WS.newClient(testServer.port())) {
            CompletionStage<WSResponse> stage = ws.url(url)
                    .post(OBJECT_MAPPER.readTree(getClass().getResourceAsStream("transferByEmail.json")));
            WSResponse response = stage.toCompletableFuture().get();
            Assert.assertEquals(Http.Status.OK, response.getStatus());

            TransfersResponse transfersResponse = Json.fromJson(response.asJson(), TransfersResponse.class);
            Assert.assertEquals(0, BigDecimal.valueOf(10.0).compareTo(transfersResponse.getPayerBalance()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void t03_shouldWithdrawByEmail() throws Exception {
        String url = "http://localhost:" + testServer.port() + "/transfers/api/withdrawByEmail";

        try (WSClient ws = WS.newClient(testServer.port())) {
            CompletionStage<WSResponse> stage = ws.url(url)
                    .post(OBJECT_MAPPER.readTree(getClass().getResourceAsStream("withdrawByEmail.json")));
            WSResponse response = stage.toCompletableFuture().get();
            Assert.assertEquals(Http.Status.OK, response.getStatus());

            TransfersResponse transfersResponse = Json.fromJson(response.asJson(), TransfersResponse.class);
            Assert.assertEquals(0, BigDecimal.valueOf(6.0).compareTo(transfersResponse.getPayerBalance()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void t04_shouldThrowWithdrawByEmail() throws Exception {
        String url = "http://localhost:" + testServer.port() + "/transfers/api/withdrawByEmail";

        try (WSClient ws = WS.newClient(testServer.port())) {
            CompletionStage<WSResponse> stage = ws.url(url)
                    .post(OBJECT_MAPPER.readTree(getClass().getResourceAsStream("overdraftedWithdrawByEmail.json")));
            WSResponse response = stage.toCompletableFuture().get();
            Assert.assertEquals(Http.Status.BAD_REQUEST, response.getStatus());

            ErrorResponse errorResponse = Json.fromJson(response.asJson(), ErrorResponse.class);
            Assert.assertEquals("Insufficient funds, try smaller sum", errorResponse.getMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @BeforeClass
    public static void before() {
        Application app = Helpers.fakeApplication();
        int port = play.api.test.Helpers.testServerPort();
        testServer = Helpers.testServer(port, app);
        testServer.start();
    }

    @AfterClass
    public static void after() {
        if (testServer != null) {
            testServer.stop();
            testServer = null;
        }
    }
}
