package rs.cod3rs.shopifine.activity;

import android.graphics.Typeface;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import net.cachapa.expandablelayout.ExpandableLayout;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.androidannotations.rest.spring.annotations.RestService;
import org.joda.time.DateTime;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import rs.cod3rs.shopifine.Prefs_;
import rs.cod3rs.shopifine.R;
import rs.cod3rs.shopifine.Util;
import rs.cod3rs.shopifine.adapter.RecommendedProductListAdapter;
import rs.cod3rs.shopifine.db.DatabaseHelper;
import rs.cod3rs.shopifine.db.ShoppingCartItem;
import rs.cod3rs.shopifine.domain.Product;
import rs.cod3rs.shopifine.domain.ProductCategory;
import rs.cod3rs.shopifine.hateoas.product_categories.ProductCategoryResponse;
import rs.cod3rs.shopifine.hateoas.products.ProductCollectionResponse;
import rs.cod3rs.shopifine.hateoas.products.ProductResponseData;
import rs.cod3rs.shopifine.http.ActionDiscounts;
import rs.cod3rs.shopifine.http.ProductCategories;
import rs.cod3rs.shopifine.http.Products;

@EActivity(R.layout.activity_product)
public class ProductActivity extends AppCompatActivity {

    @Extra
    Product product;

    @Pref
    Prefs_ prefs;

    @RestService
    ProductCategories productCategories;

    @RestService
    Products products;

    @RestService
    ActionDiscounts actionDiscounts;

    @ViewById
    TextView singleProductName;

    @ViewById
    ImageView singleProductCover;

    @ViewById
    TextView singleProductPrice;

    @ViewById
    TextView singleProductCategoryName;

    @ViewById
    FloatingActionButton wishListButton;

    @ViewById
    ExpandableLayout productDiscountsExpander;

    @ViewById
    LinearLayout productDiscountsHolder;

    @ViewById
    LinearLayout productInfo;

    private DatabaseHelper helper;

    @AfterViews
    public void getProductInfo() {
        this.helper = new DatabaseHelper(this);

        updateProductInfo(product);
        getProductCategory(product.categoryId);
        getProductsFromSameCategory(product.categoryId);
        getDiscounts(product.categoryId);
    }

    @AfterViews
    void setListener() {
        productInfo.setOnClickListener(
                view -> productDiscountsExpander.setExpanded(!productDiscountsExpander.isExpanded()));
    }

    @UiThread
    public void updateProductInfo(final Product product) {
        Picasso.get().load(product.imageUrl).into(singleProductCover);
        singleProductName.setText(product.name);
        singleProductPrice.setText(Util.formatPrice(product.price));
    }

    @Background
    public void getProductCategory(final Long categoryId) {
        final ProductCategoryResponse res = productCategories.getProductCategory(categoryId);
        final ProductCategory category = res.getData().toDomain();
        updateProductCategoryInfo(category);
    }

    @Background
    public void getDiscounts(final Long productCategoryId) {
        final List<String> discounts = actionDiscounts.getDiscountsForProductCategory(productCategoryId, DateTime.now().toString())
                .getData().stream().map(d -> String.format(getString(R.string.action_discount), d.getAttributes().getName(), d.getAttributes().getDiscount()))
                .collect(Collectors.toList());
        fillDiscounts(discounts);
    }

    @UiThread
    public void fillDiscounts(final List<String> discounts) {
        discounts.forEach(discountText -> {
            final TextView textView = createDiscountLabel(discountText);
            productDiscountsHolder.addView(textView);
        });

        if (discounts.isEmpty()) {
            final TextView textView = createDiscountLabel(getString(R.string.no_available_discounts));
            productDiscountsHolder.addView(textView);
        }
    }

    @UiThread
    public void updateProductCategoryInfo(final ProductCategory category) {
        singleProductCategoryName.setText(category.name);
    }

    @Background
    public void getProductsFromSameCategory(final Long productCategoryId) {
        final ProductCollectionResponse productsRes = products.retrieveFromCategory(productCategoryId);
        final List<Product> products = productsRes.getData().stream()
                .filter(p -> !p.getId().equals(product.id))
                .map(ProductResponseData::toDomain)
                .collect(Collectors.toList());

        updateProductsUI(products);
    }

    @UiThread
    public void updateProductsUI(final List<Product> listProducts) {
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.sameCategoryProducts);

        final LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(ProductActivity.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManager);

        final RecommendedProductListAdapter adapter = new RecommendedProductListAdapter(this, listProducts);
        recyclerView.setAdapter(adapter);
    }

    @Click
    public void shoppingButton() {
        final Integer userId = prefs.loggedUserId().get();
        final ShoppingCartItem item = new ShoppingCartItem(userId, this.product);
        try {
            addItemToShoppingCart(item);
        } catch (final SQLException e) {
            Log.e(this.getClass().getSimpleName(), e.getMessage());
        }

    }

    @Click
    public void wishListButton() {
        if (product.isInWishlist) {
            removeProductFromWishlist();
        } else {
            addProductToWishlist();
        }
    }

    private void addItemToShoppingCart(final ShoppingCartItem item) throws SQLException {
        final boolean itemExists = helper.getShoppingCartDAO().queryForEq("product_id", item.productId).size() > 0;

        if (itemExists) {
            Toast.makeText(this, R.string.already_in_shopping_cart, Toast.LENGTH_SHORT).show();
        } else {
            helper.getShoppingCartDAO().create(item);
            Toast.makeText(this, R.string.added_to_shopping_cart, Toast.LENGTH_SHORT).show();
        }
    }

    @UiThread
    void removeProductFromWishlist() {
        // TODO Call API to update wishlist for user
        product.isInWishlist = false;
        wishListButton.setImageResource(R.drawable.ic_favorite_border_black_24dp);
    }

    @UiThread
    void addProductToWishlist() {
        // TODO Call API to update wishlist for user
        product.isInWishlist = true;
        wishListButton.setImageResource(R.drawable.ic_favorite_black_24dp);
    }

    private TextView createDiscountLabel(final String content) {
        final TextView textView = new TextView(this);
        textView.setText(content);
        textView.setTextColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        textView.setTypeface(null, Typeface.ITALIC);
        return textView;
    }
}
