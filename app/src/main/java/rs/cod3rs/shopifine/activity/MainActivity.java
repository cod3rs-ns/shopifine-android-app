package rs.cod3rs.shopifine.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import rs.cod3rs.shopifine.R;
import rs.cod3rs.shopifine.fragment.ProductsFragment_;
import rs.cod3rs.shopifine.fragment.ProfileFragment_;
import rs.cod3rs.shopifine.fragment.ShoppingCartFragment_;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @ViewById
    DrawerLayout drawerLayout;

    @ViewById
    NavigationView navigationView;

    @ViewById
    Toolbar toolbar;

    @AfterViews
    void setToolbar() {
        setSupportActionBar(toolbar);
    }

    @AfterViews
    void setInitialFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame, getFragmentById(R.id.home))
                .commit();
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

        // FIXME Better implementation and comment this code
        final int fragmentId = item.getItemId();

        navigationView.setCheckedItem(fragmentId);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame, getFragmentById(fragmentId))
                .commit();

        drawerLayout.closeDrawer(GravityCompat.START);

        return false;
    }

    private Fragment getFragmentById(final int fragmentId) {
        switch (fragmentId) {
            case R.id.home:
                return ProductsFragment_.builder().build();
            case R.id.shoppingCart:
                return ShoppingCartFragment_.builder().build();
            case R.id.wishlist:
                // TODO Provide Wishlist fragment
            case R.id.orders:
                // TODO Provide Orders fragment
            case R.id.profile:
                return ProfileFragment_.builder().build();
//            case R.id.logout:
//                // TODO Logout
//                LoginActivity_.intent(this).start();
//                finish();
//                break;
            default:
                throw new IllegalArgumentException("No fragment with id " + fragmentId);
        }
    }
}
