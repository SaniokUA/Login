package azaza.login;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.GoogleAuthUtil;


import azaza.login.AuthGoogle.Authorization.AbstractGetNameTask;
import azaza.login.AuthGoogle.Authorization.GetNameInForeground;
import azaza.login.AuthGoogle.Authorization.GoogleData.GoogleData;
import azaza.login.Internet.Connect;


public class SplashActivity extends Activity {

    ProgressBar loading;
    TextView loadText;
    TextView tokText;

    Connect connect = new Connect();
    AccountManager mAccountManager;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Splash screen view

        setContentView(R.layout.activity_splash);
        loading = (ProgressBar) findViewById(R.id.progressBar1);
        loadText = (TextView) findViewById(R.id.textView1);
        tokText = (TextView) findViewById(R.id.token);
        onSing();
    }


    //authGoogle
    public void onSing() {
        loading.setVisibility(View.VISIBLE);
        loadText.setVisibility(View.VISIBLE);

        syncGoogleAccount();
    }

    private String[] getAccountNames() {
        mAccountManager = AccountManager.get(this);
        Account[] accounts = mAccountManager
                .getAccountsByType(GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE);
        String[] names = new String[accounts.length];
        for (int i = 0; i < names.length; i++) {
            names[i] = accounts[i].name;
        }
        return names;
    }

    private AbstractGetNameTask getTask(SplashActivity activity, String email,
                                        String scope) {
        return new GetNameInForeground(activity, email, scope);

    }

    public void syncGoogleAccount() {
        if (connect.isNetworkAvailable(this)) {
            String[] accountarrs = getAccountNames();
            if (accountarrs.length > 0) {
                //you can set here account for login
                getTask(SplashActivity.this, accountarrs[0], GoogleData.SCOPE).execute();
            } else {
                Toast.makeText(SplashActivity.this, "No Google Account Sync!",
                        Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(SplashActivity.this, "No Network Service!",
                    Toast.LENGTH_SHORT).show();
        }
    }


}
