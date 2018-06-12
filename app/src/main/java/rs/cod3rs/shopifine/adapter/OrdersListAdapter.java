package rs.cod3rs.shopifine.adapter;

import android.content.Context;
import android.view.ViewGroup;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import rs.cod3rs.shopifine.domain.Order;
import rs.cod3rs.shopifine.generics.RecyclerViewAdapterBase;
import rs.cod3rs.shopifine.view.OrderItemView;
import rs.cod3rs.shopifine.view.OrderItemView_;

@EBean
public class OrdersListAdapter extends RecyclerViewAdapterBase<Order, OrderItemView> {

    @RootContext
    Context context;

    @Override
    protected OrderItemView onCreateItemView(final ViewGroup parent, final int viewType) {
        return OrderItemView_.build(context);
    }
}
