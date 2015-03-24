package azaza.login.database.mongo.service.core;

import android.os.AsyncTask;

import com.mongodb.DB;

import azaza.login.database.mongo.util.Connection;

/**
 * Created by Alex on 23.03.2015.
 */
public abstract class BService {

    private Connection connection;


    public BService() {
        this.connection = Connection.getConnection();
    }

    protected DB getDataBase() {
        return connection.getDB();
    }
}
