package werpx.marketopia.Marketopia;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import werpx.marketopia.GridAutofitLayoutManager;
import werpx.marketopia.R;
import werpx.marketopia.RoomDatabase.Productltable;
import werpx.marketopia.RoomDatabase.Sqlitetable;
import werpx.marketopia.RoomDatabase.mytable;
import werpx.marketopia.RoomDatabase.productViewmodel;
import werpx.marketopia.Utils;
import werpx.marketopia.adapters.showproduct_adapter;
import werpx.marketopia.adapters.showproduct_adaptercat1;

public class Moreactivity extends AppCompatActivity {

    RecyclerView morerecyclee;
    showproduct_adapter offeradapter;
    showproduct_adaptercat1 catg1;
    Utils utils;
    Toolbar mytoolbar;
    FloatingActionButton floatingActionButton;
    TextView totalordercoast;
    productViewmodel mWordViewModel;
  //  List<Productltable> products;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moreactivity);
      //  products=new ArrayList<>();
    //    getproductsroom();
        findviewbyid();

        settotalcoast();
    }



    public void findviewbyid()
    {
        utils=new Utils(this);
        offeradapter=new showproduct_adapter(this);
        catg1=new showproduct_adaptercat1(this);

      List<Sqlitetable> products= utils.getMydatabase().getproductsitems();

        offeradapter.setproducts(products);
        catg1.setproducts(products);
        floatingActionButton=findViewById(R.id.fabmore);
        totalordercoast=findViewById(R.id.coastmore);
        morerecyclee=findViewById(R.id.morerecycle);

        mytoolbar=findViewById(R.id.moretoolbar);
        setSupportActionBar(mytoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        Intent intent=getIntent();
       int morevalue= intent.getIntExtra("more",5);
       switch (morevalue)
       {
           case 0:{


               morerecyclee.setLayoutManager(new GridLayoutManager(this,2));
               morerecyclee.setAdapter(offeradapter);
               break;
           }
           case 1:{

               morerecyclee.setLayoutManager(new GridLayoutManager(this,3));
               morerecyclee.setAdapter(offeradapter);
               break;
           }
           case 2:{
               morerecyclee.setLayoutManager(new GridLayoutManager(this,3));
               morerecyclee.setAdapter(offeradapter);
               break;
           }
       }

       floatingActionButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               startActivity(new Intent(Moreactivity.this,Shoppingbasket.class));
           }
       });


    }
/*
    public void getproductsroom()
    {
        mWordViewModel = ViewModelProviders.of(this).get(productViewmodel.class);
        mWordViewModel.getAllproductroom().observe(this, new Observer<List<Productltable>>() {
            @Override
            public void onChanged(@Nullable List<Productltable> productltables) {

                products=productltables;
            }
        });
    }
    */
    public  void  settotalcoast()
    {

        mWordViewModel = ViewModelProviders.of(this).get(productViewmodel.class);

        mWordViewModel.getAllWords().observe(this, new Observer<List<mytable>>() {
            @Override
            public void onChanged(@Nullable final List<mytable> words) {


                Double allcoast = 0.0;

                // Update the cached copy of the words in the adapter.

                for (int i = 0; i < words.size(); i++) {

                    Double unitprice = Double.parseDouble(words.get(i).getPprice());
                    int quantity = words.get(i).getPitemn();
                    Double productcoast = unitprice * quantity;
                    allcoast = allcoast + productcoast;

                }
                totalordercoast.setText(new DecimalFormat("##.##").format(allcoast));



            }


        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
