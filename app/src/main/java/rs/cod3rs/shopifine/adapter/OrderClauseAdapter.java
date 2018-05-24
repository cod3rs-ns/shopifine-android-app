package rs.cod3rs.shopifine.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.ArrayList;
import java.util.List;

import rs.cod3rs.shopifine.domain.OrderItem;
import rs.cod3rs.shopifine.view.OrderClauseView;
import rs.cod3rs.shopifine.view.OrderClauseView_;

@EBean
public class OrderClauseAdapter extends BaseAdapter {

    public List<OrderItem> items = new ArrayList<>();

    @RootContext
    Context context;

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return items.get(i).id;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        OrderClauseView orderClauseView;
        if (view == null) {
            orderClauseView = OrderClauseView_.build(context);
        } else {
            orderClauseView = (OrderClauseView) view;
        }

        orderClauseView.bind((OrderItem) getItem(i));

        return orderClauseView;
    }
}
