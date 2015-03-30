package azaza.login.AuthGoogle.Authorization;

import java.io.IOException;

import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.GooglePlayServicesAvailabilityException;
import com.google.android.gms.auth.UserRecoverableAuthException;

import org.json.JSONException;
import org.json.JSONObject;

import azaza.login.AuthGoogle.Authorization.GoogleData.GoogleData;
import azaza.login.AuthGoogle.Authorization.GoogleData.User;
import azaza.login.SplashActivity;

/**
 * This example shows how to fetch tokens if you are creating a foreground task/activity and handle
 * auth exceptions.
 */
public class GetNameInForeground extends AbstractGetNameTask {

    public GetNameInForeground(SplashActivity activity, String email, String scope) {
        super(activity, email, scope);
    }

    @Override
    protected void getUserDetails() throws JSONException {
        JSONObject profile = new JSONObject(GOOGLE_USER_DATA);
        User user = new User();
        user.setFirstName(profile.getString(GoogleData.FIRST_NAME_KEY));
        user.setLastName(profile.getString(GoogleData.LAST_NAME_KEY));
        user.setUserID(profile.getString(GoogleData.USER_ID_KEY));
        user.setPictureURL(profile.getString(GoogleData.USER_SEX));
        user.setUserName(profile.getString(GoogleData.USER_NAME_KEY));
        user.setEmailId(GoogleData.EMAIL_ID);
    }


    /**
     * Get a authentication token if one is not available. If the error is not recoverable then
     * it displays the error message on parent activity right away.
     */
    @Override
    public String fetchToken() throws IOException {
        try {
            return GoogleAuthUtil.getToken(mActivity, mEmail, mScope);
        } catch (GooglePlayServicesAvailabilityException playEx) {
            // GooglePlayServices.apk is either old, disabled, or not present.
        } catch (UserRecoverableAuthException userRecoverableException) {
            // Unable to authenticate, but the user can fix this.
            // Forward the user to the appropriate activity.
            mActivity.startActivityForResult(userRecoverableException.getIntent(), mRequestCode);
        } catch (GoogleAuthException fatalException) {
            onError("Unrecoverable error " + fatalException.getMessage(), fatalException);
        }
        return null;
    }

}
