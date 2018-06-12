package rs.cod3rs.shopifine.fragment;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.j256.ormlite.dao.Dao;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.androidannotations.rest.spring.annotations.RestService;
import org.springframework.util.StringUtils;

import java.sql.SQLException;
import java.util.List;

import rs.cod3rs.shopifine.Prefs_;
import rs.cod3rs.shopifine.R;
import rs.cod3rs.shopifine.Util;
import rs.cod3rs.shopifine.activity.MainActivity;
import rs.cod3rs.shopifine.adapter.ShoppingCartAdapter;
import rs.cod3rs.shopifine.db.DatabaseHelper;
import rs.cod3rs.shopifine.db.ShoppingCartItem;
import rs.cod3rs.shopifine.hateoas.bill_items.BillItemRequest;
import rs.cod3rs.shopifine.hateoas.bills.BillRequest;
import rs.cod3rs.shopifine.http.Orders;
import rs.cod3rs.shopifine.http.Products;

@EFragment(R.layout.fragment_shopping_cart)
public class ShoppingCartFragment extends Fragment {

    @Pref
    Prefs_ prefs;

    @RestService
    Products products;

    @RestService
    Orders orders;

    @ViewById(R.id.shopping_cart_items_container)
    RelativeLayout container;

    @ViewById
    TextView shoppingCartNoItems;

    @ViewById
    EditText pointsToSpend;

    @ViewById
    TextView cartTotalAmount;

    @ViewById(R.id.shoppingCartItemsRecyclerList)
    RecyclerView shoppingCartItemsView;

    @ViewById
    LinearLayout orderProcessingContainer;

    @Bean
    ShoppingCartAdapter adapter;

    private List<ShoppingCartItem> items;

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
            this.items = helper.getShoppingCartDAO().queryForAll();
            updateList(items);
        } catch (final SQLException e) {
            Log.e(getClass().getSimpleName(), e.getMessage());
        }
    }

    @Click
    void checkout() {
        final Integer userId = prefs.loggedUserId().get();

        final Long points;
        final String pointsText = pointsToSpend.getText().toString();
        if (StringUtils.hasText(pointsText)) {
            points = Long.valueOf(pointsText);
        } else {
            points = 0L;
        }

        final BillRequest request = BillRequest.order(userId, (long) items.size(), points);
        createOrder(userId, request);
    }

    @Background
    void createOrder(final Integer userId, final BillRequest request) {
        showProgressBar();

        final Long billId = orders.createBill(userId, request).getData().getId();

        for (int i = 0; i < items.size(); ++i) {
            orders.addBillItem(userId, billId.intValue(), new BillItemRequest(billId, i, items.get(i)));
        }

        emptyShoppingCart();
        showMessage();
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

            cartTotalAmount.setText(Util.formatPrice(adapter.amount()));
        }
    }

    private void emptyShoppingCart() {
        items.clear();
        try {
            final Dao<ShoppingCartItem, Integer> dao = helper.getShoppingCartDAO();
            dao.delete(dao.queryForAll());
        } catch (final SQLException e) {
            Log.e(getClass().getSimpleName(), e.getMessage());
        }
    }

    @UiThread
    void showProgressBar() {
        container.setVisibility(View.INVISIBLE);
        shoppingCartNoItems.setVisibility(View.INVISIBLE);
        orderProcessingContainer.setVisibility(View.VISIBLE);
    }

    @UiThread
    void showMessage() {
        Toast.makeText(getContext(), R.string.order_successfully_created, Toast.LENGTH_SHORT).show();
        final Fragment fragment = MainActivity.getFragmentById(R.id.orders);

        getFragmentManager().beginTransaction()
                .replace(R.id.frame, fragment)
                .addToBackStack(String.valueOf(4))
                .commit();
    }
}
