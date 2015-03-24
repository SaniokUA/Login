package azaza.login.database.mongo.service.essence;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import java.util.UUID;

import azaza.login.database.mongo.service.ServiceContainer;
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
    public BasicDBObject coding() {
        BasicDBObject account = new BasicDBObject();
        account.put("USERID", userId.toString());
        account.put("EMAIL", email);
        account.put("LOGIN", login);
        account.put("COUNTRY", country);
        account.put("BESTRESULT", bestResult.getResultId());

        return account;
    }

    @Override
    public void decoding(DBObject doc) {
        userId = UUID.fromString((String) doc.get("USERID"));
        email = (String) doc.get("EMAIL");
        login = (String) doc.get("LOGIN");
        country = (String) doc.get("COUNTRY");
        bestResult = new EsResult(userId);
        bestResult.setResultId(UUID.fromString((String) doc.get("BESTRESULT")));
        ResultService resultService = ServiceContainer.getInstance().getService(ResultService.class);
        resultService.findResult(bestResult);
    }
    // </editor-fold>
}
