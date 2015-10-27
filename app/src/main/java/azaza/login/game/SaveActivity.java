package azaza.login.game;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareButton;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;

import azaza.login.AuthGoogle.Authorization.GoogleData.UserData;
import azaza.login.R;
import azaza.login.Sockets.SocketManager;
import io.fabric.sdk.android.Fabric;


/**
 * Created by Alex on 08.10.2015.
 */
public class SaveActivity extends Activity {

    TextView result, name, speed, bestSpeed;
    ImageButton save, close;
    SocketManager socketManager;
    TwitterLoginButton twitterLoginButton;


    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "LOP9BhfGQqFPfoNmr1MfuWBsK";
    private static final String TWITTER_SECRET = "RNWG6l9m5LoWjYoPaKWyQNNziQ1JXqojbbKZwSqFnMCDZnqUqL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());

        final TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new TwitterCore(authConfig), new TweetComposer());
        setContentView(R.layout.activity_save);

        sharingFB();
        shringTW();

        getResults();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result to the login button.
        twitterLoginButton.onActivityResult(requestCode, resultCode, data);
    }

    public void getResults() {

        result = (TextView) findViewById(R.id.textViewScoreValue);
        name = (TextView) findViewById(R.id.textViewNameSave);
        speed = (TextView) findViewById(R.id.textViewSpeedValue);
        bestSpeed = (TextView) findViewById(R.id.textViewBestSpeedValue);

        result.setText(UserData.getRESULT());
        speed.setText(UserData.getSPEED());
        bestSpeed.setText(UserData.getBestSpeed());
        name.setText(UserData.userName);
    }


    public void onClose(View view) {
        finish();
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }

    public void onSave(View view) {

        socketManager = SocketManager.getInstance();
        socketManager.savePersonalResults();

        finish();
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }

    public void sharingFB() {
        Uri url = Uri.parse("http://azaza.com");
        ShareLinkContent content = new ShareLinkContent.Builder()
                .setContentDescription("TapChallenge")
                .setContentTitle((UserData.getRESULT()))
                .setImageUrl(url)
                .build();

        ShareButton shareButton = (ShareButton) findViewById(R.id.imageButtonFB);
        shareButton.setShareContent(content);
    }

    public void shringTW() {
        twitterLoginButton = (TwitterLoginButton) findViewById(R.id.imageButtonTW);
        twitterLoginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                Uri myImageUri = Uri.parse("https://pp.vk.me/c7009/v7009945/48cf/z9VEhdeR1TE.jpg");
                TweetComposer.Builder builder = new TweetComposer.Builder(SaveActivity.this)
                        .text("My record " + (UserData.getRESULT()) + " " + "#TapChallenge")
                        .image(myImageUri);
                builder.show();
            }
            @Override
            public void failure(TwitterException e) {
                e.printStackTrace();
            }
        });
    }
}
