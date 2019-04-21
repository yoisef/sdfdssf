package werpx.marketopia.storemodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Storeinfo {

    @SerializedName("stores")
    @Expose
    private List<Store> stores = null;

    public List<Store> getStores() {
        return stores;
    }

    public void setStores(List<Store> stores) {
        this.stores = stores;
    }

}