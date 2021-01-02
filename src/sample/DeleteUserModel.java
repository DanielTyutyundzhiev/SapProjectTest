package sample;

public class DeleteUserModel {
    String userID1;
    String salesRepID1;
    String firstName1;
    String lastName1;
    String email;

    public DeleteUserModel(String userID1, String salesRepID1, String firstName1, String lastName1, String email) {
        this.userID1 = userID1;
        this.salesRepID1 = salesRepID1;
        this.firstName1 = firstName1;
        this.lastName1 = lastName1;
        this.email = email;
    }

    public String getUserID1() {
        return userID1;
    }

    public void setUserID1(String userID1) {
        this.userID1 = userID1;
    }

    public String getSalesRepID1() {
        return salesRepID1;
    }

    public void setSalesRepID1(String salesRepID1) {
        this.salesRepID1 = salesRepID1;
    }

    public String getFirstName1() {
        return firstName1;
    }

    public void setFirstName1(String firstName1) {
        this.firstName1 = firstName1;
    }

    public String getLastName1() {
        return lastName1;
    }

    public void setLastName1(String lastName1) {
        this.lastName1 = lastName1;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
