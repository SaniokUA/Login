package azaza.login.Internet;

import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import azaza.login.SplashActivity;

/**
 * Created by Alex on 20.03.2015.
 */
public class Connect {

    public boolean isNetworkAvailable (final Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if(networkInfo != null && networkInfo.isConnected()){
            isAvailable = true;
        } else {
            isAvailable = false;
        }
        return isAvailable;
    }
}
