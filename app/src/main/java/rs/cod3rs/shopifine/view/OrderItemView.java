package rs.cod3rs.shopifine.view;

import android.content.Context;
import android.text.format.DateUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import rs.cod3rs.shopifine.R;
import rs.cod3rs.shopifine.domain.Order;
import rs.cod3rs.shopifine.generics.ViewWrapper;

@EViewGroup(R.layout.item_order)
public class OrderItemView extends LinearLayout implements ViewWrapper.Binder<Order> {

    @ViewById(R.id.singleOrderId)
    TextView orderId;

    @ViewById(R.id.singleOrderDate)
    TextView orderDate;

    @ViewById(R.id.singleOrderTotal)
    TextView orderItemsCount;

    public OrderItemView(final Context context) {
        super(context);
    }

    public void bind(final Order order) {
        orderId.setText(String.format("No. #%s", order.id.toString()));
        orderDate.setText(
                DateUtils.getRelativeDateTimeString(
                        getContext(),
                        order.createdAt.toDate().getTime(),
                        DateUtils.MINUTE_IN_MILLIS,
                        DateUtils.WEEK_IN_MILLIS,
                        0));
        orderItemsCount.setText(String.format("$%s", order.amount));
    }
}
