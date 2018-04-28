package rs.cod3rs.shopifine.fragment;

import android.support.v4.app.Fragment;
import android.widget.ListView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.rest.spring.annotations.RestService;

import java.util.ArrayList;
import java.util.List;

import rs.cod3rs.shopifine.R;
import rs.cod3rs.shopifine.adapter.ShoppingCartAdapter;
import rs.cod3rs.shopifine.domain.Product;
import rs.cod3rs.shopifine.hateoas.ProductCollectionResponse;
import rs.cod3rs.shopifine.hateoas.ProductResponseData;
import rs.cod3rs.shopifine.http.Products;

@EFragment(R.layout.fragment_shopping_cart)
public class ShoppingCartFragment extends Fragment {
    @RestService
    Products products;

    @ViewById(R.id.shoppingCartProducts)
    ListView shoppingCartProductsView;

    @Bean
    ShoppingCartAdapter adapter;

    @AfterViews
    void getData() {
        getProducts();
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
        shoppingCartProductsView.setAdapter(adapter);
    }
}
