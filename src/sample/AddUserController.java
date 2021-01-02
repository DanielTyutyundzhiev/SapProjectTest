package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.Statement;

public class AddUserController {
    @FXML
    private TextField firstnameTF;
    @FXML
    private TextField lastnameTF;
    @FXML
    private TextField emailTF;
    @FXML
    private Label messageLabel;

    public void addUser() throws IOException {
        String fisrtName=firstnameTF.getText();
        String lastName=lastnameTF.getText();
        String email=emailTF.getText();
        DataBaseConnection connection=new DataBaseConnection();
        Connection connectDB=connection.getConnection();
        File file=new File("E:\\SAP_Project\\src\\sales_repID.txt");
        Path path=Path.of("E:\\SAP_Project\\src\\sales_repID.txt");
        String id= Files.readString(path);
        System.out.println(id);
        int srID=Integer.parseInt(id);


        try{
            Statement statement=connectDB.createStatement();
            statement.executeUpdate("INSERT INTO users(user_id,first_nameU,last_nameU,email) VALUES ('"+id+"','"+fisrtName+"','"+lastName+"','"+email+"')");
            messageLabel.setText("User added successfully");

        }catch (Exception e){
            System.out.println("Error with INSERT QUERY");
            e.printStackTrace();
        }
    }
}
