package rs.cod3rs.shopifine.activity;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.androidannotations.annotations.AfterExtras;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;

import rs.cod3rs.shopifine.R;
import rs.cod3rs.shopifine.domain.Product;

@EActivity(R.layout.activity_product)
public class ProductActivity extends AppCompatActivity {

    @Extra
    Product product;

    @AfterExtras
    public void getProductInfo() {
        Log.i(ProductActivity.class.getSimpleName(), String.format("Product activity opened for id %s", product.id));
    }

}
