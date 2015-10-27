package azaza.login.game;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;

import azaza.login.R;
import azaza.login.Sockets.SocketManager;
import azaza.login.Temp.TempLocal;

/**
 * Created by Шурик on 28.02.2015.
 */
public class RecordsActivity extends GameActivity {
    Activity activity;
    SocketManager socketManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);
        TempLocal.setRecordsActivity(this);
        socketManager = SocketManager.getInstance();
        socketManager.getTopResults();
        socketManager.getPersResults();
        activity = this;
        tabInit();

    }



    public void onMenu(View view) {
        finish();
        Intent intent = new Intent(RecordsActivity.this, MenuActivity.class);
        startActivity(intent);
    }

    public void tabInit() {
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

        for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
            tabHost.getTabWidget().getChildAt(0).setBackgroundResource(R.drawable.tab_selector_left);
            tabHost.getTabWidget().getChildAt(1).setBackgroundResource(R.drawable.tab_selector_right);

        }

        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            public void onTabChanged(String tabId) {
                if (tabId.equals("World")) {

                } else {

                }

            }
        });
    }


}
