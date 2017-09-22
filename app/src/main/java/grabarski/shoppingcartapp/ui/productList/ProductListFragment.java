package grabarski.shoppingcartapp.ui.productList;

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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import grabarski.shoppingcartapp.R;
import grabarski.shoppingcartapp.data.model.Product;
import grabarski.shoppingcartapp.ui.listeners.OnProductSelectedListener;
import grabarski.shoppingcartapp.ui.productList.adapter.ProductListAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProductListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductListFragment extends Fragment implements
        OnProductSelectedListener, ProductListContract.View {

    @BindView(R.id.fragment_product_list_rv)
    RecyclerView productListRv;

    @BindView(R.id.fragment_product_list_no_data_tv)
    TextView noDataTv;

    @BindView(R.id.fragment_product_list_fab)
    FloatingActionButton floatingActionButton;

    Unbinder unbinder;

    private ProductPresenter productPresenter;
    private OnFragmentInteractionListener mListener;
    private ProductListAdapter adapter;
    private ArrayList<Product> products;

    public ProductListFragment() {

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ProductListFragment.
     */
    public static ProductListFragment newInstance() {
        ProductListFragment fragment = new ProductListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_list, container, false);
        unbinder = ButterKnife.bind(this, view);

        products = new ArrayList<>();
        productPresenter = new ProductPresenter(this);

        adapter = new ProductListAdapter(products, getContext(), this);

        productListRv.setLayoutManager(new LinearLayoutManager(getContext()));
        productListRv.setAdapter(adapter);

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
    public void onResume() {
        super.onResume();

        productPresenter.loadProducts();
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

    @Override
    public void onProductSelected(Product product) {

    }

    @Override
    public void onLongClickProduct(Product product) {

    }

    @Override
    public void showProducts(List<Product> products) {
        this.products.clear();
        this.products.addAll(products);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showAddProductForm() {

    }

    @Override
    public void showEditProductFrom(Product product) {

    }

    @Override
    public void showDeletedProductPrompt(Product product) {

    }

    @Override
    public void showGoogleSearch(Product product) {

    }

    @Override
    public void showEmptyTest() {
        productListRv.setVisibility(View.GONE);
        noDataTv.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideEmptyTest() {
        productListRv.setVisibility(View.VISIBLE);
        noDataTv.setVisibility(View.GONE);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    public interface OnFragmentInteractionListener {

    }
}
