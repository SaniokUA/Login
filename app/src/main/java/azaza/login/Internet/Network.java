package azaza.login.Internet;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Alex on 20.03.2015.
 */
public class Network {

    private static  Network instance = null;
    Context context;


    public Network(){

    }

    public Network getInstance( ){
        if (instance == null) {
            instance = new Network();
        }
        return instance;
    }


    public boolean isNetworkAvailable(final Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected()) {
            isAvailable = true;
        } else {
            isAvailable = false;
        }
        return isAvailable;
    }
}
