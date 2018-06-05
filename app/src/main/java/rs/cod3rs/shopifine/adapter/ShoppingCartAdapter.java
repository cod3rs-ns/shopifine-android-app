package rs.cod3rs.shopifine.adapter;

import android.content.Context;
import android.view.ViewGroup;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import rs.cod3rs.shopifine.db.ShoppingCartItem;
import rs.cod3rs.shopifine.generics.RecyclerViewAdapterBase;
import rs.cod3rs.shopifine.view.ShoppingCartItemView;
import rs.cod3rs.shopifine.view.ShoppingCartItemView_;

@EBean
public class ShoppingCartAdapter extends RecyclerViewAdapterBase<ShoppingCartItem, ShoppingCartItemView> {

    @RootContext
    Context context;

    @Override
    protected ShoppingCartItemView onCreateItemView(final ViewGroup parent, final int viewType) {
        return ShoppingCartItemView_.build(context);
    }
}
