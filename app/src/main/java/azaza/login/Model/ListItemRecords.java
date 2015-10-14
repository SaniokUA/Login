package azaza.login.Model;

/**
 * Created by Alex on 05.06.2015.
 */
public class ListItemRecords {

    String position;
    String userName;
    String country;
    String result;

    public ListItemRecords(String position, String userName, String result, String country) {

        this.position = position;
        this.userName = userName;
        this.result = result;
        this.country = country;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}

