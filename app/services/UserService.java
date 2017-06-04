package services;

import models.domain.Account;
import models.domain.User;

public interface UserService {

    Account create(User user);

    User getByEmail(String email);
}
