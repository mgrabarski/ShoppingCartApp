package grabarski.shoppingcartapp.dagger;

import javax.inject.Singleton;

import dagger.Component;
import grabarski.shoppingcartapp.commons.MainActivity;

/**
 * Created by Mateusz Grabarski on 23/08/2017.
 */
@Singleton
@Component (
        modules = {
                AppModule.class,
                ShoppingCartModule.class
        }
)
public interface AppComponent {
    void inject(MainActivity activity);
}
