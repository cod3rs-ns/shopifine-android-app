package rs.cod3rs.shopifine.activity;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.TextChange;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.androidannotations.rest.spring.annotations.RestService;
import org.springframework.core.NestedRuntimeException;

import rs.cod3rs.shopifine.Credentials_;
import rs.cod3rs.shopifine.Config;
import rs.cod3rs.shopifine.R;
import rs.cod3rs.shopifine.hateoas.users.GoogleAuthRequest;
import rs.cod3rs.shopifine.hateoas.users.UserAuthRequest;
import rs.cod3rs.shopifine.hateoas.users.UserAuthResponse;
import rs.cod3rs.shopifine.http.ErrorHandler;
import rs.cod3rs.shopifine.http.Users;

@EActivity(R.layout.activity_login)
public class LoginActivity extends Activity {

    private static final int RC_SIGN_IN = 100;

    @Pref
    Credentials_ credentials;

    @RestService
    Users users;

    @ViewById
    EditText username;

    @ViewById
    EditText password;

    @ViewById
    ProgressBar loginProgressBar;

    @ViewById
    TextView errorMessage;

    @ViewById
    Button logIn;

    @ViewById
    SignInButton googleSignIn;

    @Bean
    ErrorHandler errorHandler;

    private GoogleSignInClient googleSignInClient;

    @AfterInject
    void setErrorHandler() {
        users.setRestErrorHandler(errorHandler);
    }

    @AfterViews
    void init() {
        googleSignIn.setSize(SignInButton.SIZE_WIDE);
        setGoogleBtnTextAllCaps(googleSignIn);

        final GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(Config.GOOGLE_CLIENT_ID)
                .requestEmail()
                .requestProfile()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void setGoogleBtnTextAllCaps(final SignInButton signInButton) {
        for (int i = 0; i < signInButton.getChildCount(); i++) {
            final View v = signInButton.getChildAt(i);

            if (v instanceof TextView) {
                final TextView tv = (TextView) v;
                tv.setAllCaps(true);
                return;
            }
        }
    }

    @Click
    void logIn() {
        showProgressBar();
        hideErrorMessage();
        auth(username.getText().toString(), password.getText().toString());
    }

    @Click
    void signUpLink() {
        RegisterActivity_.intent(this).start();
        finish();
    }

    @Click
    void googleSignIn() {
        final Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @OnActivityResult(RC_SIGN_IN)
    void onResult(final int resultCode, final Intent data) {
        super.onActivityResult(RC_SIGN_IN, resultCode, data);

        final Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

        try {
            final GoogleSignInAccount account = task.getResult(ApiException.class);
            final String idToken = account.getIdToken();
            googleAuth(idToken);
        } catch (final ApiException e) {
            Log.w("googleAuth", "signInResult:failed code=" + e.getStatusCode());
        }
    }

    @Background
    void auth(final String username, final String password) {
        try {
            final UserAuthResponse res = users.auth(new UserAuthRequest(username, password));
            credentials.token().put(res.getToken());
            MainActivity_.intent(this).start();
            finish();
        } catch (final NestedRuntimeException e) {
            hideProgressBar();
            showWrongLoginMessage(e.getMessage());
        }
    }

    @Background
    void googleAuth(final String idToken) {
        try {
            final UserAuthResponse res = users.googleAuth(new GoogleAuthRequest(idToken));
            credentials.token().put(res.getToken());
            MainActivity_.intent(this).start();
            finish();
        } catch (final NestedRuntimeException e) {
            hideProgressBar();
            showWrongLoginMessage(e.getMessage());
        }
    }

    @TextChange({R.id.username, R.id.password})
    void hideErrorMessage() {
        errorMessage.setVisibility(View.GONE);
    }

    @UiThread
    void showProgressBar() {
        loginProgressBar.setVisibility(View.VISIBLE);
        logIn.setVisibility(View.INVISIBLE);
    }

    @UiThread
    void hideProgressBar() {
        loginProgressBar.setVisibility(View.INVISIBLE);
        logIn.setVisibility(View.VISIBLE);
    }

    @UiThread
    void showWrongLoginMessage(final String message) {
        password.setText("");
        errorMessage.setText(message);
        errorMessage.setVisibility(View.VISIBLE);
    }

    @AfterViews
    void setDefaults() {
        username.setText("dmarjanovic94");
        password.setText("sergioramos");
    }

}
