package grabarski.shoppingcartapp.commons;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import grabarski.shoppingcartapp.R;
import grabarski.shoppingcartapp.commons.adapters.ViewPagerAdapter;
import grabarski.shoppingcartapp.ui.checkout.CheckoutFragment;
import grabarski.shoppingcartapp.ui.customerList.CustomerListFragment;
import grabarski.shoppingcartapp.ui.productList.ProductListFragment;

public class MainActivity extends AppCompatActivity implements
        ProductListFragment.OnFragmentInteractionListener,
        CustomerListFragment.OnFragmentInteractionListener,
        CheckoutFragment.OnFragmentInteractionListener {

    @BindView(R.id.tabs)
    TabLayout tabs;

    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);

        setupViewPager();
    }

    private void setupViewPager() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabs.setupWithViewPager(viewPager);
    }
}
