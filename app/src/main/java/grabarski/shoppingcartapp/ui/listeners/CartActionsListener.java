package grabarski.shoppingcartapp.ui.listeners;

import grabarski.shoppingcartapp.data.model.LineItem;

/**
 * Created by Mateusz Grabarski on 03.09.2017.
 */

public interface CartActionsListener {
    void onItemDeleted(LineItem item);
    void onItemQtyChange(LineItem item, int qty);
}
