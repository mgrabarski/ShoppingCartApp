package grabarski.shoppingcartapp.ui.transaction;

import java.util.List;

import grabarski.shoppingcartapp.data.database.OnDatabaseOperationCompleteListener;
import grabarski.shoppingcartapp.data.model.Customer;
import grabarski.shoppingcartapp.data.model.LineItem;
import grabarski.shoppingcartapp.data.model.Transaction;

/**
 * Created by Mateusz Grabarski on 22.09.2017.
 */

public class TransactionContract {

    public interface View {
        void showTransaction(List<Transaction> transactions);

        void showEmptyText();

        void hideEmptyText();

        void showConfirmDeletePrompt(Transaction transaction);

        void showMessage(String message);

    }

    public interface Actions {
        void loadTransactions();

        void onDeleteItemButtonClicked(Transaction transaction);

        void editTransaction(Transaction transaction);

        void deleteTransaction(Transaction transaction);

        Customer getCustomerById(long id);
    }


    public interface Repository {
        List<LineItem> getAllLineItems();
        long saveTransaction(Transaction transaction, OnDatabaseOperationCompleteListener listener);
        List<Transaction> getAllSalesTransactions();
        void updateTransaction(Transaction transaction, OnDatabaseOperationCompleteListener listener);
        Transaction getTransactionById(long id);
        void deleteTransaction(long id, OnDatabaseOperationCompleteListener listener);
    }
}
