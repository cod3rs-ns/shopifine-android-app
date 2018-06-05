package rs.cod3rs.shopifine.adapter;

import android.content.Context;
import android.view.ViewGroup;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import rs.cod3rs.shopifine.domain.Product;
import rs.cod3rs.shopifine.generics.RecyclerViewAdapterBase;
import rs.cod3rs.shopifine.view.ProductItemView;
import rs.cod3rs.shopifine.view.ProductItemView_;

@EBean
public class ProductsAdapter extends RecyclerViewAdapterBase<Product, ProductItemView> {

    @RootContext
    Context context;

    @Override
    protected ProductItemView onCreateItemView(final ViewGroup parent, final int viewType) {
        return ProductItemView_.build(context);
    }
}
