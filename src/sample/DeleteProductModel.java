package sample;

public class DeleteProductModel {
    String id;
    String productName;
    String productType;
    String productBrand;
    String productPrice;
    String productQuantity;

    public DeleteProductModel(String id, String productName, String productType, String productBrand, String productPrice, String productQuantity) {
        this.id = id;
        this.productName = productName;
        this.productType = productType;
        this.productBrand = productBrand;
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getProductBrand() {
        return productBrand;
    }

    public void setProductBrand(String productBrand) {
        this.productBrand = productBrand;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(String productQuantity) {
        this.productQuantity = productQuantity;
    }
}
