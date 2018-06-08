package rs.cod3rs.shopifine.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

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
import rs.cod3rs.shopifine.SearchUtil;
import rs.cod3rs.shopifine.Util;
import rs.cod3rs.shopifine.activity.ProductActivity_;
import rs.cod3rs.shopifine.adapter.ProductsAdapter;
import rs.cod3rs.shopifine.domain.Product;
import rs.cod3rs.shopifine.fragment.FiltersFragmentDialog.FiltersDialogListener;
import rs.cod3rs.shopifine.generics.EndlessRecyclerViewScrollListener;
import rs.cod3rs.shopifine.hateoas.products.ProductResponseData;
import rs.cod3rs.shopifine.http.ProductCategories;
import rs.cod3rs.shopifine.http.Products;

import static rs.cod3rs.shopifine.fragment.FiltersFragmentDialog.SELECTED_CATEGORY_ARG;
import static rs.cod3rs.shopifine.fragment.FiltersFragmentDialog.SELECTED_PRICE_RANGE_ARG;

@EFragment(R.layout.fragment_products)
public class ProductsFragment extends Fragment implements FiltersDialogListener {

    private static final Integer LIMIT = 10;

    @RestService
    Products products;

    @RestService
    ProductCategories productCategories;

    @ViewById(R.id.productsRecyclerList)
    RecyclerView productsView;

    @ViewById(R.id.noProducts)
    TextView productsNoItems;

    @Bean
    ProductsAdapter adapter;

    private LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());

    private SearchView searchView = null;

    private String query = "";
    private String filters = "";

    private int priceFilterId = -1;
    private int categoryFilterId = -1;

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

    @AfterViews
    void enableSearch() {
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(final Menu menu, final MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_menu, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_search);

        setupSearchView(searchItem);

        super.onCreateOptionsMenu(menu, inflater);
    }

    private void setupSearchView(final MenuItem item) {
        if (item != null) {
            searchView = (SearchView) item.getActionView();
        }

        if (searchView != null) {
            searchView.setQueryHint(getResources().getString(R.string.search_hint));
            searchView.setMaxWidth(getResources().getDimensionPixelSize(R.dimen.searchBox));

            final SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextChange(final String query) {
                    return true;
                }

                @Override
                public boolean onQueryTextSubmit(final String query) {
                    updateSearchQuery(query);
                    return false;
                }
            };

            searchView.setOnQueryTextListener(queryTextListener);
        }
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                // Not implemented here
                return false;
            case R.id.action_filters:
                showFilterDialog();
                return true;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Background
    void getProducts(final Integer offset, final Integer limit) {
        final List<Product> productList =
                products.retrieveAll(offset, limit, combineFiltersAndQuery(filters, query))
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
        if (products.isEmpty()) {
            productsView.setVisibility(View.INVISIBLE);
            productsNoItems.setVisibility(View.VISIBLE);
        } else {
            productsView.setVisibility(View.VISIBLE);
            productsNoItems.setVisibility(View.INVISIBLE);

            adapter.clear();
            adapter.addAll(products);
            productsView.setAdapter(adapter);
        }
    }

    void showFilterDialog() {
        final FiltersFragmentDialog dialog = FiltersFragmentDialog_.builder().build();

        final Bundle bundle = new Bundle();
        bundle.putInt(SELECTED_PRICE_RANGE_ARG, this.priceFilterId);
        bundle.putInt(SELECTED_CATEGORY_ARG, this.categoryFilterId);
        dialog.setArguments(bundle);

        dialog.show(getFragmentManager(), String.valueOf(R.layout.dialog_filters));
    }

    private EndlessRecyclerViewScrollListener onScrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
        @Override
        public void onLoadMore(final int page, final int totalItemsCount, final RecyclerView view) {
            getProducts(totalItemsCount, LIMIT);
        }
    };

    private void updateSearchQuery(final String query) {
        this.query = String.format("&filter[name]=%s", query);
        getProducts(0, LIMIT);
    }

    private String combineFiltersAndQuery(final String filters, final String query) {
        if (Util.nonEmpty(filters) && Util.nonEmpty(query)) {
            return String.format("%s%s", filters, query);
        } else if (Util.nonEmpty(filters)) {
            return filters;
        } else if (Util.nonEmpty(query)) {
            return query;
        }

        return "";
    }

    @Override
    public void onFinishFilterDialog(final Integer priceFilterId, final Integer categoryFilterId) {
        this.priceFilterId = priceFilterId;
        this.categoryFilterId = categoryFilterId;

        this.filters = SearchUtil.formatQuery(priceFilterId, categoryFilterId);
        getProducts(0, LIMIT);
    }
}
