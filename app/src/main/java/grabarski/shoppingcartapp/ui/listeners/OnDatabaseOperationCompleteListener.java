package grabarski.shoppingcartapp.ui.listeners;

/**
 * Created by Mateusz Grabarski on 10.09.2017.
 */

public interface OnDatabaseOperationCompleteListener {
    void onDatabaseOperationFailed(String error);
    void onDatabaseOperationSucceeded(String message);
}
