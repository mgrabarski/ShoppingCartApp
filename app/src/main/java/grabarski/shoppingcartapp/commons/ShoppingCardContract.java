package grabarski.shoppingcartapp.commons;

import java.util.List;

import grabarski.shoppingcartapp.data.model.Customer;
import grabarski.shoppingcartapp.data.model.LineItem;

/**
 * Created by Mateusz Grabarski on 10.09.2017.
 */

public interface ShoppingCardContract {
    void addItemToCard(LineItem item);
    void removeItemFromCard(LineItem item);
    void clearAllItemsFromCard();
    List<LineItem> getShoppingCard();
    void setCustomer(Customer customer);
    void updateItemQty(LineItem item, int qty);
    Customer getSelectedCustomer();
    void completeCheckout();
}
