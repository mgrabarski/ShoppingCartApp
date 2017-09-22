package grabarski.shoppingcartapp.dagger;

import android.app.Application;

/**
 * Created by Mateusz Grabarski on 19.09.2017.
 */

public class ProntoShopApplication extends Application {

    private static AppComponent appComponent;
    private static ProntoShopApplication instance = new ProntoShopApplication();

    @Override
    public void onCreate() {
        super.onCreate();

        getAppComponent();
    }

    public AppComponent getAppComponent() {
        if(appComponent == null) {
            appComponent = DaggerAppComponent.builder()
                    .appModule(new AppModule(this))
                    .build();
        }

        return appComponent;
    }

    public static ProntoShopApplication getInstance() {
        return instance;
    }
}
