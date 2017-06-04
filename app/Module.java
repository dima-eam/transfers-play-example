import com.google.inject.AbstractModule;
import services.TransfersService;
import services.TransfersServiceImpl;
import services.UserService;
import services.UserServiceImpl;
import services.storage.TransfersStorage;
import services.storage.TransfersStorageImpl;

/**
 * This class is a Guice module that tells Guice how to bind several
 * different types. This Guice module is created when the Play
 * application starts.
 * <p>
 * Play will automatically use any class called `Module` that is in
 * the root package. You can create modules in other locations by
 * adding `play.modules.enabled` settings to the `application.conf`
 * configuration file.
 */
public class Module extends AbstractModule {

    @Override
    public void configure() {
        bind(UserService.class).to(UserServiceImpl.class);
        bind(TransfersService.class).to(TransfersServiceImpl.class);
        bind(TransfersStorage.class).to(TransfersStorageImpl.class);
    }
}
