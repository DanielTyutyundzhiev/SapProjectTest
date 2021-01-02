package sample;

public class DeleteSpModel {
    String id;
    String firstName;
    String lastNAme;
    String username;
    String password;

    public DeleteSpModel(String id, String firstName, String lastNAme, String username,String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastNAme = lastNAme;
        this.username = username;
        this.password=password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastNAme() {
        return lastNAme;
    }

    public void setLastNAme(String lastNAme) {
        this.lastNAme = lastNAme;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
