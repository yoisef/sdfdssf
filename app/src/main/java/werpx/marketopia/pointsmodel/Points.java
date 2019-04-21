package werpx.marketopia.pointsmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Points {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("store_id")
    @Expose
    private String storeId;
    @SerializedName("retailer_id")
    @Expose
    private String retailerId;
    @SerializedName("product_id")
    @Expose
    private String productId;
    @SerializedName("company_id")
    @Expose
    private String companyId;
    @SerializedName("current_balance")
    @Expose
    private Integer currentBalance;
    @SerializedName("used_balance")
    @Expose
    private Object usedBalance;
    @SerializedName("salesman_id")
    @Expose
    private Object salesmanId;
    @SerializedName("history")
    @Expose
    private Integer history;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public Integer getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(Integer currentBalance) {
        this.currentBalance = currentBalance;
    }

    public Object getUsedBalance() {
        return usedBalance;
    }

    public void setUsedBalance(Object usedBalance) {
        this.usedBalance = usedBalance;
    }

    public Object getSalesmanId() {
        return salesmanId;
    }

    public void setSalesmanId(Object salesmanId) {
        this.salesmanId = salesmanId;
    }

    public Integer getHistory() {
        return history;
    }

    public void setHistory(Integer history) {
        this.history = history;
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

}