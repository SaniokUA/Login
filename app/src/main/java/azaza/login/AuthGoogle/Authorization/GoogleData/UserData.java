package azaza.login.AuthGoogle.Authorization.GoogleData;


import java.util.ArrayList;
import java.util.List;

import azaza.login.Model.ListItemRecords;

public class UserData {

    public static int FIRST_START = 0;
    public static String userID = null;
    public static String userName = "";
    public static String firstName="";
    public static String lastName="";
    public static String email =null;
    public static String Sex = "";
    public static String SocketId;
    public static String COUNTRY = "";
    public static String AGE="";
    public static String RESULT = "0";
    public static String SPEED = "0";
    public static String BEST_SPEED = "0";
    public static String USER_RANK = "None";
    public static List<ListItemRecords> listWorldRecords =  new ArrayList<>();
    public static List<ListItemRecords> listPersonalRecords = new ArrayList<>();


    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public static void setUserName(String userName) {
        UserData.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public static void setFirstName(String firstName) {
        UserData.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public static void setLastName(String lastName) {
        UserData.lastName = lastName;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        UserData.email = email;
    }

    public String getSex() {
        return Sex;
    }

    public void setPictureURL(String Sex) {
        this.Sex = Sex;
    }

    public String getSocketId() {
        return SocketId;
    }

    public void setSocketId(String SocketId) {
        this.SocketId = SocketId;
    }
    public static String getBestSpeed() {
        return BEST_SPEED;
    }

    public static void setBestSpeed(String bestSpeed) {
        BEST_SPEED = bestSpeed;
    }

    public static String getSPEED() {
        return SPEED;
    }

    public static void setSPEED(String SPEED) {
        UserData.SPEED = SPEED;
    }

    public static String getRESULT() {
        return RESULT;
    }

    public static void setRESULT(String RESULT) {
        UserData.RESULT = RESULT;
    }

    public static String getAGE() {
        return AGE;
    }

    public static void setAGE(String AGE) {
        UserData.AGE = AGE;
    }

    public static String getCOUNTRY() {
        return COUNTRY;
    }

    public static void setCOUNTRY(String COUNTRY) {
        UserData.COUNTRY = COUNTRY;
    }

    public static void setSex(String sex) {
        Sex = sex;
    }

    public static String getUserRank() {
        return USER_RANK;
    }

    public static void setUserRank(String userRank) {
        USER_RANK = userRank;
    }

}
