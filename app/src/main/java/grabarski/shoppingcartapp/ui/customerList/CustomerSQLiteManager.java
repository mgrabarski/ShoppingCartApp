package grabarski.shoppingcartapp.ui.customerList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import grabarski.shoppingcartapp.data.database.DatabaseHelper;
import grabarski.shoppingcartapp.data.database.OnDatabaseOperationCompleteListener;
import grabarski.shoppingcartapp.data.model.Customer;
import grabarski.shoppingcartapp.utils.Constants;

/**
 * Created by Mateusz Grabarski on 25.09.2017.
 */

public class CustomerSQLiteManager implements CustomerListContract.Repository {

    private final Context mContext;
    private SQLiteDatabase database;
    private DatabaseHelper DbHelper;
    private boolean DEBUG = false;
    private final static String LOG_TAG = CustomerSQLiteManager.class.getSimpleName();

    public CustomerSQLiteManager(Context context) {
        mContext = context;
        DbHelper = DatabaseHelper.newInstance(mContext);
        database = DbHelper.getWritableDatabase();
    }

    @Override
    public List<Customer> getAllCustomers() {
        //initialize an empty list of customers
        List<Customer> customers = new ArrayList<>();

        //sql command to select all Customers;
        String selectQuery = "SELECT * FROM " + Constants.CUSTOMER_TABLE;

        //make sure the database is not empty
        if (database != null) {

            //get a cursor for all customers in the database
            Cursor cursor = database.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    //get each customer in the cursor
                    customers.add(Customer.getCustomerFromCursor(cursor));
                    cursor.moveToNext();
                }
            }
            cursor.close();
        }


        return customers;
    }

    @Override
    public Customer getCustomerById(long id) {
        //Get the cursor representing the Customer
        Cursor cursor = database.rawQuery("SELECT * FROM " + Constants.CUSTOMER_TABLE + " WHERE " +
                Constants.COLUMN_ID + " = '" + id + "'", null);

        //Create a variable of data type Customer
        Customer customer;
        if (cursor.moveToFirst()) {
            customer = Customer.getCustomerFromCursor(cursor);
        } else {
            customer = null;
        }

        cursor.close();

        //Return result: either a valid customer or null
        return customer;
    }

    @Override
    public void onDeleteCustomer(Customer customer, OnDatabaseOperationCompleteListener listener) {
        // Ensure database exists.
        if (database != null) {
            int result = database.delete(Constants.CUSTOMER_TABLE, Constants.COLUMN_ID + " = " + customer.getId(), null);

            if (result > 0) {
                listener.onDatabaseOperationSuccess("Customer Deleted");
            } else {
                listener.onDatabaseOperationFailed("Unable to Delete Customer");
            }

        }
    }

    @Override
    public void addCustomer(Customer customer, OnDatabaseOperationCompleteListener listener) {
        if (database != null) {
            //prepare the transaction information that will be saved to the database
            ContentValues values = new ContentValues();
            values.put(Constants.COLUMN_NAME, customer.getCustomerName());
            values.put(Constants.COLUMN_EMAIL, customer.getEmailAddress());
            values.put(Constants.COLUMN_PHONE, customer.getPhoneNumber());
            values.put(Constants.COLUMN_IMAGE_PATH, customer.getProfileImagePath());
            values.put(Constants.COLUMN_STREET1, customer.getStreetAddress());
            values.put(Constants.COLUMN_STREET2, customer.getStreetAddress2());
            values.put(Constants.COLUMN_CITY, customer.getCity());
            values.put(Constants.COLUMN_STATE, customer.getCity());
            values.put(Constants.COLUMN_ZIP, customer.getPostalCode());
            values.put(Constants.COLUMN_NOTE, customer.getNote());
            values.put(Constants.COLUMN_DATE_CREATED, System.currentTimeMillis());
            values.put(Constants.COLUMN_LAST_UPDATED, System.currentTimeMillis());
            try {
                database.insertOrThrow(Constants.CUSTOMER_TABLE, null, values);
                listener.onDatabaseOperationSuccess("Customer Added");
                if (DEBUG) {
                    Log.d(LOG_TAG, "Customer Added");
                }
            } catch (SQLException e) {
                listener.onDatabaseOperationFailed(e.getCause() + " " + e.getMessage());
            }
        }
    }

    @Override
    public void updatedCustomer(Customer customer, OnDatabaseOperationCompleteListener listener) {
        if (database != null) {
            //prepare the transaction information that will be saved to the database
            ContentValues values = new ContentValues();
            values.put(Constants.COLUMN_NAME, customer.getCustomerName());
            values.put(Constants.COLUMN_EMAIL, customer.getEmailAddress());
            values.put(Constants.COLUMN_PHONE, customer.getPhoneNumber());
            values.put(Constants.COLUMN_IMAGE_PATH, customer.getProfileImagePath());
            values.put(Constants.COLUMN_STREET1, customer.getStreetAddress());
            values.put(Constants.COLUMN_STREET2, customer.getStreetAddress2());
            values.put(Constants.COLUMN_CITY, customer.getCity());
            values.put(Constants.COLUMN_STATE, customer.getCity());
            values.put(Constants.COLUMN_ZIP, customer.getPostalCode());
            values.put(Constants.COLUMN_NOTE, customer.getNote());
            values.put(Constants.COLUMN_LAST_UPDATED, System.currentTimeMillis());
            //Now update the this row with the information supplied
            int result = database.update(Constants.CUSTOMER_TABLE, values,
                    Constants.COLUMN_ID + " = " + customer.getId(), null);
            if (result == 1) {
                listener.onDatabaseOperationSuccess("Customer Updated");
            } else {
                listener.onDatabaseOperationFailed("Customer Update Failed");
            }
        }
    }

    class ThreadPerTaskExecutor implements Executor {
        public void execute(Runnable r) {
            new Thread(r).start();
        }
    }
}
