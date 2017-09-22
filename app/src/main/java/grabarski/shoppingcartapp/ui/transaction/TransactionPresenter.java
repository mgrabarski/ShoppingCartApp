package grabarski.shoppingcartapp.ui.transaction;

import java.util.List;

import javax.inject.Inject;

import grabarski.shoppingcartapp.dagger.ProntoShopApplication;
import grabarski.shoppingcartapp.data.database.OnDatabaseOperationCompleteListener;
import grabarski.shoppingcartapp.data.model.Customer;
import grabarski.shoppingcartapp.data.model.Transaction;
import grabarski.shoppingcartapp.ui.customerList.CustomerListContract;

/**
 * Created by Mateusz Grabarski on 22.09.2017.
 */

public class TransactionPresenter implements TransactionContract.Actions, OnDatabaseOperationCompleteListener {

    private final TransactionContract.View mView;

    @Inject
    TransactionContract.Repository mRepository;

    @Inject
    CustomerListContract.Repository mCustomerRepository;

    public TransactionPresenter(TransactionContract.View view) {
        mView = view;
        ProntoShopApplication.getInstance().getAppComponent().inject(this);
    }

    @Override
    public void loadTransactions() {
        List<Transaction> transactions = mRepository.getAllTransactions();
        if (transactions != null && transactions.size() > 0) {
            mView.hideEmptyText();
            mView.showTransaction(transactions);
        } else {
            mView.showEmptyText();
        }
    }

    @Override
    public void onDeleteItemButtonClicked(Transaction transaction) {
        mView.showConfirmDeletePrompt(transaction);
    }

    @Override
    public void editTransaction(Transaction transaction) {
        mRepository.updateTransaction(transaction, this);
    }

    @Override
    public void deleteTransaction(Transaction transaction) {
        mRepository.deleteTransaction(transaction.getId(), this);
    }

    @Override
    public Customer getCustomerById(long id) {
        return mCustomerRepository.getCustomerById(id);
    }

    @Override
    public void onDatabaseOperationFailed(String error) {
        mView.showMessage("Error: " + error);
    }

    @Override
    public void onDatabaseOperationSuccess(String message) {
        mView.showMessage(message);
    }
}
