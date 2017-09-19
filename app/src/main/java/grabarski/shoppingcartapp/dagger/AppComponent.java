package grabarski.shoppingcartapp.dagger;

import javax.inject.Singleton;

import dagger.Component;
import grabarski.shoppingcartapp.commons.MainActivity;
import grabarski.shoppingcartapp.commons.ShoppingCart;

/**
 * Created by Mateusz Grabarski on 23/08/2017.
 */
@Singleton
@Component (
        modules = {
                AppModule.class,
                ShoppingCartModule.class,
                BusModul.class
        }
)
public interface AppComponent {
    void inject(MainActivity activity);
    void inject(ShoppingCart shoppingCart);
}
