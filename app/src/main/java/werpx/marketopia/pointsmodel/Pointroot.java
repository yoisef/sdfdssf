package werpx.marketopia.pointsmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pointroot {

    @SerializedName("operation")
    @Expose
    private String operation;
    @SerializedName("data")
    @Expose
    private Data data;

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

}