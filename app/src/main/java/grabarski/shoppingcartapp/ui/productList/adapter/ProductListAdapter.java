package grabarski.shoppingcartapp.ui.productList.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import grabarski.shoppingcartapp.R;
import grabarski.shoppingcartapp.data.model.Product;
import grabarski.shoppingcartapp.ui.listeners.OnProductSelectedListener;
import grabarski.shoppingcartapp.utils.DataFormatter;

/**
 * Created by Mateusz Grabarski on 03.09.2017.
 */

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ViewHolder> {

    private ArrayList<Product> products;
    private Context context;
    private OnProductSelectedListener listener;

    public ProductListAdapter(ArrayList<Product> products, Context context, OnProductSelectedListener listener) {
        this.products = products;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (products != null) {
            Product product = products.get(position);
            holder.populate(product);
        }
    }

    @Override
    public int getItemCount() {
        if (products != null)
            return products.size();
        else
            return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        @BindView(R.id.item_product_category_tv)
        TextView categoryTv;

        @BindView(R.id.item_product_product_name_tv)
        TextView productNameTv;

        @BindView(R.id.item_product_image)
        ImageView productImage;

        @BindView(R.id.item_product_price_tv)
        TextView priceTv;

        @BindView(R.id.item_product_add_to_cart_btn)
        ImageView addToCartBtn;

        @BindView(R.id.item_product_description_tv)
        TextView descriptionTv;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            addToCartBtn.setOnClickListener(this);
        }

        public void populate(Product product) {
            Picasso.with(context)
                    .load(product.getImagePath())
                    .fit()
                    .into(productImage);

            categoryTv.setText(product.getCategoryName());
            productNameTv.setText(product.getProductName());

            String shortDescription = product.getProductDescription().substring(0, Math.min(product.getProductDescription().length(), 70));

            descriptionTv.setText(shortDescription);
            priceTv.setText(DataFormatter.formatCurrency(product.getSalePrice()));
        }

        @Override
        public void onClick(View view) {
            Product selectedProduct = products.get(getLayoutPosition());
            listener.onProductSelected(selectedProduct);
        }

        @Override
        public boolean onLongClick(View view) {
            Product clickedProduct = products.get(getLayoutPosition());
            listener.onLongClickProduct(clickedProduct);
            return true;
        }
    }
}
