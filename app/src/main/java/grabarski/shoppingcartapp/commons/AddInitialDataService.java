package grabarski.shoppingcartapp.commons;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import grabarski.shoppingcartapp.data.SampleCustomerData;
import grabarski.shoppingcartapp.data.SampleProductData;
import grabarski.shoppingcartapp.data.database.OnDatabaseOperationCompleteListener;
import grabarski.shoppingcartapp.data.model.Customer;
import grabarski.shoppingcartapp.data.model.Product;
import grabarski.shoppingcartapp.ui.customerList.CustomerSQLiteManager;
import grabarski.shoppingcartapp.ui.productList.ProductListSQLiteManager;

/**
 * Created by Mateusz Grabarski on 25.09.2017.
 */

public class AddInitialDataService extends IntentService {

    public AddInitialDataService() {
        super("AddInitialDataService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        //Add sample Customers to database
        List<Customer> customers = SampleCustomerData.getCustomers();
        CustomerSQLiteManager customerRepository = new CustomerSQLiteManager(getApplicationContext());
        for (Customer customer : customers) {
            customerRepository.addCustomer(customer, new OnDatabaseOperationCompleteListener() {
                @Override
                public void onDatabaseOperationFailed(String message) {
                    Log.d("Customer", "Error" + message);
                }

                @Override
                public void onDatabaseOperationSuccess(String message) {
                    Log.d("Customer", "Customer Inserted");
                }
            });
        }

        //Add initial products
        List<Product> products = SampleProductData.getSampleProducts();
        ProductListSQLiteManager productSQLiteRepository = new ProductListSQLiteManager(getApplicationContext());
        for (Product product : products) {
            productSQLiteRepository.addProduct(product, new OnDatabaseOperationCompleteListener() {
                @Override
                public void onDatabaseOperationFailed(String message) {
                    Log.d("Customer", "Error" + message);
                }

                @Override
                public void onDatabaseOperationSuccess(String message) {
                    Log.d("Customer", "Customer Inserted");
                }
            });
        }

        //Add sample categories
        List<String> categories = new ArrayList<>();
        categories.add("Electronics");
        categories.add("Computers");
        categories.add("Toys");
        categories.add("Garden");
        categories.add("Kitchen");
        categories.add("Clothing");
        categories.add("Health");

        for (String category : categories) {
            productSQLiteRepository.createOrGetCategoryId(category, new OnDatabaseOperationCompleteListener() {
                @Override
                public void onDatabaseOperationFailed(String message) {
                    Log.d("Customer", "Error" + message);
                }

                @Override
                public void onDatabaseOperationSuccess(String message) {
                    Log.d("Customer", "Customer Inserted");
                }
            });
        }

        Intent restartIntent = new Intent(this, MainActivity.class);
        restartIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.startActivity(restartIntent);


    }
}
