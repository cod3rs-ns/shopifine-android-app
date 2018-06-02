package rs.cod3rs.shopifine.activity;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.rest.spring.annotations.RestService;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import rs.cod3rs.shopifine.R;
import rs.cod3rs.shopifine.adapter.RecommendedProductListAdapter;
import rs.cod3rs.shopifine.domain.Product;
import rs.cod3rs.shopifine.domain.ProductCategory;
import rs.cod3rs.shopifine.hateoas.product_categories.ProductCategoryResponse;
import rs.cod3rs.shopifine.hateoas.products.ProductCollectionResponse;
import rs.cod3rs.shopifine.hateoas.products.ProductResponseData;
import rs.cod3rs.shopifine.http.ProductCategories;
import rs.cod3rs.shopifine.http.Products;

@EActivity(R.layout.activity_product)
public class ProductActivity extends AppCompatActivity {

    @Extra
    Product product;

    @RestService
    ProductCategories productCategories;

    @RestService
    Products products;

    @ViewById
    TextView singleProductName;

    @ViewById
    ImageView singleProductCover;

    @ViewById
    TextView singleProductPrice;

    @ViewById
    TextView singleProductCategoryName;

    @AfterInject
    public void getProductInfo() {
        updateProductInfo(product);
        getProductCategory(product.categoryId);
        getProductsFromSameCategory(product.categoryId);
    }

    @UiThread
    public void updateProductInfo(final Product product) {
        Picasso.get().load(product.imageUrl).into(singleProductCover);
        singleProductName.setText(product.name);
        singleProductPrice.setText(String.format(Locale.US, "%.2f$", product.price.floatValue()));
    }

    @Background
    public void getProductCategory(final Long categoryId) {
        final ProductCategoryResponse res = productCategories.getProductCategory(categoryId);
        final ProductCategory category = res.getData().toDomain();
        updateProductCategoryInfo(category);
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
}
