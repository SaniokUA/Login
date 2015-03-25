/*
package azaza.login.my_lib;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import azaza.login.R;


public class lib {
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

    //Pause dialog
    public void pauseDialog() {
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
}
*/