package grabarski.shoppingcartapp.dagger;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Mateusz Grabarski on 23/08/2017.
 */
@Module
public class AppModule {

    private final ProntoShopApplication application;

    public AppModule(ProntoShopApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    public Context getContext() {
        return application;
    }

    @Provides
    @Singleton
    public ProntoShopApplication getApplication() {
        return application;
    }
}
