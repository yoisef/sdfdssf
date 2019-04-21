package werpx.marketopia.RoomDatabase;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "product")
public class mytable {



    @PrimaryKey(autoGenerate = true)
    private int id;



    @ColumnInfo(name = "pid")
    private String pid;
    @ColumnInfo(name = "pname")
    private String pname;
    @ColumnInfo(name = "plocalname")
    private String plocalname;



    @ColumnInfo(name = "pbar")
    private String pbar;
    @ColumnInfo(name = "pitemn")
    private Integer pitemn;
    @ColumnInfo(name = "pimg")
    private String pimg;
    @ColumnInfo(name = "pdetail")
    private String pdetail;
    @ColumnInfo(name = "pprice")
    private String pprice;
    @ColumnInfo(name = "pcat")
    private String pcat;
    @ColumnInfo(name = "pimgblob")
    private byte[] pimgblob;



    public mytable(String pid, String pname , String pbar, Integer pitemn,
                   String pimg, String pdetail, String pprice, String pcat ,
                   String plocalname, byte[]pimgblob)
    {
        this.pid=pid;
        this.pbar=pbar;
        this.pcat=pcat;
        this.pitemn=pitemn;
        this.pdetail=pdetail;
        this.pimg=pimg;
        this.pprice=pprice;
        this.pname=pname;
        this.plocalname=plocalname;
        this.pimgblob=pimgblob;
    }
    public void setId(int id) {
        this.id = id;
    }


    public int getId() {
        return id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPbar() {
        return pbar;
    }

    public void setPbar(String pbar) {
        this.pbar = pbar;
    }
    public Integer getPitemn() {
        return pitemn;
    }

    public void setPitemn(Integer pitemn) {
        this.pitemn = pitemn;
    }

    public String getPimg() {
        return pimg;
    }

    public void setPimg(String pimg) {
        this.pimg = pimg;
    }

    public String getPdetail() {
        return pdetail;
    }

    public void setPdetail(String pdetail) {
        this.pdetail = pdetail;
    }

    public String getPprice() {
        return pprice;
    }

    public void setPprice(String pprice) {
        this.pprice = pprice;
    }

    public String getPcat() {
        return pcat;
    }

    public void setPcat(String pcat) {
        this.pcat = pcat;
    }

    public String getPlocalname() {
        return plocalname;
    }

    public void setPlocalname(String plocalname) {
        this.plocalname = plocalname;
    }

    public byte[] getPimgblob() {
        return pimgblob;
    }

    public void setPimgblob(byte[] pimgblob) {
        this.pimgblob = pimgblob;
    }


}