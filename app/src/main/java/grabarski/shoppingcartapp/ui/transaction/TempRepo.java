package grabarski.shoppingcartapp.ui.transaction;

import java.util.List;

import grabarski.shoppingcartapp.data.database.OnDatabaseOperationCompleteListener;
import grabarski.shoppingcartapp.data.model.Transaction;

/**
 * Created by Mateusz Grabarski on 22.09.2017.
 */

public class TempRepo implements TransactionContract.Repository {

    public TempRepo() {
    }

    @Override
    public List<Transaction> getAllTransactions() {
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
