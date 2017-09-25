package grabarski.shoppingcartapp.data.model;

import android.database.Cursor;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import grabarski.shoppingcartapp.utils.Constants;

/**
 * Created by Mateusz Grabarski on 24.08.17.
 */

public class Transaction {

    private long id;
    private long customerId;
    private double subTotalAmount;
    private double taxAmount;
    private double totalAmount;
    private boolean paid;
    private String paymentType;
    private long transactionDate;
    private long modificationDate;
    private List<LineItem> lineItems;

    private String jsonLineItems;

    public Transaction() {
    }

    public Transaction(long _id, long custId, double subTotal, double tax, double total,
                       boolean completed, String payment, long dateOfTransaction, long dateModified) {
        id = _id;
        customerId = custId;
        subTotalAmount = subTotal;
        taxAmount = tax;
        totalAmount = total;
        paid = completed;
        paymentType = payment;
        transactionDate = dateOfTransaction;
        modificationDate = dateModified;
    }

    ;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public double getSubTotalAmount() {
        return subTotalAmount;
    }

    public void setSubTotalAmount(double subTotalAmount) {
        this.subTotalAmount = subTotalAmount;
    }

    public double getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(double taxAmount) {
        this.taxAmount = taxAmount;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public long getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(long transactionDate) {
        this.transactionDate = transactionDate;
    }

    public long getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(long modificationDate) {
        this.modificationDate = modificationDate;
    }

    public List<LineItem> getLineItems() {
        Gson gson = new Gson();
        String serializedLineItems = getJsonLineItems();
        List<LineItem> result = gson.<ArrayList<LineItem>>fromJson(serializedLineItems, new TypeToken<ArrayList<LineItem>>() {
        }.getType());
        return lineItems;
    }

    public void setLineItems(List<LineItem> lineItems) {
        Gson gson = new Gson();
        String serializedLineItems = gson.toJson(lineItems);
        this.setJsonLineItems(serializedLineItems);
    }

    public String getJsonLineItems() {
        return jsonLineItems;
    }

    public void setJsonLineItems(String jsonLineItems) {
        this.jsonLineItems = jsonLineItems;
    }

    public static Transaction getSalesTransactionFromCursor(Cursor cursor) {
        long id = cursor.getLong(cursor.getColumnIndex(Constants.COLUMN_ID));
        long customerId = cursor.getLong(cursor.getColumnIndex(Constants.COLUMN_CUSTOMER_ID));
        double subTotal = cursor.getDouble(cursor.getColumnIndex(Constants.COLUMN_SUB_TOTAL_AMOUNT));
        double tax = cursor.getDouble(cursor.getColumnIndex(Constants.COLUMN_TOTAL_AMOUNT));
        double total = cursor.getDouble(cursor.getColumnIndex(Constants.COLUMN_TOTAL_AMOUNT));
        boolean completed = cursor.getInt(cursor.getColumnIndex(Constants.COLUMN_PAYMENT_STATUS)) > 0;
        String payment = cursor.getString(cursor.getColumnIndex(Constants.COLUMN_PAYMENT_TYPE));
        long dateOfTransaction = cursor.getLong(cursor.getColumnIndex(Constants.COLUMN_DATE_CREATED));
        long dateModified = cursor.getLong(cursor.getColumnIndex(Constants.COLUMN_LAST_UPDATED));

        Transaction transaction = new Transaction(id, customerId,
                subTotal, tax, total, completed, payment, dateOfTransaction, dateModified) {

        };
        return transaction;
    }
}
