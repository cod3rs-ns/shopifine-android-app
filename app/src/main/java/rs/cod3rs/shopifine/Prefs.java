package rs.cod3rs.shopifine;

import org.androidannotations.annotations.sharedpreferences.SharedPref;

@SharedPref(SharedPref.Scope.UNIQUE)
public interface Prefs {

    int loggedUserId();

    String loggedUserImageUrl();

    String loggedUserFullName();

    String loggedUserFirstName();

    String loggedUserLastName();

    String loggedUserAddress();

}
