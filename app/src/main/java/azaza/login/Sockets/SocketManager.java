package azaza.login.Sockets;

import android.app.Activity;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import azaza.login.AuthGoogle.Authorization.GoogleData.UserData;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

/**
 * Created by Alex on 07.10.2015.
 */
public class SocketManager {

    private Socket mSocket;
    private Activity activity;
    private static final String TAG = "SocketLogs";

    public SocketManager(Activity activity) {
        this.activity = activity;
        connectSocket();
        authorization();
    }

    public boolean connectSocket() {
        try {
            mSocket = IO.socket(SocketConst.SOCKET_URL + ":" + SocketConst.SOCKET_PORT);
            mSocket.connect();
            Log.d(TAG, "Socket Connected");
            return true;
        } catch (URISyntaxException e) {
            e.printStackTrace();
            Log.d(TAG, "Error Socket Connection");
            return false;
        }
    }

    private boolean authorization() {
        JSONObject userData = new JSONObject();
        try {
            userData.put("email", UserData.email);
            userData.put("country", UserData.COUNTRY);
            userData.put("sex", UserData.Sex);
            userData.put("name", UserData.userName);
        } catch (JSONException e) {
            Log.d(TAG, "Error Json Socket");
            e.printStackTrace();
        }
        if (UserData.email!=null) {
            mSocket.emit(SocketConst.AUTHORIZATION_EVENT, userData);
            Log.d(TAG, "Socket Send Auth");
            mSocket.on(SocketConst.AUTHORIZATION_EVENT, onNewMessage);
            return true;
        }
        else{
            Log.d(TAG, "Error Socket Send Auth");
            return false;
        }
    }

    private Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    data.length();
//                    String username;
//                    String message;
//                    try {
//                        username = data.getString("username");
//                        message = data.getString("message");
//                    } catch (JSONException e) {
//                        return;
//                    }
                }
            });
        }
    };

}
