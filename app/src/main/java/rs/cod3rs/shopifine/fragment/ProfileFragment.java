package rs.cod3rs.shopifine.fragment;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.auth0.android.jwt.JWT;
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

import de.hdodenhof.circleimageview.CircleImageView;
import rs.cod3rs.shopifine.Credentials_;
import rs.cod3rs.shopifine.Prefs_;
import rs.cod3rs.shopifine.R;
import rs.cod3rs.shopifine.activity.LoginActivity_;
import rs.cod3rs.shopifine.activity.MainActivity;
import rs.cod3rs.shopifine.hateoas.buyer_categories.BuyerCategoryResponse;
import rs.cod3rs.shopifine.hateoas.users.UserResponse;
import rs.cod3rs.shopifine.hateoas.users.UserResponseAttributes;
import rs.cod3rs.shopifine.http.BuyerCategories;
import rs.cod3rs.shopifine.http.Users;

@EFragment(R.layout.fragment_profile)
public class ProfileFragment extends Fragment {

    @Pref
    Prefs_ prefs;

    @RestService
    Users users;

    @RestService
    BuyerCategories buyerCategories;

    @Pref
    Credentials_ credentials;

    @ViewById
    CircleImageView userImage;

    @ViewById
    RelativeLayout profileInfo;

    @ViewById
    LinearLayout customerInfo;

    private GoogleSignInClient googleSignInClient;

    @AfterInject
    void extractUserIdFromToken() {
        final JWT jwt = new JWT(credentials.token().get());
        final Integer userId = jwt.getClaim("id").asInt();

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
            Log.e(this.getClass().getSimpleName(), e.getMessage());
        }
    }

    @UiThread
    void updateProfileInfo() {
        final TextView name = (TextView) profileInfo.findViewById(R.id.profile_name);
        final TextView address = (TextView) profileInfo.findViewById(R.id.profile_address);

        Picasso.get().load(prefs.loggedUserImageUrl().get()).into(userImage);
        name.setText(prefs.loggedUserFullName().get());

        if (prefs.loggedUserAddress().get().isEmpty()) {
            address.setVisibility(View.INVISIBLE);
        } else {
            address.setText(prefs.loggedUserAddress().get());
        }
    }

    @UiThread
    void updateCustomerInfo(final String categoryName, final Long pointsValue) {
        final TextView category = (TextView) customerInfo.findViewById(R.id.profile_category);
        final TextView points = (TextView) customerInfo.findViewById(R.id.profile_points);

        final String pointsLabelText = pointsValue == null ? "0" : String.valueOf(pointsValue);

        category.setText(categoryName);
        points.setText(pointsLabelText);
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
                .addOnCompleteListener(this.getActivity(), task -> {
                    LoginActivity_.intent(this.getActivity()).start();
                    this.getActivity().finish();
                });
    }

    @Click
    void editProfileBtn() {
        final EditProfileFragmentDialog dialog = EditProfileFragmentDialog_.builder().build();
        dialog.show(getFragmentManager(), String.valueOf(R.layout.dialog_edit_profile));
    }

    private void changeFragment(final int fragmentId, final int navBarPosition) {
        final Fragment fragment = MainActivity.getFragmentById(fragmentId);

        getFragmentManager().beginTransaction()
                .replace(R.id.frame, fragment)
                .addToBackStack(String.valueOf(navBarPosition))
                .commit();
    }
}
