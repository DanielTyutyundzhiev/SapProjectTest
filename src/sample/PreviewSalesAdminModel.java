package sample;

public class PreviewSalesAdminModel {
    String salesID;
    String date;
    String productName;
    String quantity;
    String finalPrice;
    String salesRepID;
    String userID;

    public PreviewSalesAdminModel(String salesID, String date, String productName, String quantity, String finalPrice, String salesRepID,String userID) {
        this.salesID = salesID;
        this.date = date;
        this.productName = productName;
        this.quantity = quantity;
        this.finalPrice = finalPrice;
        this.salesRepID = salesRepID;
        this.userID=userID;

    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getSalesID() {
        return salesID;
    }

    public void setSalesID(String salesID) {
        this.salesID = salesID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(String finalPrice) {
        this.finalPrice = finalPrice;
    }

    public String getSalesRepID() {
        return salesRepID;
    }

    public void setSalesRepID(String salesRepID) {
        this.salesRepID = salesRepID;
    }
}
