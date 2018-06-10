package rs.cod3rs.shopifine.view;

import android.content.Context;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.androidannotations.rest.spring.annotations.RestService;

import java.sql.SQLException;

import rs.cod3rs.shopifine.Prefs_;
import rs.cod3rs.shopifine.R;
import rs.cod3rs.shopifine.Util;
import rs.cod3rs.shopifine.adapter.WishlistItemsAdapter;
import rs.cod3rs.shopifine.db.DatabaseHelper;
import rs.cod3rs.shopifine.db.ShoppingCartItem;
import rs.cod3rs.shopifine.domain.WishlistItem;
import rs.cod3rs.shopifine.generics.ViewWrapper;
import rs.cod3rs.shopifine.http.Wishlists;

@EViewGroup(R.layout.item_wishlist_product)
public class WishlistItemView extends LinearLayout implements ViewWrapper.Binder<WishlistItem> {

    private final DatabaseHelper helper;

    @Bean
    WishlistItemsAdapter parentAdapter;

    @Pref
    Prefs_ prefs;

    @ViewById
    ImageView wishlistProductImage;

    @ViewById
    TextView wishlistProductName;

    @ViewById
    TextView wishlistProductPrice;

    @ViewById
    TextView wishlistProductCategory;

    @ViewById
    ImageButton removeFromWishlist;

    @ViewById
    ImageButton wishlistAddToShoppingCart;

    @RestService
    Wishlists wishlists;

    private WishlistItem item;

    public WishlistItemView(final Context context) {
        super(context);
        this.helper = new DatabaseHelper(context);
    }

    @Click
    public void removeFromWishlist() {
        removeProductFromWishlist();
    }

    @Click
    public void wishlistAddToShoppingCart() {
        final Integer userId = prefs.loggedUserId().get();
        final ShoppingCartItem cartItem = new ShoppingCartItem(userId, item.product);

        addItemToShoppingCart(cartItem);
    }

    @UiThread
    void addItemToShoppingCart(final ShoppingCartItem item) {
        try {
            final boolean itemExists = helper.getShoppingCartDAO().queryForEq("product_id", item.productId).size() > 0;

            if (itemExists) {
                Toast.makeText(getContext(), R.string.already_in_shopping_cart, Toast.LENGTH_SHORT).show();
            } else {
                helper.getShoppingCartDAO().create(item);
                Toast.makeText(getContext(), R.string.added_to_shopping_cart, Toast.LENGTH_SHORT).show();
            }
        } catch (final SQLException e) {
            Log.e(getClass().getSimpleName(), e.getMessage());
        }
    }

    @UiThread
    void removeProductFromWishlist() {
        apiRemoveProductFromWishlist();
        parentAdapter.remove(item);
        Toast.makeText(getContext(), R.string.removed_from_wishlist, Toast.LENGTH_SHORT).show();
    }

    @Background
    void apiRemoveProductFromWishlist() {
        final Integer userId = prefs.loggedUserId().get();
        wishlists.removeItem(userId, item.id);
    }

    @Override
    public void bind(final WishlistItem data) {
        this.item = data;

        Picasso.get().load(data.product.imageUrl).into(wishlistProductImage);
        wishlistProductName.setText(String.format("%s", data.product.name));
        wishlistProductPrice.setText(Util.formatPrice(data.product.price));
        wishlistProductCategory.setText(String.format("%s", data.product.category.name));
    }
}
