package azaza.login.game;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import azaza.login.DB;
import azaza.login.R;


public class MainActivity extends StartActivity {


    StringBuilder Result = new StringBuilder();
    TextView textView;
    ImageButton button;
    private int i;
    TextView TextResult;
    String data;
    AlertDialog.Builder ad;
    Context context;
    Chronometer chronometer;

    DB db;
    public String result;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        TextResult = (TextView) findViewById(R.id.textView);
        textView = (TextView)findViewById(R.id.textView);

        button = (ImageButton)findViewById(R.id.button);
        button.setOnClickListener(listener);
        chronometer = (Chronometer)findViewById(R.id.chronometer);
        chronometer.start();
        chron();


}

    //Game Time
    public void  chron(){
    chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                    long myElapsedMillis = SystemClock.elapsedRealtime()
                        - chronometer.getBase();
                if( myElapsedMillis < 8000){
                    chronometer.setTextColor(0xFF00FF00);
                }else{
                    chronometer.setTextColor(0xffff0000);
                }

                if (myElapsedMillis > 9999) {
                    button.setClickable(false);
                    result = (String) ((TextView)findViewById(R.id.textView)).getText();
                    resultDialog();
                    chronometer.stop();
                }
            }
        });
    }

    public void saveResult(){

        db = new DB(this);
        db.open();
        double time = 10;
        double speed;
        int newRes = Integer.parseInt(result);
        speed = newRes/time;
        Math.round(speed);
        db.addRec("User", newRes, speed);
        db.close();
    }

    //Pause dialog
    public void pauseDialog(){
    context = MainActivity.this;
    String title = "Пауза";
    String message = "Нажмите продолжить";
    String button2String = "Продолжить";

    ad = new AlertDialog.Builder(context);
    ad.setTitle(title);  // заголовок
    ad.setMessage(message); // сообщение
    ad.setNegativeButton(button2String, new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int arg1) {
            Toast.makeText(context, "Жми на кнопку",
                    Toast.LENGTH_LONG).show();
        }
    });
    ad.setCancelable(false);
    ad.show();
}

    //Result dialog
    public void resultDialog(){
        context = MainActivity.this;
        String title = "Ваш результат";
        String message = "За 10 секунд вы успели нажать: ";
        String button2String = "Сохранить";
        String button1String = "Отмена";

        ad = new AlertDialog.Builder(context);
        ad.setTitle(title);  // заголовок
        ad.setMessage(message+result+" раз"); // сообщение
        ad.setPositiveButton(button1String, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
                finish();
                Intent intent = new Intent(MainActivity.this, StartActivity.class);
                startActivity(intent);
            }
        });
        ad.setNegativeButton(button2String, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
                saveResult();
                finish();
                Intent intent = new Intent(MainActivity.this, Records.class);
                startActivity(intent);

            }
        });
        ad.setCancelable(false);
        ad.show();
    }

    //Save Points
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        data = (String) ((TextView)findViewById(R.id.textView)).getText();
        String Res = data.toString();
        outState.putString("SaveResult", Res);
    }

    //Restore Poins
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String SaveResult = savedInstanceState.getString("SaveResult");
        Result.setLength(0);
        Result.append(SaveResult);
        TextResult.setText(Result.toString());
        i =  Integer.parseInt(Result.toString());
        pauseDialog();
    }

    //Press button
    OnClickListener listener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            ((TextView)findViewById(R.id.textView)).setText(""+ ++i);
        }
    };


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}



