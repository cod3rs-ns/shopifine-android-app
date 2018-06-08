package rs.cod3rs.shopifine.fragment;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.rest.spring.annotations.RestService;

import java.sql.SQLException;
import java.util.List;

import rs.cod3rs.shopifine.R;
import rs.cod3rs.shopifine.Util;
import rs.cod3rs.shopifine.adapter.ShoppingCartAdapter;
import rs.cod3rs.shopifine.db.DatabaseHelper;
import rs.cod3rs.shopifine.db.ShoppingCartItem;
import rs.cod3rs.shopifine.http.Products;

@EFragment(R.layout.fragment_shopping_cart)
public class ShoppingCartFragment extends Fragment {

    @RestService
    Products products;

    @ViewById(R.id.shopping_cart_items_container)
    RelativeLayout container;

    @ViewById
    TextView shoppingCartNoItems;

    @ViewById
    TextView cartTotalAmount;

    @ViewById(R.id.shoppingCartItemsRecyclerList)
    RecyclerView shoppingCartItemsView;

    @Bean
    ShoppingCartAdapter adapter;

    private DatabaseHelper helper;

    @AfterInject
    void setupDatabase() {
        this.helper = new DatabaseHelper(getContext());
    }

    @AfterViews
    void bindAdapter() {
        shoppingCartItemsView.setAdapter(adapter);
        shoppingCartItemsView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @AfterViews
    void populateShoppingCartItems() {
        try {
            final List<ShoppingCartItem> items = helper.getShoppingCartDAO().queryForAll();
            updateList(items);
        } catch (final SQLException e) {
            Log.e(getClass().getSimpleName(), e.getMessage());
        }
    }

    @Click
    void checkout() {
        Log.i(getClass().getSimpleName(), "Clicked on checkout");
    }

    @UiThread
    void updateList(final List<ShoppingCartItem> items) {
        if (items.isEmpty()) {
            container.setVisibility(View.INVISIBLE);
            shoppingCartNoItems.setVisibility(View.VISIBLE);
        } else {
            container.setVisibility(View.VISIBLE);
            shoppingCartNoItems.setVisibility(View.INVISIBLE);

            adapter.addAll(items);
            shoppingCartItemsView.setAdapter(adapter);

            final double amount = items.stream().mapToDouble(i -> i.quantity * i.product.price).sum();
            cartTotalAmount.setText(Util.formatPrice(amount));
        }
    }
}
