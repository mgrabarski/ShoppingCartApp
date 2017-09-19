package grabarski.shoppingcartapp.dagger;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import grabarski.shoppingcartapp.commons.ShoppingCart;

/**
 * Created by Mateusz Grabarski on 19.09.2017.
 */
@Module
public class ShoppingCartModule {

    @Provides
    @Singleton
    SharedPreferences provideSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Provides
    @Singleton
    ShoppingCart provideShoppingCart(SharedPreferences sharedPreferences) {
        return new ShoppingCart(sharedPreferences);
    }
}
