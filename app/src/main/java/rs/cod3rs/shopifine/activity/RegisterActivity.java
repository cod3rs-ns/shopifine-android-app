package rs.cod3rs.shopifine.activity;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.TextChange;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.rest.spring.annotations.RestService;
import org.springframework.core.NestedRuntimeException;

import java.util.Objects;

import rs.cod3rs.shopifine.R;
import rs.cod3rs.shopifine.hateoas.RelationshipData;
import rs.cod3rs.shopifine.hateoas.RequestRelationship;
import rs.cod3rs.shopifine.hateoas.users.UserRequest;
import rs.cod3rs.shopifine.hateoas.users.UserRequestAttributes;
import rs.cod3rs.shopifine.hateoas.users.UserRequestData;
import rs.cod3rs.shopifine.hateoas.users.UserRequestRelationships;
import rs.cod3rs.shopifine.http.Users;

import static rs.cod3rs.shopifine.hateoas.DataTypes.CATEGORIES_TYPE;
import static rs.cod3rs.shopifine.hateoas.DataTypes.USER_TYPE;

@EActivity(R.layout.activity_register)
public class RegisterActivity extends AppCompatActivity {

    private static final String USER_CATEGORY = "CUSTOMER";
    private static final Long SILVER_CATEGORY_ID = 1L;

    @RestService
    Users users;

    @ViewById
    EditText signUpFirstName;

    @ViewById
    EditText signUpLastName;

    @ViewById
    EditText signUpUsername;

    @ViewById
    EditText signUpPassword;

    @ViewById
    EditText signUpPasswordRepeat;

    @ViewById
    ProgressBar signUpProgressBar;

    @ViewById
    TextView signUpErrorMessage;

    @ViewById
    Button signUpBtn;

    private Place selectedPlace;

    @AfterViews
    void customizeAddressSearchBar() {
        final PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.addressFragment);

        final float scaledDensity = this.getApplicationContext().getResources().getDisplayMetrics().scaledDensity;
        final float textSize = signUpFirstName.getTextSize() / scaledDensity;

