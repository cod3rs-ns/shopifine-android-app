package rs.cod3rs.shopifine.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.auth0.android.jwt.JWT;

import org.androidannotations.annotations.AfterInject;
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

import java.util.List;
import java.util.stream.Collectors;

import rs.cod3rs.shopifine.Credentials_;
import rs.cod3rs.shopifine.R;
import rs.cod3rs.shopifine.activity.OrderActivity_;
import rs.cod3rs.shopifine.adapter.OrdersListAdapter;
import rs.cod3rs.shopifine.domain.Order;
import rs.cod3rs.shopifine.domain.OrderState;
import rs.cod3rs.shopifine.hateoas.discounts.DiscountResponseData;
import rs.cod3rs.shopifine.hateoas.bills.BillResponseData;
import rs.cod3rs.shopifine.http.Orders;

@EFragment(R.layout.fragment_orders_tab)
public class OrdersFragmentTab extends Fragment {

    @FragmentArg("orderFragmentType")
    OrderState orderFragmentType;

    @RestService Orders orders;

    @Pref Credentials_ credentials;

    @ViewById(R.id.ordersRecyclerList)
    RecyclerView ordersList;

    @Bean OrdersListAdapter adapter;

    private Integer user;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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

    @AfterInject
    void extractUserIdFromToken() {
        final JWT jwt = new JWT(credentials.token().get());
        user = jwt.getClaim("id").asInt();
    }

    @AfterViews
    void getData() {
        getOrders();
    }

    @Background
    void getOrders() {
        try {
            final List<Order> ordersList =
                    orders.getBills(user, orderFragmentType.name())
                            .getData()
                            .stream()
                            .map(BillResponseData::toDomain)
                            .peek(
                                    order ->
                                            order.discounts =
                                                    orders.getBillDiscounts(user, order.id)
                                                            .getData()
                                                            .stream()
                                                            .map(DiscountResponseData::toDomain)
                                                            .collect(Collectors.toList()))
                            .collect(Collectors.toList());
            updateList(ordersList);
        } catch (final NestedRuntimeException e) {
            Log.e(this.getClass().getSimpleName(), e.getMessage());
        }
    }

    @UiThread
    void updateList(final List<Order> orders) {
        adapter.addAll(orders);
        ordersList.setAdapter(adapter);
    }
}
