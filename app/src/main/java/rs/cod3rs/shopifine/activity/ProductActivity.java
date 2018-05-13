package rs.cod3rs.shopifine.activity;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.AfterExtras;
import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.rest.spring.annotations.RestService;

import rs.cod3rs.shopifine.R;
import rs.cod3rs.shopifine.domain.Product;
import rs.cod3rs.shopifine.domain.ProductCategory;
import rs.cod3rs.shopifine.hateoas.product_categories.ProductCategoryResponse;
import rs.cod3rs.shopifine.http.ProductCategories;

@EActivity(R.layout.activity_product)
public class ProductActivity extends AppCompatActivity {

    @Extra
    Product product;

    @RestService
    ProductCategories productCategories;

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
        Log.i(ProductActivity.class.getSimpleName(), String.format("Product activity opened for id %s", product.id));
        updateProductInfo(product);
        getProductCategory(product.categoryId);
    }

    @UiThread
    public void updateProductInfo(final Product product) {
        Picasso.get().load(product.imageUrl).into(singleProductCover);
        singleProductName.setText(product.name);
        singleProductPrice.setText(String.format("%s$", product.price));
    }

    @Background
    public void getProductCategory(final Long categoryId) {
        System.out.println(productCategories);
        final ProductCategoryResponse res = productCategories.getProductCategory(categoryId);
        System.out.println(res);
        final ProductCategory category = res.getData().toDomain();
        updateProductCategoryInfo(category);
    }

    @UiThread
    public void updateProductCategoryInfo(final ProductCategory category) {
        singleProductCategoryName.setText(category.name);
    }
}
