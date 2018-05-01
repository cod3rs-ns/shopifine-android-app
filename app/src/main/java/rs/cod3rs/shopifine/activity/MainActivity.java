package rs.cod3rs.shopifine.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.auth0.android.jwt.JWT;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.androidannotations.rest.spring.annotations.RestService;
import org.springframework.core.NestedRuntimeException;

import de.hdodenhof.circleimageview.CircleImageView;
import rs.cod3rs.shopifine.Credentials_;
import rs.cod3rs.shopifine.Prefs_;
import rs.cod3rs.shopifine.R;
import rs.cod3rs.shopifine.domain.User;
import rs.cod3rs.shopifine.fragment.ProductsFragment_;
import rs.cod3rs.shopifine.fragment.ProfileFragment_;
import rs.cod3rs.shopifine.fragment.ShoppingCartFragment_;
import rs.cod3rs.shopifine.hateoas.users.UserResponse;
import rs.cod3rs.shopifine.hateoas.users.UserResponseAttributes;
import rs.cod3rs.shopifine.http.ErrorHandler;
import rs.cod3rs.shopifine.http.Users;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Pref
    Prefs_ prefs;

    @Pref
    Credentials_ credentials;

    @RestService
    Users users;

    @Bean
    ErrorHandler errorHandler;

    @AfterInject
    void setErrorHandler() {
        users.setRestErrorHandler(errorHandler);
    }

    @AfterInject
    void extractUserIdFromToken() {
        final JWT jwt = new JWT(credentials.token().get());
        final Integer userId = jwt.getClaim("id").asInt();
        getLoggedUser(userId);
    }

    @Background
    void getLoggedUser(final Integer userId) {
        try {
            final UserResponse res = users.getUser(userId);
            final UserResponseAttributes attrs = res.getData().getAttributes();
            final User u = new User(attrs.getUsername(), attrs.getFirstName(), attrs.getLastName(), attrs.getAddress());

            prefs.edit()
                    .loggedUserImageUrl().put(u.getImage())
                    .loggedUserFullName().put(u.getFullName())
                    .loggedUserAddress().put(u.address)
                    .apply();

            setHeader();
        } catch (final NestedRuntimeException e) {
            Log.e(this.getClass().getSimpleName(), e.getMessage());
        }
    }

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
        final ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
    }

    @UiThread
    void setHeader() {
        final LinearLayout header = (LinearLayout) navigationView.getHeaderView(0);

        final CircleImageView profileImage = (CircleImageView) header.findViewById(R.id.navProfileImage);
        final TextView name = (TextView) header.findViewById(R.id.navUserName);
        final TextView address = (TextView) header.findViewById(R.id.navUserAddress);

        Picasso.get().load(prefs.loggedUserImageUrl().get()).into(profileImage);
        name.setText(prefs.loggedUserFullName().get());
        address.setText(prefs.loggedUserAddress().get());
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        final int fragmentId = item.getItemId();

        if (R.id.logout == fragmentId) {
            credentials.edit().token().remove();
            LoginActivity_.intent(this).start();
            finish();
        } else {
            navigationView.setCheckedItem(fragmentId);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame, getFragmentById(fragmentId))
                    .commit();

            drawerLayout.closeDrawer(GravityCompat.START);
        }

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
            default:
                throw new IllegalArgumentException("No fragment with id " + fragmentId);
        }
    }
}
