package rs.cod3rs.shopifine.adapter;

import android.content.Context;
import android.view.ViewGroup;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import rs.cod3rs.shopifine.domain.OrderClause;
import rs.cod3rs.shopifine.generics.RecyclerViewAdapterBase;
import rs.cod3rs.shopifine.view.OrderClauseView;
import rs.cod3rs.shopifine.view.OrderClauseView_;

@EBean
public class OrderClausesAdapter extends RecyclerViewAdapterBase<OrderClause, OrderClauseView> {

    @RootContext
    Context context;

    @Override
    protected OrderClauseView onCreateItemView(final ViewGroup parent, final int viewType) {
        return OrderClauseView_.build(context);
    }
}
