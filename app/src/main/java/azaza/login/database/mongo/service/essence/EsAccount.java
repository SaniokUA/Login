package azaza.login.database.mongo.service.essence;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

import azaza.login.database.mongo.service.essence.core.BMongoEssence;
import azaza.login.database.mongo.service.implement.ResultService;

/**
 * Created by Alex on 23.03.2015.
 */
public class EsAccount extends BMongoEssence {

    private UUID userId;
    private String email;
    private String login;
    private String country;
    private EsResult bestResult;
    private boolean success;

    // <editor-fold defaultstate="collapsed" desc="Getter / Setter">
    public UUID getUserId() {
        return userId;
    }

    public void setUserId() {
        this.userId = UUID.randomUUID();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public EsResult getBestResult() {
        return bestResult;
    }

    public void setBestResult(EsResult bestResult) {
        this.bestResult = bestResult;
    }
    // </editor-fold>

    public EsAccount() {
        this.setUserId();
        bestResult = new EsResult(userId);
    }

    // <editor-fold defaultstate="collapsed" desc="Coding / Decoding">
    @Override
    protected JSONObject toJson() {
        JSONObject json = new JSONObject();

        try {
            //json.put("USERID", userId.toString());
            json.put("EMAIL", email);
            json.put("LOGIN", login);
            json.put("COUNTRY", country);
            json.put("BESTRESULT", bestResult.getResultId());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return json;
    }

    @Override
    protected void parse(JSONObject json) {
        try {
            userId = UUID.fromString((String) json.get("USERID"));
            success = (boolean) json.get("SUCCESS");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    // </editor-fold>
}
