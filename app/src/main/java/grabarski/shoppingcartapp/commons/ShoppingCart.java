package grabarski.shoppingcartapp.commons;

import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.otto.Bus;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import grabarski.shoppingcartapp.dagger.ProntoShopApplication;
import grabarski.shoppingcartapp.data.events.SelectedCustomerEvent;
import grabarski.shoppingcartapp.data.events.UpdateToolbarEvent;
import grabarski.shoppingcartapp.data.model.Customer;
import grabarski.shoppingcartapp.data.model.LineItem;
import grabarski.shoppingcartapp.utils.Constants;

/**
 * Created by Mateusz Grabarski on 23/08/2017.
 */

public class ShoppingCart implements ShoppingCardContract {

    public static final String TAG = ShoppingCart.class.getSimpleName();
    private static boolean DEBUG = true;

    private List<LineItem> shoppingCart;
    private Customer selectedCustomer;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Inject
    Bus mBus;

    public ShoppingCart(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;

        ProntoShopApplication.getInstance().getAppComponent().inject(this);

        initShoppingCart();
    }

    private void initShoppingCart() {
        shoppingCart = new ArrayList<>();
        selectedCustomer = new Customer();
        Gson gson = new Gson();

        if (sharedPreferences.getBoolean(Constants.OPEN_CART_EXISTS, false)) {
            String serializedCartItems = sharedPreferences.getString(Constants.SERIALIZED_CART_ITEMS, "");

            logMessage("Serialized cart items: " + serializedCartItems);

            String serializedCustomer = sharedPreferences.getString(Constants.SERIALIZED_SELECTED_CUSTOMER, "");

            logMessage("Serialized selected customer: " + serializedCustomer);

            if (!serializedCartItems.equals("")) {
                shoppingCart = gson.<ArrayList<LineItem>>fromJson(serializedCartItems, new TypeToken<ArrayList<LineItem>>() {
                }.getType());
            }

            if (!serializedCustomer.equals(""))
                selectedCustomer = gson.fromJson(serializedCustomer, Customer.class);
        }

        refreshToolbar();
    }

    public void saveCartToPreferences() {
        if (shoppingCart != null) {
            Gson gson = new Gson();
            String serializedItems = gson.toJson(shoppingCart);
            String serializedCustomer = gson.toJson(selectedCustomer);

            logMessage("Saved serialized items: " + serializedItems);
            logMessage("Saved serialized selected customer: " + serializedCustomer);

            editor.putString(Constants.SERIALIZED_CART_ITEMS, serializedItems).commit();
            editor.putString(Constants.SERIALIZED_SELECTED_CUSTOMER, serializedCustomer).commit();
            editor.putBoolean(Constants.OPEN_CART_EXISTS, true).commit();
        }
    }

    @Override
    public void addItemToCard(LineItem item) {
        boolean isItemInCart = false;
        int itemPosition = 0;

        for (LineItem lineItem : shoppingCart) {
            if (lineItem.getId() == item.getId()) {
                itemPosition = shoppingCart.indexOf(lineItem);
                isItemInCart = true;
                LineItem selectedItem = shoppingCart.get(itemPosition);
                selectedItem.setQuantity(lineItem.getQuantity() + item.getQuantity());
                shoppingCart.set(itemPosition, selectedItem);
                break;
            }
        }

        if (!isItemInCart) {
            shoppingCart.add(item);
        }

        refreshToolbar();
    }

    @Override
    public void removeItemFromCard(LineItem item) {
        shoppingCart.remove(item);
        refreshToolbar();
    }

    @Override
    public void clearAllItemsFromCard() {
        shoppingCart.clear();
        selectedCustomer = null;

        editor.putString(Constants.SERIALIZED_CART_ITEMS, "").commit();
        editor.putString(Constants.SERIALIZED_SELECTED_CUSTOMER, "").commit();
        editor.putBoolean(Constants.OPEN_CART_EXISTS, false).commit();

        refreshToolbar();
        mBus.post(new SelectedCustomerEvent(new Customer(), true));
    }

    @Override
    public List<LineItem> getShoppingCard() {
        return shoppingCart;
    }

    @Override
    public void setCustomer(Customer customer) {
        this.selectedCustomer = customer;
        mBus.post(new SelectedCustomerEvent(customer, false));
    }

    @Override
    public void updateItemQty(LineItem item, int qty) {
        boolean isItemInCart = false;
        int itemPosition = 0;

        for (LineItem lineItem : shoppingCart) {
            if (lineItem.getId() == item.getId()) {
                itemPosition = shoppingCart.indexOf(lineItem);
                LineItem itemInCart = shoppingCart.get(itemPosition);
                itemInCart.setQuantity(qty);
                shoppingCart.set(itemPosition, itemInCart);
                isItemInCart = true;
                break;
            }
        }

        if (!isItemInCart) {
            item.setQuantity(qty);
            shoppingCart.add(item);
        }

        refreshToolbar();
    }

    @Override
    public Customer getSelectedCustomer() {
        return selectedCustomer;
    }

    @Override
    public void completeCheckout() {
        shoppingCart.clear();
        refreshToolbar();
        mBus.post(new SelectedCustomerEvent(new Customer(), true));
    }

    private void logMessage(String message) {
        if (DEBUG)
            Log.d(TAG, message);
    }

    private void refreshToolbar() {
        mBus.post(new UpdateToolbarEvent(shoppingCart));
    }
}
