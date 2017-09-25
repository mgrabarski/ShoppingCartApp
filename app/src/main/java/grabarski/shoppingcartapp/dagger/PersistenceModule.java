package grabarski.shoppingcartapp.dagger;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import grabarski.shoppingcartapp.ui.customerList.CustomerListContract;
import grabarski.shoppingcartapp.ui.customerList.CustomerSQLiteManager;
import grabarski.shoppingcartapp.ui.productList.ProductListContract;
import grabarski.shoppingcartapp.ui.productList.ProductListSQLiteManager;
import grabarski.shoppingcartapp.ui.transaction.TransactionContract;
import grabarski.shoppingcartapp.ui.transaction.TransactionSQLiteManager;

/**
 * Created by Mateusz Grabarski on 22.09.2017.
 */
@Module
public class PersistenceModule {

    @Provides
    @Singleton
    public ProductListContract.Repository getProductRepository(Context context) {
        return new ProductListSQLiteManager(context);
    }

    @Provides
    @Singleton
    public CustomerListContract.Repository getCustomerRepository(Context context) {
        return new CustomerSQLiteManager(context);
    }

    @Provides
    @Singleton
    public TransactionContract.Repository getTransactionRepository(Context context) {
        return new TransactionSQLiteManager(context);
    }
}
