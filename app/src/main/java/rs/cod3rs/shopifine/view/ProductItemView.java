package rs.cod3rs.shopifine.view;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.rest.spring.annotations.RestService;

import java.util.Locale;

import rs.cod3rs.shopifine.R;
import rs.cod3rs.shopifine.domain.Product;
import rs.cod3rs.shopifine.domain.ProductCategory;
import rs.cod3rs.shopifine.http.ProductCategories;

@EViewGroup(R.layout.item_product)
public class ProductItemView extends LinearLayout {

    @RestService
    ProductCategories productCategories;

    @ViewById
    ImageView productImage;

    @ViewById
    TextView productName;

    @ViewById
    TextView productCategory;

    @ViewById
    TextView productPrice;

    public ProductItemView(final Context context) {
        super(context);
    }

    @Click
    public void addToShoppingCart() {
        Log.i(this.getClass().getSimpleName(), "Clicked to adding to shopping cart: " + this.productName + ".");
    }

    @Click
    public void addToWishlist() {
        Log.i(this.getClass().getSimpleName(), "Clicked to adding to wishlist: " + this.productName + ".");
    }

    public void bind(final Product product) {
        Picasso.get().load(product.imageUrl).into(productImage);
        productName.setText(product.name);
        productPrice.setText(String.format(Locale.US, "%.2f €", product.price));
        retrieveCategory(product.categoryId);
    }

    @Background
    public void retrieveCategory(final Long categoryId) {
        final ProductCategory category = productCategories.getProductCategory(categoryId).getData().toDomain();
        updateProductCategory(category);
    }

    @UiThread
    public void updateProductCategory(final ProductCategory category) {
        productCategory.setText(category.name);
    }
}
