package rs.cod3rs.shopifine.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import rs.cod3rs.shopifine.R;
import rs.cod3rs.shopifine.activity.OrderActivity_;
import rs.cod3rs.shopifine.adapter.OrdersListAdapter;
import rs.cod3rs.shopifine.domain.Order;

@EFragment(R.layout.fragment_orders_tab)
public class OrdersFragmentTab extends Fragment {

    @ViewById(R.id.ordersRecyclerList)
    RecyclerView ordersList;

    @Bean
    OrdersListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_orders_tab, container, false);
    }

    @AfterViews
    void bindAdapter() {
        ordersList.setAdapter(adapter);
        ordersList.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter.setOnItemClickListener((position, view, data) -> OrderActivity_.intent(getContext()).start().withAnimation(0, 0));
    }

    @AfterViews
    void getData() {
        getOrders();
    }

    @Background
    void getOrders() {
        final List<Order> orders = new ArrayList<>();
        orders.add(new Order(1123, new Date(), 12));
        orders.add(new Order(21233, new Date(), 1232));
        orders.add(new Order(3123, new Date(), 142));
        orders.add(new Order(43234, new Date(), 1));
        updateList(orders);
    }

    @UiThread
    void updateList(final List<Order> orders) {
        adapter.addAll(orders);
        ordersList.setAdapter(adapter);
    }

}
