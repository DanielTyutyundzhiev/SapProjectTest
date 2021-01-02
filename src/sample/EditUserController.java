package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class EditUserController implements Initializable {
    @FXML
    private TableView<DeleteUserModel> table;
    @FXML
    private TableColumn<DeleteUserModel,String> col_userID;
    @FXML
    private TableColumn<DeleteUserModel,String> col_salesRepID;
    @FXML
    private TableColumn<DeleteUserModel,String> col_firstName;
    @FXML
    private TableColumn<DeleteUserModel,String> col_lastName;
    @FXML
    private TableColumn<DeleteUserModel,String> col_email;
    @FXML
    private TextField idField;
    @FXML
    private Label deleteMessage;
    @FXML
    private Button editButton;
    @FXML
    private Label firstnameLabel;
    @FXML
    private Label lastnameLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private TextField firstnameField;
    @FXML
    private TextField lastnameField;
    @FXML
    private TextField emailField;

    ObservableList<DeleteUserModel> oblist= FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        editButton.setVisible(false);
        firstnameField.setVisible(false);
        lastnameField.setVisible(false);
        emailField.setVisible(false);

        firstnameLabel.setVisible(false);
        lastnameLabel.setVisible(false);
        emailLabel.setVisible(false);


        DataBaseConnection connection=new DataBaseConnection();
        Connection connectDB=connection.getConnection();
        File file=new File("E:\\SAP_Project\\src\\sales_repID.txt");
        Path path=Path.of("E:\\SAP_Project\\src\\sales_repID.txt");
        String id= null;
        try {
            id = Files.readString(path);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        int srID=Integer.parseInt(id);
        try {
            ResultSet rs=connectDB.createStatement().executeQuery("SELECT * FROM users WHERE user_id="+srID);
            while (rs.next()){
                oblist.add(new DeleteUserModel(rs.getString("u_id"),rs.getString("user_id"),rs.getString("first_nameU"),rs.getString("last_nameU"),rs.getString("email")));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        col_userID.setCellValueFactory(new PropertyValueFactory<>("userID1"));
        col_salesRepID.setCellValueFactory(new PropertyValueFactory<>("salesRepID1"));
        col_firstName.setCellValueFactory(new PropertyValueFactory<>("firstName1"));
        col_lastName.setCellValueFactory(new PropertyValueFactory<>("lastName1"));
        col_email.setCellValueFactory(new PropertyValueFactory<>("email"));

        table.setItems(oblist);
    }
    public void editUser(){
        int idSmurt= Integer.parseInt(idField.getText());
        String firstname = "";
        String lastname = "";
        String email = "";
        DataBaseConnection connection=new DataBaseConnection();
        Connection connectDB=connection.getConnection();

        try {
            Statement statement = connectDB.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users WHERE u_id=" + idSmurt);
            if(resultSet.next()) {
                firstname = resultSet.getString("first_nameU");
                System.out.println(firstname);
                lastname = resultSet.getString("last_nameU");
                email = resultSet.getString("email");

                if (!firstnameField.getText().isBlank()) {
                    firstname = firstnameField.getText();
                    System.out.println(firstname);
                }
                if (!lastnameField.getText().isBlank()) {
                    lastname = lastnameField.getText();
                }
                if (!emailField.getText().isBlank()) {
                    email = emailField.getText();
                }

                try {
                    Statement statement1 = connectDB.createStatement();
                    statement1.executeUpdate("UPDATE users SET first_nameU= '" + firstname + "', last_nameU='" + lastname + "', email = '" + email + "' WHERE u_id=" + idSmurt);
                } catch (Exception e) {
                    e.getCause();
                }
            }
        }catch (Exception e){
            System.out.println("dab");
            e.printStackTrace();
        }


    }

    public void updateTable(){
        boolean isShown=false;

        if(idField.getText().isBlank()){
            table.getItems().clear();
            DataBaseConnection connection=new DataBaseConnection();
            Connection connectDB=connection.getConnection();
            File file=new File("E:\\SAP_Project\\src\\sales_repID.txt");
            Path path=Path.of("E:\\SAP_Project\\src\\sales_repID.txt");
            String id= null;
            try {
                id = Files.readString(path);
            } catch (IOException exception) {
                exception.printStackTrace();
            }

            int srID=Integer.parseInt(id);
            try {
                ResultSet rs=connectDB.createStatement().executeQuery("SELECT * FROM users WHERE user_id="+srID);
                while (rs.next()){
                    oblist.add(new DeleteUserModel(rs.getString("u_id"),rs.getString("user_id"),rs.getString("first_nameU"),rs.getString("last_nameU"),rs.getString("email")));
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            col_userID.setCellValueFactory(new PropertyValueFactory<>("userID1"));
            col_salesRepID.setCellValueFactory(new PropertyValueFactory<>("salesRepID1"));
            col_firstName.setCellValueFactory(new PropertyValueFactory<>("firstName1"));
            col_lastName.setCellValueFactory(new PropertyValueFactory<>("lastName1"));
            col_email.setCellValueFactory(new PropertyValueFactory<>("email"));

            table.setItems(oblist);
        }else{
            table.getItems().clear();
            int idTextFieldSmurt= Integer.parseInt(idField.getText());
            ObservableList<DeleteUserModel> oblist= FXCollections.observableArrayList();

            DataBaseConnection connection=new DataBaseConnection();
            Connection connectDB=connection.getConnection();
            File file=new File("E:\\SAP_Project\\src\\sales_repID.txt");
            Path path=Path.of("E:\\SAP_Project\\src\\sales_repID.txt");
            String idSR= null;
            try {
                idSR = Files.readString(path);
            } catch (IOException exception) {
                exception.printStackTrace();
            }

            int srID=Integer.parseInt(idSR);


            try{
                ResultSet rs1=connectDB.createStatement().executeQuery("SELECT * FROM users WHERE u_id="+idTextFieldSmurt);
                int identSR=0;
                if (rs1.next()) {
                    identSR = rs1.getInt(2);
                }

                if(!rs1.next()&&srID!=identSR){

                    deleteMessage.setText("No user with id "+idTextFieldSmurt+" found.");
                    isShown=false;
                }else{
                    isShown=true;
                    try{
                        ResultSet rs = connectDB.createStatement().executeQuery("SELECT * FROM users WHERE u_id=" + idTextFieldSmurt);
                        if(rs.next()){
                            oblist.add(new DeleteUserModel(rs.getString("u_id"),rs.getString("user_id"),rs.getString("first_nameU"),rs.getString("last_nameU"), rs.getString("email")));
                        }

                    }catch (Exception e){
                        System.out.println("Error");
                    }
                    col_userID.setCellValueFactory(new PropertyValueFactory<>("userID1"));
                    col_salesRepID.setCellValueFactory(new PropertyValueFactory<>("salesRepID1"));
                    col_firstName.setCellValueFactory(new PropertyValueFactory<>("firstName1"));
                    col_lastName.setCellValueFactory(new PropertyValueFactory<>("lastName1"));
                    col_email.setCellValueFactory(new PropertyValueFactory<>("email"));
                    table.setItems(oblist);
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        if(idField.getText().isBlank()||!isShown){
            editButton.setVisible(false);
            firstnameField.setVisible(false);
            lastnameField.setVisible(false);
            emailField.setVisible(false);
            firstnameLabel.setVisible(false);
            lastnameLabel.setVisible(false);
            emailLabel.setVisible(false);
        }else{
            editButton.setVisible(true);
            firstnameField.setVisible(true);
            lastnameField.setVisible(true);
            emailField.setVisible(true);

            firstnameLabel.setVisible(true);
            lastnameLabel.setVisible(true);
            emailLabel.setVisible(true);
        }
    }
}
