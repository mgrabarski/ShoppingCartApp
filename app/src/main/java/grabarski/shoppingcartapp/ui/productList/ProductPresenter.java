package grabarski.shoppingcartapp.ui.productList;

import com.squareup.otto.Bus;

import java.util.List;

import javax.inject.Inject;

import grabarski.shoppingcartapp.commons.ShoppingCart;
import grabarski.shoppingcartapp.dagger.ProntoShopApplication;
import grabarski.shoppingcartapp.data.database.OnDatabaseOperationCompleteListener;
import grabarski.shoppingcartapp.data.model.LineItem;
import grabarski.shoppingcartapp.data.model.Product;

/**
 * Created by Mateusz Grabarski on 22.09.2017.
 */

public class ProductPresenter implements ProductListContract.Actions, OnDatabaseOperationCompleteListener {

    private ProductListContract.View mView;

    @Inject
    ProductListContract.Repository mRepository;

    @Inject
    ShoppingCart mShoppingCart;

    @Inject
    Bus mBus;

    public ProductPresenter(ProductListContract.View mView) {
        this.mView = mView;
        ProntoShopApplication.getInstance().getAppComponent().inject(this);
    }

    @Override
    public void loadProducts() {
        List<Product> availableProducts = mRepository.getAllProducts();

        if (availableProducts != null && availableProducts.size() > 0) {
            mView.hideEmptyTest();
            mView.showProducts(availableProducts);
        } else mView.showEmptyTest();
    }

    @Override
    public void onAddBtnClick() {
        mView.showAddProductForm();
    }

    @Override
    public void onAddToCartBtnClick(Product product) {
        LineItem lineItem = new LineItem(product, 1);
        mShoppingCart.addItemToCard(lineItem);
    }

    @Override
    public Product getProduct(long id) {
        return mRepository.getProductById(id);
    }

    @Override
    public void addProduct(Product product) {
        mRepository.addProduct(product, this);
    }

    @Override
    public void onDeleteProductBtnClick(Product product) {
        mView.showDeletedProductPrompt(product);
    }

    @Override
    public void onDeleteProduct(Product product) {
        mRepository.deleteProduct(product, this);
    }

    @Override
    public void onEditProductBtnClick(Product product) {
        mView.showEditProductFrom(product);
    }

    @Override
    public void updateProduct(Product product) {
        mRepository.updateProduct(product, this);
    }

    @Override
    public void onGoogleSearchBtnClick(Product product) {
        mView.showGoogleSearch(product);
    }

    @Override
    public void onDatabaseOperationFailed(String message) {
        mView.showMessage("Error: " + message);
    }

    @Override
    public void onDatabaseOperationSuccess(String message) {
        mView.showMessage(message);
    }
}
