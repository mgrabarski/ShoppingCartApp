package grabarski.shoppingcartapp.ui.checkout;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import grabarski.shoppingcartapp.R;
import grabarski.shoppingcartapp.data.model.LineItem;
import grabarski.shoppingcartapp.ui.checkout.adapter.CheckoutListAdapter;
import grabarski.shoppingcartapp.ui.listeners.CartActionsListener;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CheckoutFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CheckoutFragment extends Fragment implements CartActionsListener {

    @BindView(R.id.fragment_checkout_rv)
    RecyclerView checkoutRv;

    @BindView(R.id.fragment_checkout_no_data_tv)
    TextView noDataTv;

    @BindView(R.id.fragment_checkout_sub_total_tv)
    TextView subTotalTv;

    @BindView(R.id.fragment_checkout_tax_tv)
    TextView taxTv;

    @BindView(R.id.fragment_checkout_total_tv)
    TextView totalTv;

    @BindView(R.id.fragment_checkout_button_cash)
    RadioButton buttonCash;

    @BindView(R.id.fragment_checkout_button_card)
    RadioButton buttonCard;

    @BindView(R.id.fragment_checkout_button_paypal)
    RadioButton buttonPaypal;

    @BindView(R.id.fragment_checkout_payment_type)
    RadioGroup paymentType;

    @BindView(R.id.fragment_checkout_clear_cart_button)
    Button cartBtn;

    @BindView(R.id.fragment_checkout_checkout_cart_button)
    Button checkoutCartBtn;

    Unbinder unbinder;
    private OnFragmentInteractionListener mListener;
    private CheckoutListAdapter adapter;

    public CheckoutFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CheckoutFragment.
     */
    public static CheckoutFragment newInstance() {
        CheckoutFragment fragment = new CheckoutFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_checkout, container, false);
        unbinder = ButterKnife.bind(this, view);

        ArrayList<LineItem> items = new ArrayList<>();

        adapter = new CheckoutListAdapter(items, getContext(), this);

        checkoutRv.setLayoutManager(new LinearLayoutManager(getContext()));
        checkoutRv.setAdapter(adapter);

        if (items.size() < 1)
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
        checkoutRv.setVisibility(View.GONE);
    }

    private void hideEmptyTextMessage() {
        noDataTv.setVisibility(View.GONE);
        checkoutRv.setVisibility(View.VISIBLE);
    }

    @Override
    public void onItemDeleted(LineItem item) {

    }

    @Override
    public void onItemQtyChange(LineItem item, int qty) {

    }

    public interface OnFragmentInteractionListener {

    }
}
