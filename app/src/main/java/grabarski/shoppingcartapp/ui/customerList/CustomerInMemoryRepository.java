package grabarski.shoppingcartapp.ui.customerList;

import java.util.List;

import grabarski.shoppingcartapp.data.SampleCustomerData;
import grabarski.shoppingcartapp.data.database.OnDatabaseOperationCompleteListener;
import grabarski.shoppingcartapp.data.model.Customer;

/**
 * Created by Mateusz Grabarski on 22.09.2017.
 */

public class CustomerInMemoryRepository implements CustomerListContract.Repository {

    public CustomerInMemoryRepository() {
    }

    @Override
    public List<Customer> getAllCustomers() {
        return SampleCustomerData.getCustomers();
    }

    @Override
    public Customer getCustomerById(long id) {
        return null;
    }

    @Override
    public void onDeleteCustomer(Customer customer, OnDatabaseOperationCompleteListener listener) {

    }

    @Override
    public void addCustomer(Customer customer, OnDatabaseOperationCompleteListener listener) {

    }

    @Override
    public void updatedCustomer(Customer customer, OnDatabaseOperationCompleteListener listener) {

    }
}
