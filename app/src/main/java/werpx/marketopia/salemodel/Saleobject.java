package werpx.marketopia.salemodel;

import com.google.gson.annotations.SerializedName;

public class Saleobject {

    @SerializedName("barcode")
    private String barcode;
    @SerializedName("quantity")
    private String quantity;
    @SerializedName("retailer_id")
    private String retailer_id;
    @SerializedName("product_id")
    private String product_id;
    @SerializedName("live")
    private Boolean live;

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getRetailer_id() {
        return retailer_id;
    }

    public void setRetailer_id(String retailer_id) {
        this.retailer_id = retailer_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public Boolean getLive() {
        return live;
    }

    public void setLive(Boolean live) {
        this.live = live;
    }



}
