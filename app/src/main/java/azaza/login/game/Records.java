package azaza.login.game;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TabHost;
import android.widget.TextView;

import azaza.login.DB;
import azaza.login.R;

/**
 * Created by Шурик on 28.02.2015.
 */
public class Records extends MainActivity{

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
    }


    public void getResults(){
        textView = (TextView)findViewById(R.id.textView2);
        db = new DB(this);
        db.open();
        textView.setText(textView.getText().toString()+ "\n" + "    №    " + "Name     "  + "Results   "  + "Speed" + "\n");


        Cursor cursor=db.getAllData();
        if(cursor!=null){
            cursor.moveToFirst();
            if (cursor.moveToFirst()) {
                int i=1;
                do {

                    textView.setText(textView.getText().toString()+ "\n" + "    " +
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
