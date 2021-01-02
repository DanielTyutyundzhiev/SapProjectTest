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

public class EditProductController implements Initializable {
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
    @FXML
    private TextField nameField;
    @FXML
    private TextField typeField;
    @FXML
    private TextField brandField;
    @FXML
    private TextField priceField;
    @FXML
    private TextField quantityField;
    @FXML
    private Label firstnameLabel;
    @FXML
    private Label lastnameLabel;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label passwordLabel;
    @FXML
    private Label quantityLabel;
    @FXML
    private Button editButton;


    ObservableList<DeleteProductModel> oblist= FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        editButton.setVisible(false);
        nameField.setVisible(false);
        typeField.setVisible(false);
        brandField.setVisible(false);
        priceField.setVisible(false);
        quantityField.setVisible(false);
        firstnameLabel.setVisible(false);
        lastnameLabel.setVisible(false);
        usernameLabel.setVisible(false);
        passwordLabel.setVisible(false);
        quantityLabel.setVisible(false);

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
    public void editSPonAction(ActionEvent event){
        String id=idField.getText();
        String productName = "";
        String productType = "";
        String productBrand = "";
        String productPrice = "";
        String productQuantity = "";

        DataBaseConnection connection=new DataBaseConnection();
        Connection connectDB=connection.getConnection();

        try {
            Statement statement = connectDB.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM products WHERE product_id=" + id);
            if (resultSet.next()) {
                productName = resultSet.getString("product_name");
                productType = resultSet.getString("product_type");
                productBrand = resultSet.getString("product_brand");
                productPrice = resultSet.getString("product_price");
                productQuantity = resultSet.getString("product_quantity");


            }
        }catch (Exception e){
            e.getCause();
        }
        if(!nameField.getText().isBlank()){
            productName=nameField.getText();
        }if(!typeField.getText().isBlank()){
            productType=typeField.getText();
        }if(!brandField.getText().isBlank()){
            productBrand=brandField.getText();
        }if(!priceField.getText().isBlank()){
            productPrice=priceField.getText();
        }if(!quantityField.getText().isBlank()){
            productQuantity=quantityField.getText();
        }
        try{
            Statement statement1=connectDB.createStatement();
            statement1.executeUpdate("UPDATE products SET product_name= '"+productName+"', product_type='"+productType+"', product_brand = '"+productBrand+"', product_price = '"+productPrice+"', product_quantity = '"+productQuantity+"' WHERE product_id="+id );
        }catch (Exception e){
            e.printStackTrace();
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
            idField.clear();
        }else{

            table.getItems().clear();
            String id=idField.getText();
            ObservableList<DeleteProductModel> oblist= FXCollections.observableArrayList();

            DataBaseConnection connection=new DataBaseConnection();
            Connection connectDB=connection.getConnection();
            try {
                ResultSet rs1=connectDB.createStatement().executeQuery("SELECT * FROM products WHERE product_id="+id);
                if(!rs1.next()){
                    deleteMessage.setText("No product with id "+id+" found.");
                    isShown=false;
                }else{
                    isShown=true;
                    try {
                        ResultSet rs = connectDB.createStatement().executeQuery("SELECT * FROM products WHERE product_id=" + id);
                        if(rs.next()){
                            oblist.add(new DeleteProductModel(rs.getString("product_id"),rs.getString("product_name"),rs.getString("product_type"),rs.getString("product_brand"), rs.getString("product_price"),rs.getString("product_quantity")));
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
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }
        if(idField.getText().isBlank()||!isShown){
            editButton.setVisible(false);
            nameField.setVisible(false);
            typeField.setVisible(false);
            brandField.setVisible(false);
            priceField.setVisible(false);
            quantityField.setVisible(false);
            firstnameLabel.setVisible(false);
            lastnameLabel.setVisible(false);
            usernameLabel.setVisible(false);
            passwordLabel.setVisible(false);
            quantityLabel.setVisible(false);
        }else{
            editButton.setVisible(true);
            nameField.setVisible(true);
            typeField.setVisible(true);
            brandField.setVisible(true);
            priceField.setVisible(true);
            quantityField.setVisible(true);
            firstnameLabel.setVisible(true);
            lastnameLabel.setVisible(true);
            usernameLabel.setVisible(true);
            passwordLabel.setVisible(true);
            quantityLabel.setVisible(true);
        }
    }
}
