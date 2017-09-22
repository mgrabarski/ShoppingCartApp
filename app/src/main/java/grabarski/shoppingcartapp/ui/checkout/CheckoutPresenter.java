package grabarski.shoppingcartapp.ui.checkout;

import java.util.List;

import javax.inject.Inject;

import grabarski.shoppingcartapp.commons.ShoppingCart;
import grabarski.shoppingcartapp.data.database.OnDatabaseOperationCompleteListener;
import grabarski.shoppingcartapp.data.model.LineItem;
import grabarski.shoppingcartapp.data.model.Transaction;

/**
 * Created by Mateusz Grabarski on 22.09.2017.
 */

public class CheckoutPresenter implements CheckoutContract.Actions, OnDatabaseOperationCompleteListener {

    private CheckoutContract.View mView;

    @Inject
    ShoppingCart mCart;

    @Inject
    CheckoutContract.Repository mRepository;

    private double subTotal = 0.0;
    private double total;
    private double tax;
    private double taxRate = 0.08;
    private String selectedPaymentType = "";
    private boolean paid = false;

    public CheckoutPresenter(CheckoutContract.View view) {
        mView = view;
    }


    @Override
    public void loadLineItems() {
        List<LineItem> availableLineItems = mCart.getShoppingCard();
        calculateTotals(availableLineItems);
        if (availableLineItems != null && availableLineItems.size() > 0) {
            mView.hideText();
            mView.showLineItem(availableLineItems);
        } else {
            mView.showEmptyText();
        }
    }

    private void calculateTotals(List<LineItem> availableLineItems) {
        subTotal = 0.0;
        total = 0.0;
        tax = 0.0;

        for (LineItem item : availableLineItems) {
            subTotal += item.getSumPrice();
        }

        tax = subTotal * taxRate;
        total = tax + subTotal;
        mView.showCartTotals(tax, subTotal, total);
    }

    @Override
    public void onCheckoutButtonClicked() {
        mView.showConfirmCheckout();
    }

    @Override
    public void onDeleteItemButtonClicked(LineItem item) {
        mCart.removeItemFromCard(item);
        loadLineItems();
    }

    @Override
    public void checkout() {
        //Ensure a customer is selected
        if (mCart.getShoppingCard() == null || mCart.getShoppingCard().size() == 0) {
            mView.showMessage("Cart is empty");
            return;
        }

        if (mCart.getSelectedCustomer() == null || mCart.getSelectedCustomer().getId() == 0) {
            mView.showMessage("No Customer is selected");
            return;
        }

        Transaction transaction = new Transaction();
        transaction.setCustomerId(mCart.getSelectedCustomer().getId());
        transaction.setLineItems(mCart.getShoppingCard());
        transaction.setTaxAmount(tax);
        transaction.setSubTotalAmount(subTotal);
        transaction.setTotalAmount(total);
        transaction.setPaymentType(selectedPaymentType);
        transaction.setPaid(paid);
        mRepository.saveTransaction(transaction, this);
    }

    @Override
    public void onClearButtonClicked() {
        mView.showConfirmClearCart();
    }

    @Override
    public void clearShoppingCart() {
        mCart.clearAllItemsFromCard();
        loadLineItems();
    }

    @Override
    public void setPaymentType(String paymentType) {
        selectedPaymentType = paymentType;
    }

    @Override
    public void markAsPaid(boolean paid) {
        this.paid = paid;
    }

    @Override
    public void onItemQuantityChanged(LineItem item, int qty) {
        mCart.updateItemQty(item, qty);
    }

    @Override
    public void onDatabaseOperationFailed(String error) {
        mView.showMessage("Error Message: " + error);
    }

    @Override
    public void onDatabaseOperationSuccess(String message) {
        mView.showMessage(message);
    }
}
