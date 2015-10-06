package azaza.login.game;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleCursorAdapter;
import android.widget.TabHost;
import android.widget.TextView;

import azaza.login.R;
import azaza.login.database.DB;

/**
 * Created by Шурик on 28.02.2015.
 */
public class Records extends MainActivity {

    TextView ResultList;
    ListView lvData;
    DB db;
    SimpleCursorAdapter scAdapter;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.records);

        TabHost tabHost = (TabHost) findViewById(R.id.tabHost);
        tabHost.setup();

        TabHost.TabSpec tabSpec = tabHost.newTabSpec("World");
        tabSpec.setContent(R.id.world);
        tabSpec.setIndicator("World");
        tabHost.addTab(tabSpec);


        tabSpec = tabHost.newTabSpec("Personal");
        tabSpec.setContent(R.id.personal);
        tabSpec.setIndicator("Personal");
        tabHost.addTab(tabSpec);

        getResults();


        final Button btnOpenPopup = (Button) findViewById(R.id.openpopup);
        btnOpenPopup.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                LayoutInflater layoutInflater = (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = layoutInflater.inflate(R.layout.popup, null);
                final PopupWindow popupWindow = new PopupWindow(popupView, FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
                Button btnDismiss = (Button) popupView.findViewById(R.id.dismiss);
                btnDismiss.setOnClickListener(new Button.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        popupWindow.dismiss();
                    }
                });

                popupWindow.showAsDropDown(btnOpenPopup, 0, 0);

            }
        });
    }


    public void getResults() {
        textView = (TextView) findViewById(R.id.textView2);
        db = new DB(this);
        db.open();
        textView.setText(textView.getText().toString() + "\n" + "    №    " + "Name     " + "Results   " + "Speed" + "\n");


        Cursor cursor = db.getAllData();
        if (cursor != null) {
            cursor.moveToFirst();
            if (cursor.moveToFirst()) {
                int i = 1;
                do {

                    textView.setText(textView.getText().toString() + "\n" + "    " +
                            i++ + ")      " + cursor.getString(1) + "          " + cursor.getString(2) + "            " + cursor.getString(3));
                } while (cursor.moveToNext());
            }
        }
        db.close();

    }

    public void onMenu(View view) {
        finish();
        Intent intent = new Intent(Records.this, StartActivity.class);
        startActivity(intent);
    }


}
