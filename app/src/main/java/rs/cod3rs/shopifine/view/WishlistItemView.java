package rs.cod3rs.shopifine.view;

import android.content.Context;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import rs.cod3rs.shopifine.Prefs_;
import rs.cod3rs.shopifine.R;
import rs.cod3rs.shopifine.Util;
import rs.cod3rs.shopifine.db.DatabaseHelper;
import rs.cod3rs.shopifine.domain.WishlistItem;
import rs.cod3rs.shopifine.generics.ViewWrapper;

@EViewGroup(R.layout.item_wishlist_product)
public class WishlistItemView extends LinearLayout implements ViewWrapper.Binder<WishlistItem> {

    private final DatabaseHelper helper;

    @Pref Prefs_ prefs;

    @ViewById ImageView wishlistProductImage;

    @ViewById TextView wishlistProductName;

    @ViewById TextView wishlistProductPrice;

    @ViewById TextView wishlistProductCategory;

    @ViewById ImageButton removeFromWishlist;

    @ViewById ImageButton addToShoppingCart;

    public WishlistItemView(final Context context) {
        super(context);
        this.helper = new DatabaseHelper(context);
    }

    @Override
    public void bind(final WishlistItem data) {
        Picasso.get().load(data.product.imageUrl).into(wishlistProductImage);
        wishlistProductName.setText(data.product.name);
        wishlistProductPrice.setText(Util.formatPrice(data.product.price));
        wishlistProductCategory.setText("Category"); // TODO handle null
    }
}
