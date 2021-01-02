package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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

public class DeleteUserController implements Initializable {
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

    ObservableList<DeleteUserModel> oblist= FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

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
        System.out.println(id);
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
    public void removeOnAction(ActionEvent event) throws SQLException {
        String id=idField.getText();
        DataBaseConnection connection=new DataBaseConnection();
        Connection connectDB=connection.getConnection();
        String insertToDelete="DELETE FROM users WHERE u_id="+id;
        String sql_res="SELECT * FROM users WHERE u_id="+id;
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
            Statement statement2=connectDB.createStatement();
            ResultSet rs=statement2.executeQuery(sql_res);
            if(!rs.next()){
                deleteMessage.setText("User not Found");
            }
            else{
                int var=rs.getInt(2);
                if(var==srID) {
                    try {
                        Statement statement = connectDB.createStatement();
                        statement.executeUpdate(insertToDelete);
                        deleteMessage.setText("User Removed");

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else{
                    deleteMessage.setText("No permission to delete that user");
                }
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public void updateTable(){

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
        System.out.println(id);
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
}
