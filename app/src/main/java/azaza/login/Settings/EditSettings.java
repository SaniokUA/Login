package azaza.login.Settings;

import android.content.SharedPreferences;

/**
 * Created by Alex on 05.10.2015.
 */
public class EditSettings {

    public static void saveUserName(SharedPreferences settings, String accountName) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(SettingsConst.PREF_ACCOUNT_EMAIL, accountName);
        editor.commit();
    }

    public static void deleteUserName(SharedPreferences settings) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(SettingsConst.PREF_ACCOUNT_EMAIL, null);
        editor.commit();
    }

}
