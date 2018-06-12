package rs.cod3rs.shopifine.view;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.text.format.DateUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import java.util.Locale;

import rs.cod3rs.shopifine.R;
import rs.cod3rs.shopifine.activity.OrderMapActivity_;
import rs.cod3rs.shopifine.domain.Order;
import rs.cod3rs.shopifine.domain.OrderState;
import rs.cod3rs.shopifine.generics.ViewWrapper;

@EViewGroup(R.layout.item_order)
public class OrderItemView extends LinearLayout implements ViewWrapper.Binder<Order> {

    @ViewById(R.id.singleOrderId)
    TextView orderId;

    @ViewById(R.id.singleOrderDate)
    TextView orderDate;

    @ViewById(R.id.singleOrderTotal)
    TextView orderItemsCount;

    @ViewById
    FloatingActionButton showOnMapButton;

    private Order order;

    public OrderItemView(final Context context) {
        super(context);
    }

    @Click
    public void showOnMapButton() {
        OrderMapActivity_.intent(getContext())
                .order(order)
                .start();
    }

    public void bind(final Order order) {
        this.order = order;
        if(order.state == OrderState.DISPATCHED) {
            showOnMapButton.setVisibility(VISIBLE);
        }
        orderId.setText(String.format("No. #%s", order.id.toString()));
        orderDate.setText(
                DateUtils.getRelativeDateTimeString(
                        getContext(),
                        order.createdAt.toDate().getTime(),
                        DateUtils.MINUTE_IN_MILLIS,
                        DateUtils.WEEK_IN_MILLIS,
                        0));
        orderItemsCount.setText((String.format(Locale.getDefault(), "$ %.2f%n", order.amount)));
    }
}
