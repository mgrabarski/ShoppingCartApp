package grabarski.shoppingcartapp.commons;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import grabarski.shoppingcartapp.R;
import grabarski.shoppingcartapp.ui.checkout.CheckoutFragment;
import grabarski.shoppingcartapp.ui.customerList.CustomerListFragment;
import grabarski.shoppingcartapp.ui.productList.ProductListFragment;

public class MainActivity extends AppCompatActivity implements
        ProductListFragment.OnFragmentInteractionListener,
        CustomerListFragment.OnFragmentInteractionListener,
        CheckoutFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
