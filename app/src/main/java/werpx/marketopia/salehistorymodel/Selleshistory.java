package werpx.marketopia.salehistorymodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Selleshistory {

    @SerializedName("sales")
    @Expose
    private List<Sale> sales = null;

    public List<Sale> getSales() {
        return sales;
    }

    public void setSales(List<Sale> sales) {
        this.sales = sales;
    }

}