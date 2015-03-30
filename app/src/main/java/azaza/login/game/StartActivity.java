package azaza.login.game;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import azaza.login.AuthGoogle.Authorization.AbstractGetNameTask;
import azaza.login.AuthGoogle.Authorization.GoogleData.User;
import azaza.login.R;


public class StartActivity extends ActionBarActivity {

    AlertDialog.Builder ad;
    Context context;
    TextView hello;
    TextView debug;
    User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start);
        hello = (TextView) findViewById(R.id.hello);
        debug = (TextView) findViewById(R.id.textDebug);


        getName();

    }

    //Button Start
    public void onStart(View view) {
        startDialog();
    }

    //Dialog window
    public void startDialog() {
        context = StartActivity.this;
        String title = "Начало игры";
        String message = "Установите рекорд по нажатию кнопки за 10 секунд";
        String button1String = "Старт";
        String button2String = "Назад";

        ad = new AlertDialog.Builder(context);
        ad.setTitle(title);  // заголовок
        ad.setMessage(message); // сообщение
        ad.setPositiveButton(button2String, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
//                Intent intent = new Intent(StartActivity.this, MainActivity.class);
//                finish();
            }
        });
        ad.setNegativeButton(button1String, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
                finish();
                Intent intent = new Intent(StartActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        ad.setCancelable(false);
        ad.show();
    }

    // Button Exit
    public void onExit(View view) {
        finish();
    }

    //View all Results
    public void onRecords(View view) {
        finish();
        Intent intent = new Intent(StartActivity.this, Records.class);
        startActivity(intent);
    }

    public void getName() {
        try {
            JSONObject profileData = new JSONObject(
                    AbstractGetNameTask.GOOGLE_USER_DATA);

            if (profileData.has("name")) {
                String textName = profileData.getString("given_name");
                hello.setText(textName);
            }
            debug.setText(user.getLastName());


        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}

