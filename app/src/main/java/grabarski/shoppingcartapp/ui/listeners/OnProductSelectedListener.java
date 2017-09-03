package grabarski.shoppingcartapp.ui.listeners;

import grabarski.shoppingcartapp.data.model.Product;

/**
 * Created by Mateusz Grabarski on 03.09.2017.
 */

public interface OnProductSelectedListener {
    void onProductSelected(Product product);
    void onLongClickProduct(Product product);
}