        final LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) autocompleteFragment.getView().getLayoutParams();
        params.setMargins(0, 0, 0, 0);
        autocompleteFragment.getView().setLayoutParams(params);

        final EditText placeField = (EditText) autocompleteFragment.getView().findViewById(R.id.place_autocomplete_search_input);

        placeField.setTextColor(getResources().getColor(R.color.colorWhite));
        placeField.setHintTextColor(signUpFirstName.getHintTextColors());
        placeField.setHint(getResources().getString(R.string.address));
        placeField.setTextSize(textSize);

        final ImageView searchIcon = (ImageView) autocompleteFragment.getView().findViewById(R.id.place_autocomplete_search_button);
        searchIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_person_pin_circle_white_24dp));
        searchIcon.setPadding(13, 0, 0, 5);

        final ImageView deleteIcon = (ImageView) autocompleteFragment.getView().findViewById(R.id.place_autocomplete_clear_button);
        deleteIcon.setImageDrawable(getResources().getDrawable(R.drawable.outline_clear_white_24));
        deleteIcon.setPadding(0, 0, getResources().getDimensionPixelSize(R.dimen.googleAddressClearMargin), 5);
    }

    @AfterViews
    void addAddressSelectionListener() {
        final PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.addressFragment);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(final Place place) {
                final EditText placeField = (EditText) autocompleteFragment.getView().findViewById(R.id.place_autocomplete_search_input);
                placeField.setError(null);
                selectedPlace = place;
            }

            @Override
            public void onError(final Status status) {
                Log.e("Google address", "An error occurred: " + status);
            }
        });
    }

    @AfterViews
    void addAddressRemovingListener() {
        final PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.addressFragment);

        autocompleteFragment.getView().findViewById(R.id.place_autocomplete_clear_button)
                .setOnClickListener(view -> {
                    autocompleteFragment.setText("");
                    view.setVisibility(View.GONE);
                    selectedPlace = null;
                });
    }

    @Click
    void signUpBtn() {
        final UserRequest userReq = validate();
        if (userReq != null) {
            showProgressBar();
            hideErrorMessage();
            registerUser(userReq);
        }
    }

    @Background
    void registerUser(final UserRequest userReq) {
        try {
            users.register(userReq);
            LoginActivity_.intent(this).start();
            finish();
        } catch (final NestedRuntimeException e) {
            hideProgressBar();
            showWrongSignupMessage(e.getMessage());
        }
    }

    @Click
    void loginLink() {
        LoginActivity_.intent(this).start();
        finish();
    }

    @UiThread
    void hideProgressBar() {
        signUpProgressBar.setVisibility(View.INVISIBLE);
        signUpBtn.setVisibility(View.VISIBLE);
    }

    @UiThread
    void showWrongSignupMessage(final String message) {
        clearFields();
        signUpErrorMessage.setText(message);
        signUpErrorMessage.setVisibility(View.VISIBLE);
    }

    @UiThread
    void showProgressBar() {
        signUpProgressBar.setVisibility(View.VISIBLE);
        signUpBtn.setVisibility(View.INVISIBLE);
    }

    @TextChange({R.id.signUpFirstName, R.id.signUpLastName, R.id.signUpUsername, R.id.signUpPassword, R.id.signUpPasswordRepeat})
    void hideErrorMessage() {
        signUpErrorMessage.setVisibility(View.GONE);
    }

    private UserRequest validate() {
        boolean valid = true;

        final String firstName = signUpFirstName.getText().toString().trim();
        if (firstName.isEmpty()) {
            signUpFirstName.setError(getResources().getString(R.string.fn_missing_err));
            valid = false;
        }

        final String lastName = signUpLastName.getText().toString().trim();
        if (lastName.isEmpty()) {
            signUpLastName.setError(getResources().getString(R.string.ln_missing_err));
            valid = false;
        }

        final String username = signUpUsername.getText().toString().trim();
        if (username.isEmpty()) {
            signUpUsername.setError(getResources().getString(R.string.username_missing_err));
            valid = false;
        }

        final String password = signUpPassword.getText().toString().trim();
        if (password.isEmpty() || password.length() < 6) {
            signUpPassword.setError(getResources().getString(R.string.pass_min_err));
            valid = false;
        }

        final String passwordRepeat = signUpPasswordRepeat.getText().toString().trim();
        if (!password.equals(passwordRepeat)) {
            signUpPasswordRepeat.setError(getResources().getString(R.string.pass_match_err));
            valid = false;
        }

        if (Objects.isNull(selectedPlace)) {
            final PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.addressFragment);
            final EditText placeField = (EditText) autocompleteFragment.getView().findViewById(R.id.place_autocomplete_search_input);
            placeField.setError(getResources().getString(R.string.address_missing_err));
            valid = false;
        }

        if (valid) {
            final Double longitude = selectedPlace.getLatLng().longitude;
            final Double latitude = selectedPlace.getLatLng().latitude;

            final UserRequestAttributes attrs = new UserRequestAttributes(username, password, firstName, lastName, USER_CATEGORY, selectedPlace.getAddress().toString(), longitude, latitude);
            final UserRequestRelationships relationships = new UserRequestRelationships(new RequestRelationship(new RelationshipData(CATEGORIES_TYPE, SILVER_CATEGORY_ID)));
            final UserRequestData data = new UserRequestData(USER_TYPE, attrs, relationships);
            return new UserRequest(data);
        }

        return null;
    }

    private void clearFields() {
        invalidateField(signUpFirstName);
        invalidateField(signUpLastName);
        invalidateField(signUpUsername);
        invalidateField(signUpPassword);
        invalidateField(signUpPasswordRepeat);

        final PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.addressFragment);
        final EditText placeField = (EditText) autocompleteFragment.getView().findViewById(R.id.place_autocomplete_search_input);
        invalidateField(placeField);

        final ImageView deleteIcon = (ImageView) autocompleteFragment.getView().findViewById(R.id.place_autocomplete_clear_button);
        deleteIcon.setVisibility(View.INVISIBLE);
    }

    private void invalidateField(final EditText field) {
        field.setText("");
        field.setError(null);
    }
}
