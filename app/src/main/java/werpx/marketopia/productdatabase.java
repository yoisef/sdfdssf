package werpx.marketopia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import werpx.marketopia.RoomDatabase.Sqlieofflinehistory;
import werpx.marketopia.RoomDatabase.Sqlitetable;
import werpx.marketopia.productmodels.getallproductsroot;


public class productdatabase extends SQLiteOpenHelper {


    public static final String Databasename = "products.db";
    public static final String Tablename1 = "products_list";
    public static final String Tablename2 = "offline_sales";
    public static final String Tablename3 = "offline_products";
    public static final String Tablename5 = "finalof_products";
    public static final String Tablename4 = "productsids_table";
    public static final String Tablename6 = "user_info";



    public static final String columna = "Pro_name";
    public static final String columnb = "Pro_bar";
    public static final String columnc = "Pro_price";
    public static final String columnd = "Pro_img";
    public static final String columne = "Pro_detail";
    public static final String columnf = "Pro_id";
    public static final String columng = "Pro_localnam";
    public static final String columnh = "Prod_img";
    public static final String columni = "Prod_fav";


    public static final String column1 = "product_bar";
    public static final String column2 = "user_img";







    public static final String columnone = "ornum";
    public static final String columntwo = "ordata";
    public static final String columnthree = "oramount";
    public static final String columnfour = "orunits";
    public static final String columnfive = "orbarcodes";
    public static final String columnsix = "orretailerids";
    public static final String columnseven = "orproductids";
    public static final String columneight = "orlives";
    public static final String columnnine = "orquantity";


    public static final String productidcolumn ="productid";




