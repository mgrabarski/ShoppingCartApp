package grabarski.shoppingcartapp.data.events;

import grabarski.shoppingcartapp.data.model.Customer;

/**
 * Created by Mateusz Grabarski on 20.09.2017.
 */

public class SelectedCustomerEvent {
    private Customer customer;
    private boolean clearCustomer;

    public SelectedCustomerEvent(Customer customer, boolean clearCustomer) {
        this.customer = customer;
        this.clearCustomer = clearCustomer;
    }

    public Customer getCustomer() {
        return customer;
    }

    public boolean isClearCustomer() {
        return clearCustomer;
    }
}
