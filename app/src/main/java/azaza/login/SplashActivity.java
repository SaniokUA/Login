package azaza.login;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.nkzawa.socketio.client.Socket;
import com.google.android.gms.auth.GoogleAuthUtil;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;

import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.github.nkzawa.socketio.client.IO;

import azaza.login.Internet.Connect;
import azaza.login.database.mongo.service.ServiceContainer;
import azaza.login.database.mongo.service.essence.EsAccount;
import azaza.login.database.mongo.service.implement.AccountService;


public class SplashActivity extends Activity {

    ProgressBar loading;
    TextView loadText;
    TextView tokText;

    Connect connect = new Connect();
    AccountManager mAccountManager;
    private static final String SCOPE = "oauth2:https://www.googleapis.com/auth/userinfo.profile";

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


        Executors.newSingleThreadExecutor().

                submit(new Runnable() {

                           @Override
                           public void run() {
                               EsAccount esAccount = new EsAccount();
                               esAccount.setCountry("Test");
                               esAccount.setEmail("test@test.test");
                               esAccount.setLogin("test");

                               AccountService accountService = ServiceContainer.getInstance().getService(AccountService.class);
                               accountService.createAccount(esAccount);
                           }
                       }

                );
    }


    private void Test() {
        EsAccount esAccount = new EsAccount();
        esAccount.setCountry("Test");
        esAccount.setEmail("test@test.test");
        esAccount.setLogin("test");

        AccountService accountService = ServiceContainer.getInstance().getService(AccountService.class);
        accountService.createAccount(esAccount);
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
                getTask(SplashActivity.this, accountarrs[0], SCOPE).execute();
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
