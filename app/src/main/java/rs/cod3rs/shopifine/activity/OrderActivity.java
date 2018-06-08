package rs.cod3rs.shopifine.activity;

import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.auth0.android.jwt.JWT;

import net.cachapa.expandablelayout.ExpandableLayout;

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
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import rs.cod3rs.shopifine.Credentials_;
import rs.cod3rs.shopifine.R;
import rs.cod3rs.shopifine.adapter.OrderClausesAdapter;
import rs.cod3rs.shopifine.domain.Order;
import rs.cod3rs.shopifine.domain.OrderClause;
import rs.cod3rs.shopifine.hateoas.discounts.DiscountResponseData;
import rs.cod3rs.shopifine.hateoas.bill_items.BillItemResponseData;
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

    @ViewById RecyclerView orderClausesRecyclerView;

    @ViewById ExpandableLayout orderDiscountsExpander;

    @ViewById LinearLayout orderDiscountsHolder;

    @ViewById LinearLayout orderSummary;

    @Bean OrderClausesAdapter adapter;

    private Integer user;

    @AfterViews
    void setListener() {
        orderSummary.setOnClickListener(
                view -> orderDiscountsExpander.setExpanded(!orderDiscountsExpander.isExpanded()));
    }

    @AfterViews
    void bindAdapter() {
        orderClausesRecyclerView.setAdapter(adapter);
        orderClausesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setOnItemClickListener(
                (position, view, data) ->
                        view.billItemDiscountsExpander.setExpanded(
                                !view.billItemDiscountsExpander.isExpanded()));
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
        totalValue.setText(String.format(Locale.getDefault(), "$%.2f%n", order.amount));
        discountValue.setText(String.format("%s%%", order.discount));
        order.discounts.forEach(
                i -> {
                    final TextView textView = new TextView(this);
                    textView.setText(
                            String.format(
                                    "\u2022 %s discount of %s%% %s",
                                    StringUtils.capitalize(i.type.toLowerCase()),
                                    String.valueOf(i.discount),
                                    i.name.toLowerCase()));
                    textView.setTextColor(ContextCompat.getColor(this, R.color.colorWhite));
                    textView.setTypeface(null, Typeface.ITALIC);

                    orderDiscountsHolder.addView(textView);
                });
    }

    @Background
    void getItems() {
        final List<OrderClause> orderClauses =
                orders.getBillItems(user, order.id)
                        .getData()
                        .stream()
                        .map(BillItemResponseData::toDomain)
                        .peek(
                                clause ->
                                        clause.product =
                                                products.retrieveOne(clause.linkedProductId)
                                                        .getData()
                                                        .toDomain())
                        .peek(
                                clause ->
                                        clause.discounts =
                                                orders.getBillItemDiscounts(
                                                                user, order.id, clause.id)
                                                        .getData()
                                                        .stream()
                                                        .map(DiscountResponseData::toDomain)
                                                        .collect(Collectors.toList()))
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
        orderClausesRecyclerView.setAdapter(adapter);
    }
}
