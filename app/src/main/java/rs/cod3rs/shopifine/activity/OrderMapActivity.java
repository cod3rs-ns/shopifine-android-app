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
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.FragmentById;
import org.androidannotations.annotations.SystemService;
import org.androidannotations.annotations.sharedpreferences.Pref;

import rs.cod3rs.shopifine.Prefs_;
import rs.cod3rs.shopifine.R;
import rs.cod3rs.shopifine.domain.Order;


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

    @AfterViews
    void init() {
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        final LatLng orderPoint = new LatLng(order.latitude, order.longitude);
        final LatLng homePoint = new LatLng(
                Double.valueOf(prefs.loggedUserHomeLatitude().get()),
                Double.valueOf(prefs.loggedUserHomeLongitude().get()));

        final MarkerOptions orderMarker = new MarkerOptions()
                .position(orderPoint)
                .title("Order")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.baseline_local_shipping_black_36))
                .visible(true);
        final MarkerOptions homeMarker = new MarkerOptions()
                .position(homePoint)
                .title("Home")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.baseline_home_black_36))
                .visible(true);

        map.addMarker(orderMarker);
        map.addMarker(homeMarker);

        final LatLngBounds bounds = new LatLngBounds.Builder().include(orderPoint).include(homePoint).build();
        final CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 100);

        map.animateCamera(cu);
        map.moveCamera(cu);

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
                            .title("Me")
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.baseline_person_pin_circle_black_36))
                            .visible(true);
                    map.addMarker(currentMarker);


                    final LatLngBounds threeWayBounds = new LatLngBounds.Builder().include(orderPoint).include(homePoint).include(currentPoint).build();
                    final CameraUpdate threeWayCU = CameraUpdateFactory.newLatLngBounds(threeWayBounds, 100);

                    map.animateCamera(threeWayCU);
                    map.moveCamera(threeWayCU);
                } else {
                    Log.i(getClass().getName(), "No location.");
                }
            });
        }

    }
}