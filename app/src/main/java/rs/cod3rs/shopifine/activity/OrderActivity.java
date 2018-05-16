package rs.cod3rs.shopifine.activity;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import rs.cod3rs.shopifine.R;
import rs.cod3rs.shopifine.adapter.OrderClauseAdapter;
import rs.cod3rs.shopifine.domain.OrderItem;
import rs.cod3rs.shopifine.domain.Product;
import rs.cod3rs.shopifine.fragment.ProductsFragment;

@EActivity(R.layout.activity_order)
public class OrderActivity extends AppCompatActivity {

    @ViewById
    TextView orderId;

    @ViewById
    TextView orderStatus;

    @ViewById
    TextView orderCreated;

    @ViewById
    TextView orderPointsGained;

    @ViewById
    TextView orderPointsSpent;

    @ViewById
    TextView discountValue;

    @ViewById
    TextView totalValue;

    @ViewById(R.id.orderItems)
    ListView orderItemsView;

    @Bean
    OrderClauseAdapter adapter;

    @AfterViews
    void bindAdapter() {
        orderItemsView.setAdapter(adapter);
    }

    @AfterViews
    void getData() {
        getItems();
    }

    @AfterViews
    void showValues() {
        orderId.setText(String.format(Locale.US, "#%s", "10123A2"));
        orderStatus.setText("Status");
        orderCreated.setText("20-07-2018");
        orderPointsGained.setText("20");
        orderPointsSpent.setText("15");
        totalValue.setText("14%");
        discountValue.setText("14584.23");
    }

    @Background
    void getItems() {
        final List<OrderItem> p = new ArrayList<>();
        p.add(new OrderItem(1, 1, 1, 12.00, 244.0, 10.1, 124.00));
        p.add(new OrderItem(2, 2, 1, 12.00, 244.0, 10.1, 124.00));
        p.add(new OrderItem(3, 3, 1, 12.00, 244.0, 10.1, 124.00));
        p.add(new OrderItem(4, 4, 1, 12.00, 244.0, 10.1, 124.00));
        p.add(new OrderItem(5, 5, 1, 12.00, 244.0, 10.1, 124.00));
        p.add(new OrderItem(6, 6, 1, 12.00, 244.0, 10.1, 124.00));
        updateList(p);
    }

    @UiThread
    void updateList(final List<OrderItem> updated) {
        adapter.items = updated;
        orderItemsView.setAdapter(adapter);
    }


}