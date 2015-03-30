package azaza.login.database.mongo.service.implement;

import java.util.List;

import azaza.login.database.mongo.service.core.AServiceClass;
import azaza.login.database.mongo.service.essence.EsAccount;
import azaza.login.database.mongo.service.essence.EsResult;

/**
 * Created by Alex on 23.03.2015.
 */
@AServiceClass(name = "ResultService")
public class ResultService {

    public boolean createResult(EsResult esResult) {
        /*
        DB dataBase = this.getDataBase();
        DBCollection songs = dataBase.getCollection("results");

        BasicDBObject basicDBObject = esResult.coding();
        songs.insert(basicDBObject);
        */

        return true;
    }

    public boolean findResult(EsResult esResult) {
        /*
        DB dataBase = this.getDataBase();
        DBCollection songs = dataBase.getCollection("results");
        BasicDBObject findQuery = new BasicDBObject("RESULTID", esResult.getResultId());
        DBCursor docs = songs.find(findQuery);
        if (docs.hasNext()) {
            DBObject doc = docs.next();
            esResult.decoding(doc);

            return true;
        }
        */
        return false;
    }

    public List<EsResult> findAllResultsForUser(EsAccount esAccount) {
        /*
        DB dataBase = this.getDataBase();
        DBCollection songs = dataBase.getCollection("results");
        BasicDBObject findQuery = new BasicDBObject("USERID", esAccount.getUserId());
        DBCursor docs = songs.find(findQuery);

        List<EsResult> esResults = new ArrayList<>();

        while (docs.hasNext()) {
            DBObject doc = docs.next();

            EsResult esResult = new EsResult(esAccount.getUserId());
            esResult.decoding(doc);

            esResults.add(esResult);

        }

        return esResults;*/
        return null;
    }

    public boolean removeResult(EsResult esResult) {
        /*
        DB dataBase = this.getDataBase();
        DBCollection songs = dataBase.getCollection("results");
        BasicDBObject findQuery = new BasicDBObject("RESULTID", esResult.getResultId());
        DBCursor docs = songs.find(findQuery);
        if (docs.hasNext()) {
            DBObject doc = docs.next();
            songs.remove(doc);

            return true;
        }*/
        return false;
    }
}
