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
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.androidannotations.rest.spring.annotations.RestService;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import rs.cod3rs.shopifine.Prefs_;
import rs.cod3rs.shopifine.R;
import rs.cod3rs.shopifine.activity.ProductActivity_;
import rs.cod3rs.shopifine.adapter.WishlistItemsAdapter;
import rs.cod3rs.shopifine.domain.WishlistItem;
import rs.cod3rs.shopifine.hateoas.wishlist.WishlistItemResponseData;
import rs.cod3rs.shopifine.http.ProductCategories;
import rs.cod3rs.shopifine.http.Products;
import rs.cod3rs.shopifine.http.Wishlists;

@EFragment(R.layout.fragment_wishlist)
public class WishlistFragment extends Fragment {

    @Bean
    public WishlistItemsAdapter adapter;

    @RestService
    Wishlists wishlists;

    @RestService
    Products products;

    @RestService
    ProductCategories productCategories;

    @ViewById(R.id.wishlistRecyclerView)
    RecyclerView wishlistView;

    @Pref
    Prefs_ prefs;

    private LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());

    @AfterViews
    void bindAdapter() {
        wishlistView.setAdapter(adapter);
        adapter.setOnItemClickListener(
                (position, view, data) ->
                        ProductActivity_.intent(getContext()).product(data.product).start());
        wishlistView.setLayoutManager(layoutManager);
    }

    @AfterViews
    void getData() {
        getWishlist();
    }

    @Background
    void getWishlist() {
        final List<WishlistItem> wishlist =
                wishlists
                        .getWishlist(prefs.loggedUserId().get())
                        .getData()
                        .stream()
                        .map(WishlistItemResponseData::toDomain)
                        .peek(this::retrieveProduct)
                        .peek(this::retrieveProductCategory)
                        .collect(Collectors.toList());
        updateList(wishlist);
    }

    @UiThread
    void updateList(final List<WishlistItem> wishlist) {
        adapter.addAll(wishlist);
        if (Objects.nonNull(wishlistView)) {
            wishlistView.setAdapter(adapter);
        }
    }

    private void retrieveProduct(WishlistItem wishlistItem) {
        wishlistItem.product = products.retrieveOne(prefs.loggedUserId().get(), wishlistItem.productId).getData().toDomain();
    }

    private void retrieveProductCategory(WishlistItem wi) {
        wi.product.category = productCategories.
                getProductCategory(wi.product.categoryId)
                .getData()
                .toDomain();
    }
}
