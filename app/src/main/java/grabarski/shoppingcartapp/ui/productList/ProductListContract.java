package grabarski.shoppingcartapp.ui.productList;

import java.util.List;

import grabarski.shoppingcartapp.data.database.OnDatabaseOperationCompleteListener;
import grabarski.shoppingcartapp.data.model.Category;
import grabarski.shoppingcartapp.data.model.Product;

/**
 * Created by Mateusz Grabarski on 21.09.2017.
 */

public interface ProductListContract {

    public interface View {
        void showProducts(List<Product> products);

        void showAddProductForm();

        void showEditProductFrom(Product product);

        void showDeletedProductPrompt(Product product);

        void showGoogleSearch(Product product);

        void showEmptyTest();

        void hideEmptyTest();

        void showMessage(String message);
    }

    public interface Actions {
        void loadProducts();

        void onAddBtnClick();

        void onAddToCartBtnClick(Product product);

        Product getProduct(long id);

        void addProduct(Product product);

        void onDeleteProductBtnClick(Product product);

        void onDeleteProduct(Product product);

        void onEditProductBtnClick(Product product);

        void updateProduct(Product product);

        void onGoogleSearchBtnClick(Product product);
    }

    public interface Repository {
        List<Product> getAllProducts();

        Product getProductById(long id);

        void deleteProduct(Product product, OnDatabaseOperationCompleteListener listener);

        void addProduct(Product product, OnDatabaseOperationCompleteListener listener);

        void updateProduct(Product product, OnDatabaseOperationCompleteListener listener);

        List<Category> getAllCategories();
    }
}
