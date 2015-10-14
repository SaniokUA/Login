package azaza.login;

import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.common.AccountPicker;

import azaza.login.AuthGoogle.Authorization.GoogleData.GoogleAuth;
import azaza.login.AuthGoogle.Authorization.GoogleData.UserData;
import azaza.login.Internet.Network;
import azaza.login.Settings.EditSettings;
import azaza.login.Settings.LoadSettings;


public class LoadingActivity extends Activity {

    ProgressBar loading;
    TextView loadText;
    TextView tokText;
    Button reconnectButton;
    LinearLayout layoutRecon, layoutLoading;
    static SharedPreferences settings;

    public static final int REQUEST_ACCOUNT_PICKER = 1000;

    GoogleAuth googleAuth;



    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        loading = (ProgressBar) findViewById(R.id.loadingBar);
        loadText = (TextView) findViewById(R.id.loadingText);
        tokText = (TextView) findViewById(R.id.token);
        layoutRecon = (LinearLayout) findViewById(R.id.layoutRecon);
        layoutLoading = (LinearLayout) findViewById(R.id.layoutLoading);
        reconnectButton = (Button) findViewById(R.id.reconnectButton);
        googleAuth = new GoogleAuth(this);
        settings = getSharedPreferences("PressButton", Context.MODE_PRIVATE);
        LoadSettings.getInstance(this);

        if(new Network().getInstance().isNetworkAvailable(this)) {
            onSing();
        }
        else{
            layoutLoading.setVisibility(View.GONE);
            layoutRecon.setVisibility(View.VISIBLE);
        }
    }


    //authGoogle
    public void onSing() {
        loading.setVisibility(View.VISIBLE);
        loadText.setVisibility(View.VISIBLE);

        if (UserData.email == null) {
            showGoogleAccountPicker();
        }else{

            googleAuth.signInWithGplus(UserData.email);
        }
    }

    private void showGoogleAccountPicker() {
        Intent googlePicker = AccountPicker.newChooseAccountIntent(null, null,
                new String[]{GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE}, true, null, null, null, null);
        startActivityForResult(googlePicker, REQUEST_ACCOUNT_PICKER);
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        if (requestCode == REQUEST_ACCOUNT_PICKER && resultCode == RESULT_OK) {
            String accountName = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
            EditSettings.saveUserName(settings, accountName);

            if (accountName != null) {
                googleAuth.signInWithGplus(accountName);
            }

        } else {

        }
    }

    public void onReconnect(View view){
        if(new Network().getInstance().isNetworkAvailable(this)) {
            onSing();
        }else{
            Toast.makeText(LoadingActivity.this, "No connection to internet", Toast.LENGTH_SHORT).show();
        }
    }

}
