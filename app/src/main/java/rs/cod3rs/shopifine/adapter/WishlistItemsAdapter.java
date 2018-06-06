package rs.cod3rs.shopifine.adapter;

import android.content.Context;
import android.view.ViewGroup;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import rs.cod3rs.shopifine.domain.WishlistItem;
import rs.cod3rs.shopifine.generics.RecyclerViewAdapterBase;
import rs.cod3rs.shopifine.view.WishlistItemView;
import rs.cod3rs.shopifine.view.WishlistItemView_;

@EBean
public class WishlistItemsAdapter extends RecyclerViewAdapterBase<WishlistItem, WishlistItemView> {

    @RootContext Context context;

    @Override
    protected WishlistItemView onCreateItemView(final ViewGroup parent, final int viewType) {
        return WishlistItemView_.build(context);
    }
}
