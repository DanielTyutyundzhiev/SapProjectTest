package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AddProductController {
    @FXML
    private TextField prnameField;
    @FXML
    private TextField prtypeField;
    @FXML
    private TextField prbrandField;
    @FXML
    private TextField prpriceField;
    @FXML
    private TextField prquantityField;
    @FXML
    private Button closeBtn;
    @FXML
    private Label messageLabel;

    public void closeButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) closeBtn.getScene().getWindow();
        stage.close();
    }

    public void addProduct() throws SQLException {
        DataBaseConnection connection = new DataBaseConnection();
        Connection connectDB = connection.getConnection();
        String prname = prnameField.getText();
        String prtype = prtypeField.getText();
        String prbrand = prbrandField.getText();
        String prprice = prpriceField.getText();
        String prquantity = prquantityField.getText();
        try {
            ResultSet rs = connectDB.createStatement().executeQuery("SELECT * FROM products WHERE product_name= '" + prname+"'");
            if (rs.next()) {
                try {
                    //ResultSet rs1=connectDB.createStatement().executeQuery("SELECT * FROM products WHERE product_name="+prname);
                    int oldQnt = rs.getInt(6);
                    int total = oldQnt + Integer.parseInt(prquantity);
                    try {
                        connectDB.createStatement().executeUpdate("UPDATE products SET product_quantity= '" + total + "' WHERE product_name = '" + prname+"'");
                    } catch (Exception e) {
                        e.printStackTrace();
                        e.getCause();
                    }
                } catch (Exception e) {
                    e.getCause();

                }
                messageLabel.setText("Quantity update");
            } else {
                String insertFields = "INSERT INTO products(product_name,product_type,product_brand,product_price,product_quantity) VALUES ('" + prname + "','" + prtype + "','" + prbrand + "','" + prprice + "', '" + prquantity + "')";
                try {
                    Statement statement = connectDB.createStatement();
                    statement.executeUpdate(insertFields);
                    if (isNumeric(prquantity)) {
                        messageLabel.setText("Product  added successfully!");
                    }

                } catch (Exception e) {
                    e.getCause();
                    messageLabel.setText("Invalid quantity!");
                }


            }
        }catch (Exception e){
            e.getCause();
        }


       /* ResultSet rs2=connectDB.createStatement().executeQuery("SELECT * FROM products WHERE product_name="+prname);
        if (rs2.next()) {

            Statement statement2=connectDB.createStatement();
            Statement statement3=connectDB.createStatement();
            ResultSet hui=statement3.executeQuery("SELECT product_quantity FROM products WHERE product_name="+prname);
            int hui2=hui.getInt(1);
            int total=hui2+Integer.parseInt(prquantity);
            statement2.executeUpdate("UPDATE products SET product_quantity = '"+total+"' WHERE product_name="+prname);

        } else {
            String insertFields = "INSERT INTO products(product_name,product_type,product_brand,product_price,product_quantity) VALUES ('" + prname + "','" + prtype + "','" + prbrand + "','" + prprice + "', '" + prquantity + "')";
            try {
                Statement statement = connectDB.createStatement();
                statement.executeUpdate(insertFields);
                if (isNumeric(prquantity)) {
                    messageLabel.setText("Product  added successfully!");
                }

            } catch (Exception e) {
                e.getCause();
                messageLabel.setText("Invalid quantity!");
            }


        }*/
        prnameField.clear();
        prtypeField.clear();
        prbrandField.clear();
        prpriceField.clear();
        prquantityField.clear();
    }
    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }
}
