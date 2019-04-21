package werpx.marketopia.RoomDatabase;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;


@Dao
public interface WordDao {

    // insert for ordermenu
    @Insert
    void insert(mytable table);

    @Query("DELETE FROM product")
    void deleteAll();

    @Query("DELETE FROM product WHERE ID = :id")
    abstract void deleterow(long id);

    @Query("UPDATE product SET pitemn = :value1 WHERE pbar = :bar")
    void updateproduct(long value1, long bar);

    @Delete
    void deleteit(mytable model);

    @Query("SELECT COUNT(pid) FROM product")
    LiveData<Integer> getRowCount();

    @Query("SELECT * from product ")
   LiveData<List<mytable>>  getAllWords();



    //historytable
    @Insert
    void inserthis(historytable table);

    @Query("SELECT * from history ")
    LiveData<List<historytable>> getAllHis();

    @Query("DELETE FROM history")
    void deleteAllHis();


    //products table





    @Insert
    void insertproducts(Productltable productltable);

    @Query("SELECT isfavourit FROM products_table WHERE barcode = :barcode")
    LiveData<Integer> getfavouritvalue(String barcode);


    @Query("UPDATE products_table SET isfavourit = :value1 WHERE barcode = :bar")
    void updatefavouritvalue(long value1, long bar);

    @Insert
    void insertProductforlist(Productltable product);

    @Query("SELECT * FROM products_table WHERE barcode = :bar")
    Productltable findProduct(String bar);

    @Query("SELECT * from products_table ")
    LiveData<List<Productltable>> getallproductsroom();

    @Query("DELETE FROM products_table")
    void deleteAllproductroom();






}