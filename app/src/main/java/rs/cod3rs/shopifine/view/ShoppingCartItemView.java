package rs.cod3rs.shopifine.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
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
import java.util.Locale;

import rs.cod3rs.shopifine.R;
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
    TextView productCategory;

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

    @Override
    public void bind(final ShoppingCartItem item) {
        this.item = item;

        Picasso.get().load(item.product.imageUrl).into(productImage);
        productName.setText(item.product.name);
        productCategory.setText(item.product.category.name);
        productPrice.setText(String.format(Locale.US, "%.2f €", item.product.price));
        itemQuantity.setText(String.valueOf(item.quantity));
    }

    private void updateItem(final ShoppingCartItem item) {
        try {
            if (0 == item.quantity) {
                helper.getShoppingCartDAO().delete(item);
                ((ShoppingCartAdapter)((RecyclerView) this.getParent()).getAdapter()).remove(item);
                Toast.makeText(getContext(), R.string.removed_from_shopping_cart, Toast.LENGTH_SHORT).show();
            } else {
                helper.getShoppingCartDAO().update(item);
                updateQuantity(item.quantity);
            }
        } catch (final SQLException e) {
            e.printStackTrace();
        }
    }

    @UiThread
    void updateQuantity(final Integer quantity) {
        itemQuantity.setText(String.valueOf(quantity));
    }
}
