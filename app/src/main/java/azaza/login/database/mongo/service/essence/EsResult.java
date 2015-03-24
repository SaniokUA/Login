package azaza.login.database.mongo.service.essence;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import java.util.UUID;

import azaza.login.database.mongo.service.essence.core.BMongoEssence;

/**
 * Created by Alex on 23.03.2015.
 */
public class EsResult extends BMongoEssence {
    private UUID resultId;
    private UUID userId;
    private String dataTime;
    private int counter;
    private int lostTime;

    // <editor-fold defaultstate="collapsed" desc="Getter / Setter">
    public UUID getResultId() {
        return resultId;
    }

    public void setResultId(UUID resultId) {
        this.resultId = resultId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public int getLostTime() {
        return lostTime;
    }

    public void setLostTime(int lostTime) {
        this.lostTime = lostTime;
    }

    public String getDataTime() {
        return dataTime;
    }

    public void setDataTime(String dataTime) {
        this.dataTime = dataTime;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }
    // </editor-fold>

    public EsResult(UUID userID){
        this.resultId= UUID.randomUUID();
        this.userId=userID;
        dataTime= "23.03.2015";
        counter=0;
        lostTime=0;
    }

    @Override
    public BasicDBObject coding() {
        BasicDBObject result = new BasicDBObject();

        result.put("RESULTID", resultId.toString());
        result.put("USERID", userId.toString());
        result.put("DATATIME", dataTime);
        result.put("COUNTER", counter);
        result.put("LOSTTIME", lostTime);

        return result;
    }

    @Override
    public void decoding(DBObject doc) {
        resultId = UUID.fromString((String) doc.get("RESULTID"));
        userId = UUID.fromString((String) doc.get("USERID"));
        dataTime = (String) doc.get("DATATIME");
        counter = (int) doc.get("COUNTER");
        lostTime = (int) doc.get("LOSTTIME");
    }
}
