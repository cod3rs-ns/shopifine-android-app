package rs.cod3rs.shopifine.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.rest.spring.annotations.RestService;
import org.joda.time.DateTime;

import java.sql.SQLException;

import rs.cod3rs.shopifine.R;
import rs.cod3rs.shopifine.Util;
import rs.cod3rs.shopifine.adapter.ShoppingCartAdapter;
import rs.cod3rs.shopifine.db.DatabaseHelper;
import rs.cod3rs.shopifine.db.ShoppingCartItem;
import rs.cod3rs.shopifine.generics.ViewWrapper;
import rs.cod3rs.shopifine.http.ActionDiscounts;

@EViewGroup(R.layout.item_shopping_cart_product)
public class ShoppingCartItemView extends LinearLayout implements ViewWrapper.Binder<ShoppingCartItem> {

    @RestService
    ActionDiscounts discounts;

    @ViewById
    ImageView productImage;

    @ViewById
    TextView productName;

    @ViewById
    TextView productPrice;

    @ViewById
    TextView productDiscount;

    @ViewById
    TextView itemQuantity;

    private Double discount;

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
        getDiscountAmount();
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
            final RecyclerView parent = (RecyclerView) this.getParent();
            final ShoppingCartAdapter adapter = (ShoppingCartAdapter) parent.getAdapter();
            final TextView totalAmount = (TextView) ((RelativeLayout) getParent().getParent()).findViewById(R.id.cartTotalAmount);

            if (EMPTY == item.quantity) {
                helper.getShoppingCartDAO().delete(item);
                adapter.remove(item);

                if (EMPTY == adapter.getItemCount()) {
                    final FrameLayout layout = (FrameLayout) parent.getParent().getParent();
                    layout.findViewById(R.id.shopping_cart_items_container).setVisibility(INVISIBLE);
                    layout.findViewById(R.id.shopping_cart_no_items).setVisibility(VISIBLE);
                } else {
                    updateTotalAmount(totalAmount, adapter);
                }

                Toast.makeText(getContext(), R.string.removed_from_shopping_cart, Toast.LENGTH_SHORT).show();
            } else {
                helper.getShoppingCartDAO().update(item);
                adapter.update(item);

                updateQuantityAndTotalAmount(item.quantity);
            }
        } catch (final SQLException e) {
            Log.e(getClass().getSimpleName(), e.getMessage());
        }
    }

    @Background
    void getDiscountAmount() {
        final Double discountAmount = discounts.getDiscountsForProductCategory(this.item.product.categoryId, DateTime.now().toString())
                .getData().stream().mapToDouble(data -> data.getAttributes().getDiscount()).sum();

        setDiscountAmount(discountAmount);
    }

    @UiThread
    void updateQuantityAndTotalAmount(final Integer quantity) {
        final TextView totalAmount = (TextView) ((RelativeLayout) getParent().getParent()).findViewById(R.id.cartTotalAmount);
        final RecyclerView parent = (RecyclerView) this.getParent();
        final ShoppingCartAdapter adapter = (ShoppingCartAdapter) parent.getAdapter();

        itemQuantity.setText(String.valueOf(quantity));
        updateTotalAmount(totalAmount, adapter);
    }

    @UiThread
    void setDiscountAmount(final Double discountAmount) {
        productDiscount.setText(String.format(getResources().getString(R.string.shopping_cart_discount), discountAmount));

        final RecyclerView parent = (RecyclerView) this.getParent();
        final ShoppingCartAdapter adapter = (ShoppingCartAdapter) parent.getAdapter();

        this.item.discountAmount = discountAmount;
        adapter.update(this.item);
    }

    @UiThread
    void updateTotalAmount(final TextView totalAmount, final ShoppingCartAdapter adapter) {
        totalAmount.setText(Util.formatPrice(adapter.amount()));
    }

    public Double getDiscount() {
        return discount;
    }

    private static final int EMPTY = 0;
}
