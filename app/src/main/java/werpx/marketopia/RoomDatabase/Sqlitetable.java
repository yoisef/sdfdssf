package werpx.marketopia.RoomDatabase;

public class Sqlitetable {


    private int id;




    private String pid;
    private String name;



    private byte[] imgblob;



    private int isfavourit;




    private String localnam;

    private String price;

    private String barcode;

    private String description;
    private String imge;







    public Sqlitetable(String pid,String name, String barcode, String price,
                       String imge, String description,String localname,byte[] imgblob,
                       int isfavourit



                         ) {

        this.pid=pid;
        this.name = name;
        this.price = price;
        this.barcode = barcode;
        this.description = description;
        this.imgblob=imgblob;

        this.localnam=localname;
        this.imge=imge;
        this.isfavourit=isfavourit;


    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
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

    public String getLocalnam() {
        return localnam;
    }

    public void setLocalnam(String localnam) {
        this.localnam = localnam;
    }

    public byte[] getImgblob() {
        return imgblob;
    }

    public void setImgblob(byte[] imgblob) {
        this.imgblob = imgblob;
    }

    public int getIsfavourit() {
        return isfavourit;
    }

    public void setIsfavourit(int isfavourit) {
        this.isfavourit = isfavourit;
    }








}