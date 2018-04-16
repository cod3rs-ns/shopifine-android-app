package rs.cod3rs.shopifine.activity;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.ViewById;

import rs.cod3rs.shopifine.R;
import rs.cod3rs.shopifine.adapter.ProductsAdapter;
import rs.cod3rs.shopifine.domain.Product;

@EActivity(R.layout.activity_products)
public class ProductsActivity extends AppCompatActivity {

    @ViewById
    ListView products;

    @Bean
    ProductsAdapter adapter;

    @AfterViews
    void bindAdapter() {
        products.setAdapter(adapter);
    }

    @ItemClick
    void productsItemClicked(final Product product) {
        Log.i("AAAAAAA", "AABABABABABABA");
    }

}
