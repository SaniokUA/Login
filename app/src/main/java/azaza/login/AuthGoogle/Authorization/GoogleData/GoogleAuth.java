package azaza.login.AuthGoogle.Authorization.GoogleData;

import android.app.Activity;
import android.content.IntentSender;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

import azaza.login.Sockets.SocketManager;

import static com.google.android.gms.plus.Plus.PeopleApi;

/**
 * Created by Alex on 17.07.2015.
 */
public class GoogleAuth extends Activity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    static Activity activity;
    SocketManager socketManager;

    public GoogleAuth(Activity activity) {
        GoogleAuth.activity = activity;
        socketManager = SocketManager.getInstance();
    }

    private static final int RC_SIGN_IN = 100;
    public GoogleApiClient mGoogleApiClient;
    private ConnectionResult mConnectionResult;
    private boolean mIntentInProgress;

    UserData userData = new UserData();


    public void getUserAccounts(String googleAcc) {
        if (googleAcc != null) {
            mGoogleApiClient = new GoogleApiClient.Builder(activity)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(Plus.API)
                    .addScope(Plus.SCOPE_PLUS_LOGIN)
                    .setAccountName(googleAcc)
                    .build();
            mGoogleApiClient.connect();
        }
    }


    private void resolveSignInError() {
        if (mConnectionResult.hasResolution()) {
            try {
                mIntentInProgress = true;
                mConnectionResult.startResolutionForResult(activity, RC_SIGN_IN);
            } catch (IntentSender.SendIntentException e) {
                mIntentInProgress = false;
                mGoogleApiClient.connect();

            }
        }
    }


    /**
     * Error connectionFailed
     * Only to more 2 Accounts Google
     */
    @Override
    public void onConnectionFailed(ConnectionResult result) {
        if (!result.hasResolution()) {
            GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), activity, 0).show();
            return;
        }
        if (!mIntentInProgress) {
            // Store the ConnectionResult for later usage
            mConnectionResult = result;
            resolveSignInError();
        }
    }

    /**
     * If user connected to Google
     * Get user info
     */
    @Override
    public void onConnected(Bundle arg0) {

        getProfileInformation();

    }


    /**
     * Fetching user's information name, email, profile pic
     */
    public void getProfileInformation() {
        try {
            Person currentPerson = PeopleApi.getCurrentPerson(mGoogleApiClient);
            UserData.setFirstName(currentPerson.getName().getGivenName());
            UserData.setLastName(currentPerson.getName().getFamilyName());
            UserData.setUserName(currentPerson.getDisplayName());
            UserData.setEmail(Plus.AccountApi.getAccountName(mGoogleApiClient));
            UserData.setCOUNTRY(currentPerson.getPlacesLived().get(0).getValue());
            UserData.setSex(String.valueOf(currentPerson.getGender()));
            socketManager.authorization(activity);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onConnectionSuspended(int arg0) {
        mGoogleApiClient.connect();
    }

    /**
     * Sign-in into google
     */


    public void signInWithGplus(String accGoogle) {

        getUserAccounts(accGoogle);

    }

    /**
     * Sign-out from google
     */
    public void signOutFromGplus() {
        if (mGoogleApiClient == null) {
        } else {
            Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
            //   mGoogleApiClient.disconnect();
        }
    }



}


