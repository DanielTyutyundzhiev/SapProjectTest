package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class LoginController {
    int spID;

    @FXML
    private Button cancel;
    @FXML
    private Label labelMessage;
    @FXML
    private Button login;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private CheckBox isAdmin;
    @FXML
    private CheckBox isSalesRep;

    public void loginButton(javafx.event.ActionEvent event){
        labelMessage.setText("Trying to login...");
        if(username.getText().isBlank()==false&&password.getText().isBlank()==false){
            labelMessage.setText("Trying to login...");
            validateLogin();

        }else{
            labelMessage.setText("Please enter username and password..");
        }




    }


    public void cancelOnAction(javafx.event.ActionEvent event) {
        Stage stage=(Stage) cancel.getScene().getWindow();
        stage.close();
    }
    public void validateLogin() {
        DataBaseConnection connect = new DataBaseConnection();
        Connection connectDB = connect.getConnection();
        String verifyLoginAdmin = "SELECT count(1) FROM admin_account WHERE username = '" + username.getText() + "' AND password ='" + password.getText() + "'";
        String verifyLoginSalesRep = "SELECT * FROM sales_representative WHERE username = '" + username.getText() + "' AND password ='" + password.getText() + "'";
        if (isAdmin.isSelected()==true) {
            try {
                Statement statement = connectDB.createStatement();
                ResultSet queryResult = statement.executeQuery(verifyLoginAdmin);

                while (queryResult.next()) {
                    if (queryResult.getInt(1) == 1) {
                        labelMessage.setText("Successfully logged in!");
                        clear();
                        createAdminMenu();
                    } else {
                        labelMessage.setText("Invalid Credentials..");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                e.getCause();
            }
        }
        else if(isSalesRep.isSelected()==true){
            try {
                Statement statement = connectDB.createStatement();
                ResultSet queryResult = statement.executeQuery(verifyLoginSalesRep);

                if (queryResult.next()) {

                    labelMessage.setText("Successfully logged in!");
                    clear();
                    salesRepMenu();
                    spID = queryResult.getInt(1);
                    System.out.println(spID);
                    try {
                        File file=new File("E:\\SAP_Project\\src\\sales_repID.txt");
                        FileWriter writer=new FileWriter("E:\\SAP_Project\\src\\sales_repID.txt");
                        System.out.println(spID);
                        writer.write(Integer.toString(spID));
                        System.out.println(spID);
                        writer.close();

                        BufferedReader bufferedReader=new BufferedReader(new FileReader(file));
                        String st;
                        while ((st=bufferedReader.readLine())!=null){
                            System.out.println(st);
                        }

                    }catch (Exception e){
                        e.getCause();
                    }
                }
                else {
                    labelMessage.setText("Invalid Credentials..");
                }

            } catch (Exception e) {
                e.printStackTrace();
                e.getCause();
            }
        }
    }
    public void salesRepMenu(){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("salesRepMenu.fxml"));
            Stage addSPStage=new Stage();
            addSPStage.setTitle("Sales Representative Menu");
            addSPStage.setScene(new Scene(root, 600, 400));
            addSPStage.show();

        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }
    public void createAdminMenu(){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("adminMenu.fxml"));
            Stage addSPStage=new Stage();
            addSPStage.setTitle("Admin Menu");
            addSPStage.setScene(new Scene(root, 600, 400));
            addSPStage.show();

        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }

    }
    public void clear(){
        username.clear();
        password.clear();
        isSalesRep.setSelected(false);
        isAdmin.setSelected(false);
        labelMessage.setText("");
    }
}
