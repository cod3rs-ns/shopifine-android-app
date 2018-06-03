package rs.cod3rs.shopifine.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.sql.SQLException;

import rs.cod3rs.shopifine.R;
import rs.cod3rs.shopifine.Util;
import rs.cod3rs.shopifine.adapter.ShoppingCartAdapter;
import rs.cod3rs.shopifine.db.DatabaseHelper;
import rs.cod3rs.shopifine.db.ShoppingCartItem;
import rs.cod3rs.shopifine.generics.ViewWrapper;

@EViewGroup(R.layout.item_shopping_cart_product)
public class ShoppingCartItemView extends LinearLayout implements ViewWrapper.Binder<ShoppingCartItem> {

    @ViewById
    ImageView productImage;

    @ViewById
    TextView productName;

    @ViewById
    TextView productPrice;

    @ViewById
    TextView itemQuantity;

    private ShoppingCartItem item;

    private final DatabaseHelper helper;

    public ShoppingCartItemView(final Context context) {
        super(context);
        this.helper = new DatabaseHelper(context);
    }

    @Override
    public void bind(final ShoppingCartItem item) {
        this.item = item;

        Picasso.get().load(item.product.imageUrl).into(productImage);
        productName.setText(item.product.name);
        productPrice.setText(Util.formatPrice(item.product.price));
        itemQuantity.setText(String.valueOf(item.quantity));
    }

    @Click
    public void quantityPlusOne() {
        item.quantity += 1;
        updateItem(item);
    }

    @Click
    public void quantityMinusOne() {
        item.quantity -= 1;
        updateItem(item);
    }

    private void updateItem(final ShoppingCartItem item) {
        try {
            if (EMPTY == item.quantity) {
                helper.getShoppingCartDAO().delete(item);

                final RecyclerView parent = (RecyclerView) this.getParent();
                final ShoppingCartAdapter adapter = (ShoppingCartAdapter) parent.getAdapter();
                adapter.remove(item);

                if (EMPTY == adapter.getItemCount()) {
                    final FrameLayout layout = (FrameLayout) parent.getParent().getParent();
                    layout.findViewById(R.id.shopping_cart_items_container).setVisibility(INVISIBLE);
                    layout.findViewById(R.id.shopping_cart_no_items).setVisibility(VISIBLE);
                }

                Toast.makeText(getContext(), R.string.removed_from_shopping_cart, Toast.LENGTH_SHORT).show();
            } else {
                helper.getShoppingCartDAO().update(item);
                updateQuantityAndTotalAmount(item.quantity);
            }
        } catch (final SQLException e) {
            Log.e(this.getClass().getSimpleName(), e.getMessage());
        }
    }

    @UiThread
    void updateQuantityAndTotalAmount(final Integer quantity) {
        // TODO Update Total amount
        itemQuantity.setText(String.valueOf(quantity));
    }

    private static final int EMPTY = 0;
}
