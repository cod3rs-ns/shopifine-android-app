package rs.cod3rs.shopifine.activity;

import android.support.v7.app.AppCompatActivity;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;

import rs.cod3rs.shopifine.R;

@EActivity(R.layout.activity_register)
public class RegisterActivity extends AppCompatActivity {

    @Click(R.id.log_in_link)
    void logIn() {
        LoginActivity_.intent(this).start();
    }
}
