package grabarski.shoppingcartapp.dagger;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import grabarski.shoppingcartapp.ui.customerList.CustomerInMemoryRepository;
import grabarski.shoppingcartapp.ui.customerList.CustomerListContract;
import grabarski.shoppingcartapp.ui.productList.ProductInMemoryRepository;
import grabarski.shoppingcartapp.ui.productList.ProductListContract;

/**
 * Created by Mateusz Grabarski on 22.09.2017.
 */
@Module
public class PersistenceModule {

    @Provides
    @Singleton
    private ProductListContract.Repository getProductRepository(Context context) {
        return new ProductInMemoryRepository();
    }

    @Provides
    @Singleton
    public CustomerListContract.Repository getCustomerRepository(Context context) {
        return new CustomerInMemoryRepository();
    }
}
