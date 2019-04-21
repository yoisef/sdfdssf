package werpx.marketopia.salehistorymodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Sale {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("barcode")
    @Expose
    private String barcode;
    @SerializedName("quantity")
    @Expose
    private Integer quantity;
    @SerializedName("product_id")
    @Expose
    private String productId;
    @SerializedName("store_id")
    @Expose
    private String storeId;
    @SerializedName("retailer_id")
    @Expose
    private String retailerId;
    @SerializedName("loyality")
    @Expose
    private Integer loyality;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("store")
    @Expose
    private Store store;
    @SerializedName("retailer")
    @Expose
    private Retailer retailer;
    @SerializedName("product")
    @Expose
    private Product product;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getRetailerId() {
        return retailerId;
    }

    public void setRetailerId(String retailerId) {
        this.retailerId = retailerId;
    }

    public Integer getLoyality() {
        return loyality;
    }

    public void setLoyality(Integer loyality) {
        this.loyality = loyality;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public Retailer getRetailer() {
        return retailer;
    }

    public void setRetailer(Retailer retailer) {
        this.retailer = retailer;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

}