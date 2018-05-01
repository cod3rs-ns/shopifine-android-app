package rs.cod3rs.shopifine;

import org.androidannotations.annotations.sharedpreferences.SharedPref;

@SharedPref(SharedPref.Scope.UNIQUE)
public interface Credentials {

    String token();

}
