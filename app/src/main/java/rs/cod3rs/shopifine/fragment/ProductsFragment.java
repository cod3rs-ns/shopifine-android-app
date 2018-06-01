package rs.cod3rs.shopifine.fragment;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.ListView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.rest.spring.annotations.RestService;

import java.util.ArrayList;
import java.util.List;

import rs.cod3rs.shopifine.R;
import rs.cod3rs.shopifine.activity.ProductActivity_;
import rs.cod3rs.shopifine.adapter.ProductsAdapter;
import rs.cod3rs.shopifine.domain.Product;
import rs.cod3rs.shopifine.hateoas.products.ProductCollectionResponse;
import rs.cod3rs.shopifine.hateoas.products.ProductResponseData;
import rs.cod3rs.shopifine.http.Products;

@EFragment(R.layout.fragment_products)
public class ProductsFragment extends Fragment {
    @RestService
    Products products;

    @ViewById(R.id.products)
    ListView productsView;

    @Bean
    ProductsAdapter adapter;

    @AfterViews
    void bindAdapter() {
        productsView.setAdapter(adapter);
    }

    @AfterViews
    void getData() {
        getProducts();
    }

    @ItemClick
    void productsItemClicked(final Product product) {
        Log.i(ProductsFragment.class.getSimpleName(), String.format("Clicked on product %s", product.name));
        ProductActivity_.intent(this).product(product).start();
    }

    @Background
    void getProducts() {
        final ProductCollectionResponse res = products.retrieveAll();
        final List<Product> p = new ArrayList<>();

        for (final ProductResponseData data : res.getData()) {
            p.add(data.toDomain());
        }

        updateList(p);
    }

    @UiThread
    void updateList(final List<Product> p) {
        adapter.products = p;
        productsView.setAdapter(adapter);
    }
}
