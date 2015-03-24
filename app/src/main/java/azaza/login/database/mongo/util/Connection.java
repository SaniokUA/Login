package azaza.login.database.mongo.util;

/**
 * Created by Alex on 23.03.2015.
 */

import android.os.AsyncTask;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Connection extends AsyncTask<Void, Void, Void>{
    private final String mongoClientURI = "mongodb://android:android@ds061747.mongolab.com:61747/android";
    private static volatile Connection instance;
    private DB dataBase;

    private Connection() throws UnknownHostException {
        dataBase = ConnectionToDataBase();
    }

    /**
     * Override this method to perform a computation on a background thread. The
     * specified parameters are the parameters passed to {@link #execute}
     * by the caller of this task.
     * <p/>
     * This method can call {@link #publishProgress} to publish updates
     * on the UI thread.
     *
     * @param params The parameters of the task.
     * @return A result, defined by the subclass of this task.
     * @see #onPreExecute()
     * @see #onPostExecute
     * @see #publishProgress
     */
    @Override
    protected Void doInBackground(Void... params) {
        instance=this;
        return null;
    }

     public static Connection getConnection() {
        Connection localConnection = instance;
        if (localConnection == null) {
            synchronized (Connection.class) {
                localConnection = instance;
                if (localConnection == null) {
                    try {
                        localConnection = new Connection();
                        localConnection.execute();
                    } catch (UnknownHostException ex) {
                        Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
                    }
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