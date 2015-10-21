package azaza.login.Temp;

import android.app.Activity;

/**
 * Created by Alex on 21.10.2015.
 */
public class TempLocal {


    public static Activity LOADING_ACTIVITY = null;
    public static Activity RECORDS_ACTIVITY = null;



    public static Activity getLoadingActivity() {
        return LOADING_ACTIVITY;
    }

    public static void setLoadingActivity(Activity loadingActivity) {
        LOADING_ACTIVITY = loadingActivity;
    }
    public static Activity getRecordsActivity() {
        return RECORDS_ACTIVITY;
    }

    public static void setRecordsActivity(Activity recordsActivity) {
        RECORDS_ACTIVITY = recordsActivity;
    }


}
