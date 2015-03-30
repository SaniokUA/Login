package azaza.login.database.mongo.util;

/**
 * Created by Alex on 23.03.2015.
 */

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

import java.net.URISyntaxException;


public class Connection {
    private final String socketURI = "http://192.168.10.22:3001";
    private static volatile Connection instance;
    private Socket socket;

    private Connection() {
    }

    public static Connection getInstance() {
        Connection localConnection = instance;
        if (localConnection == null) {
            synchronized (Connection.class) {
                localConnection = instance;
                if (localConnection == null) {
                    instance = localConnection = new Connection();
                }
            }
        }
        return instance;
    }

    private Socket ConnectionToSocket() throws URISyntaxException {
        return IO.socket("http://192.168.10.22:3001");
    }

    public Socket getSocket() {
        if (socket != null) {
            try {
                socket = ConnectionToSocket();
                socket.connect();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
        return socket;
    }
}