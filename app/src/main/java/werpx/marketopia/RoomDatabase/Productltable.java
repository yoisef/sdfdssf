package werpx.marketopia.RoomDatabase;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;


@Entity(tableName = "products_table")
public class Productltable {


    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "price")
    private String price;

    @ColumnInfo(name = "barcode")
    private String barcode;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "imge")
    private String imge;

    @ColumnInfo(name = "localname")
    private String localname;

    @ColumnInfo(name = "isfavourit")
    private Integer isfavourit;

    @ColumnInfo(name = "prodid")
    private String prodid;



    public Productltable(String name, String price, String barcode,
                         String description, String imge, String localname, Integer isfavourit, String prodid



    ) {

        this.name = name;
        this.price = price;
        this.barcode = barcode;
        this.description = description;
        this.imge=imge;
        this.localname=localname;
        this.isfavourit=isfavourit;
        this.prodid=prodid;


    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getImge() {
        return imge;
    }

    public void setImge(String imge) {
        this.imge = imge;
    }


    public String getLocalname() {
        return localname;
    }

    public void setLocalname(String localname) {
        this.localname = localname;
    }

    public Integer getIsfavourit() {
        return isfavourit;
    }

    public void setIsfavourit(Integer isfavourit) {
        this.isfavourit = isfavourit;
    }

    public String getProdid() {
        return prodid;
    }

    public void setProdid(String prodid) {
        this.prodid = prodid;
    }





}