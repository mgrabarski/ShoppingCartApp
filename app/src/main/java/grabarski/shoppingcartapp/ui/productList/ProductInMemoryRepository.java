package grabarski.shoppingcartapp.ui.productList;

import java.util.List;

import grabarski.shoppingcartapp.data.SampleProductData;
import grabarski.shoppingcartapp.data.database.OnDatabaseOperationCompleteListener;
import grabarski.shoppingcartapp.data.model.Category;
import grabarski.shoppingcartapp.data.model.Product;

/**
 * Created by Mateusz Grabarski on 22.09.2017.
 */

public class ProductInMemoryRepository implements ProductListContract.Repository {

    public ProductInMemoryRepository() {
    }

    @Override
    public List<Product> getAllProducts() {
        return SampleProductData.getSampleProducts();
    }

    @Override
    public Product getProductById(long id) {
        return null;
    }

    @Override
    public void deleteProduct(Product product, OnDatabaseOperationCompleteListener listener) {

    }

    @Override
    public void addProduct(Product product, OnDatabaseOperationCompleteListener listener) {

    }

    @Override
    public void updateProduct(Product product, OnDatabaseOperationCompleteListener listener) {

    }

    @Override
    public List<Category> getAllCategories() {
        return null;
    }
}
