package sample;

import java.sql.Connection;
import java.sql.DriverManager;

public class DataBaseConnection {
    public static Connection get;
    public Connection databaseLink;
    public  Connection getConnection(){
        String databaseName="account";
        String databaseUser="root";
        String databasePassword="chasha2000";
        String url="jdbc:mysql://localhost/"+databaseName;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            databaseLink= DriverManager.getConnection(url,databaseUser,databasePassword);
        }catch (Exception e){
            e.printStackTrace();
            e.getCause();

        }
        return databaseLink;
    }

}
