package rs.cod3rs.shopifine.fragment;

import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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
import rs.cod3rs.shopifine.activity.ProductActivity_;
import rs.cod3rs.shopifine.adapter.WishlistItemsAdapter;
import rs.cod3rs.shopifine.domain.WishlistItem;
import rs.cod3rs.shopifine.http.Wishlists;

@EFragment(R.layout.fragment_wishlist)
public class WishlistFragment extends Fragment {

    @RestService Wishlists wishlists;

    @ViewById(R.id.wishlistRecyclerView)
    RecyclerView wishlistView;

    @Bean WishlistItemsAdapter adapter;

    private LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());

    @AfterViews
    void bindAdapter() {
        wishlistView.setAdapter(adapter);
        adapter.setOnItemClickListener(
                (position, view, data) ->
                        ProductActivity_.intent(getContext()).product(data.getProduct()).start());
        wishlistView.setLayoutManager(layoutManager);
    }

    @AfterViews
    void getData() {
        getWishlist();
    }

    @Background
    void getWishlist() {
        // TODO call API
        final List<WishlistItem> wishlist = new ArrayList<>();
        updateList(wishlist);
    }

    @UiThread
    void updateList(final List<WishlistItem> wishlist) {
        adapter.addAll(wishlist);
        wishlistView.setAdapter(adapter);
    }
}
