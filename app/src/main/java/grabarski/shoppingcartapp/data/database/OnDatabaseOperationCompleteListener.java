package grabarski.shoppingcartapp.data.database;

/**
 * Created by Mateusz Grabarski on 22.09.2017.
 */

public interface OnDatabaseOperationCompleteListener {
    void onDatabaseOperationFailed(String message);
    void onDatabaseOperationSuccess(String message);
}
