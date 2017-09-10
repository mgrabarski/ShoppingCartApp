package grabarski.shoppingcartapp.commons.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import grabarski.shoppingcartapp.ui.checkout.CheckoutFragment;
import grabarski.shoppingcartapp.ui.customerList.CustomerListFragment;
import grabarski.shoppingcartapp.ui.productList.ProductListFragment;

/**
 * Created by Mateusz Grabarski on 07.09.2017.
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment selectedPosition;

        switch (position) {
            case 0:
                selectedPosition = ProductListFragment.newInstance();
                break;
            case 1:
                selectedPosition = CustomerListFragment.newInstance();
                break;
            case 2:
                selectedPosition = CheckoutFragment.newInstance();
                break;
            default:
                selectedPosition = ProductListFragment.newInstance();
                break;
        }

        return selectedPosition;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";

        switch (position) {
            case 0:
                title = "Products";
                break;
            case 1:
                title = "Customers";
                break;
            case 2:
                title = "Shopping Card";
                break;
        }

        return title;
    }
}
