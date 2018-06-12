package rs.cod3rs.shopifine.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.androidannotations.rest.spring.annotations.RestService;
import org.springframework.core.NestedRuntimeException;
import org.w3c.dom.Text;

import java.util.List;
import java.util.stream.Collectors;

import rs.cod3rs.shopifine.Prefs_;
import rs.cod3rs.shopifine.R;
import rs.cod3rs.shopifine.activity.OrderActivity_;
import rs.cod3rs.shopifine.adapter.OrdersListAdapter;
import rs.cod3rs.shopifine.domain.Order;
import rs.cod3rs.shopifine.domain.OrderState;
import rs.cod3rs.shopifine.hateoas.bills.BillResponseData;
import rs.cod3rs.shopifine.hateoas.discounts.DiscountResponseData;
import rs.cod3rs.shopifine.http.Orders;

@EFragment(R.layout.fragment_orders_tab)
public class OrdersFragmentTab extends Fragment {

    @Pref
    Prefs_ prefs;

    @FragmentArg("orderFragmentType")
    OrderState orderFragmentType;

    @RestService
    Orders orders;

    @ViewById(R.id.ordersRecyclerList)
    RecyclerView ordersList;

    @ViewById
    TextView noOrders;

    @Bean
    OrdersListAdapter adapter;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_orders_tab, container, false);
    }

    @AfterViews
    void bindAdapter() {
        ordersList.setAdapter(adapter);
        ordersList.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter.setOnItemClickListener(
                (position, view, data) ->
                        OrderActivity_.intent(getContext())
                                .order(data)
                                .start()
                                .withAnimation(0, 0));
    }

    @AfterViews
    void getData() {
        getOrders();
    }

    @Background
    void getOrders() {
        final Integer userId = prefs.loggedUserId().get();

        try {
            final List<Order> ordersList =
                    orders.getBills(userId, orderFragmentType.name())
                            .getData()
                            .stream()
                            .map(BillResponseData::toDomain)
                            .peek(
                                    order ->
                                            order.discounts =
                                                    orders.getBillDiscounts(userId, order.id)
                                                            .getData()
                                                            .stream()
                                                            .map(DiscountResponseData::toDomain)
                                                            .collect(Collectors.toList()))
                            .collect(Collectors.toList());
            updateList(ordersList);
        } catch (final NestedRuntimeException e) {
            Log.e(getClass().getSimpleName(), e.getMessage());
        }
    }

    @UiThread
    void updateList(final List<Order> orders) {
        if (orders.isEmpty()) {
            ordersList.setVisibility(View.INVISIBLE);
            noOrders.setVisibility(View.VISIBLE);
        } else {
            ordersList.setVisibility(View.VISIBLE);
            noOrders.setVisibility(View.INVISIBLE);

            adapter.addAll(orders);
            ordersList.setAdapter(adapter);
        }
    }
}
