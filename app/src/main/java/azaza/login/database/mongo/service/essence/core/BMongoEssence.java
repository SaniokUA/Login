package azaza.login.database.mongo.service.essence.core;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * Created by Alex on 23.03.2015.
 */
public abstract class BMongoEssence {

    public abstract BasicDBObject coding();

    public abstract void decoding(DBObject doc);

    public void decoding(Object object){
        DBObject doc=(DBObject)object;
        decoding(doc);
    }

}
