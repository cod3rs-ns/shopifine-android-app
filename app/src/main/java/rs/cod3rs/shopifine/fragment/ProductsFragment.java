package rs.cod3rs.shopifine.fragment;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.rest.spring.annotations.RestService;

import java.util.List;
import java.util.stream.Collectors;

import rs.cod3rs.shopifine.R;
import rs.cod3rs.shopifine.activity.ProductActivity_;
import rs.cod3rs.shopifine.adapter.ProductsAdapter;
import rs.cod3rs.shopifine.domain.Product;
import rs.cod3rs.shopifine.generics.EndlessRecyclerViewScrollListener;
import rs.cod3rs.shopifine.hateoas.products.ProductResponseData;
import rs.cod3rs.shopifine.http.ProductCategories;
import rs.cod3rs.shopifine.http.Products;

@EFragment(R.layout.fragment_products)
public class ProductsFragment extends Fragment {

    private static final Integer LIMIT = 10;

    @RestService
    Products products;

    @RestService
    ProductCategories productCategories;

    @ViewById(R.id.productsRecyclerList)
    RecyclerView productsView;

    @Bean
    ProductsAdapter adapter;

    private LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());

    @AfterViews
    void bindAdapter() {
        productsView.setAdapter(adapter);
        adapter.setOnItemClickListener((position, view, data) ->
                ProductActivity_.intent(getContext())
                        .product(data).start());
        productsView.setLayoutManager(layoutManager);
        productsView.addOnScrollListener(onScrollListener);
    }

    @AfterViews
    void getData() {
        getProducts(0, LIMIT);
    }

    @Background
    void getProducts(final Integer offset, final Integer limit) {
        final List<Product> productList =
                products.retrieveAll(offset, limit)
                        .getData()
                        .stream()
                        .map(ProductResponseData::toDomain)
                        .peek(p ->
                                p.category = productCategories.
                                        getProductCategory(p.categoryId)
                                        .getData()
                                        .toDomain())
                        .collect(Collectors.toList());

        updateList(productList);
    }

    @UiThread
    void updateList(final List<Product> products) {
        adapter.addAll(products);
        productsView.setAdapter(adapter);
    }

    private EndlessRecyclerViewScrollListener onScrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
        @Override
        public void onLoadMore(final int page, final int totalItemsCount, final RecyclerView view) {
            getProducts(totalItemsCount, LIMIT);
        }
    };
}
