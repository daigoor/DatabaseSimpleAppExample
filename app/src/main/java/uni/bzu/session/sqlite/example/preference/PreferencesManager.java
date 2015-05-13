package uni.bzu.session.sqlite.example.preference;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Mohamad on 4/13/2015.
 */
public class PreferencesManager {

    private static final String NAME = "bzu_example";

    private Context context;

    public PreferencesManager(Context context) {
        this.context = context;
    }

    public void setLoggedIn(boolean isLoggedIn) {
        SharedPreferences pref = this.context.getSharedPreferences(NAME, this.context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("loggedIn", isLoggedIn);
        editor.commit();
    }

    public boolean isLoggedIn() {
        SharedPreferences pref = this.context.getSharedPreferences(NAME, this.context.MODE_PRIVATE);
        return pref.getBoolean("loggedIn", false);
    }
}
