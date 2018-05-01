package rs.cod3rs.shopifine.activity;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.TextChange;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.androidannotations.rest.spring.annotations.RestService;
import org.springframework.core.NestedRuntimeException;

import rs.cod3rs.shopifine.Credentials_;
import rs.cod3rs.shopifine.R;
import rs.cod3rs.shopifine.hateoas.users.UserAuthRequest;
import rs.cod3rs.shopifine.hateoas.users.UserAuthResponse;
import rs.cod3rs.shopifine.http.ErrorHandler;
import rs.cod3rs.shopifine.http.Users;

@EActivity(R.layout.activity_login)
public class LoginActivity extends Activity {

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

    @Bean
    ErrorHandler errorHandler;

    @AfterInject
    void setErrorHandler() {
        users.setRestErrorHandler(errorHandler);
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
        username.setText("dmarjanovic");
        password.setText("sergioramos");
    }

}
