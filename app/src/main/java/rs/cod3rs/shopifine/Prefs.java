package rs.cod3rs.shopifine;

import org.androidannotations.annotations.sharedpreferences.SharedPref;

@SharedPref
public interface Prefs {

    String token();

    String loggedUserImageUrl();

    String loggedUserFullName();

    String loggedUserAddress();

}
