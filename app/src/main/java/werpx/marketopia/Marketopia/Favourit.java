package werpx.marketopia.Marketopia;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import werpx.marketopia.GridAutofitLayoutManager;
import werpx.marketopia.R;
import werpx.marketopia.RoomDatabase.Productltable;
import werpx.marketopia.RoomDatabase.Sqlitetable;
import werpx.marketopia.RoomDatabase.productViewmodel;
import werpx.marketopia.Utils;
import werpx.marketopia.adapters.favouritadapter;

public class Favourit extends AppCompatActivity {

    Toolbar mtoolbar;
    RecyclerView recyclerView;
    favouritadapter favouritadapter;
    private productViewmodel mWordViewModel;
    List<Sqlitetable> favouritmenu;
    List<Sqlitetable> sqlitetables;
    Utils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourit);

        utils=new Utils(this);

        sqlitetables=utils.getMydatabase().getproductsitems();

        favouritmenu=new ArrayList<>();

        favouritadapter=new favouritadapter(this);
        findviewbyid();

    }


    void findviewbyid()
    {
        mtoolbar=findViewById(R.id.favourittoolbar);
        recyclerView=findViewById(R.id.favouritrecycle);




        for (int i=0;i<sqlitetables.size();i++)
        {
            if (sqlitetables.get(i).getIsfavourit()==1)
            {
                favouritmenu.add(sqlitetables.get(i));
            }
        }
        favouritadapter.setproducts(favouritmenu);


/*
        mWordViewModel = ViewModelProviders.of(this).get(productViewmodel.class);
        mWordViewModel.getAllproductroom().observe(this, new Observer<List<Productltable>>() {
            @Override
            public void onChanged(@Nullable List<Productltable> productltables) {

                for (int i=0;i<productltables.size();i++)
                {
                    if (productltables.get(i).getIsfavourit()==1)
                    {
                        favouritmenu.add(productltables.get(i));
                    }
                }
                favouritadapter.setproducts(favouritmenu);

            }
        });
*/
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setAdapter(favouritadapter);

        setSupportActionBar(mtoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

    }

    @Override
    public boolean onSupportNavigateUp () {
        onBackPressed();
        return true;
    }
}
