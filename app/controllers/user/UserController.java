package controllers.user;

import controllers.helpers.JsonParsable;
import models.api.CreateUserRequest;
import models.api.CreateUserResponse;
import models.api.ErrorResponse;
import models.domain.Account;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import services.UserService;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Controller with user managing methods. Uses messages in JSON format.
 */
@Singleton
public class UserController extends Controller implements JsonParsable {

    /**
     * {@link UserService} to manage users.
     */
    private final UserService userService;

    @Inject
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Creates user and account in transfers system.
     *
     * @return result with user and account data or error response with error message.
     */
    public Result create() {
        try {
            CreateUserRequest request = parse(request(), CreateUserRequest.class);
            Account account = userService.create(request.getUser(), request.getInitialBalance());
            return ok(Json.toJson(new CreateUserResponse(request.getUser(), account)));
        } catch (Exception e) {
            return badRequest(Json.toJson(ErrorResponse.of(e.getMessage())));
        }
    }

}
