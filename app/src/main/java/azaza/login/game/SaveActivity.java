package azaza.login.game;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import azaza.login.AuthGoogle.Authorization.GoogleData.UserData;
import azaza.login.R;

/**
 * Created by Alex on 08.10.2015.
 */
public class SaveActivity extends Activity{

    TextView result, name, speed, bestSpeed;
    ImageButton save, close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);

        getResults();

    }

    public void getResults(){

        result = (TextView) findViewById(R.id.textViewScoreValue);
        name = (TextView) findViewById(R.id.textViewNameSave);
        speed = (TextView) findViewById(R.id.textViewSpeedValue);
        bestSpeed = (TextView) findViewById(R.id.textViewBestSpeedValue);

        result.setText(UserData.getRESULT());
        speed.setText(UserData.getSPEED());
        bestSpeed.setText(UserData.getBestSpeed());
        name.setText(UserData.userName);
    }

    public void onClose(View view){
        finish();
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }

    public void onSave(View view){
        finish();
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }
}
