package com.example.hqrestaurant;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    private static final String PREF = "hq_session";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_EMAIL = "email";

    private final SharedPreferences sp;

    public SessionManager(Context ctx) {
        sp = ctx.getSharedPreferences(PREF, Context.MODE_PRIVATE);
    }

    public void saveUser(String username, String email) {
        sp.edit()
                .putString(KEY_USERNAME, username)
                .putString(KEY_EMAIL, email)
                .apply();
    }

    public String getUsername() {
        return sp.getString(KEY_USERNAME, null);
    }

    public String getEmail() {
        return sp.getString(KEY_EMAIL, null);
    }

    public void logout() {
        sp.edit().clear().apply();
    }
}

