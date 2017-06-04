package services;

import models.domain.Account;
import models.domain.User;
import services.storage.TransfersStorageImpl;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class UserServiceImpl implements UserService {

    private final TransfersStorageImpl transfersStorage;

    @Inject
    public UserServiceImpl(TransfersStorageImpl transfersStorage) {
        this.transfersStorage = transfersStorage;
    }

    @Override
    public Account create(User user) {
        Account account = Account.generate();
        transfersStorage.put(account, user);
        return account;
    }

    @Override
    public User getByEmail(String email) {
        return new User(email, "found", "found");
    }
}
