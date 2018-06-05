package rs.cod3rs.shopifine.view;

import android.content.Context;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.sql.SQLException;

import rs.cod3rs.shopifine.Prefs_;
import rs.cod3rs.shopifine.R;
import rs.cod3rs.shopifine.Util;
import rs.cod3rs.shopifine.db.DatabaseHelper;
import rs.cod3rs.shopifine.db.ShoppingCartItem;
import rs.cod3rs.shopifine.domain.Product;
import rs.cod3rs.shopifine.generics.ViewWrapper;

@EViewGroup(R.layout.item_product)
public class ProductItemView extends LinearLayout implements ViewWrapper.Binder<Product> {

    @Pref
    Prefs_ prefs;

    @ViewById
    ImageView productImage;

    @ViewById
    TextView productName;

    @ViewById
    TextView productCategory;

    @ViewById
    TextView productPrice;

    @ViewById
    ImageButton addToWishlist;

    private Product product;

    private final DatabaseHelper helper;

    public ProductItemView(final Context context) {
        super(context);
        this.helper = new DatabaseHelper(context);
    }

    @Click
    public void addToShoppingCart() {
        final Integer userId = prefs.loggedUserId().get();
        final ShoppingCartItem item = new ShoppingCartItem(userId, this.product);

        try {
            addItemToShoppingCart(item);
        } catch (final SQLException e) {
            Log.e(this.getClass().getSimpleName(), e.getMessage());
        }
    }

    @Click
    public void addToWishlist() {
        if (product.isInWishlist) {
            removeProductFromWishlist();
        } else {
            addProductToWishlist();
        }
    }

    @Override
    public void bind(final Product product) {
        this.product = product;

        Picasso.get().load(product.imageUrl).into(productImage);
        productName.setText(product.name);
        productPrice.setText(Util.formatPrice(product.price));
        productCategory.setText(product.category.name);

        if (product.isInWishlist) {
            addToWishlist.setBackgroundResource(R.drawable.ic_favorite_black_24dp);
        }
    }

    private void addItemToShoppingCart(final ShoppingCartItem item) throws SQLException {
        final boolean itemExists = helper.getShoppingCartDAO().queryForEq("product_id", item.productId).size() > 0;

        if (itemExists) {
            Toast.makeText(getContext(), R.string.already_in_shopping_cart, Toast.LENGTH_SHORT).show();
        } else {
            helper.getShoppingCartDAO().create(item);
            Toast.makeText(getContext(), R.string.added_to_shopping_cart, Toast.LENGTH_SHORT).show();
        }
    }

    @UiThread
    void removeProductFromWishlist() {
        // TODO Call API to update wishlist for user
        product.isInWishlist = false;
        addToWishlist.setBackgroundResource(R.drawable.ic_favorite_border_black_24dp);
    }

    @UiThread
    void addProductToWishlist() {
        // TODO Call API to update wishlist for user
        product.isInWishlist = true;
        addToWishlist.setBackgroundResource(R.drawable.ic_favorite_black_24dp);
    }
}
