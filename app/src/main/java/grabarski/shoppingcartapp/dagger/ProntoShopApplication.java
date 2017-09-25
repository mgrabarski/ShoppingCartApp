package grabarski.shoppingcartapp.dagger;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.squareup.otto.Bus;

import grabarski.shoppingcartapp.commons.AddInitialDataService;
import grabarski.shoppingcartapp.utils.Constants;

/**
 * Created by Mateusz Grabarski on 19.09.2017.
 */

public class ProntoShopApplication extends Application {

    private static AppComponent appComponent;
    private static ProntoShopApplication instance = new ProntoShopApplication();
    private Bus bus;

    @Override
    public void onCreate() {
        super.onCreate();

        getAppComponent();

        instance.bus = new Bus();

        initDefaultProducts();
    }

    public AppComponent getAppComponent() {
        if (appComponent == null) {
            appComponent = DaggerAppComponent.builder()
                    .appModule(new AppModule(this))
                    .build();
        }

        return appComponent;
    }

    public static ProntoShopApplication getInstance() {
        return instance;
    }

    public Bus getBus() {
        return bus;
    }

    private void initDefaultProducts() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (sharedPreferences.getBoolean(Constants.FIRST_RUN, true)) {
            startService(new Intent(this, AddInitialDataService.class));
            editor.putBoolean(Constants.FIRST_RUN, false).apply();
        }
    }
}
