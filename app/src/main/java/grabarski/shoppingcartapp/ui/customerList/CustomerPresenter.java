package grabarski.shoppingcartapp.ui.customerList;

import com.squareup.otto.Bus;

import java.util.List;

import javax.inject.Inject;

import grabarski.shoppingcartapp.commons.ShoppingCart;
import grabarski.shoppingcartapp.dagger.ProntoShopApplication;
import grabarski.shoppingcartapp.data.database.OnDatabaseOperationCompleteListener;
import grabarski.shoppingcartapp.data.model.Customer;

/**
 * Created by Mateusz Grabarski on 22.09.2017.
 */

public class CustomerPresenter implements CustomerListContract.Actions, OnDatabaseOperationCompleteListener {

    private CustomerListContract.View mView;

    @Inject
    CustomerListContract.Repository mRepository;

    @Inject
    ShoppingCart mShoppingCart;

    @Inject
    Bus mBus;

    public CustomerPresenter(CustomerListContract.View mView) {
        this.mView = mView;
        ProntoShopApplication.getInstance().getAppComponent().inject(this);
    }

    @Override
    public void loadCustomer() {
        List<Customer> availableCustomers = mRepository.getAllCustomers();

        if (availableCustomers != null && availableCustomers.size() > 0) {
            mView.hideEmptyText();
            mView.showCustomers(availableCustomers);
        } else mView.hideEmptyText();
    }

    @Override
    public Customer getCustomer(long id) {
        return mRepository.getCustomerById(id);
    }

    @Override
    public void onCustomerSelected(Customer customer) {
        mShoppingCart.setCustomer(customer);
    }

    @Override
    public void onAddCustomerButtonClicked() {
        mView.showAddCustomerForm();
    }

    @Override
    public void addCustomer(Customer customer) {
        mRepository.addCustomer(customer, this);
    }

    @Override
    public void onDeleteCustomerButtonClicked(Customer customer) {
        mView.showDeleteCustomerPrompt(customer);
    }

    @Override
    public void deleteCustomer(Customer customer) {
        mRepository.onDeleteCustomer(customer, this);
    }

    @Override
    public void onEditCustomerButtonClicked(Customer customer) {
        mView.showEditCustomerForm(customer);
    }

    @Override
    public void updateCustomer(Customer customer) {
        mRepository.updatedCustomer(customer, this);
    }

    @Override
    public void onDatabaseOperationFailed(String message) {
        mView.showMessage("Error: " + message);
    }

    @Override
    public void onDatabaseOperationSuccess(String message) {
        mView.showMessage(message);
    }
}
