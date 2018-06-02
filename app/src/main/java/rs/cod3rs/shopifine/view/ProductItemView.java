package rs.cod3rs.shopifine.view;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.sql.SQLException;
import java.util.Locale;

import rs.cod3rs.shopifine.Prefs_;
import rs.cod3rs.shopifine.R;
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
            e.printStackTrace();
        }
    }

    @Click
    public void addToWishlist() {
        Log.i(this.getClass().getSimpleName(), "Clicked to adding to wishlist: " + this.productName + ".");
    }

    @Override
    public void bind(final Product product) {
        this.product = product;

        Picasso.get().load(product.imageUrl).into(productImage);
        productName.setText(product.name);
        productPrice.setText(String.format(Locale.US, "%.2f €", product.price));
        productCategory.setText(product.category.name);
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
}
