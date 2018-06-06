package rs.cod3rs.shopifine.view;

import android.content.Context;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import rs.cod3rs.shopifine.Prefs_;
import rs.cod3rs.shopifine.R;
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

    @ViewById ImageButton removeFromWishlist;

    @ViewById ImageButton addToShoppingCart;

    public WishlistItemView(final Context context) {
        super(context);
        this.helper = new DatabaseHelper(context);
    }

    @Override
    public void bind(final WishlistItem data) {
        // TODO actual binding
    }
}
