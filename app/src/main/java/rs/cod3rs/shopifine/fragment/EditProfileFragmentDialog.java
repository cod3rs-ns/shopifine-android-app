package rs.cod3rs.shopifine.fragment;

import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.rest.spring.annotations.RestService;
import org.springframework.core.NestedRuntimeException;

import java.util.Objects;

import rs.cod3rs.shopifine.R;
import rs.cod3rs.shopifine.hateoas.users.UpdateUserRequest;
import rs.cod3rs.shopifine.hateoas.users.UpdateUserRequestAttributes;
import rs.cod3rs.shopifine.hateoas.users.UpdateUserRequestData;
import rs.cod3rs.shopifine.http.Users;

import static rs.cod3rs.shopifine.hateoas.DataTypes.USER_TYPE;

@EFragment(R.layout.dialog_edit_profile)
public class EditProfileFragmentDialog extends DialogFragment {

    public static final String FIRST_NAME_ARG = "first_name";
    public static final String LAST_NAME_ARG = "last_name";
    public static final String ADDRESS_ARG = "address";

    @ViewById
    EditText editProfileFirstName;

    @ViewById
    EditText editProfileLastName;

    @RestService
    Users users;

    @ViewById
    ProgressBar editProfileProgressBar;

    @ViewById
    Button saveEditBtn;

    private Place selectedPlace;

    private Boolean addressUpdated;

    private EditProfileDialogListener listener;

