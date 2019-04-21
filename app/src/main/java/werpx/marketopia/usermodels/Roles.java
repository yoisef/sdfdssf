package werpx.marketopia.usermodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Roles {

    @SerializedName("retailerAdmin")
    @Expose
    private List<RetailerAdmin> retailerAdmin = null;
    @SerializedName("retailer")
    @Expose
    private List<Object> retailer = null;

    public List<RetailerAdmin> getRetailerAdmin() {
        return retailerAdmin;
    }

    public void setRetailerAdmin(List<RetailerAdmin> retailerAdmin) {
        this.retailerAdmin = retailerAdmin;
    }

    public List<Object> getRetailer() {
        return retailer;
    }

    public void setRetailer(List<Object> retailer) {
        this.retailer = retailer;
    }

}