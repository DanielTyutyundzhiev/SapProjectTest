package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class SalesRepMenuController {
    @FXML
    private Button logoutButton;

    public void logoutOnAction(ActionEvent event){
        Stage stage=(Stage) logoutButton.getScene().getWindow();
        stage.close();

    }
    public void addUser(){
        try {


            Parent root = FXMLLoader.load(getClass().getResource("addUser.fxml"));
            Stage addSPStage = new Stage();
            addSPStage.setTitle("");
            addSPStage.setScene(new Scene(root, 600, 700));
            addSPStage.show();
        }
        catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }

    }
    public void deleteUser(){
        try {


            Parent root = FXMLLoader.load(getClass().getResource("deleteUser.fxml"));
            Stage addSPStage = new Stage();
            addSPStage.setTitle("");
            addSPStage.setScene(new Scene(root, 800, 400));
            addSPStage.show();
        }
        catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }

    }
    public void editUser(){
        try {


            Parent root = FXMLLoader.load(getClass().getResource("editUser.fxml"));
            Stage addSPStage = new Stage();
            addSPStage.setTitle("");
            addSPStage.setScene(new Scene(root, 800, 400));
            addSPStage.show();
        }
        catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }

    }
    public void makeSale(){
        try {


            Parent root = FXMLLoader.load(getClass().getResource("makesale.fxml"));
            Stage addSPStage = new Stage();
            addSPStage.setTitle("");
            addSPStage.setScene(new Scene(root, 792, 535));
            addSPStage.show();
        }
        catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }

    }
    public void previewSales(){
        try {


            Parent root = FXMLLoader.load(getClass().getResource("previewSalesUser.fxml"));
            Stage addSPStage = new Stage();
            addSPStage.setTitle("");
            addSPStage.setScene(new Scene(root, 792, 535));
            addSPStage.show();
        }
        catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }

    }

    public void cancelOnAction(javafx.event.ActionEvent event) {
        Stage stage=(Stage) logoutButton.getScene().getWindow();
        stage.close();
    }
}
