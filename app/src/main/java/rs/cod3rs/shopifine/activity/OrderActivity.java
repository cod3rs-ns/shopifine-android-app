package rs.cod3rs.shopifine.activity;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.auth0.android.jwt.JWT;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.androidannotations.rest.spring.annotations.RestService;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import rs.cod3rs.shopifine.Credentials_;
import rs.cod3rs.shopifine.R;
import rs.cod3rs.shopifine.adapter.OrderClausesAdapter;
import rs.cod3rs.shopifine.domain.Order;
import rs.cod3rs.shopifine.domain.OrderClause;
import rs.cod3rs.shopifine.hateoas.bill_clauses.BillClauseResponseData;
import rs.cod3rs.shopifine.http.Orders;
import rs.cod3rs.shopifine.http.Products;

@EActivity(R.layout.activity_order)
public class OrderActivity extends AppCompatActivity {

    @RestService Orders orders;

    @RestService Products products;

    @Pref Credentials_ credentials;

    @Extra Order order;

    @ViewById TextView orderId;

    @ViewById TextView orderStatus;

    @ViewById TextView orderCreated;

    @ViewById TextView orderPointsGained;

    @ViewById TextView orderPointsSpent;

    @ViewById TextView discountValue;

    @ViewById TextView totalValue;

    @ViewById(R.id.orderClausesRecyclerView)
    RecyclerView orderItemsView;

    @Bean OrderClausesAdapter adapter;

    private Integer user;

    @AfterViews
    void bindAdapter() {
        orderItemsView.setAdapter(adapter);
        orderItemsView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setOnItemClickListener(
                (position, view, data) -> {
                    view.expandableLayout.setExpanded(!view.expandableLayout.isExpanded());
                    Toast.makeText(
                                    this,
                                    String.format("Clicked on: %s", data.id.toString()),
                                    Toast.LENGTH_SHORT)
                            .show();
                });
    }

    @AfterViews
    void getData() {
        getItems();
    }

    @AfterViews
    void showValues() {
        orderId.setText(String.format(Locale.US, "#%s", order.id));
        orderStatus.setText(order.state.name());
        orderCreated.setText(
                DateUtils.getRelativeDateTimeString(
                        this,
                        order.createdAt.toDate().getTime(),
                        DateUtils.MINUTE_IN_MILLIS,
                        DateUtils.WEEK_IN_MILLIS,
                        0));
        orderPointsGained.setText(String.valueOf(order.pointsGained));
        orderPointsSpent.setText(String.valueOf(order.pointsSpent));
        totalValue.setText(String.format(Locale.getDefault(), "$ %.2f%n", order.amount));
        discountValue.setText(String.format("%s %%", order.discount));
    }

    @Background
    void getItems() {
        final List<OrderClause> orderClauses =
                orders.getBillClauses(user, order.id)
                        .getData()
                        .stream()
                        .map(BillClauseResponseData::toDomain)
                        .peek(
                                a -> {
                                    System.out.println(a.linkedProductId);
                                    a.product =
                                            products.retrieveOne(a.linkedProductId)
                                                    .getData()
                                                    .toDomain();
                                })
                        .collect(Collectors.toList());
        updateList(orderClauses);
    }

    @AfterInject
    void extractUserIdFromToken() {
        final JWT jwt = new JWT(credentials.token().get());
        user = jwt.getClaim("id").asInt();
    }

    @UiThread
    void updateList(final List<OrderClause> updated) {
        adapter.addAll(updated);
        orderItemsView.setAdapter(adapter);
    }
}
