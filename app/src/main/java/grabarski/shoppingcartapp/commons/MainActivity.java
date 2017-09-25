package grabarski.shoppingcartapp.commons;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import grabarski.shoppingcartapp.R;
import grabarski.shoppingcartapp.commons.adapters.ViewPagerAdapter;
import grabarski.shoppingcartapp.dagger.ProntoShopApplication;
import grabarski.shoppingcartapp.data.database.DatabaseHelper;
import grabarski.shoppingcartapp.data.events.SelectedCustomerEvent;
import grabarski.shoppingcartapp.data.events.UpdateToolbarEvent;
import grabarski.shoppingcartapp.data.model.LineItem;
import grabarski.shoppingcartapp.ui.checkout.CheckoutFragment;
import grabarski.shoppingcartapp.ui.customerList.CustomerListFragment;
import grabarski.shoppingcartapp.ui.productList.ProductListFragment;
import grabarski.shoppingcartapp.utils.DataFormatter;

public class MainActivity extends AppCompatActivity implements
        ProductListFragment.OnFragmentInteractionListener,
        CustomerListFragment.OnFragmentInteractionListener,
        CheckoutFragment.OnFragmentInteractionListener {

    @BindView(R.id.tabs)
    TabLayout tabs;

    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @BindView(R.id.number_of_items)
    TextView mQtyTextView;

    @BindView(R.id.total_amount)
    TextView mTotalTextView;

    @BindView(R.id.custom_toolbar_name_tv)
    TextView mNameTextView;

    private Bus mBus;

    @Inject
    ShoppingCart mCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);

        setupViewPager();

        mBus = ProntoShopApplication.getInstance().getBus();

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
    }

    private void setupViewPager() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabs.setupWithViewPager(viewPager);
    }

    @Override
    protected void onPause() {
        super.onPause();
        ProntoShopApplication.getInstance().getAppComponent().inject(this);
        mCart.saveCartToPreferences();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            ProntoShopApplication.getInstance().getAppComponent().inject(this);
            mCart.saveCartToPreferences();
            mBus.unregister(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Subscribe
    public void onUpdateToolbar(UpdateToolbarEvent event) {
        populateToolbar(event.getLineItems());
    }

    @Subscribe
    public void onCustomerSelected(SelectedCustomerEvent event) {
        if (event.isClearCustomer()) {
            mNameTextView.setText(R.string.not_selected);
        } else {
            mNameTextView.setText(event.getCustomer().getCustomerName());
        }
    }

    private void populateToolbar(List<LineItem> listOfItemsInShoppingCart) {
        double totalAmount = 0;
        int numberOfItems = 0;
        if (listOfItemsInShoppingCart != null && listOfItemsInShoppingCart.size() > 0) {
            for (LineItem item : listOfItemsInShoppingCart) {
                totalAmount += item.getSumPrice();
                numberOfItems += item.getQuantity();
            }
            mTotalTextView.setText(DataFormatter.formatCurrency(totalAmount));

            if (numberOfItems > 1) {
                mQtyTextView.setText(numberOfItems + " items");
            } else {
                mQtyTextView.setText(numberOfItems + " item");
            }
        } else {
            mTotalTextView.setText(DataFormatter.formatCurrency(0.00));
            mQtyTextView.setText(0 + " item");

        }

    }
}
