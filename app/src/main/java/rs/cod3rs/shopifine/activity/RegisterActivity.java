package rs.cod3rs.shopifine.activity;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import rs.cod3rs.shopifine.R;

@EActivity(R.layout.activity_register)
public class RegisterActivity extends AppCompatActivity {

    @ViewById
    EditText signUpFirstName;

    @AfterViews
    void customizeAddressSearchBar() {
        final PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.addressFragment);

        final float scaledDensity = this.getApplicationContext().getResources().getDisplayMetrics().scaledDensity;
        final float textSize = signUpFirstName.getTextSize() / scaledDensity;

        final LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) autocompleteFragment.getView().getLayoutParams();
        params.setMargins(0, 0, 0, 0);
        autocompleteFragment.getView().setLayoutParams(params);

        final EditText etPlace = (EditText) autocompleteFragment.getView().findViewById(R.id.place_autocomplete_search_input);

        etPlace.setTextColor(getResources().getColor(R.color.colorWhite));
        etPlace.setHintTextColor(signUpFirstName.getHintTextColors());
        etPlace.setHint(getResources().getString(R.string.address));
        etPlace.setTextSize(textSize);

        final ImageView searchIcon = (ImageView) ((LinearLayout) autocompleteFragment.getView()).getChildAt(0);
        searchIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_person_pin_circle_white_24dp));
        searchIcon.setPadding(13, 0, 0, 5);

        final ImageView deleteIcon = (ImageView) ((LinearLayout) autocompleteFragment.getView()).getChildAt(2);
        deleteIcon.setImageDrawable(getResources().getDrawable(R.drawable.outline_clear_white_24));
        deleteIcon.setPadding(0, 0, getResources().getDimensionPixelSize(R.dimen.googleAddressClearMargin), 5);
    }

    @AfterViews
    void addAddressSelectionListener() {
        final PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.addressFragment);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(final Place place) {
                // TODO: Get info about the selected place.
                Log.i("", "Place: " + place.getName());
            }

            @Override
            public void onError(final Status status) {
                // TODO: Handle the error.
                Log.i("", "An error occurred: " + status);
            }
        });
    }

    @Click
    void signUpBtn() {
        LoginActivity_.intent(this).start();
        finish();
    }

    @Click
    void loginLink() {
        LoginActivity_.intent(this).start();
        finish();
    }
}
