package grabarski.shoppingcartapp.dagger;

import javax.inject.Singleton;

import dagger.Component;
import grabarski.shoppingcartapp.commons.MainActivity;
import grabarski.shoppingcartapp.commons.ShoppingCart;
import grabarski.shoppingcartapp.ui.customerList.CustomerPresenter;
import grabarski.shoppingcartapp.ui.productList.ProductPresenter;
import grabarski.shoppingcartapp.ui.transaction.TransactionPresenter;

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
    void inject(ProductPresenter productPresenter);
    void inject(CustomerPresenter customerPresenter);
    void inject(TransactionPresenter transactionPresenter);
}
