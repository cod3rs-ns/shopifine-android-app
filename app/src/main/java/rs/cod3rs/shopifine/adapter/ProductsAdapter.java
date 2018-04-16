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
import rs.cod3rs.shopifine.view.ProductItemView;
import rs.cod3rs.shopifine.view.ProductItemView_;

@EBean
public class ProductsAdapter extends BaseAdapter {

    List<Product> products = new ArrayList<Product>() {{
            new Product(1L, "test-1", "test-2", 1L, 23.2);
            new Product(2L, "test-2", "test-1", 1L, 999.99);
    }};

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
        ProductItemView productItemView;
        if (view == null) {
            productItemView = ProductItemView_.build(context);
        } else {
            productItemView = (ProductItemView) view;
        }

        productItemView.bind((Product) getItem(i));

        return productItemView;
    }
}