    @AfterViews
    public void updateInfo() {
        final Bundle bundle = this.getArguments();
        if (bundle != null) {
            final String fn = bundle.getString(FIRST_NAME_ARG, "");
            final String ln = bundle.getString(LAST_NAME_ARG, "");
            final String address = bundle.getString(ADDRESS_ARG, "");

            editProfileFirstName.setText(fn);
            editProfileLastName.setText(ln);

            final PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment) getActivity().getFragmentManager().findFragmentById(R.id.editProfileAddress);
            final EditText placeField = (EditText) autocompleteFragment.getView().findViewById(R.id.place_autocomplete_search_input);
            placeField.setText(address);
            addressUpdated = false;
        }
    }

    @AfterViews
    public void customizeView() {
        final PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment) getActivity().getFragmentManager().findFragmentById(R.id.editProfileAddress);

        final float scaledDensity = getResources().getDisplayMetrics().scaledDensity;
        final float textSize = editProfileFirstName.getTextSize() / scaledDensity;

        final LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) autocompleteFragment.getView().getLayoutParams();
        params.setMargins(0, 0, 0, 0);
        autocompleteFragment.getView().setLayoutParams(params);

        final EditText placeField = (EditText) autocompleteFragment.getView().findViewById(R.id.place_autocomplete_search_input);

        placeField.setTextColor(getResources().getColor(R.color.colorWhite));
        placeField.setHintTextColor(editProfileFirstName.getHintTextColors());
        placeField.setHint(getResources().getString(R.string.address));
        placeField.setTextSize(textSize);

        final ImageView searchIcon = (ImageView) autocompleteFragment.getView().findViewById(R.id.place_autocomplete_search_button);
        searchIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_person_pin_circle_white_24dp));
        searchIcon.setPadding(13, 0, 0, 5);

        final ImageView deleteIcon = (ImageView) autocompleteFragment.getView().findViewById(R.id.place_autocomplete_clear_button);
        deleteIcon.setImageDrawable(getResources().getDrawable(R.drawable.outline_clear_white_24));
        deleteIcon.setPadding(0, 0, getResources().getDimensionPixelSize(R.dimen.googleAddressClearMargin), 5);
    }

    @Override
    public void onAttach(final Context context) {
        super.onAttach(context);
        try {
            listener = (EditProfileDialogListener) context;
        } catch (final ClassCastException e) {
            throw new ClassCastException(String.format("%s must implement EditProfileDialogListener", context.toString()));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        final FragmentManager manager = getActivity().getFragmentManager();
        final PlaceAutocompleteFragment placeFragment = (PlaceAutocompleteFragment) manager.findFragmentById(R.id.editProfileAddress);
        if (Objects.nonNull(placeFragment)) {
            manager.beginTransaction().remove(placeFragment).commit();
        }
    }

    @AfterViews
    void addAddressSelectionListener() {
        final PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment) getActivity().getFragmentManager().findFragmentById(R.id.editProfileAddress);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(final Place place) {
                final EditText placeField = (EditText) autocompleteFragment.getView().findViewById(R.id.place_autocomplete_search_input);
                placeField.setError(null);
                addressUpdated = true;
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
        final PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment) getActivity().getFragmentManager().findFragmentById(R.id.editProfileAddress);

        autocompleteFragment.getView().findViewById(R.id.place_autocomplete_clear_button)
                .setOnClickListener(view -> {
                    autocompleteFragment.setText("");
                    addressUpdated = true;
                    view.setVisibility(View.GONE);
                    selectedPlace = null;
                });
    }

    @Click
    void cancelBtn() {
        final Fragment prev = getActivity().getSupportFragmentManager().findFragmentByTag(String.valueOf(R.layout.dialog_edit_profile));
        if (prev != null) {
            final DialogFragment df = (DialogFragment) prev;
            df.dismiss();
        }
    }

    @Click
    void saveEditBtn() {
        final UpdateUserRequest userReq = validate();
        if (userReq != null) {
            showProgressBar();
            updateCustomer(userReq);
        }
    }

    @Background
    void updateCustomer(final UpdateUserRequest userReq) {
        try {
            users.updateCustomer(userReq);
            final UpdateUserRequestAttributes attributes = userReq.getData().getAttributes();
            listener.onFinishEditDialog(attributes.getFirstName(), attributes.getLastName(), attributes.getAddress());
            cancelBtn();
        } catch (final NestedRuntimeException e) {
            hideProgressBar();
        }
    }

    @UiThread
    void hideProgressBar() {
        editProfileProgressBar.setVisibility(View.INVISIBLE);
        saveEditBtn.setVisibility(View.VISIBLE);
    }

    @UiThread
    void showProgressBar() {
        editProfileProgressBar.setVisibility(View.VISIBLE);
        saveEditBtn.setVisibility(View.INVISIBLE);
    }

    private UpdateUserRequest validate() {
        boolean valid = true;

        final String firstName = editProfileFirstName.getText().toString().trim();
        if (firstName.isEmpty()) {
            editProfileFirstName.setError(getResources().getString(R.string.fn_missing_err));
            valid = false;
        }

        final String lastName = editProfileLastName.getText().toString().trim();
        if (lastName.isEmpty()) {
            editProfileLastName.setError(getResources().getString(R.string.ln_missing_err));
            valid = false;
        }

        if (addressUpdated && Objects.isNull(selectedPlace)) {
            final PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment) getActivity().getFragmentManager().findFragmentById(R.id.editProfileAddress);
            final EditText placeField = (EditText) autocompleteFragment.getView().findViewById(R.id.place_autocomplete_search_input);
            placeField.setError(getResources().getString(R.string.address_missing_err));
            valid = false;
        }

        if (valid) {
            final UpdateUserRequestAttributes attrs;
            if (Objects.nonNull(selectedPlace)) {
                final Double longitude = selectedPlace.getLatLng().longitude;
                final Double latitude = selectedPlace.getLatLng().latitude;
                attrs = new UpdateUserRequestAttributes(firstName, lastName, selectedPlace.getAddress().toString(), longitude, latitude);
            } else {
                attrs = new UpdateUserRequestAttributes(firstName, lastName, null, null, null);
            }

            final UpdateUserRequestData data = new UpdateUserRequestData(USER_TYPE, attrs);
            return new UpdateUserRequest(data);
        }

        return null;
    }

    public interface EditProfileDialogListener {
        void onFinishEditDialog(String firstName, String lastName, String address);
    }
}
