package grabarski.shoppingcartapp.ui.transaction;

import java.util.List;

import grabarski.shoppingcartapp.data.database.OnDatabaseOperationCompleteListener;
import grabarski.shoppingcartapp.data.model.LineItem;
import grabarski.shoppingcartapp.data.model.Transaction;

/**
 * Created by Mateusz Grabarski on 22.09.2017.
 */

public class TempRepo implements TransactionContract.Repository {

    public TempRepo() {
    }

    @Override
    public List<LineItem> getAllLineItems() {
        return null;
    }

    @Override
    public long saveTransaction(Transaction transaction, OnDatabaseOperationCompleteListener listener) {
        return 0;
    }

    @Override
    public List<Transaction> getAllSalesTransactions() {
        return null;
    }

    @Override
    public void updateTransaction(Transaction transaction, OnDatabaseOperationCompleteListener listener) {

    }

    @Override
    public Transaction getTransactionById(long id) {
        return null;
    }

    @Override
    public void deleteTransaction(long id, OnDatabaseOperationCompleteListener listener) {

    }
}
