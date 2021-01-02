package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddSpController {
    @FXML
    private Button closeButton;
    @FXML
    private Button addSPButton;
    @FXML
    private Label messageLabel;
    @FXML
    private PasswordField passwordTF;
    @FXML
    private PasswordField repeatpwTF;
    @FXML
    private Label confirmPasswordMessage;
    @FXML
    private TextField firstnameTF;
    @FXML
    private TextField lastnameTF;
    @FXML
    private TextField usernameTF;



    public void addSPOnAction(ActionEvent event){
        if(isValidPassword(passwordTF.getText())){
            if(passwordTF.getText().equals(repeatpwTF.getText())){
                addSP();
            }else{
                confirmPasswordMessage.setText("Password does not match!");
            }
        }
        else{
            confirmPasswordMessage.setText("Password does not match the criteria");
        }
    }


    public void closeButtonOnAction(ActionEvent event){
        Stage stage=(Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    public void addSP(){
        DataBaseConnection connection=new DataBaseConnection();
        Connection connectDB=connection.getConnection();
        String firstName=firstnameTF.getText();
        String lastName=lastnameTF.getText();
        String userName=usernameTF.getText();
        String password=passwordTF.getText();

        String insertFields="INSERT INTO sales_representative(firstName,secondName,username,password) VALUES ('"+firstName +"','"+ lastName+ "','"+ userName +"','"+ password+ "')";
        String insertTORegister=insertFields;
        try {

            Statement statement=connectDB.createStatement();
            //statement.executeQuery("ALTER TABLE sales_representative AUTO_INCREMENT ="+maxID+1);
            statement.executeUpdate(insertTORegister);
            messageLabel.setText("Sales Representative added successfully!");
            confirmPasswordMessage.setText("");

        }catch (Exception e){
            e.getCause();
            messageLabel.setText("User already registered!");
        }

    }
    public static boolean isValidPassword(String password)
    {

        // Regex to check valid password.
        String regex = "^(?=.*[0-9])"
                + "(?=.*[a-z])(?=.*[A-Z])"
                + "(?=.*[@#$%^&+=_])"
                + "(?=\\S+$).{8,20}$";

        Pattern p = Pattern.compile(regex);


        if (password == null) {
            return false;
        }

        Matcher m = p.matcher(password);

        // Return if the password
        // matched the ReGex
        return m.matches();
    }
}
