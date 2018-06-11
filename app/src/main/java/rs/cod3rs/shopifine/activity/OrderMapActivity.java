package rs.cod3rs.shopifine.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.FragmentById;
import org.androidannotations.annotations.SystemService;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.androidannotations.rest.spring.annotations.RestService;

import java.util.Objects;

import rs.cod3rs.shopifine.Prefs_;
import rs.cod3rs.shopifine.R;
import rs.cod3rs.shopifine.domain.Order;
import rs.cod3rs.shopifine.domain.User;
import rs.cod3rs.shopifine.hateoas.users.UserResponse;
import rs.cod3rs.shopifine.hateoas.users.UserResponseAttributes;
import rs.cod3rs.shopifine.http.Users;


@EActivity(R.layout.activity_order_map)
public class OrderMapActivity extends AppCompatActivity implements OnMapReadyCallback {

    @FragmentById(R.id.g_map)
    SupportMapFragment mapFragment;

    @Extra
    Order order;

    @Pref
    Prefs_ prefs;

    @SystemService
    LocationManager locationManager;

    private double currentLatitude;

    private double currentLongitude;

    private GoogleMap map;

    @RestService
    Users users;

    @AfterViews
    void init() {
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        map = googleMap;
        getUser();
    }

    @Background
    public void getUser() {
        final UserResponse res = users.getUser(prefs.loggedUserId().get());
        final UserResponseAttributes attrs = res.getData().getAttributes();
        showMap(attrs.getLatitude(), attrs.getLongitude());
    }

    @UiThread
    public void showMap(final Double homeLatitude, final Double homeLongitude) {
        final LatLng orderPoint = new LatLng(order.latitude, order.longitude);

        final MarkerOptions orderMarker = new MarkerOptions()
                .position(orderPoint)
                .title(String.valueOf(R.string.map_order))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.baseline_local_shipping_black_36))
                .visible(true);
        map.addMarker(orderMarker);

        if (Objects.nonNull(homeLatitude) && Objects.nonNull(homeLongitude)) {
            final LatLng homePoint = new LatLng(homeLatitude, homeLongitude);

            final MarkerOptions homeMarker = new MarkerOptions()
                    .position(homePoint)
                    .title(String.valueOf(R.string.map_home))
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.baseline_home_black_36))
                    .visible(true);
            map.addMarker(homeMarker);

            final LatLngBounds bounds = new LatLngBounds.Builder().include(orderPoint).include(homePoint).build();
            final CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 100);
            map.animateCamera(cu);
            map.moveCamera(cu);
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.e(getClass().getName(), "No permissions.");
        } else {
            LocationServices.getFusedLocationProviderClient(this).getLastLocation().addOnSuccessListener(this, location -> {
                if (location != null) {
                    this.currentLatitude = location.getLatitude();
                    this.currentLongitude = location.getLongitude();

                    final LatLng currentPoint = new LatLng(currentLatitude, currentLongitude);
                    final MarkerOptions currentMarker = new MarkerOptions()
                            .position(currentPoint)
                            .title(String.valueOf(R.string.map_me))
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.baseline_person_pin_circle_black_36))
                            .visible(true);
                    map.addMarker(currentMarker);

                    if (Objects.nonNull(homeLatitude) && Objects.nonNull(homeLongitude)) {
                        final LatLng homePoint = new LatLng(homeLatitude, homeLongitude);
                        final LatLngBounds threeWayBounds = new LatLngBounds.Builder().include(orderPoint).include(homePoint).include(currentPoint).build();
                        final CameraUpdate threeWayCU = CameraUpdateFactory.newLatLngBounds(threeWayBounds, 100);

                        map.animateCamera(threeWayCU);
                        map.moveCamera(threeWayCU);
                    }
                } else {
                    Log.i(getClass().getName(), "No location.");
                }
            });
        }
    }
}
