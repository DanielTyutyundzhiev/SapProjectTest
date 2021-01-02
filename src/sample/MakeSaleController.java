package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.sql.*;
import java.util.ResourceBundle;

public class MakeSaleController implements Initializable {
    @FXML
    private TableView<DeleteUserModel> table;
    @FXML
    private TableColumn<DeleteUserModel,String> col_idUser;
    @FXML
    private TableColumn<DeleteUserModel,String> col_firstName;
    @FXML
    private TableColumn<DeleteUserModel,String> col_lastName;
    @FXML
    private TableColumn<DeleteUserModel,String> col_email;

    @FXML
    private TableView<DeleteProductModel> table2;
    @FXML
    private TableColumn<DeleteProductModel,String> col_id2;
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
    private Label deleteMessage;
    @FXML
    private TextField fieldUserId;
    @FXML
    private TextField fieldProductId;
    @FXML
    private TextField fieldQuantity;
    @FXML
    private Label messageLabel;

    ObservableList<DeleteUserModel> oblist= FXCollections.observableArrayList();
    ObservableList<DeleteProductModel> oblist1= FXCollections.observableArrayList();
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
        int srID=Integer.parseInt(id);
        try {
            ResultSet rs=connectDB.createStatement().executeQuery("SELECT * FROM users WHERE user_id="+srID);
            while (rs.next()){
                oblist.add(new DeleteUserModel(rs.getString("u_id"),rs.getString("user_id"),rs.getString("first_nameU"),rs.getString("last_nameU"),rs.getString("email")));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        col_idUser.setCellValueFactory(new PropertyValueFactory<>("userID1"));
        col_firstName.setCellValueFactory(new PropertyValueFactory<>("firstName1"));
        col_lastName.setCellValueFactory(new PropertyValueFactory<>("lastName1"));
        col_email.setCellValueFactory(new PropertyValueFactory<>("email"));

        table.setItems(oblist);


        try {
            ResultSet rs=connectDB.createStatement().executeQuery("SELECT * FROM products ");
            while (rs.next()){
                oblist1.add(new DeleteProductModel(rs.getString("product_id"),rs.getString("product_name"),rs.getString("product_type"),rs.getString("product_brand"),rs.getString("product_price"),rs.getString("product_quantity")));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        col_id2.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_productName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        col_productType.setCellValueFactory(new PropertyValueFactory<>("productType"));
        col_productBrand.setCellValueFactory(new PropertyValueFactory<>("productBrand"));
        col_productPrice.setCellValueFactory(new PropertyValueFactory<>("productPrice"));
        col_productQuantity.setCellValueFactory(new PropertyValueFactory<>("productQuantity"));
        table2.setItems(oblist1);
    }

    public void sell(){
        boolean dab=false;
        System.out.println(validateProduct());
        System.out.println(validateUser());
        System.out.println(validateQuantity());
        if(validateProduct()&&validateUser()&&validateQuantity()){
            DataBaseConnection connection=new DataBaseConnection();
            Connection connectDB=connection.getConnection();
            int userID= Integer.parseInt(fieldUserId.getText());
            int productId= Integer.parseInt(fieldProductId.getText());
            int productQuantity= Integer.parseInt(fieldQuantity.getText());
            double finalPrice=0;
            String price="";
            int quantity=0;
            try {
                Statement statement = connectDB.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM products WHERE product_id=" + productId);
                if(resultSet.next()){
                    price=resultSet.getString(5);
                    quantity=resultSet.getInt(6);
                }

                finalPrice=Double.parseDouble(price)*productQuantity;
            }catch (Exception e){

            }
            String finalPrice1=String.valueOf(finalPrice);


            try{
                java.util.Date date=new java.util.Date();

                java.sql.Date sqlDate=new java.sql.Date(date.getTime());

                Statement statement=connectDB.createStatement();
                //statement.executeUpdate("INSERT INTO sales(u_id,product_id,quantity,final_price,date ) VALUES ('"+userID+"','"+productId+"','"+productQuantity+"','"+finalPrice+"','"+sqlDate+"'");
                PreparedStatement ps=connectDB.prepareStatement("INSERT INTO sales (u_id,product_id,quantity,final_price,date) VALUES(?,?,?,?,?)");
                ps.setInt(1,userID);
                ps.setInt(2,productId);
                ps.setInt(3,productQuantity);
                ps.setString(4,finalPrice1);
                ps.setDate(5,sqlDate);
                int i =  ps.executeUpdate();
                Statement statement1=connectDB.createStatement();
                int dab1=quantity-productQuantity;
                statement1.executeUpdate("UPDATE products SET product_quantity = '"+dab1+"' WHERE product_id="+productId);


                dab=true;


            }catch (Exception e){
                System.out.println("Error with insert query");
                e.printStackTrace();
            }

        }
        if(dab){
            messageLabel.setText("Sell successfully made");
        }
        else messageLabel.setText("Something went wrong");

    }

    public boolean validateQuantity(){

        int productQuantity= Integer.parseInt(fieldQuantity.getText());
        int productId= Integer.parseInt(fieldProductId.getText());
        DataBaseConnection connection=new DataBaseConnection();
        Connection connectDB=connection.getConnection();
        try{
            Statement statement = connectDB.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM products WHERE product_id=" + productId);
            int quantityInStock=0;
            if(resultSet.next()){

                quantityInStock=resultSet.getInt(6);
                if(quantityInStock<productQuantity){
                    deleteMessage.setText("Insufficient amount in stock");
                    return false;
                }else{
                    return true;
                }
            }
            else return false;

        }catch (Exception e){
            System.out.println("Error with quantity query");
            return false;
        }

    }

    public boolean validateProduct(){
        int productID= Integer.parseInt(fieldProductId.getText());
        DataBaseConnection connection=new DataBaseConnection();
        Connection connectDB=connection.getConnection();

        try {
            Statement statement = connectDB.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM products WHERE product_id=" + productID);
            if(resultSet.next()){
                return true;
            }else{
                deleteMessage.setText("Product not found");
                return false;
            }



        }catch (Exception e){

            System.out.println("Error with query product");
            return false;
        }
    }

    public boolean validateUser(){
        int userID= Integer.parseInt(fieldUserId.getText());
        DataBaseConnection connection=new DataBaseConnection();
        Connection connectDB=connection.getConnection();

        try{
            Statement statement = connectDB.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users WHERE u_id=" + userID);
            if(resultSet.next()){
                File file=new File("E:\\SAP_Project\\src\\sales_repID.txt");
                Path path=Path.of("E:\\SAP_Project\\src\\sales_repID.txt");
                String id= null;
                try {
                    id = Files.readString(path);
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
                int srID=Integer.parseInt(id);

                int identSR=0;

                identSR = resultSet.getInt(2);
                if(srID==identSR){
                    return true;
                }else {
                    return false;
                }

            }
            else{
                deleteMessage.setText("User not found");
                return false;
            }

        }catch (Exception e){
            System.out.println("Error with query user");
            return false;
        }

    }
}
