package rs.cod3rs.shopifine.adapter;


import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import rs.cod3rs.shopifine.R;
import rs.cod3rs.shopifine.activity.ProductActivity_;
import rs.cod3rs.shopifine.domain.Product;

public class RecommendedProductListAdapter extends RecyclerView.Adapter<RecommendedProductListAdapter.ViewHolder> {

    private final List<Product> products;
    private final LayoutInflater mInflater;

    public RecommendedProductListAdapter(final Context context, final List<Product> products) {
        this.mInflater = LayoutInflater.from(context);
        this.products = products;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View view = mInflater.inflate(R.layout.item_product_vertical, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Product product = getItem(position);

        Picasso.get().load(product.imageUrl).into(holder.image);

        holder.productName.setText(product.name);
        holder.productPrice.setText(String.format("%.2f$", product.price));
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final ImageView image;
        private final TextView productName;
        private final TextView productPrice;

        ViewHolder(final View itemView) {
            super(itemView);

            image = (ImageView) itemView.findViewById(R.id.recommendedProductImage);
            productName = (TextView) itemView.findViewById(R.id.recommendedProductName);
            productPrice = (TextView) itemView.findViewById(R.id.recommendedProductPrice);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(final View view) {
            final Product product = getItem(getAdapterPosition());
            ProductActivity_.intent(itemView.getContext()).product(product).start();
            ((Activity) view.getContext()).finish();
        }
    }

    private Product getItem(final int id) {
        return products.get(id);
    }

}
