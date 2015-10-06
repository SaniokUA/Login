package azaza.login.Settings;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import azaza.login.AuthGoogle.Authorization.GoogleData.UserData;

/**
 * Created by Alex on 13.07.2015.
 */
public class LoadSettings extends Activity {

    public static String USER_ACCOUNT;
    public static SharedPreferences settings;
    Activity activity;

    private static LoadSettings instance = null;

    private LoadSettings(Activity activity) {
        this.activity = activity;
        settings  =  activity.getSharedPreferences("PressButton", Context.MODE_PRIVATE);
        loadPreferences();
    }

    public static LoadSettings getInstance(Activity activity) {
        if (instance == null) {
            instance = new LoadSettings(activity);
        }
        return instance;
    }

    public void savePreferences() {
        // получить доступ к объекту Editor, чтобы изменить общие настройки.
        SharedPreferences.Editor editor = settings.edit();
        // задать новые базовые типы в объекте общих настроек.
        editor.putString(SettingsConst.PREF_ACCOUNT_EMAIL, null);
        editor.putInt(SettingsConst.PREF_ACCOUNT_FIRST_START, 1);
        editor.commit();
    }

    public void loadPreferences() {
        try {
            int  FRIST_START = settings.getInt(SettingsConst.PREF_ACCOUNT_FIRST_START, 0);
            if (FRIST_START == 0) {
                savePreferences();
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        UserData.email = settings.getString(SettingsConst.PREF_ACCOUNT_EMAIL, null);
    }

    public static String getUserAccount() {
        return USER_ACCOUNT;
    }

    public static void setUserAccount(String userAccount) {
        USER_ACCOUNT = userAccount;
    }

}
