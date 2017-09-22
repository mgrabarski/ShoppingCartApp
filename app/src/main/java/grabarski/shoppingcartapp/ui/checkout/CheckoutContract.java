package grabarski.shoppingcartapp.ui.checkout;

import java.util.List;

import grabarski.shoppingcartapp.data.database.OnDatabaseOperationCompleteListener;
import grabarski.shoppingcartapp.data.model.LineItem;
import grabarski.shoppingcartapp.data.model.Transaction;

/**
 * Created by Mateusz Grabarski on 22.09.2017.
 */

public class CheckoutContract {

    public interface View {
        void showLineItem(List<LineItem> items);

        void showEmptyText();

        void showCartTotals(double tax, double subTotal, double total);

        void showConfirmCheckout();

        void showConfirmClearCart();

        void hideText();

        void showMessage(String message);

    }

    public interface Actions {
        void loadLineItems();

        void onCheckoutButtonClicked();

        void onDeleteItemButtonClicked(LineItem item);

        void checkout();

        void onClearButtonClicked();

        void clearShoppingCart();

        void setPaymentType(String paymentType);

        void markAsPaid(boolean paid);

        void onItemQuantityChanged(LineItem item, int qty);

    }

    public interface Repository {
        List<LineItem> getAllLineItems();

        void saveTransaction(Transaction transaction, OnDatabaseOperationCompleteListener listener);

        void updateTransaction(Transaction transaction, OnDatabaseOperationCompleteListener listener);
    }
}
