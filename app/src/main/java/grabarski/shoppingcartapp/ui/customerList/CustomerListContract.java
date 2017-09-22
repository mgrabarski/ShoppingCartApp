package grabarski.shoppingcartapp.ui.customerList;

import java.util.List;

import grabarski.shoppingcartapp.data.database.OnDatabaseOperationCompleteListener;
import grabarski.shoppingcartapp.data.model.Customer;

/**
 * Created by Mateusz Grabarski on 21.09.2017.
 */

public interface CustomerListContract {

    public interface View {
        void showCustomers(List<Customer> customers);

        void showAddCustomerForm();

        void showDeleteCustomerPrompt(Customer customer);

        void showEditCustomerForm(Customer customer);

        void showEmptyText();

        void hideEmptyText();

        void showMessage(String message);

    }

    public interface Actions {
        void loadCustomer();

        Customer getCustomer(long id);

        void onCustomerSelected(Customer customer);

        void onAddCustomerButtonClicked();

        void addCustomer(Customer customer);

        void onDeleteCustomerButtonClicked(Customer customer);

        void deleteCustomer(Customer customer);

        void onEditCustomerButtonClicked(Customer customer);

        void updateCustomer(Customer customer);

    }

    public interface Repository {
        List<Customer> getAllCustomers();

        Customer getCustomerById(long id);

        void onDeleteCustomer(Customer customer, OnDatabaseOperationCompleteListener listener);

        void addCustomer(Customer customer, OnDatabaseOperationCompleteListener listener);

        void updatedCustomer(Customer customer, OnDatabaseOperationCompleteListener listener);
    }
}
