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

/**
 * Test of transfers between user accounts. Uses test server instance for the whole test suite.
 * Here I use test execution in certain order, but in practice tests should be independent.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TransfersTest {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static TestServer testServer;

    @Test
    public void t01_shouldCreateUser1() throws Exception {
        String url = "http://localhost:" + testServer.port() + "/transfers/api/createUser";

        try (WSClient ws = WS.newClient(testServer.port())) {
            CompletionStage<WSResponse> stage = ws.url(url)
                    .post(OBJECT_MAPPER.readTree(getClass().getResourceAsStream("json/createUser1Request.json")));
            WSResponse response = stage.toCompletableFuture().get();
            Assert.assertEquals(Http.Status.OK, response.getStatus());

            CreateUserResponse createUserResponse = Json.fromJson(response.asJson(), CreateUserResponse.class);

            User user = createUserResponse.getUser();
            Assert.assertEquals(user.getEmail(), "user1@mail.com");
            Assert.assertEquals(user.getFirstName(), "tester");
            Assert.assertEquals(user.getLastName(), "test");

            Account account = createUserResponse.getAccount();
            Assert.assertNotNull(account);
            Assert.assertEquals(0, BigDecimal.valueOf(10).compareTo(account.getBalance()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void t02_shouldCreateUser2() throws Exception {
        String url = "http://localhost:" + testServer.port() + "/transfers/api/createUser";

        try (WSClient ws = WS.newClient(testServer.port())) {
            CompletionStage<WSResponse> stage = ws.url(url)
                    .post(OBJECT_MAPPER.readTree(getClass().getResourceAsStream("json/createUser2Request.json")));
            WSResponse response = stage.toCompletableFuture().get();
            Assert.assertEquals(Http.Status.OK, response.getStatus());

            CreateUserResponse createUserResponse = Json.fromJson(response.asJson(), CreateUserResponse.class);

            User user = createUserResponse.getUser();
            Assert.assertEquals(user.getEmail(), "user2@mail.com");
            Assert.assertEquals(user.getFirstName(), "tester");
            Assert.assertEquals(user.getLastName(), "test");

            Account account = createUserResponse.getAccount();
            Assert.assertNotNull(account);
            Assert.assertEquals(0, BigDecimal.valueOf(20).compareTo(account.getBalance()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void t03_shouldTransferFromUser1ToUser2() throws Exception {
        String url = "http://localhost:" + testServer.port() + "/transfers/api/transferByEmail";

        try (WSClient ws = WS.newClient(testServer.port())) {
            CompletionStage<WSResponse> stage = ws.url(url)
                    .post(OBJECT_MAPPER.readTree(getClass().getResourceAsStream("json/transferByEmailUser1toUser2.json")));
            WSResponse response = stage.toCompletableFuture().get();
            Assert.assertEquals(Http.Status.OK, response.getStatus());

            TransfersResponse transfersResponse = Json.fromJson(response.asJson(), TransfersResponse.class);
            Assert.assertEquals(0, BigDecimal.valueOf(4).compareTo(transfersResponse.getPayerBalance()));
            Assert.assertEquals(0, BigDecimal.valueOf(26).compareTo(transfersResponse.getPayeeBalance()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void t04_shouldReturnInsufficientFunds() throws Exception {
        String url = "http://localhost:" + testServer.port() + "/transfers/api/transferByEmail";

        try (WSClient ws = WS.newClient(testServer.port())) {
            CompletionStage<WSResponse> stage = ws.url(url)
                    .post(OBJECT_MAPPER.readTree(getClass().getResourceAsStream("json/transferByEmailUser1toUser2.json")));
            WSResponse response = stage.toCompletableFuture().get();
            Assert.assertEquals(Http.Status.BAD_REQUEST, response.getStatus());

            ErrorResponse errorResponse = Json.fromJson(response.asJson(), ErrorResponse.class);
            Assert.assertEquals("Insufficient funds, try smaller sum", errorResponse.getMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void t05_shouldTransferFromUser2ToUser1() throws Exception {
        String url = "http://localhost:" + testServer.port() + "/transfers/api/transferByEmail";

        try (WSClient ws = WS.newClient(testServer.port())) {
            CompletionStage<WSResponse> stage = ws.url(url)
                    .post(OBJECT_MAPPER.readTree(getClass().getResourceAsStream("json/transferByEmailUser2toUser1.json")));
            WSResponse response = stage.toCompletableFuture().get();
            Assert.assertEquals(Http.Status.OK, response.getStatus());

            TransfersResponse transfersResponse = Json.fromJson(response.asJson(), TransfersResponse.class);
            Assert.assertEquals(0, BigDecimal.valueOf(16).compareTo(transfersResponse.getPayerBalance()));
            Assert.assertEquals(0, BigDecimal.valueOf(14).compareTo(transfersResponse.getPayeeBalance()));
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
