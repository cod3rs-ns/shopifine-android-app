package rs.cod3rs.shopifine.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.ArrayList;
import java.util.List;

import rs.cod3rs.shopifine.domain.Order;
import rs.cod3rs.shopifine.view.OrderItemView;
import rs.cod3rs.shopifine.view.OrderItemView_;

@EBean
public class OrdersListAdapter extends BaseAdapter {

    public List<Order> orders = new ArrayList<>();

    @RootContext
    Context context;

    @Override
    public int getCount() {
        return orders.size();
    }

    @Override
    public Object getItem(int i) {
        return orders.get(i);
    }

    @Override
    public long getItemId(int i) {
        return orders.get(i).id;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        OrderItemView productItemView;
        if (view == null) {
            productItemView = OrderItemView_.build(context);
        } else {
            productItemView = (OrderItemView) view;
        }

        productItemView.bind((Order) getItem(i));

        return productItemView;
    }
}