package azaza.login.database.mongo.util;

/**
 * Created by Alex on 23.03.2015.
 */

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;


public class Connection {
    private final String mongoClientURI = "mongodb://android:android@ds061747.mongolab.com:61747/android";
    private static volatile Connection instance;
    private DB dataBase;

    private Connection() {
        dataBase = ConnectionToDataBase();
    }

    public static Connection getConnection() {
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

    private DB ConnectionToDataBase() {
        MongoClientURI uri = new MongoClientURI(mongoClientURI);
        MongoClient client = new MongoClient(uri);
        return client.getDB(uri.getDatabase());
    }

    public DB getDB() {
        if (dataBase != null)
            dataBase = ConnectionToDataBase();
        return dataBase;
    }
}