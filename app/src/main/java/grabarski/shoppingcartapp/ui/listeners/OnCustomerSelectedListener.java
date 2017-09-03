package grabarski.shoppingcartapp.ui.listeners;

import grabarski.shoppingcartapp.data.model.Customer;

/**
 * Created by Mateusz Grabarski on 03.09.2017.
 */

public interface OnCustomerSelectedListener {
    void onCustomerSelected(Customer customer);
    void onLongClickCustomer(Customer customer);
}
