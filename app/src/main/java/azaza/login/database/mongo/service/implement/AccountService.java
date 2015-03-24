package azaza.login.database.mongo.service.implement;

import android.os.AsyncTask;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import azaza.login.database.mongo.service.core.BService;
import azaza.login.database.mongo.service.essence.EsAccount;

/**
 * Created by Alex on 23.03.2015.
 */
public class AccountService extends BService {

    public boolean createAccount(EsAccount esAccount) {
        DB dataBase = this.getDataBase();
        DBCollection songs = dataBase.getCollection("accounts");
        BasicDBObject findQuery = new BasicDBObject("EMAIL", esAccount.getEmail());
        DBCursor docs = songs.find(findQuery);
        if (docs.hasNext()) {
            BasicDBObject basicDBObject = esAccount.coding();
            songs.insert(basicDBObject);

            return true;
        }
        return false;
    }

    public boolean verifyAccount(EsAccount esAccount) {
        DB dataBase = this.getDataBase();
        DBCollection songs = dataBase.getCollection("accounts");
        BasicDBObject findQuery = new BasicDBObject("USERID", esAccount.getUserId());
        DBCursor docs = songs.find(findQuery);
        if (docs.hasNext()) {
            DBObject doc = docs.next();
            esAccount.decoding(doc);

            return true;
        }
        return false;
    }

    public boolean removeAccount(EsAccount esAccount) {
        DB dataBase = this.getDataBase();
        DBCollection songs = dataBase.getCollection("accounts");
        BasicDBObject findQuery = new BasicDBObject("USERID", esAccount.getUserId());
        DBCursor docs = songs.find(findQuery);
        if (docs.hasNext()) {
            DBObject doc = docs.next();
            songs.remove(doc);

            return true;
        }
        return false;
    }
}
