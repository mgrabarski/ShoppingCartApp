package grabarski.shoppingcartapp.ui.checkout.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import grabarski.shoppingcartapp.R;
import grabarski.shoppingcartapp.data.model.LineItem;
import grabarski.shoppingcartapp.ui.listeners.CartActionsListener;
import grabarski.shoppingcartapp.utils.DataFormatter;

/**
 * Created by Mateusz Grabarski on 03.09.2017.
 */

public class CheckoutListAdapter extends RecyclerView.Adapter<CheckoutListAdapter.ViewHolder> {

    private final List<LineItem> mLineItems;
    private final Activity mContext;
    private final CartActionsListener mListener;

    public CheckoutListAdapter(List<LineItem> lineItems, Context context, CartActionsListener listener) {
        mLineItems = lineItems;
        mContext = (Activity) context;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shopping, parent, false);
        return new ViewHolder(rowView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (mLineItems !=null) {
            holder.populate(mLineItems.get(position));
        }
    }

    @Override
    public int getItemCount() {
        if (mLineItems != null) {
            return mLineItems.size();
        } else {
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.product_image)
        ImageView productImage;

        @BindView(R.id.text_view_product_name)
        TextView productName;

        @BindView(R.id.text_view_price)
        TextView price;

        @BindView(R.id.edit_text_qty)
        EditText qtyEditText;

        @BindView(R.id.button_delete)
        Button deleteButton;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            deleteButton.setOnClickListener(this);
            qtyEditText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LineItem item = mLineItems.get(getLayoutPosition());
                    updateQtyDialog(item);
                }
            });

        }

        @Override
        public void onClick(View v) {
            LineItem item = mLineItems.get(getLayoutPosition());
            mListener.onItemDeleted(item);
        }

        public void populate(LineItem item) {
            Picasso.with(mContext)
                    .load(item.getImagePath())
                    .fit()
                    .into(productImage);
            productName.setText(item.getProductName());
            price.setText(DataFormatter.formatCurrency(item.getSalePrice()));
            qtyEditText.setText(String.valueOf(item.getQuantity()));
        }
    }

    private void updateQtyDialog(final LineItem item) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
        LayoutInflater inflater = mContext.getLayoutInflater();

        View rootView = inflater.inflate(R.layout.dialog_enter_item_qty, null);
        dialog.setView(rootView);

        View titleView = inflater.inflate(R.layout.dialog_title, null);
        TextView titleText = titleView.findViewById(R.id.text_view_dialog_title);
        titleText.setText(item.getProductName());
        dialog.setCustomTitle(titleView);

        final EditText qtyEditText = rootView.findViewById(R.id.edit_text_item_qty);
        dialog.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (!qtyEditText.getText().toString().isEmpty()) {
                    int qtyEntered = Integer.parseInt(qtyEditText.getText().toString());
                    mListener.onItemQtyChange(item, qtyEntered);
                } else {
                    Toast.makeText(mContext, "Invalid Qty", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        dialog.show();

    }
}
