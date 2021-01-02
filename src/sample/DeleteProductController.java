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

public class DeleteProductController implements Initializable {
    @FXML
    private TableView<DeleteProductModel> table;
    @FXML
    private TableColumn<DeleteProductModel,String> col_id;
    @FXML
    private TableColumn<DeleteProductModel,String> col_productName;
    @FXML
    private TableColumn<DeleteProductModel,String> col_productType;
    @FXML
    private TableColumn<DeleteProductModel,String> col_productBrand;
    @FXML
    private TableColumn<DeleteProductModel,String> col_productPrice;
    @FXML
    private TableColumn<DeleteProductModel,String> col_productQuantity;
    @FXML
    private TextField idField;
    @FXML
    private Label deleteMessage;
    ObservableList<DeleteProductModel> oblist= FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        DataBaseConnection connection=new DataBaseConnection();
        Connection connectDB=connection.getConnection();
        try {
            ResultSet rs=connectDB.createStatement().executeQuery("SELECT * FROM products ");
            while (rs.next()){
                oblist.add(new DeleteProductModel(rs.getString("product_id"),rs.getString("product_name"),rs.getString("product_type"),rs.getString("product_brand"),rs.getString("product_price"),rs.getString("product_quantity")));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_productName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        col_productType.setCellValueFactory(new PropertyValueFactory<>("productType"));
        col_productBrand.setCellValueFactory(new PropertyValueFactory<>("productBrand"));
        col_productPrice.setCellValueFactory(new PropertyValueFactory<>("productPrice"));
        col_productQuantity.setCellValueFactory(new PropertyValueFactory<>("productQuantity"));
        table.setItems(oblist);
    }

    public void removeOnAction(ActionEvent event) throws SQLException {
        String id=idField.getText();
        DataBaseConnection connection=new DataBaseConnection();
        Connection connectDB=connection.getConnection();
        String insertToDelete="DELETE FROM products WHERE product_id="+id;
        String sql_res="SELECT * FROM products WHERE product_id="+id;
        try{
            Statement statement2=connectDB.createStatement();
            ResultSet rs=statement2.executeQuery(sql_res);
            if(!rs.next()){
                deleteMessage.setText("Product not Found");
            }
            else{
                try {
                    Statement statement=connectDB.createStatement();
                    statement.executeUpdate(insertToDelete);
                    deleteMessage.setText("Product Removed");

                }catch (Exception e){
                    e.printStackTrace();
                }
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public void refresh(){
        table.getItems().clear();
        DataBaseConnection connection=new DataBaseConnection();
        Connection connectDB=connection.getConnection();
        try {
            ResultSet rs=connectDB.createStatement().executeQuery("SELECT * FROM products ");
            while (rs.next()){
                oblist.add(new DeleteProductModel(rs.getString("product_id"),rs.getString("product_name"),rs.getString("product_type"),rs.getString("product_brand"),rs.getString("product_price"),rs.getString("product_quantity")));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_productName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        col_productType.setCellValueFactory(new PropertyValueFactory<>("productType"));
        col_productBrand.setCellValueFactory(new PropertyValueFactory<>("productBrand"));
        col_productPrice.setCellValueFactory(new PropertyValueFactory<>("productPrice"));
        col_productQuantity.setCellValueFactory(new PropertyValueFactory<>("productQuantity"));
        table.setItems(oblist);
        deleteMessage.setText("");
        idField.clear();

    }
}
