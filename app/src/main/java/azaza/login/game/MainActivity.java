package azaza.login.game;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.RotateAnimation;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import azaza.login.R;
import azaza.login.database.DB;


public class MainActivity extends StartActivity {

    TextView textView;
    ImageButton button;
    private int i = 0;
    private double k = 0;
    int count = 0;
    public int bestRes = 0;
    TextView TextResult;
    TextView PressToStart, resultText;
    LinearLayout startLayout;
    LinearLayout resultLayout;
    AlertDialog.Builder ad;
    Context context;
    Chronometer chronometer;
    ImageView Arrow;
    ImageView smallArow;
    int sec = 0;
    long start = 0;
    DB db;
    public String result;
    float smallArrowRotateDeg = -38;
    float duration;


    private Handler process = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        TextResult = (TextView) findViewById(R.id.textView);
        textView = (TextView) findViewById(R.id.textView);
        PressToStart = (TextView) findViewById(R.id.PressToStart);
        startLayout = (LinearLayout) findViewById(R.id.startLayout);
        button = (ImageButton) findViewById(R.id.button);
        Arrow = (ImageView) findViewById(R.id.arrow);
        smallArow = (ImageView) findViewById(R.id.smallarrow);

        button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                ((TextView) findViewById(R.id.textView)).setText("" + ++i);
                count++;
                k = k + 3;
                maineRotate((float) (-170 + k));
                result = (String) ((TextView) findViewById(R.id.textView)).getText();

            }
        });

        chronometer = (Chronometer) findViewById(R.id.chronometer);
        startLayout.setVisibility(View.VISIBLE);


        smalleRotate(smallArrowRotateDeg);
        maineRotate(-170);


    }

    //Game Time
    public void chron() {
        start = SystemClock.elapsedRealtime();
        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                long myElapsedMillis = SystemClock.elapsedRealtime() - chronometer.getBase();
                sec = (int) (myElapsedMillis / 1000);
                if (myElapsedMillis < 8000) {
                    chronometer.setTextColor(0xFF00FF00);
                } else {
                    chronometer.setTextColor(0xffff0000);
                }

                if (myElapsedMillis > 9999) {
                    process.removeCallbacks(newProcess);
//                    resultLayout.setVisibility(View.VISIBLE);
                    button.setClickable(false);
                    result = (String) ((TextView) findViewById(R.id.textView)).getText();
                    //resultDialog();
                    chronometer.stop();
                }
            }
        });
    }

    public void saveResult() {

        db = new DB(this);
        db.open();
        double time = 10;
        double speed;
        int newRes = Integer.parseInt(result);
        speed = newRes / time;
        Math.round(speed);
        db.addRec("User", newRes, speed);
        db.close();

    }

    //Result dialog
    public void resultDialog() {
        context = MainActivity.this;
        String title = "Ваш результат";
        String message = "За 10 секунд вы успели нажать: ";
        String button2String = "Сохранить";
        String button1String = "Отмена";

        ad = new AlertDialog.Builder(context);
        ad.setTitle(title);  // заголовок

        ad.setMessage(message + result + " раз."); // сообщение
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


    //Big arrow animation
    private void maineRotate(float degree) {
        final RotateAnimation rotateAnim = new RotateAnimation(degree, degree,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.94f);
        rotateAnim.setDuration(0);
        rotateAnim.setFillAfter(true);
        Arrow.startAnimation(rotateAnim);
    }

    //Small arrow animation
    public void smalleRotate(float deg) {

        final RotateAnimation rotateAnim = new RotateAnimation(smallArrowRotateDeg, deg,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.94f);
        rotateAnim.setDuration(500);
        rotateAnim.setFillAfter(true);
        smallArrowRotateDeg = deg;
        smallArow.startAnimation(rotateAnim);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    //start game Tap and timer
    public void onStartGame(View view) {
        startLayout.setVisibility(View.GONE);
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();
        chron();
        //Start after Tap
        process.postDelayed(newProcess, 0);
    }

    //Small arrow animation
    private Runnable newProcess = new Runnable() {
        public void run() {
            duration = count / ((float) (SystemClock.elapsedRealtime() - start) / 1000);
            if (duration > 1) {
                smalleRotate((float) (-38 + (duration - 1) * 33));
            }
            bestRes = count > bestRes ? count : bestRes;
            process.postDelayed(this, 1000 / 60);
        }
    };



}

