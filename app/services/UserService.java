package services;

import models.Account;
import models.User;

public interface UserService {

    Account create(User user);

    User getByEmail(String email);
}
