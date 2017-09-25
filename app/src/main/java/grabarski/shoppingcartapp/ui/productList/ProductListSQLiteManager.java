package grabarski.shoppingcartapp.ui.productList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import grabarski.shoppingcartapp.data.database.DatabaseHelper;
import grabarski.shoppingcartapp.data.database.OnDatabaseOperationCompleteListener;
import grabarski.shoppingcartapp.data.model.Category;
import grabarski.shoppingcartapp.data.model.Product;
import grabarski.shoppingcartapp.utils.Constants;

/**
 * Created by Mateusz Grabarski on 25.09.2017.
 */

public class ProductListSQLiteManager implements ProductListContract.Repository {

    public static final String LOG_TAG = ProductListSQLiteManager.class.getSimpleName();

    private DatabaseHelper databaseHelper;
    private Context mContext;
    private SQLiteDatabase sqLiteDatabase;

    public ProductListSQLiteManager(Context mContext) {
        this.mContext = mContext;
        databaseHelper = DatabaseHelper.newInstance(mContext);
        sqLiteDatabase = databaseHelper.getWritableDatabase();
    }


    @Override
    public List<Product> getAllProducts() {
        //initialize an empty list of customers
        List<Product> products = new ArrayList<>();

        //sql command to select all Products;
        String selectQuery = "SELECT * FROM " + Constants.PRODUCT_TABLE;

        //make sure the database is not empty
        if (sqLiteDatabase != null) {

            //get a cursor for all products in the database
            Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    //get each product in the cursor
                    products.add(Product.getProductFromCursor(cursor));
                    cursor.moveToNext();
                }
            }
            cursor.close();
        }

        return products;
    }

    @Override
    public Product getProductById(long id) {
        //Get the cursor representing the Product
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + Constants.PRODUCT_TABLE + " WHERE " +
                Constants.COLUMN_ID + " = '" + id + "'", null);

        //Create a variable of data type Product
        Product product;
        if (cursor.moveToFirst()) {
            product = Product.getProductFromCursor(cursor);
        } else {
            product = null;
        }
        cursor.close();
        //Return result: either a valid product or null
        return product;
    }

    @Override
    public void deleteProduct(Product product, OnDatabaseOperationCompleteListener listener) {

        // Ensure database exists.
        if (sqLiteDatabase != null) {
            int result = sqLiteDatabase.delete(Constants.PRODUCT_TABLE, Constants.COLUMN_ID + " = " + product.getId(), null);

            if (result > 0) {
                listener.onDatabaseOperationSuccess("Product Deleted");
            } else {
                listener.onDatabaseOperationFailed("Unable to Delete Product");
            }

        }

    }

    @Override
    public void addProduct(Product product, OnDatabaseOperationCompleteListener listener) {

        //ensure that the database exists
        if (sqLiteDatabase != null) {
            //prepare the transaction information that will be saved to the database
            ContentValues values = new ContentValues();
            values.put(Constants.COLUMN_NAME, product.getProductName());
            values.put(Constants.COLUMN_DESCRIPTION, product.getProductDescription());
            values.put(Constants.COLUMN_PRICE, product.getSalePrice());
            values.put(Constants.COLUMN_PURCHASE_PRICE, product.getPurchasePrice());
            values.put(Constants.COLUMN_IMAGE_PATH, product.getImagePath());
            values.put(Constants.COLUMN_CATEGORY_ID, createOrGetCategoryId(product.getCategoryName(), listener));
            values.put(Constants.COLUMN_CATEGORY_NAME, product.getCategoryName());
            values.put(Constants.COLUMN_DATE_CREATED, System.currentTimeMillis());
            values.put(Constants.COLUMN_LAST_UPDATED, System.currentTimeMillis());
            try {
                sqLiteDatabase.insertOrThrow(Constants.PRODUCT_TABLE, null, values);
                listener.onDatabaseOperationSuccess("Product Added");

                Log.d(LOG_TAG, "Product Added");
            } catch (SQLException e) {
                listener.onDatabaseOperationFailed(e.getCause() + " " + e.getMessage());
            }
        }

    }

    @Override
    public void updateProduct(Product product, OnDatabaseOperationCompleteListener listener) {
        //Ensure that the database exists
        if (sqLiteDatabase != null) {
            //Prepare the transaction information that will be saved to the database
            ContentValues values = new ContentValues();
            values.put(Constants.COLUMN_NAME, product.getProductName());
            values.put(Constants.COLUMN_DESCRIPTION, product.getProductDescription());
            values.put(Constants.COLUMN_PRICE, product.getSalePrice());
            values.put(Constants.COLUMN_PURCHASE_PRICE, product.getPurchasePrice());
            values.put(Constants.COLUMN_IMAGE_PATH, product.getImagePath());
            values.put(Constants.COLUMN_CATEGORY_ID, product.getCategoryId());
            values.put(Constants.COLUMN_CATEGORY_NAME, product.getCategoryName());
            values.put(Constants.COLUMN_LAST_UPDATED, System.currentTimeMillis());

            //Now update the this row with the information supplied
            int result = sqLiteDatabase.update(Constants.PRODUCT_TABLE, values,
                    Constants.COLUMN_ID + " = " + product.getId(), null);
            if (result == 1) {
                listener.onDatabaseOperationSuccess("Product Updated");
            } else {
                listener.onDatabaseOperationFailed("Product Update Failed");
            }
        }
    }

    @Override
    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<Category>();

        //Command to select all Categories
        String selectQuery = "SELECT * FROM " + Constants.CATEGORY_TABLE;

        //Get a cursor for all Categories in the database
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                categories.add(Category.fromCursor(cursor));
                cursor.moveToNext();
            }
            cursor.close();
        }
        return categories;
    }


    public long createOrGetCategoryId(final String category, OnDatabaseOperationCompleteListener listener) {
        Category foundCategory = getCategory(category);
        if (foundCategory == null) {
            foundCategory = addCategory(category, listener);
        }
        return foundCategory.getId();
    }

    private Category addCategory(final String categoryName, OnDatabaseOperationCompleteListener listener) {
        Category category = new Category();
        category.setCategoryName(categoryName);
        saveCategory(category, listener);
        return category;
    }

    private void saveCategory(Category category, OnDatabaseOperationCompleteListener listener) {

        ContentValues values = new ContentValues();
        values.put(Constants.COLUMN_NAME, category.getCategoryName());
        try {
            sqLiteDatabase.insertOrThrow(Constants.CATEGORY_TABLE, null, values);
        } catch (SQLException e) {
            listener.onDatabaseOperationFailed("Unable to save Category");

        }

    }

    private Category getCategory(final String categoryName) {

        Category category = null;
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + Constants.CATEGORY_TABLE + " " +
                "WHERE " + Constants.COLUMN_NAME + " = '" + categoryName + "'", null);
        if (cursor.moveToFirst()) {
            category = Category.fromCursor(cursor);
        }
        cursor.close();
        return category;
    }
}
