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

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class DeleteSpController implements Initializable {
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
    private TextField idField;
    @FXML
    private Label deleteMessage;
    ObservableList<DeleteSpModel> oblist= FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        DataBaseConnection connection=new DataBaseConnection();
        Connection connectDB=connection.getConnection();
        try {
            ResultSet rs=connectDB.createStatement().executeQuery("SELECT * FROM sales_representative ");
            while (rs.next()){
                oblist.add(new DeleteSpModel(rs.getString("user_id"),rs.getString("firstName"),rs.getString("secondName"),rs.getString("username"),rs.getString("password")));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        col_lastName.setCellValueFactory(new PropertyValueFactory<>("lastNAme"));
        col_UserName.setCellValueFactory(new PropertyValueFactory<>("username"));
        table.setItems(oblist);
    }
    public void removeOnAction(ActionEvent event) throws SQLException {
        String id=idField.getText();
        DataBaseConnection connection=new DataBaseConnection();
        Connection connectDB=connection.getConnection();
        String insertToDelete="DELETE FROM sales_representative WHERE user_id="+id;
        String sql_res="SELECT * FROM sales_representative WHERE user_id="+id;
        try{
            Statement statement2=connectDB.createStatement();
            ResultSet rs=statement2.executeQuery(sql_res);
            if(!rs.next()){
                deleteMessage.setText("User not Found");
            }
            else{
                try {
                    Statement statement=connectDB.createStatement();
                    statement.executeUpdate(insertToDelete);
                    deleteMessage.setText("User Removed");

                }catch (Exception e){
                    e.printStackTrace();
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

    }

}
