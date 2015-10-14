package azaza.login.game;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.TextView;

import azaza.login.AuthGoogle.Authorization.GoogleData.UserData;
import azaza.login.R;
import azaza.login.Settings.LoadSettings;


public class MenuActivity extends ActionBarActivity {

    AlertDialog.Builder ad;
    Context context;
    TextView hello, rank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        LoadSettings.getInstance(this);

        hello = (TextView) findViewById(R.id.hello);
        rank = (TextView) findViewById(R.id.textViewWorldRankValue);
        rank.setText(UserData.USER_RANK);

        getName();

    }

    //Button Start
    public void onStart(View view) {
        startDialog();
    }

    //Dialog window
    public void startDialog() {
        context = MenuActivity.this;
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
                Intent intent = new Intent(MenuActivity.this, GameActivity.class);
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
        Intent intent = new Intent(MenuActivity.this, RecordsActivity.class);
        startActivity(intent);
    }

    public void getName() {

        hello.setText(UserData.firstName);
    }


}

