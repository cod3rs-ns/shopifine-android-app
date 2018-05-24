package rs.cod3rs.shopifine.generics;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class RecyclerViewAdapterBase<D, V extends View & ViewWrapper.Binder<D>> extends RecyclerView.Adapter<ViewWrapper<D, V>> {

    private List<D> items = new ArrayList<>();

    private OnItemClickListener<D, V> listener;

    public void setOnItemClickListener(OnItemClickListener<D, V> listener) {
        this.listener = listener;
    }

    @Override
    public final ViewWrapper<D, V> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewWrapper<>(onCreateItemView(parent, viewType));
    }

    protected abstract V onCreateItemView(ViewGroup parent, int viewType);

    @Override
    public final void onBindViewHolder(ViewWrapper<D, V> viewHolder, final int position) {
        final V view = viewHolder.getView();
        final D data = items.get(position);
        view.bind(data);
        view.setOnClickListener(v -> {
            Log.i(this.getClass().getSimpleName(), String.format("Clicked on item %s, with data: %s", position, data));
            onItemClick(position, view, data);
        });
    }

    public void onItemClick(int position, V view, D data) {
        if (listener != null) {
            listener.onItemClick(position, view, data);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void add(D item) {
        items.add(item);
        notifyDataSetChanged();
    }

    public void addAll(Collection<D> collection) {
        items.addAll(collection);
        notifyDataSetChanged();
    }

    public void clear() {
        items.clear();
        notifyDataSetChanged();
    }

    public interface OnItemClickListener<D, V> {
        void onItemClick(int position, V view, D data);
    }
}