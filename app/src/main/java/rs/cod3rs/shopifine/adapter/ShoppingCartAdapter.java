package rs.cod3rs.shopifine.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.ArrayList;
import java.util.List;

import rs.cod3rs.shopifine.domain.Product;
import rs.cod3rs.shopifine.view.ShoppingCartItemView;
import rs.cod3rs.shopifine.view.ShoppingCartItemView_;

@EBean
public class ShoppingCartAdapter extends BaseAdapter {

    public List<Product> products = new ArrayList<>();

    @RootContext
    Context context;

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Object getItem(int i) {
        return products.get(i);
    }

    @Override
    public long getItemId(int i) {
        return products.get(i).id;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final ShoppingCartItemView shoppingCartItemView;
        if (view == null) {
            shoppingCartItemView = ShoppingCartItemView_.build(context);
        } else {
            shoppingCartItemView = (ShoppingCartItemView) view;
        }

        shoppingCartItemView.bind((Product) getItem(i));

        return shoppingCartItemView;
    }
}
