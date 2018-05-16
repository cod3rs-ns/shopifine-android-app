package rs.cod3rs.shopifine.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ItemClick;
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

    @ViewById(R.id.ordersList)
    ListView ordersList;

    @Bean
    OrdersListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_orders_tab, container, false);
    }

    @AfterViews
    void bindAdapter() {
        ordersList.setAdapter(adapter);
    }

    @AfterViews
    void getData() {
        getOrders();
    }

    @ItemClick
    void ordersListItemClicked(int position) {
        Log.i(OrdersFragmentTab.class.getSimpleName(), String.format("Clicked on order %s", position));
        Intent intent = new Intent(getActivity(), OrderActivity_.class);
        startActivity(intent);
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
        adapter.orders = orders;
        ordersList.setAdapter(adapter);
    }

}