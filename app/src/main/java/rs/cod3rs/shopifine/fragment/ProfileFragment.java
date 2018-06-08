package rs.cod3rs.shopifine.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.androidannotations.rest.spring.annotations.RestService;
import org.springframework.core.NestedRuntimeException;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import rs.cod3rs.shopifine.Credentials_;
import rs.cod3rs.shopifine.Prefs_;
import rs.cod3rs.shopifine.R;
import rs.cod3rs.shopifine.activity.LoginActivity_;
import rs.cod3rs.shopifine.activity.MainActivity;
import rs.cod3rs.shopifine.fragment.EditProfileFragmentDialog.EditProfileDialogListener;
import rs.cod3rs.shopifine.hateoas.buyer_categories.BuyerCategoryResponse;
import rs.cod3rs.shopifine.hateoas.users.UserResponse;
import rs.cod3rs.shopifine.hateoas.users.UserResponseAttributes;
import rs.cod3rs.shopifine.http.BuyerCategories;
import rs.cod3rs.shopifine.http.Users;

import static rs.cod3rs.shopifine.fragment.EditProfileFragmentDialog.ADDRESS_ARG;
import static rs.cod3rs.shopifine.fragment.EditProfileFragmentDialog.FIRST_NAME_ARG;
import static rs.cod3rs.shopifine.fragment.EditProfileFragmentDialog.LAST_NAME_ARG;

@EFragment(R.layout.fragment_profile)
public class ProfileFragment extends Fragment implements EditProfileDialogListener {

    @Pref
    Credentials_ credentials;

    @Pref
    Prefs_ prefs;

    @RestService
    Users users;

    @RestService
    BuyerCategories buyerCategories;

    @ViewById
    CircleImageView userImage;

    @ViewById
    RelativeLayout profileInfo;

    @ViewById
    LinearLayout customerInfo;

    @ViewById
    TextView profileName;

    @ViewById
    TextView profileAddress;

    @ViewById
    TextView profileCategory;

    @ViewById
    TextView profilePoints;

    private GoogleSignInClient googleSignInClient;

    @AfterInject
    void extractUserIdFromToken() {
        final Integer userId = prefs.loggedUserId().get();

        updateProfileInfo();
        getLoggedUser(userId);
    }

    @AfterViews
    void setGoogleClient() {
        final GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build();
        googleSignInClient = GoogleSignIn.getClient(this.getContext(), gso);
    }

    @Background
    void getLoggedUser(final Integer userId) {
        try {
            final UserResponse res = users.getUser(userId);
            final UserResponseAttributes attrs = res.getData().getAttributes();

            final BuyerCategoryResponse catResp = buyerCategories.getBuyerCategory(res.getData().getRelationships().getBuyerCategory().getData().getId());

            updateCustomerInfo(catResp.getData().getAttributes().getName(), attrs.getPoints());
        } catch (final NestedRuntimeException e) {
            Log.e(getClass().getSimpleName(), e.getMessage());
        }
    }

    @UiThread
    void updateProfileInfo() {
        Picasso.get().load(prefs.loggedUserImageUrl().get()).into(userImage);
        profileName.setText(prefs.loggedUserFullName().get());

        if (!prefs.loggedUserAddress().get().isEmpty()) {
            profileAddress.setVisibility(View.VISIBLE);
            profileAddress.setText(prefs.loggedUserAddress().get());
        }
    }

    @UiThread
    void updateCustomerInfo(final String categoryName, final Long pointsValue) {
        final String pointsLabelText = pointsValue == null ? "0" : String.valueOf(pointsValue);

        profileCategory.setText(categoryName);
        profilePoints.setText(pointsLabelText);
    }

    @Click
    void profileShoppingCartBtn() {
        changeFragment(R.id.shoppingCart, 1);
    }

    @Click
    void profileWishListBtn() {
        // TODO Call Wishlist fragment
    }

    @Click
    void profileOrdersBtn() {
        // TODO Call Orders fragment
    }

    @Click
    void profileHomeBtn() {
        changeFragment(R.id.home, 0);
    }

    @Click
    void profileLogoutBtn() {
        credentials.edit().token().remove();
        googleSignInClient.signOut()
                .addOnCompleteListener(getActivity(), task -> {
                    LoginActivity_.intent(getActivity()).start();
                    getActivity().finish();
                });
    }

    @Click
    void editProfileBtn() {
        final EditProfileFragmentDialog dialog = EditProfileFragmentDialog_.builder().build();

        final Bundle bundle = new Bundle();
        bundle.putString(FIRST_NAME_ARG, prefs.loggedUserFirstName().get());
        bundle.putString(LAST_NAME_ARG, prefs.loggedUserLastName().get());
        bundle.putString(ADDRESS_ARG, prefs.loggedUserAddress().get());
        dialog.setArguments(bundle);

        dialog.show(getFragmentManager(), String.valueOf(R.layout.dialog_edit_profile));
    }

    private void changeFragment(final int fragmentId, final int navBarPosition) {
        final Fragment fragment = MainActivity.getFragmentById(fragmentId);

        getFragmentManager().beginTransaction()
                .replace(R.id.frame, fragment)
                .addToBackStack(String.valueOf(navBarPosition))
                .commit();
    }

    @Override
    public void onFinishEditDialog(final String firstName, final String lastName, final String address) {
        if (Objects.nonNull(firstName)) {
            prefs.edit().loggedUserFirstName().put(firstName).apply();
        }

        if (Objects.nonNull(lastName)) {
            prefs.edit().loggedUserLastName().put(lastName).apply();
        }

        if (Objects.nonNull(address)) {
            prefs.edit().loggedUserAddress().put(address).apply();
        }

        if (Objects.nonNull(firstName) || Objects.nonNull(lastName)) {
            prefs.edit().loggedUserFullName().put(String.format("%s %s", firstName, lastName)).apply();
        }

        updateProfileInfo();
    }
}
