package azaza.login.database.mongo.service.core;

import azaza.login.database.mongo.util.Connection;

/**
 * Created by Alex on 26.03.2015.
 */
public class BServic {
    private Connection connection;

    public BServic() {
        this.connection = Connection.getInstance();
    }
}
