package models;

public class User {

    public enum UserRole {
        ADMIN,
        CASHIER,
        CHEF,
        CUSTOMER,
        WAITER,
    }

    private Integer  userId;
    private UserRole userRole;
    private String   userName;
    private String   userEmail;
    private String   userPassword;

    public Integer getUserId() {
        return userId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserId(Integer _id) {
        this.userId = _id;
    }

    public void setUserEmail(String _userEmail) {
        this.userEmail = _userEmail;
    }

    public void setUserName(String _userName) {
        this.userName = _userName;
    }

    public void setUserPassword(String _userPassword) {
        this.userPassword = _userPassword;
    }

    public void setUserRole(UserRole _userRole) {
        this.userRole = _userRole;
    }

}
