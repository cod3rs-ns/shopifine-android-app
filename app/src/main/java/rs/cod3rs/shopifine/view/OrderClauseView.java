package rs.cod3rs.shopifine.view;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.widget.TextView;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import java.text.DecimalFormat;
import java.util.Locale;

import rs.cod3rs.shopifine.R;
import rs.cod3rs.shopifine.domain.OrderItem;

@EViewGroup(R.layout.item_order_clause)
public class OrderClauseView extends ConstraintLayout {

    @ViewById(R.id.productName)
    TextView name;

    @ViewById
    TextView amountDetails;

    @ViewById
    TextView discountDetails;

    public OrderClauseView(final Context context) {
        super(context);
    }

    public void bind(final OrderItem item) {
        name.setText(item.ordinal.toString() + ". Product Name");
        amountDetails.setText(String.format(Locale.US, "%d x %s", item.quantity, item.price));
        discountDetails.setText(String.format(Locale.US, "%s%% in total of %s", item.discount, new DecimalFormat("#.##").format(item.discountAmount)));
        System.out.println("Fucking bind.");
    }
}