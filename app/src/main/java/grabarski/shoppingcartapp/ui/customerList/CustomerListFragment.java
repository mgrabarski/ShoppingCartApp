package grabarski.shoppingcartapp.ui.customerList;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import grabarski.shoppingcartapp.R;
import grabarski.shoppingcartapp.data.model.Customer;
import grabarski.shoppingcartapp.ui.customerList.adapter.CustomersListAdapter;
import grabarski.shoppingcartapp.ui.listeners.OnCustomerSelectedListener;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CustomerListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CustomerListFragment extends Fragment implements OnCustomerSelectedListener {

    @BindView(R.id.fragment_customer_list_rv)
    RecyclerView customerListRv;

    @BindView(R.id.fragment_customer_list_no_data_tv)
    TextView noDataTv;

    @BindView(R.id.fragment_customer_list_fab)
    FloatingActionButton floatingActionButton;

    Unbinder unbinder;

    private OnFragmentInteractionListener mListener;
    private CustomersListAdapter adapter;

    public CustomerListFragment() {

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CustomerListFragment.
     */
    public static CustomerListFragment newInstance() {
        CustomerListFragment fragment = new CustomerListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer_list, container, false);

        unbinder = ButterKnife.bind(this, view);

        ArrayList<Customer> customers = new ArrayList<>();

        adapter = new CustomersListAdapter(customers, getContext(), this);

        customerListRv.setLayoutManager(new LinearLayoutManager(getContext()));
        customerListRv.setAdapter(adapter);

        if (customers.size() < 1)
            showEmptyTextMessage();
        else
            hideEmptyTextMessage();

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void showEmptyTextMessage() {
        noDataTv.setVisibility(View.VISIBLE);
        customerListRv.setVisibility(View.GONE);
    }

    private void hideEmptyTextMessage() {
        noDataTv.setVisibility(View.GONE);
        customerListRv.setVisibility(View.VISIBLE);
    }

    @Override
    public void onCustomerSelected(Customer customer) {

    }

    @Override
    public void onLongClickCustomer(Customer customer) {

    }

    public interface OnFragmentInteractionListener {

    }
}
