package azaza.login.game;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

import android.view.animation.RotateAnimation;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import azaza.login.DB;
import azaza.login.R;


import java.util.Timer;
import java.util.TimerTask;

import static java.lang.Thread.sleep;


public class MainActivity extends StartActivity {

    TextView textView;
    ImageButton button;
    private int i = 0;
    private double k = 0;
    int count = 0;
    int sum = 0;
    int bestRes = 0;
    double step = 0;
    int[] bestSpeed;
    TextView TextResult;
    TextView PressToStart;
    AlertDialog.Builder ad;
    Context context;
    Chronometer chronometer;
    ImageView Arrow;
    ImageView smallArow;
    final String LOG_TAG = "myLogs";

    DB db;
    public String result;

    Timer timer = new Timer();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        TextResult = (TextView) findViewById(R.id.textView);
        textView = (TextView) findViewById(R.id.textView);
        PressToStart = (TextView) findViewById(R.id.PressToStart);

        button = (ImageButton) findViewById(R.id.button);

        Arrow = (ImageView) findViewById(R.id.arrow);
        smallArow = (ImageView) findViewById(R.id.smallarrow);

        button.setOnClickListener(listener);

        PressToStart.setVisibility(View.INVISIBLE);
        chronometer = (Chronometer) findViewById(R.id.chronometer);
        chronometer.start();
        chron();

        smalleRotate(-38);
        maineRotate(-170);

        onSeconds();


    }

    //Game Time
    public void chron() {
        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                long myElapsedMillis = SystemClock.elapsedRealtime()
                        - chronometer.getBase();
                if (myElapsedMillis < 8000) {
                    chronometer.setTextColor(0xFF00FF00);
                } else {
                    chronometer.setTextColor(0xffff0000);
                }

                if (myElapsedMillis > 9999) {
                    button.setClickable(false);
                    result = (String) ((TextView) findViewById(R.id.textView)).getText();
                    resultDialog();
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
        ad.setMessage(message + result + " раз"); // сообщение
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

    //Press button
    OnClickListener listener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            ((TextView) findViewById(R.id.textView)).setText("" + ++i);
            count++;
            k = k + 3;
            maineRotate((float) (-170 + k));
            step = step + 30;
            smalleRotate((float) (-38 + step));
        }
    };


    private void maineRotate(float degree) {
        final RotateAnimation rotateAnim = new RotateAnimation(degree, degree + 5,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.94f);
        rotateAnim.setDuration(0);
        rotateAnim.setFillAfter(true);
        Arrow.startAnimation(rotateAnim);
    }

    private void smalleRotate(float degree) {
        final RotateAnimation rotateAnim = new RotateAnimation(degree, degree,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.94f);
        rotateAnim.setDuration(1000L);
        rotateAnim.setFillAfter(true);
        smallArow.startAnimation(rotateAnim);
    }

    public void onSeconds() {
        timer.schedule(new Task(), 1000);
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


    private class Task extends TimerTask {
        @Override
        public void run() {
            for (int sec = 0; sec <= 10; sec++) {
                //bestSpeed[sec]=count;
                sum = sum + count;
                Log.d(LOG_TAG, String.valueOf(count));
                count = 0;
                step = 0;
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (sec == 10) {
                    Log.d(LOG_TAG, String.valueOf(sum));
                }
            }

        }
    }


    public int bestSpeed(int array[]) {
        int lengh = bestSpeed.length;
        for (int s = 0; s <= lengh; s++) {
            bestRes = bestSpeed[0];
            if (bestSpeed[s] > bestRes) {
                bestRes = bestSpeed[s];
            }
        }
        return bestRes;
    }
}



