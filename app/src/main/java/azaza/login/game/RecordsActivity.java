package azaza.login.game;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import azaza.login.Adapter.ListItemAdapter;
import azaza.login.Model.ListItemRecords;
import azaza.login.R;

/**
 * Created by Шурик on 28.02.2015.
 */
public class RecordsActivity extends GameActivity {

    ListView recordsList;
    List<ListItemRecords> data;
    ListItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);
        tabInit();

        recordsList = (ListView) findViewById(R.id.listViewWorld);
        List<ListItemRecords> list = new ArrayList<ListItemRecords>();

        for(int i=0; i<10; i++) {
            list.add(get("1", " Alex Sasha", "100", "ua"));
        }
        adapter = new ListItemAdapter(this, list);
        recordsList.setAdapter(adapter);
    }

    public ListItemRecords get(String position, String userName, String result, String country) {
        return new ListItemRecords(position, userName, result, country);
    }

    public void onMenu(View view) {
        finish();
        Intent intent = new Intent(RecordsActivity.this, MenuActivity.class);
        startActivity(intent);
    }

    public void tabInit(){
        TabHost tabHost = (TabHost) findViewById(R.id.tabHost);
        tabHost.setup();


        TabHost.TabSpec tabSpec = tabHost.newTabSpec("World");
        tabSpec.setContent(R.id.world);
        tabSpec.setIndicator("World", getResources().getDrawable(R.drawable.active_tab_left));
        View v = getLayoutInflater().inflate(R.layout.tab_header, null);
        ((TextView) v.findViewById(R.id.textViewTabHead)).setText("World");
        tabSpec.setIndicator(v);
        tabHost.addTab(tabSpec);


        tabSpec = tabHost.newTabSpec("Personal");
        tabSpec.setContent(R.id.personal);
        tabSpec.setIndicator("Personal", getResources().getDrawable(R.drawable.active_tab_right));
        View ve = getLayoutInflater().inflate(R.layout.tab_header, null);
        ((TextView) ve.findViewById(R.id.textViewTabHead)).setText("Personal");
        tabSpec.setIndicator(ve);
        tabHost.addTab(tabSpec);

        tabHost.setCurrentTabByTag("World");

        for(int i=0;i<tabHost.getTabWidget().getChildCount();i++)
        {
            tabHost.getTabWidget().getChildAt(0).setBackgroundResource(R.drawable.tab_selector_left);
            tabHost.getTabWidget().getChildAt(1).setBackgroundResource(R.drawable.tab_selector_right);

        }

        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            public void onTabChanged(String tabId) {

            }
        });
    }


}
