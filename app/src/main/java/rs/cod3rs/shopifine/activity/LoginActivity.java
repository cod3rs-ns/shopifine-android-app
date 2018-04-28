package rs.cod3rs.shopifine.activity;

import android.app.Activity;
import android.util.Log;
import android.widget.EditText;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.androidannotations.rest.spring.annotations.RestService;

import rs.cod3rs.shopifine.Prefs_;
import rs.cod3rs.shopifine.R;
import rs.cod3rs.shopifine.hateoas.UserAuthRequest;
import rs.cod3rs.shopifine.hateoas.UserAuthResponse;
import rs.cod3rs.shopifine.http.ErrorHandler;
import rs.cod3rs.shopifine.http.Users;

@EActivity(R.layout.activity_login)
public class LoginActivity extends Activity {

    @Pref
    Prefs_ prefs;

    @RestService
    Users users;

    @ViewById
    EditText username;

    @ViewById
    EditText password;

    @Bean
    ErrorHandler errorHandler;

    @AfterInject
    void setErrorHandler() {
        users.setRestErrorHandler(errorHandler);
    }

    @Click(R.id.log_in)
    void logIn() {
        auth(username.getText().toString(), password.getText().toString());
    }

    @Click(R.id.sign_up_link)
    void signUp() {
        RegisterActivity_.intent(this).start();
        finish();
    }

    @Background
    void auth(final String username, final String password) {
        final UserAuthResponse res = users.auth(new UserAuthRequest(username, password));

        if (res != null) {
            prefs.token().put(res.getToken());
            ProductsActivity_.intent(getApplicationContext()).start();
            finish();
        } else {
            // TODO Handle Error
            Log.d(LoginActivity.class.getSimpleName(), "Wrong credentials.");
        }
    }

}
