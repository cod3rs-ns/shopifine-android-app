package rs.cod3rs.shopifine;

import org.androidannotations.annotations.sharedpreferences.SharedPref;

@SharedPref
public interface Prefs {

    String loggedUserImageUrl();

    String loggedUserFullName();

    String loggedUserAddress();

}
