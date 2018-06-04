package rs.cod3rs.shopifine.fragment;

import android.app.FragmentManager;
import android.support.v4.app.DialogFragment;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.Objects;

import rs.cod3rs.shopifine.R;

@EFragment(R.layout.dialog_edit_profile)
public class EditProfileFragmentDialog extends DialogFragment {

    @ViewById
    EditText editProfileFirstName;

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
    public void onDestroyView() {
        super.onDestroyView();

        final FragmentManager manager = getActivity().getFragmentManager();
        final PlaceAutocompleteFragment placeFragment = (PlaceAutocompleteFragment) manager.findFragmentById(R.id.editProfileAddress);
        if (Objects.nonNull(placeFragment)) {
            manager.beginTransaction().remove(placeFragment).commit();
        }
    }
}