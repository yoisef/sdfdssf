package werpx.marketopia.RoomDatabase;

public class Sqlieofflinehistory {



    private String id;
    private String ornum;
    private String ordata;
    private String oramount;
    private String orunits;
    private String orbarcodes;

    private String orretailerids;
    private String orproductids;
    private String orlives;



    private String quantities;



    public Sqlieofflinehistory( String ornum, String ordata,
                                String oramount, String orunits,String orbarcodes,
                                 String orretailerids, String orproductids,
                                String orlives,String quantities) {
        this.ornum = ornum;
        this.ordata = ordata;

        this.oramount = oramount;
        this.orunits = orunits;
        this.orbarcodes = orbarcodes;

        this.orretailerids = orretailerids;
        this.orproductids = orproductids;
        this.orlives = orlives;
        this.quantities=quantities;
    }



    public String getOrnum() {
        return ornum;
    }

    public String getOrdata() {
        return ordata;
    }



    public String getOramount() {
        return oramount;
    }

    public String getOrunits() {
        return orunits;
    }
    public String getOrbarcodes() {
        return orbarcodes;
    }



    public String getOrretailerids() {
        return orretailerids;
    }

    public String getOrproductids() {
        return orproductids;
    }

    public String getOrlives() {
        return orlives;
    }
    public String getQuantities() {
        return quantities;
    }


}
