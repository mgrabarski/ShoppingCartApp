package grabarski.shoppingcartapp.ui.customerList.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import grabarski.shoppingcartapp.R;
import grabarski.shoppingcartapp.data.model.Customer;
import grabarski.shoppingcartapp.ui.listeners.OnCustomerSelectedListener;

/**
 * Created by Mateusz Grabarski on 03.09.2017.
 */

public class CustomersListAdapter extends RecyclerView.Adapter<CustomersListAdapter.ViewHolder> {

    private ArrayList<Customer> customers;
    private Context context;
    private OnCustomerSelectedListener listener;
    private boolean shouldHighlightSelectedCustomer = false;
    private int selectedPosition = 0;

    public CustomersListAdapter(ArrayList<Customer> customers, Context context, OnCustomerSelectedListener listener) {
        this.customers = customers;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_customer, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (customers != null) {
            Customer customer = customers.get(position);
            holder.populate(customer, position);
        }
    }

    @Override
    public int getItemCount() {
        if (customers != null)
            return customers.size();
        else
            return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        @BindView(R.id.item_customer_avatar_civ)
        CircleImageView avatarCiv;

        @BindView(R.id.item_customer_name_tv)
        TextView nameTv;

        @BindView(R.id.item_customer_email_tv)
        TextView emailTv;

        @BindView(R.id.item_customer_root_ll)
        LinearLayout rootLl;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            avatarCiv.setOnClickListener(this);
            avatarCiv.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            shouldHighlightSelectedCustomer = true;
            selectedPosition = getLayoutPosition();
            listener.onCustomerSelected(customers.get(getLayoutPosition()));
            notifyDataSetChanged();
        }

        @Override
        public boolean onLongClick(View view) {
            listener.onLongClickCustomer(customers.get(getLayoutPosition()));
            return true;
        }

        public void populate(Customer customer, int position) {
            Picasso.with(context)
                    .load(customer.getProfileImagePath())
                    .fit()
                    .into(avatarCiv);

            nameTv.setText(customer.getCustomerName());
            emailTv.setText(customer.getEmailAddress());

            if (shouldHighlightSelectedCustomer) {
                if (selectedPosition == position) {
                    rootLl.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
                } else {
                    rootLl.setBackgroundColor(Color.TRANSPARENT);
                }
            } else {
                rootLl.setBackgroundColor(Color.TRANSPARENT);
            }
        }
    }
}
