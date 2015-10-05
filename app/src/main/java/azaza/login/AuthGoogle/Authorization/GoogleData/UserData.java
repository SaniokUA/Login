package azaza.login.AuthGoogle.Authorization.GoogleData;


public class UserData {

    public static String userID;
    public static String userName;
    public static String firstName;
    public static String lastName;
    public static String email;
    public static String Sex;
    public static String SocketId;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

}
