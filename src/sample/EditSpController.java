package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class EditSpController implements Initializable {

    @FXML
    private TableView<DeleteSpModel> table;
    @FXML
    private TableColumn<DeleteSpModel,String> col_id;
    @FXML
    private TableColumn<DeleteSpModel,String> col_firstName;
    @FXML
    private TableColumn<DeleteSpModel,String> col_lastName;
    @FXML
    private TableColumn<DeleteSpModel,String> col_UserName;
    @FXML
    private TableColumn<DeleteSpModel,String> col_Password;
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
    private Label usernameLabel;
    @FXML
    private Label passwordLabel;
    @FXML
    private TextField firstnameField;
    @FXML
    private TextField lastnameField;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    ObservableList<DeleteSpModel> oblist= FXCollections.observableArrayList();
    @Override

    public void initialize(URL url, ResourceBundle resourceBundle) {
        editButton.setVisible(false);
        firstnameField.setVisible(false);
        lastnameField.setVisible(false);
        usernameField.setVisible(false);
        passwordField.setVisible(false);
        firstnameLabel.setVisible(false);
        lastnameLabel.setVisible(false);
        usernameLabel.setVisible(false);
        passwordLabel.setVisible(false);
        DataBaseConnection connection=new DataBaseConnection();
        Connection connectDB=connection.getConnection();
        try {
            ResultSet rs=connectDB.createStatement().executeQuery("SELECT * FROM sales_representative ");
            while (rs.next()){
                oblist.add(new DeleteSpModel(rs.getString("user_id"),rs.getString("firstName"),rs.getString("secondName"),rs.getString("username"), rs.getString("password")));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        col_lastName.setCellValueFactory(new PropertyValueFactory<>("lastNAme"));
        col_UserName.setCellValueFactory(new PropertyValueFactory<>("username"));
        col_Password.setCellValueFactory(new PropertyValueFactory<>("password"));
        table.setItems(oblist);

    }

    public void editSPonAction(ActionEvent event){
        String id=idField.getText();
        String firstname = "";
        String lastname = "";
        String username = "";
        String password = "";

        DataBaseConnection connection=new DataBaseConnection();
        Connection connectDB=connection.getConnection();

        try {
            Statement statement = connectDB.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM sales_representative WHERE user_id=" + id);
            if (resultSet.next()) {
                firstname = resultSet.getString("firstname");
                lastname = resultSet.getString("secondName");
                username = resultSet.getString("username");
                password = resultSet.getString("password");



            }
        }catch (Exception e){
            e.getCause();
        }
        if(!firstnameField.getText().isBlank()){
            firstname=firstnameField.getText();
        }if(!lastnameField.getText().isBlank()){
            lastname=lastnameField.getText();
        }if(!usernameField.getText().isBlank()){
            username=usernameField.getText();
        }if(!passwordField.getText().isBlank()){
            password=passwordField.getText();
        }
        try{
            Statement statement1=connectDB.createStatement();
            statement1.executeUpdate("UPDATE sales_representative SET firstName= '"+firstname+"', secondName='"+lastname+"', username = '"+username+"', password = '"+password+"' WHERE user_id="+id );
        }catch (Exception e){
            e.getCause();
        }



    }
    public void updateTable(){

        boolean isShown=false;

        if(idField.getText().isBlank()){
            table.getItems().clear();
            DataBaseConnection connection=new DataBaseConnection();
            Connection connectDB=connection.getConnection();
            try {
                ResultSet rs=connectDB.createStatement().executeQuery("SELECT * FROM sales_representative ");
                while (rs.next()){
                    oblist.add(new DeleteSpModel(rs.getString("user_id"),rs.getString("firstName"),rs.getString("secondName"),rs.getString("username"), rs.getString("password")));
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
            col_firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
            col_lastName.setCellValueFactory(new PropertyValueFactory<>("lastNAme"));
            col_UserName.setCellValueFactory(new PropertyValueFactory<>("username"));
            table.setItems(oblist);
            deleteMessage.setText("");
            idField.clear();

        }else{

            table.getItems().clear();
            String id=idField.getText();
            ObservableList<DeleteSpModel> oblist= FXCollections.observableArrayList();

            DataBaseConnection connection=new DataBaseConnection();
            Connection connectDB=connection.getConnection();
            try {
                ResultSet rs1=connectDB.createStatement().executeQuery("SELECT * FROM sales_representative WHERE user_id="+id);
                if(!rs1.next()){
                    deleteMessage.setText("No user with id "+id+" found.");
                    isShown=false;
                }else{
                    isShown=true;
                    try {
                        ResultSet rs = connectDB.createStatement().executeQuery("SELECT * FROM sales_representative WHERE user_id=" + id);
                        if(rs.next()){
                            oblist.add(new DeleteSpModel(rs.getString("user_id"),rs.getString("firstName"),rs.getString("secondName"),rs.getString("username"), rs.getString("password")));
                        }
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
                    col_firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
                    col_lastName.setCellValueFactory(new PropertyValueFactory<>("lastNAme"));
                    col_UserName.setCellValueFactory(new PropertyValueFactory<>("username"));
                    col_Password.setCellValueFactory(new PropertyValueFactory<>("password"));
                    table.setItems(oblist);
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if(idField.getText().isBlank()||!isShown){
            editButton.setVisible(false);
            firstnameField.setVisible(false);
            lastnameField.setVisible(false);
            usernameField.setVisible(false);
            passwordField.setVisible(false);
            firstnameLabel.setVisible(false);
            lastnameLabel.setVisible(false);
            usernameLabel.setVisible(false);
            passwordLabel.setVisible(false);
        }else{
            editButton.setVisible(true);
            firstnameField.setVisible(true);
            lastnameField.setVisible(true);
            usernameField.setVisible(true);
            passwordField.setVisible(true);
            firstnameLabel.setVisible(true);
            lastnameLabel.setVisible(true);
            usernameLabel.setVisible(true);
            passwordLabel.setVisible(true);
        }


    }
}
