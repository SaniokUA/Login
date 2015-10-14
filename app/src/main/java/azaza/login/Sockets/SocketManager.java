package azaza.login.Sockets;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import azaza.login.Adapter.ListItemAdapter;
import azaza.login.AuthGoogle.Authorization.GoogleData.UserData;
import azaza.login.Model.ListItemRecords;
import azaza.login.R;
import azaza.login.game.MenuActivity;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

/**
 * Created by Alex on 07.10.2015.
 */
public class SocketManager {

    private Socket mSocket;
    private static final String TAG = "SocketLogs";
    private static SocketManager instance = null;


    private SocketManager() {
        connectSocket();
    }

    public static SocketManager getInstance() {
        if (instance == null) {
            instance = new SocketManager();
        }
        return instance;
    }

    private boolean connectSocket() {
        try {
            mSocket = IO.socket(SocketConst.SOCKET_URL + ":" + SocketConst.SOCKET_PORT);
            mSocket.connect();
            Log.d(TAG, "Socket Connected");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "Error Socket Connection");
            return false;
        }
    }

    public boolean authorization(final Activity activity) {
        JSONObject userData = new JSONObject();
        try {
            userData.put("email", UserData.getEmail());
            userData.put("country", UserData.getCOUNTRY());
            userData.put("sex", UserData.Sex);
            userData.put("name", UserData.userName);
        } catch (JSONException e) {
            Log.d(TAG, "Error Json Socket");
            e.printStackTrace();
        }

        if (UserData.email != null) {
            mSocket.on(SocketConst.AUTHORIZATION_EVENT, new Emitter.Listener() {
                @Override
                public void call(final Object... args) {
                    JSONObject jsonData = (JSONObject) args[0];
                    JSONObject data = null;
                    try {
                        data = jsonData.getJSONObject("data");
                        String userId;
                        userId = data.getString("user_id");
                        UserData.userID = userId;

                        getRank(activity);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            mSocket.emit(SocketConst.AUTHORIZATION_EVENT, userData);
            return true;
        } else {
            Log.d(TAG, "Error Socket Send Auth");
            return false;
        }
    }


    public boolean getRank(final Activity activity) {
        JSONObject userDataRank = new JSONObject();
        try {
            userDataRank.put("user_id", UserData.userID);
        } catch (JSONException e) {
            Log.d(TAG, "Error Json Socket");
            e.printStackTrace();
        }
        if (UserData.userID != null) {
            mSocket.on(SocketConst.GET_RANK_EVENT, new Emitter.Listener() {
                @Override
                public void call(final Object... args) {
                    JSONObject jsonData = (JSONObject) args[0];
                    JSONObject data = null;
                    try {
                        data = jsonData.getJSONObject("data");
                        String userRank;

                        userRank = data.getString("rank");
                        UserData.USER_RANK = userRank;

                        Intent intent = new Intent(activity, MenuActivity.class);
                        activity.startActivity(intent);
                        activity.finish();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            mSocket.emit(SocketConst.GET_RANK_EVENT, userDataRank);
            Log.d(TAG, "Socket Send Rank");
            return true;
        } else {
            Log.d(TAG, "Error Socket Send Rank");
            return false;
        }
    }


    public boolean savePersonalResults() {

        JSONObject userDataResults = new JSONObject();
        try {
            userDataResults.put("account_id", UserData.userID);
            userDataResults.put("score", UserData.RESULT);
            userDataResults.put("speed", UserData.SPEED);
            userDataResults.put("speed_per_sec", UserData.BEST_SPEED);
        } catch (JSONException e) {
            Log.d(TAG, "Error Json Socket SAVE RES");
            e.printStackTrace();
        }

        if (UserData.userID != null) {
            mSocket.on(SocketConst.SAVE_RESULT_EVENT, onSaveListener);
            mSocket.emit(SocketConst.SAVE_RESULT_EVENT, userDataResults);
            Log.d(TAG, "Socket Send Save Res");
            return true;
        } else {
            Log.d(TAG, "Error Socket Send SAVE RES");
            return false;
        }
    }

    private Emitter.Listener onSaveListener = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
                    JSONObject jsonData = (JSONObject) args[0];
                    JSONObject data = null;
                    try {
                        data = jsonData.getJSONObject("data");
                        String userId;
                        String userRank;

                        userId = data.getString("user_id");
                        UserData.userID = userId;
                        userRank = data.getString("rank");
                        UserData.USER_RANK = userRank;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
    };


    public boolean getTopResults(final Activity activity) {
        JSONObject userDataGetResults = new JSONObject();
        try {
            userDataGetResults.put("account_id", UserData.userID);
            userDataGetResults.put("limit", "10");
        } catch (JSONException e) {
            Log.d(TAG, "Error Json Socket SAVE RES");
            e.printStackTrace();
        }

        if (UserData.userID != null) {
            mSocket.on(SocketConst.GET_TOP_RESULTS_EVENT, new Emitter.Listener() {
                @Override
                public void call(final Object... args) {
                    JSONObject jsonData = (JSONObject) args[0];
                    JSONArray recordsData = null;
                    JSONObject results;
                    JSONObject player;
                    try {
                        recordsData = jsonData.getJSONObject("data").getJSONArray("results");
                        UserData.listWorldRecords = new ArrayList<ListItemRecords>();
                        for (int i = 0; i < recordsData.length(); i++) {
                            results = recordsData.getJSONObject(i).getJSONObject("result");
                            player = recordsData.getJSONObject(i).getJSONObject("account");
                            UserData.listWorldRecords.add(get(("" + (1 + i)), player.getString("name"), results.getString("score"), player.getString("country")));
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ListView recordsWorldList = (ListView) activity.findViewById(R.id.listViewWorld);
                                    ListItemAdapter adapter = new ListItemAdapter(activity, UserData.listWorldRecords);
                                    recordsWorldList.setAdapter(adapter);
                                }
                            });
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                private ListItemRecords get(String position, String name, String score, String country) {
                    return new ListItemRecords(position, name, score, country);
                }

            });
            mSocket.emit(SocketConst.GET_TOP_RESULTS_EVENT, userDataGetResults);
            Log.d(TAG, "Socket Send TOP Res");

            return true;
        } else {
            Log.d(TAG, "Error Socket Send TOP RES");
            return false;
        }
    }

    public boolean getPersResults(final Activity activity) {
        JSONObject userDataGetPersResults = new JSONObject();
        try {
            userDataGetPersResults.put("account_id", UserData.userID);
        } catch (JSONException e) {
            Log.d(TAG, "Error Json Socket SAVE RES");
            e.printStackTrace();
        }

        if (UserData.userID != null) {
            mSocket.on(SocketConst.GET_PERS_RESULTS_EVENT, new Emitter.Listener() {
                @Override
                public void call(final Object... args) {
                    JSONObject jsonData = (JSONObject) args[0];
                    JSONArray recordsData = null;
                    JSONObject results;
                    JSONObject player;
                    try {
                        recordsData = jsonData.getJSONObject("data").getJSONArray("results");
                        UserData.listPersonalRecords = new ArrayList<>();

                        for (int i = 0; i < recordsData.length(); i++) {
                            results = recordsData.getJSONObject(i).getJSONObject("result");
                            player = recordsData.getJSONObject(i).getJSONObject("account");
                            UserData.listPersonalRecords.add(get((""), player.getString("name"), results.getString("score"), player.getString("country")));
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ListView recordsPersonalList = (ListView) activity.findViewById(R.id.listViewPersonal);
                                    ListItemAdapter adapter = new ListItemAdapter(activity, UserData.listPersonalRecords);
                                    recordsPersonalList.setAdapter(adapter);
                                }
                            });
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                private ListItemRecords get(String position, String name, String score, String country) {
                    return new ListItemRecords(position, name, score, country);
                }

            });
            mSocket.emit(SocketConst.GET_PERS_RESULTS_EVENT, userDataGetPersResults);
            Log.d(TAG, "Socket Send TOP Res");

            return true;
        } else {
            Log.d(TAG, "Error Socket Send TOP RES");
            return false;
        }
    }


}
