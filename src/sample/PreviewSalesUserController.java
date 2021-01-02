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
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class PreviewSalesUserController implements Initializable {
    @FXML
    private TableView<PreviewSalesAdminModel> table;
    @FXML
    private TableColumn<PreviewSalesAdminModel, String> col_salesID;
    @FXML
    private TableColumn<PreviewSalesAdminModel, String> col_date;
    @FXML
    private TableColumn<PreviewSalesAdminModel, String> col_productName;
    @FXML
    private TableColumn<PreviewSalesAdminModel, String> col_Quantity;
    @FXML
    private TableColumn<PreviewSalesAdminModel, String> col_finalPrice;

    @FXML
    private Label productId;
    @FXML
    private Label productName;
    @FXML
    private Label productType;
    @FXML
    private Label productBrand;
    @FXML
    private Label productPrice;
    @FXML
    private Label productQuantity;
    @FXML
    private Label userId;
    @FXML
    private Label userFirstName;
    @FXML
    private Label userLastName;
    @FXML
    private Label userEmail;
    @FXML
    private TextField idField;

    ObservableList<PreviewSalesAdminModel> oblist = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DataBaseConnection connection = new DataBaseConnection();
        Connection connectDB = connection.getConnection();

        File file = new File("E:\\SAP_Project\\src\\sales_repID.txt");
        Path path = Path.of("E:\\SAP_Project\\src\\sales_repID.txt");
        String id = null;
        try {
            id = Files.readString(path);
        } catch (IOException exception) {
            exception.printStackTrace();
        }


        try {
            ResultSet rs = connectDB.createStatement().executeQuery("SELECT\n" +
                    "a.u_id,b.u_id,b.user_id,c.user_id,a.sale_id,a.quantity,a.final_price,a.date,a.product_id,d.product_id,d.product_name\n" +
                    "FROM sales_representative c\n" +
                    "JOIN  users b on c.user_id=b.user_id\n" +
                    "JOIN sales a on a.u_id=b.u_id\n" +
                    "JOIN products d on a.product_id=d.product_id\n" +
                    "WHERE c.user_id=" + id);
            while (rs.next()) {
                oblist.add(new PreviewSalesAdminModel(rs.getString("sale_id"), rs.getString("date"), rs.getString("product_name"), rs.getString("quantity"), rs.getString("final_price"), rs.getString("user_id"), rs.getString("u_id")));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        col_salesID.setCellValueFactory(new PropertyValueFactory<>("salesID"));
        col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        col_productName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        col_Quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        col_finalPrice.setCellValueFactory(new PropertyValueFactory<>("finalPrice"));
        table.setItems(oblist);

    }

    public void previewDetails() {
        productId.setText("");
        productName.setText("");
        productType.setText("");
        productBrand.setText("");
        productPrice.setText("");
        productQuantity.setText("");

        userId.setText("");
        userFirstName.setText("");
        userLastName.setText("");
        userEmail.setText("");


        if (!idField.getText().isBlank()) {
            int id = Integer.parseInt(idField.getText());
            DataBaseConnection connection = new DataBaseConnection();
            Connection connectDB = connection.getConnection();

            try {

                Statement statement = connectDB.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM sales WHERE sale_id=" + id);
                if (resultSet.next()) {
                    ResultSet resultSet1 = statement.executeQuery("SELECT\n" +
                            "a.u_id,b.u_id,b.user_id,c.user_id,a.sale_id,a.product_id,d.product_id\n" +
                            "FROM sales_representative c\n" +
                            "JOIN  users b on c.user_id=b.user_id\n" +
                            "JOIN sales a on a.u_id=b.u_id\n" +
                            "JOIN products d on a.product_id=d.product_id\n" +
                            "WHERE sale_id=" + id);
                    int salesRepIdvalidation1 = 0;
                    int srID = 0;
                    if (resultSet1.next()) {
                        salesRepIdvalidation1 = resultSet1.getInt(3);
                        File file = new File("E:\\SAP_Project\\src\\sales_repID.txt");
                        Path path = Path.of("E:\\SAP_Project\\src\\sales_repID.txt");
                        String idd = null;
                        try {
                            idd = Files.readString(path);
                        } catch (IOException exception) {
                            exception.printStackTrace();
                        }

                        srID = Integer.parseInt(idd);
                    }

                    if (salesRepIdvalidation1 == srID) {

                        try {
                            Statement statement2 = connectDB.createStatement();
                            ResultSet resultSet2 = statement.executeQuery("SELECT \n" +
                                    "a.product_id,a.product_name,a.product_type,a.product_brand,a.product_price,a.product_quantity,b.product_id\n" +
                                    "FROM products a JOIN sales b on a.product_id=b.product_id\n" +
                                    "WHERE sale_id=" + id);

                            String productId1 = "";
                            String productName1 = "";
                            String productType1 = "";
                            String productBrand1 = "";
                            String productPrice1 = "";
                            String productQuantity1 = "";

                            if (resultSet2.next()) {
                                productId.setText(productId1 = resultSet2.getString(1));

                                productName.setText(productName1 = resultSet2.getString(2));
                                productType.setText(productType1 = resultSet2.getString(3));
                                productBrand.setText(productBrand1 = resultSet2.getString(4));
                                productPrice.setText(productPrice1 = resultSet2.getString(5));
                                productQuantity.setText(productQuantity1 = resultSet2.getString(6));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            System.out.println("Error with query 2");
                        }
                        try {
                            Statement statement3 = connectDB.createStatement();
                            ResultSet resultSet3 = statement.executeQuery("SELECT \n" +
                                    "a.u_id,a.first_nameU,a.last_nameU,a.email,b.u_id\n" +
                                    "FROM users a\n" +
                                    "JOIN sales b on a.u_id=b.u_id\n" +
                                    "WHERE sale_id=" + id);
                            String userID1 = "";
                            String userFirstName1 = "";
                            String userLastName1 = "";
                            String userEmail1 = "";
                            if (resultSet3.next()) {
                                userId.setText(userID1 = resultSet3.getString(1));
                                userFirstName.setText(userFirstName1 = resultSet3.getString(2));
                                userLastName.setText(userLastName1 = resultSet3.getString(3));
                                userEmail.setText(userEmail1 = resultSet3.getString(4));
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                            System.out.println("Error with query 3");
                        }

                    }

                }

            } catch (Exception e) {
                System.out.println("Error with query 1");
                e.printStackTrace();
            }


        }

    }
}
