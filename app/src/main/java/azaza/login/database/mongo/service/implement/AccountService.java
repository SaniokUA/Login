package azaza.login.database.mongo.service.implement;

import azaza.login.database.mongo.service.core.AServiceClass;
import azaza.login.database.mongo.service.core.AServiceMethod;
import azaza.login.database.mongo.service.core.BServic;
import azaza.login.database.mongo.service.essence.EsAccount;

/**
 * Created by Alex on 23.03.2015.
 */
@AServiceClass(name = "AccountService")
public class AccountService extends BServic {

    @AServiceMethod(name = "createAccount")
    public boolean createAccount(EsAccount esAccount) {
        /*
        DB dataBase = this.getDataBase();
        DBCollection songs = dataBase.getCollection("accounts");
        BasicDBObject findQuery = new BasicDBObject("EMAIL", esAccount.getEmail());
        DBCursor docs = songs.find(findQuery);
        if (!docs.hasNext()) {
            BasicDBObject basicDBObject = esAccount.coding();
            songs.insert(basicDBObject);

            return true;
        }*/
        return false;
    }

    @AServiceMethod(name = "verifyAccount")
    public boolean verifyAccount(EsAccount esAccount) {
        int i = 0;
        /*
        DB dataBase = this.getDataBase();
        DBCollection songs = dataBase.getCollection("accounts");
        BasicDBObject findQuery = new BasicDBObject("USERID", esAccount.getUserId());
        DBCursor docs = songs.find(findQuery);
        if (docs.hasNext()) {
            DBObject doc = docs.next();
            esAccount.decoding(doc);

            return true;
        }
        */
        return false;
    }

    @AServiceMethod(name = "removeAccount")
    public boolean removeAccount(EsAccount esAccount) {
        /*
        DB dataBase = this.getDataBase();
        DBCollection songs = dataBase.getCollection("accounts");
        BasicDBObject findQuery = new BasicDBObject("USERID", esAccount.getUserId());
        DBCursor docs = songs.find(findQuery);
        if (docs.hasNext()) {
            DBObject doc = docs.next();
            songs.remove(doc);

            return true;
        }
        */
        return false;
    }
}
