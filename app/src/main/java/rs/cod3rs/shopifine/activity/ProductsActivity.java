package rs.cod3rs.shopifine.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
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
public class ProductsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @ViewById
    Toolbar toolbar;

    @ViewById
    ListView products;

    @ViewById
    DrawerLayout drawerLayout;

    @ViewById
    NavigationView navigationView;

    @Bean
    ProductsAdapter adapter;

    @AfterViews
    void bindAdapter() {
        products.setAdapter(adapter);
    }

    @AfterViews
    void setToolbar() {
        setSupportActionBar(toolbar);
    }

    @ItemClick
    void productsItemClicked(final Product product) {
        Log.i("AAAAAAA", "AABABABABABABA");
    }

    @AfterViews
    void setDrawer() {
        // FIXME Better implementation
        final ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}
