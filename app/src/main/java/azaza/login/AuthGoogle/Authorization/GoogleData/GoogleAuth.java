package azaza.login.AuthGoogle.Authorization.GoogleData;

import android.app.Activity;
import android.content.IntentSender;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.plus.People;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

import azaza.login.Sockets.SocketManager;
import azaza.login.Temp.TempLocal;

import static com.google.android.gms.plus.Plus.PeopleApi;

/**
 * Created by Alex on 17.07.2015.
 */
public class GoogleAuth extends Activity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, ResultCallback<People.LoadPeopleResult> {

    static Activity activity;
    SocketManager socketManager;

    public GoogleAuth() {
        GoogleAuth.activity = TempLocal.getLoadingActivity();
        socketManager = SocketManager.getInstance();
    }

    private static final int RC_SIGN_IN = 100;
    public GoogleApiClient mGoogleApiClient;
    private ConnectionResult mConnectionResult;
    private boolean mIntentInProgress;

    public void getUserAccounts(String googleAcc) {
        if (googleAcc != null) {
            mGoogleApiClient = new GoogleApiClient.Builder(activity)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(Plus.API)
                    .addScope(new Scope(Scopes.PROFILE))
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

    @Override
    public void onConnected(Bundle connectionHint) {

        PeopleApi.loadVisible(mGoogleApiClient, null).setResultCallback(this);

        getProfileInformation();
    }


    /**
     * Fetching user's information name, email, profile pic
     */
    public void getProfileInformation() {
        try {
            if(PeopleApi.getCurrentPerson(mGoogleApiClient)!=null) {
                Person currentPerson = PeopleApi.getCurrentPerson(mGoogleApiClient);

                if (currentPerson.hasDisplayName()) {

                    if (currentPerson.getName().hasGivenName()) {
                        UserData.setFirstName(currentPerson.getName().getGivenName());
                    }
                    if (currentPerson.getName().hasFamilyName()) {
                        UserData.setLastName(currentPerson.getName().getFamilyName());
                    }

                    if (currentPerson.hasDisplayName()) {
                        UserData.setUserName(currentPerson.getDisplayName());
                    }

                }
                if (currentPerson.hasPlacesLived()) {
                    UserData.setCOUNTRY(currentPerson.getPlacesLived().get(0).getValue());
                }
                if (currentPerson.hasGender()) {
                    UserData.setSex(String.valueOf(currentPerson.getGender()));
                }
                UserData.setEmail(Plus.AccountApi.getAccountName(mGoogleApiClient));
                socketManager.authorization();
            }
            else{
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    @Override
    public void onConnectionSuspended(int arg0) {
        mGoogleApiClient.connect();
    }

    public void signInWithGplus(String accGoogle) {
        getUserAccounts(accGoogle);
    }

    public void signOutFromGplus() {
        if (mGoogleApiClient == null) {
        } else {
            Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
            //   mGoogleApiClient.disconnect();
        }
    }


    @Override
    public void onResult(People.LoadPeopleResult loadPeopleResult) {

    }
}