    public productdatabase(Context context) {
        super(context, Databasename, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String example = "CREATE TABLE " + Tablename1 + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,Pro_name TEXT ,Pro_bar TEXT ,Pro_price TEXT ,Pro_img TEXT ,Pro_detail TEXT,Pro_id TEXT,Pro_localnam TEXT,Prod_img BLOB,Prod_fav INTEGER DEFAULT 0);";
        String oflinetable = "CREATE TABLE " + Tablename2 + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,ornum TEXT ,ordata TEXT  ,oramount TEXT ,orunits TEXT ,orbarcodes TEXT,orretailerids TEXT ,orproductids TEXT ,orlives TEXT ,orquantity TEXT);";
        String offlineproducts = "CREATE TABLE " + Tablename3 + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,Pro_name TEXT ,Pro_bar TEXT ,Pro_price TEXT ,Pro_img TEXT ,Pro_detail TEXT);";
        String productsids = "CREATE TABLE " + Tablename4 + " (ID INTEGER PRIMARY KEY AUTOINCREMENT ,productid TEXT);";
        String finalproducts = "CREATE TABLE " + Tablename5 + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,Pro_name TEXT ,Pro_bar TEXT ,Pro_price TEXT ,Pro_img TEXT ,Pro_detail TEXT);";
        String userinfo = "CREATE TABLE " + Tablename6 + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,product_bar TEXT ,Prod_img BLOB );";


        db.execSQL(example);
        db.execSQL(oflinetable);
        db.execSQL(offlineproducts);
        db.execSQL(productsids);
        db.execSQL(finalproducts);
        db.execSQL(userinfo);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + Tablename1);
        db.execSQL("DROP TABLE IF EXISTS " + Tablename2);
        db.execSQL("DROP TABLE IF EXISTS " + Tablename3);
        db.execSQL("DROP TABLE IF EXISTS " + Tablename4);
        db.execSQL("DROP TABLE IF EXISTS " + Tablename5);
        db.execSQL("DROP TABLE IF EXISTS " + Tablename6);


        onCreate(db);

    }


    public Boolean insertofflineproductsids(String id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(productidcolumn, id);
        long result = db.insert(Tablename4, null, contentValues);

        if (result == -1) {
            return false;

        } else {
            return true;
        }
    }

    public ArrayList<String> getofflineproductsid()
    {
        SQLiteDatabase db = this.getReadableDatabase();

        String myquery = "SELECT * FROM " + Tablename4 + ";";


        Cursor cursor = db.rawQuery(myquery, null);

       ArrayList<String> ids=new ArrayList<>();
        while (cursor.moveToNext()) {

            int index=cursor.getColumnIndexOrThrow(productidcolumn);
            String id=cursor.getString(index);
            ids.add(id);

        }
        cursor.close();



        //add to list here


        return ids;
    }


    public Boolean insertofflieproducts(String name, String bar, String price, String img, String detail ,String localnam)
    {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(columna, name);
        contentValues.put(columnb, bar);
        contentValues.put(columnc, price);
        contentValues.put(columnd, img);
        contentValues.put(columne, detail);
        contentValues.put(columng, localnam);


        long result = db.insert(Tablename3, null, contentValues);

        if (result == -1) {
            return false;

        } else {
            return true;
        }
    }
    public Boolean insertofflinefinaleproducts(String name, String bar, String price, String img, String detail ,String localnam)
    {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(columna, name);
        contentValues.put(columnb, bar);
        contentValues.put(columnc, price);
        contentValues.put(columnd, img);
        contentValues.put(columne, detail);
        contentValues.put(columng, localnam);


        long result = db.insert(Tablename5, null, contentValues);

        if (result == -1) {
            return false;

        } else {
            return true;
        }
    }

    public List<Sqlitetable> getofflineproducts()
    {
        SQLiteDatabase db = this.getReadableDatabase();

        String myquery = "SELECT * FROM " + Tablename3 + ";";


        Cursor cursor = db.rawQuery(myquery, null);

        List<Sqlitetable> myproducts=new ArrayList<>();
        while (cursor.moveToNext()) {

            int index=cursor.getColumnIndexOrThrow(columnb);
            String barcode=cursor.getString(index);
            int indexloc=cursor.getColumnIndexOrThrow(columng);
            String localnam=cursor.getString(indexloc);
            int indexx=cursor.getColumnIndexOrThrow(columnc);
            String price=cursor.getString(indexx);
            int indexxxx = cursor.getColumnIndexOrThrow(columnd);
            String img = cursor.getString(indexxxx);
            int indexxxxx = cursor.getColumnIndexOrThrow(columnd);
           byte[] imgblob = cursor.getBlob(indexxxxx);

            myproducts.add(new Sqlitetable(null,null,barcode,price,img,null,localnam,null,0));

        }
        cursor.close();



        //add to list here


        return myproducts;
    }

    public Boolean insertdatalistproducts(String name, String bar, String price, String img, String detail ,String idproduct,String localnam,byte[] imgblob,Integer fav) {



        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(columna, name);
        contentValues.put(columnb, bar);
        contentValues.put(columnc, price);
        contentValues.put(columnd, img);
        contentValues.put(columne, detail);
        contentValues.put(columnf,idproduct);
        contentValues.put(columng,localnam);
        contentValues.put(columnh,imgblob);
        contentValues.put(columni,fav);

        long result = db.insert(Tablename1, null, contentValues);

        if (result == -1) {
            return false;

        } else {
            return true;
        }
    }

    public Boolean updatefavcondition(String barcode,int value) {
        SQLiteDatabase dbr = this.getReadableDatabase();
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        long result=-1;

        String getrow = "SELECT Prod_fav FROM " + Tablename1 + " WHERE " + columnb + " = " + barcode + ";";


                cv.put(columni, value);

               result = db.update(Tablename1, cv, "Pro_bar= '" + barcode + "'", null);

        if (result == -1) {
            return false;

        } else {
            return true;
        }





    }


    public List<Sqlitetable> getofflinefinalproducts()
    {
        SQLiteDatabase db = this.getReadableDatabase();

        String myquery = "SELECT * FROM " + Tablename5 + ";";


        Cursor cursor = db.rawQuery(myquery, null);

        List<Sqlitetable> myproducts=new ArrayList<>();
        while (cursor.moveToNext()) {

            int index=cursor.getColumnIndexOrThrow(columnb);
            String barcode=cursor.getString(index);
            int indexloc=cursor.getColumnIndexOrThrow(columng);
            String localnam=cursor.getString(indexloc);
            int indexx=cursor.getColumnIndexOrThrow(columnc);
            String price=cursor.getString(indexx);
            int indexxxx = cursor.getColumnIndexOrThrow(columnd);
            String img = cursor.getString(indexxxx);
            int indexxxxk = cursor.getColumnIndexOrThrow(columnh);
            byte[] imgblob = cursor.getBlob(indexxxxk);


            myproducts.add(new Sqlitetable(null,null,barcode,price,img,null,localnam,null,0));

        }
        cursor.close();



        //add to list here


        return myproducts;
    }



    public Boolean insertofflinesales(String ornum,String ordata ,String oramount,String Orunits ,String orbarcodes,String orretailerids ,String orproductids,String orlives,String quantities)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(columnone,ornum);
        contentValues.put(columntwo,ordata);
        contentValues.put(columnthree,oramount);
        contentValues.put(columnfour,Orunits);
        contentValues.put(columnfive,orbarcodes);
        contentValues.put(columnsix,orretailerids);
        contentValues.put(columnseven,orproductids);
        contentValues.put(columneight,orlives);
        contentValues.put(columnnine,quantities);


        long result = db.insert(Tablename2, null, contentValues);
        if (result == -1) {
            return false;

        } else {
            return true;
        }

    }

    public Boolean insertdatalistproducts(String name, String bar, String price, String img, String detail ,String idproduct,String localnam,byte[] imgblob) {



        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(columna, name);
        contentValues.put(columnb, bar);
        contentValues.put(columnc, price);
        contentValues.put(columnd, img);
        contentValues.put(columne, detail);
        contentValues.put(columnf,idproduct);
        contentValues.put(columng,localnam);
        contentValues.put(columnh,imgblob);

        long result = db.insert(Tablename1, null, contentValues);

        if (result == -1) {
            return false;

        } else {
            return true;
        }
    }


    public List<Sqlieofflinehistory> getofflinesales()
    {
        SQLiteDatabase db = this.getReadableDatabase();

        String myquery = "SELECT * FROM " + Tablename2 + ";";


        Cursor cursor = db.rawQuery(myquery, null);

        List<Sqlieofflinehistory> myproducts=new ArrayList<>();
        while (cursor.moveToNext()) {

            int index=cursor.getColumnIndexOrThrow(columnone);
            String ornum=cursor.getString(index);
            int indexx = cursor.getColumnIndexOrThrow(columntwo);
            String ordata = cursor.getString(indexx);
            int indexxx = cursor.getColumnIndexOrThrow(columnthree);
            String oramount = cursor.getString(indexxx);
            int indexxxx = cursor.getColumnIndexOrThrow(columnfour);
            String orunits = cursor.getString(indexxxx);
            int indexxxxx = cursor.getColumnIndexOrThrow(columnfive);
            String orbarcodes  = cursor.getString(indexxxxx);
            int indexxxxxx=cursor.getColumnIndexOrThrow(columnsix);
            String retailersids=cursor.getString(indexxxxxx);
            int indexxxxxxx=cursor.getColumnIndexOrThrow(columnseven);
            String productids=cursor.getString(indexxxxxxx);
            int indexxxxxxxx=cursor.getColumnIndexOrThrow(columneight);
            String lives=cursor.getString(indexxxxxxxx);
            int last=cursor.getColumnIndexOrThrow(columnnine);
            String quantites=cursor.getString(last);

            myproducts.add(new Sqlieofflinehistory(ornum,ordata,oramount,orunits,orbarcodes,retailersids,productids,lives,quantites));

        }
        cursor.close();



        //add to list here


        return myproducts;
    }

    public void deleteofflinesales()
    {
        SQLiteDatabase db = this.getWritableDatabase();
         db.delete(Tablename2,null,null);
        db.execSQL("delete from "+ Tablename2);
        db.close();
    }

    public void deleteofflineproducts()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Tablename3,null,null);
        db.execSQL("delete from "+ Tablename3);
        db.close();
    }
    public void deleteofflinefinalproducts()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Tablename5,null,null);
        db.execSQL("delete from "+ Tablename5);
        db.close();
    }

    public void deleteproductitems()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Tablename1,null,null);
        db.execSQL("delete from "+ Tablename1);
        db.close();

    }

    public void deleteproductsids()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Tablename4,null,null);
        db.execSQL("delete from "+ Tablename4);
        db.close();

    }

    public void deleterowofofflineproducts(String barcode)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " +Tablename3+ " WHERE "+columnb+"='"+barcode+"'");
        db.close();


    }



    public List<Sqlitetable> getproductsitems()
    {
        SQLiteDatabase db = this.getReadableDatabase();

        String myquery = "SELECT * FROM " + Tablename1 + ";";


        Cursor cursor = db.rawQuery(myquery, null);

        List<Sqlitetable> myproducts=new ArrayList<>();
        while (cursor.moveToNext()) {


            int indexnam=cursor.getColumnIndexOrThrow(columna);
            String name=cursor.getString(indexnam);

            int indexlocalnam=cursor.getColumnIndexOrThrow(columng);
            String namelocal=cursor.getString(indexlocalnam);

            int index=cursor.getColumnIndexOrThrow(columnb);
            String barcode=cursor.getString(index);

            int pricenum=cursor.getColumnIndexOrThrow(columnc);
            String price=cursor.getString(pricenum);
                int indexxxx = cursor.getColumnIndexOrThrow(columnd);
                String img = cursor.getString(indexxxx);
                int indexproduct=cursor.getColumnIndexOrThrow(columnf);
                String idproduct=cursor.getString(indexproduct);
            int indeximgblob=cursor.getColumnIndexOrThrow(columnh);
          byte[] blob=cursor.getBlob(indeximgblob);
            int fav=cursor.getColumnIndexOrThrow(columni);
           int j=cursor.getInt(fav);


            myproducts.add(new Sqlitetable(idproduct,name,barcode,price,img,null,namelocal,blob,j));

            }
            cursor.close();



            //add to list here


        return myproducts;
    }


    public Sqlitetable getdataforrowinproduct(String barsearch) {
        SQLiteDatabase db = this.getReadableDatabase();
      Sqlitetable mytable =null;

        // Filter results WHERE "title" = 'My Title'
        String myquery = "SELECT * FROM " + Tablename1 + " WHERE " + columnb + "=" + barsearch + ";";


        Cursor cursor = db.rawQuery(myquery, null);

     try {
         db.beginTransaction();
         if (cursor.getCount() > 0) {
             cursor.moveToFirst();
             int index = cursor.getColumnIndexOrThrow(columna);
             String nam = cursor.getString(index);
             int indexlocal = cursor.getColumnIndexOrThrow(columng);
             String localnam = cursor.getString(indexlocal);
             int indexx = cursor.getColumnIndexOrThrow(columnb);
             String bar = cursor.getString(indexx);
             int indexxx = cursor.getColumnIndexOrThrow(columnc);
             String price = cursor.getString(indexxx);
             int indexxxx = cursor.getColumnIndexOrThrow(columnd);
             String img = cursor.getString(indexxxx);
             int indexxxxx = cursor.getColumnIndexOrThrow(columne);
             String detail = cursor.getString(indexxxxx);
             int indexid=cursor.getColumnIndexOrThrow(columnf);
             String idproduct=cursor.getString(indexid);
             int indeximg=cursor.getColumnIndexOrThrow(columnh);
            byte[]imgblob=cursor.getBlob(indeximg);

             mytable = new Sqlitetable(idproduct,nam, bar, price, img, detail,localnam,imgblob,0);
     }
     db.setTransactionSuccessful();


            //add to list here
     } catch (Exception e) {
         Log.w("Exception:", e);
     } finally {
         db.endTransaction();
     }
        return mytable;
    }

    public Sqlitetable getdataforrowinproductoffline(String barsearch) {
        SQLiteDatabase db = this.getReadableDatabase();
      Sqlitetable mytable =null;

        // Filter results WHERE "title" = 'My Title'
        String myquery = "SELECT * FROM " + Tablename3 + " WHERE " + columnb + "=" + barsearch + ";";


        Cursor cursor = db.rawQuery(myquery, null);

        try {
            db.beginTransaction();
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                int index = cursor.getColumnIndexOrThrow(columna);
                String nam = cursor.getString(index);

                int indexlocal = cursor.getColumnIndexOrThrow(columng);
                String namlocal = cursor.getString(indexlocal);
                int indexx = cursor.getColumnIndexOrThrow(columnb);
                String bar = cursor.getString(indexx);
                int indexxx = cursor.getColumnIndexOrThrow(columnc);
                String price = cursor.getString(indexxx);
                int indexxxx = cursor.getColumnIndexOrThrow(columnh);
               byte[] imgblon = cursor.getBlob(indexxxx);


                mytable = new Sqlitetable(null,nam, bar, price, null, null,namlocal,imgblon,0);
            }
            db.setTransactionSuccessful();


            //add to list here
        } catch (Exception e) {
            Log.w("Exception:", e);
        } finally {
            db.endTransaction();
        }
        return mytable;
    }
    public Sqlitetable getdataforrowinfinalproductoffline(String barsearch) {
        SQLiteDatabase db = this.getReadableDatabase();
        Sqlitetable mytable =null;

        // Filter results WHERE "title" = 'My Title'
        String myquery = "SELECT * FROM " + Tablename5 + " WHERE " + columnb + "=" + barsearch + ";";


        Cursor cursor = db.rawQuery(myquery, null);

        try {
            db.beginTransaction();
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                int index = cursor.getColumnIndexOrThrow(columna);
                String nam = cursor.getString(index);
                int indexlocal = cursor.getColumnIndexOrThrow(columng);
                String namlocal = cursor.getString(indexlocal);
                int indexx = cursor.getColumnIndexOrThrow(columnb);
                String bar = cursor.getString(indexx);
                int indexxx = cursor.getColumnIndexOrThrow(columnc);
                String price = cursor.getString(indexxx);
                int indexxxx = cursor.getColumnIndexOrThrow(columnh);
               byte[] imgblob = cursor.getBlob(indexxx);


                mytable = new Sqlitetable(null,nam, bar, price, null, null,namlocal,imgblob,0);
            }
            db.setTransactionSuccessful();


            //add to list here
        } catch (Exception e) {
            Log.w("Exception:", e);
        } finally {
            db.endTransaction();
        }
        return mytable;
    }



    public void getallproducts(final Context con)
    {

        final SQLiteDatabase db = this.getReadableDatabase();
        Call<getallproductsroot> callproducts;

         String usertoken = con.getSharedPreferences("token", Context.MODE_PRIVATE).getString("usertoken","def");


       Retrofitclient myretro= Retrofitclient.getInstance();
        Retrofit retrofittok=  myretro.getretro();
        final Endpoints myendpoints = retrofittok.create(Endpoints.class);

        callproducts=myendpoints.getproductdetails("Bearer "+usertoken);
        callproducts.enqueue(new Callback<getallproductsroot>() {
            @Override
            public void onResponse(Call<getallproductsroot> call, Response<getallproductsroot> response) {

                if (response.isSuccessful())

                {


                    int sizee=response.body().getProducts().size();

                    try {
                        db.beginTransaction();
                        for (int i=0;i<sizee;i++)
                        {

                            String barcode= response.body().getProducts().get(i).getBarcode();
                            String img=response.body().getProducts().get(i).getImage().getUrl();
                            String price=response.body().getProducts().get(i).getPrice();
                            String name=response.body().getProducts().get(i).getName();
                            String desc=response.body().getProducts().get(i).getDescription();
                            //insertdatalistproducts(name,barcode,price,img,desc);


                        }
                        db.setTransactionSuccessful();

                    } catch (Exception e) {
                        Log.w("Exception:", e);
                    } finally {
                        db.endTransaction();
                    }





                }
                else
                {
                    Toast.makeText(con,"null",Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<getallproductsroot> call, Throwable t) {

                Toast.makeText(con
                        ,"failed",Toast.LENGTH_LONG).show();

            }
        });

    }

    public long getProfilesCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(db, Tablename1);
        db.close();
        return count;
    }

    public Boolean adduser( String barcode, byte[] image) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new  ContentValues();
        cv.put(column1,  barcode);
        cv.put(column2,   image);
        long result = db.insert(Tablename6, null, cv);
        if (result == -1) {
            return false;

        } else {
            return true;
        }
    }

    public byte[] getuserimgbyname(String bar)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        String myquerye="select * from user_info where product_bar='"+bar+"'";
        Cursor cursor = db.rawQuery(myquerye,null);
        byte[] image=null;

        while (cursor.moveToNext()) {

            int index=cursor.getColumnIndexOrThrow(column2);
            image=cursor.getBlob(index);

        }
        cursor.close();

        return image;



    }

    public Boolean updateimagevalues(byte[] mimg,String barcode)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(columnh,mimg);

       String update="UPDATE "+Tablename1+" SET Pro_bar = 'Texas' WHERE ID = 6" ;

      long result=  db.update(Tablename1,cv, "Pro_bar= '"+barcode+"'",null);


        if (result == -1) {
            return false;

        } else {
            return true;
        }
    }







}



