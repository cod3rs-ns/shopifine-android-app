package rs.cod3rs.shopifine.view;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.cachapa.expandablelayout.ExpandableLayout;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import java.text.DecimalFormat;
import java.util.Locale;

import rs.cod3rs.shopifine.R;
import rs.cod3rs.shopifine.domain.OrderClause;
import rs.cod3rs.shopifine.generics.ViewWrapper;

@EViewGroup(R.layout.item_order_clause)
public class OrderClauseView extends LinearLayout implements ViewWrapper.Binder<OrderClause> {

    @ViewById public ExpandableLayout expandableLayout;

    @ViewById TextView productName;

    @ViewById TextView amountDetails;

    @ViewById TextView discountDetails;

    @ViewById TextView orderClauseTotal;

    public OrderClauseView(final Context context) {
        super(context);
    }

    public void bind(final OrderClause item) {
        productName.setText(String.format("%s. %s", item.ordinal.toString(), item.product.name));
        amountDetails.setText(String.format(Locale.US, "%d x %s", item.quantity, item.price));
        discountDetails.setText(
                String.format(
                        Locale.US,
                        "%s %% in total of %s",
                        item.discount,
                        new DecimalFormat("#.##").format(item.discountAmount)));
        orderClauseTotal.setText(String.format(Locale.getDefault(), "$ %.2f%n", item.amount));
    }
}
