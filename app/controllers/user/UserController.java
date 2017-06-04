package controllers.user;

import controllers.helpers.JsonParsable;
import models.domain.Account;
import models.domain.User;
import models.api.CreateUserResponse;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import services.UserService;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class UserController extends Controller implements JsonParsable {

    private final UserService userService;

    @Inject
    public UserController(UserService userService) {
        this.userService = userService;
    }

    public Result create() {
        User user = parse(request(), User.class);
        Account account = userService.create(user);
        return ok(Json.toJson(new CreateUserResponse(user, account)));
    }

}
