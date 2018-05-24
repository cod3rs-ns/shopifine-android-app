package rs.cod3rs.shopifine.activity;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import rs.cod3rs.shopifine.R;
import rs.cod3rs.shopifine.adapter.OrderClausesAdapter;
import rs.cod3rs.shopifine.domain.OrderClause;

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

    @ViewById(R.id.orderClausesRecyclerView)
    RecyclerView orderItemsView;

    @Bean
    OrderClausesAdapter adapter;

    @AfterViews
    void bindAdapter() {
        orderItemsView.setAdapter(adapter);
        orderItemsView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setOnItemClickListener((position, view, data) -> Toast.makeText(this, String.format("Clicked on: %s", data.id.toString()), Toast.LENGTH_SHORT).show());
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
        final List<OrderClause> p = new ArrayList<>();
        p.add(new OrderClause(1, 1, 1, 12.00, 244.0, 10.1, 124.00));
        p.add(new OrderClause(2, 2, 1, 12.00, 244.0, 10.1, 124.00));
        p.add(new OrderClause(3, 3, 1, 12.00, 244.0, 10.1, 124.00));
        p.add(new OrderClause(4, 4, 1, 12.00, 244.0, 10.1, 124.00));
        p.add(new OrderClause(5, 5, 1, 12.00, 244.0, 10.1, 124.00));
        p.add(new OrderClause(6, 6, 1, 12.00, 244.0, 10.1, 124.00));
        updateList(p);
    }

    @UiThread
    void updateList(final List<OrderClause> updated) {
        adapter.addAll(updated);
        orderItemsView.setAdapter(adapter);
    }

}
