package rs.cod3rs.shopifine.view;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import rs.cod3rs.shopifine.R;
import rs.cod3rs.shopifine.domain.Order;

@EViewGroup(R.layout.item_order)
public class OrderItemView extends LinearLayout {

    @ViewById(R.id.singleOrderId)
    TextView orderId;

    @ViewById(R.id.singleOrderDate)
    TextView orderDate;

    @ViewById(R.id.singleOrderItemsCount)
    TextView orderItemsCount;

    public OrderItemView(final Context context) {
        super(context);
    }

    public void bind(final Order order) {
        orderId.setText(String.format("No. #%s", order.id.toString()));
        orderDate.setText("12-12-2012");
        orderItemsCount.setText(String.format("%s", order.totalItems));
    }
}
