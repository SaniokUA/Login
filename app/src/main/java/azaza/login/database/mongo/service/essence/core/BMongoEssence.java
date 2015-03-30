package azaza.login.database.mongo.service.essence.core;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import org.json.JSONObject;

/**
 * Created by Alex on 23.03.2015.
 */
public abstract class BMongoEssence {

    protected abstract JSONObject toJson();

    protected abstract void parse(JSONObject json);

    public JSONObject coding() {
        return toJson();
    }

    public void decoding(JSONObject json) {
        decoding(json);
    }

}
