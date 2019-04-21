package werpx.marketopia.adapters;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.LayoutDirection;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import werpx.marketopia.Endpoints;
import werpx.marketopia.GlideApp;
import werpx.marketopia.Marketopia.Shoppingbasket;
import werpx.marketopia.R;
import werpx.marketopia.Retrofitclient;
import werpx.marketopia.RoomDatabase.Productltable;
import werpx.marketopia.RoomDatabase.Sqlitetable;
import werpx.marketopia.RoomDatabase.mytable;
import werpx.marketopia.RoomDatabase.productViewmodel;
import werpx.marketopia.productdatabase;
import werpx.marketopia.productmodels.Rootproductdetail;

public class showproduct_adaptercat1 extends RecyclerView.Adapter<showproduct_adaptercat1.viewholder> {


    Context mycontext;
    private productdatabase mydatabase;
    private List<Sqlitetable> products;
    private List<mytable> myproducts;
    private productViewmodel mWordViewModel;
    private Call<Rootproductdetail> mcall;
    private SharedPreferences prefs;
    private String usertoken;
    String CurrentLang;
    private Configuration configuration;
    private int applanguage;
    Boolean netornot=true;




    public showproduct_adaptercat1(Context con)
    {
        this.mycontext=con;
        products=new ArrayList<>();
        CurrentLang = Locale.getDefault().getLanguage();

        prefs = mycontext.getSharedPreferences("token", Context.MODE_PRIVATE);
        usertoken=prefs.getString("usertoken","def");
        configuration = con.getResources().getConfiguration();
        applanguage= configuration.getLayoutDirection();
        mydatabase=new productdatabase(con);

        mWordViewModel = ViewModelProviders.of((FragmentActivity) con).get(productViewmodel.class);
        mWordViewModel.getAllWords().observe((LifecycleOwner) con, new Observer<List<mytable>>() {
            @Override
            public void onChanged(@Nullable final List<mytable> words) {


                myproducts=words;
                Double allcoast=0.0;

                // Update the cached copy of the words in the adapter.

                for(int i=0;i<words.size();i++)
                {

                    Double unitprice=Double.parseDouble(words.get(i).getPprice());
                    int quantity=words.get(i).getPitemn();
                    Double productcoast=unitprice*quantity;
                    allcoast=allcoast+productcoast;

                }
                //   pricetotal.setText(String.valueOf(allcoast));

            }


        });

    }


    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(mycontext).inflate(R.layout.rowshowproduct, parent, false);
        return new showproduct_adaptercat1.viewholder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull final viewholder holder, final int position) {


        if (products!=null)
        {

            // if(haveNetworkConnection()) {


            if (products.get(position).getImge()== null) {

                GlideApp.with(mycontext)
                        .load(mycontext.getResources().getDrawable(R.drawable.default_product))
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(holder.myimg);

            } else {

                GlideApp.with(mycontext)
                        .load(products.get(position).getImge())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(holder.myimg);


            }
                 /*
            }

           else {

                if (products.get(position).getImgblob()!=null)
                {
                    GlideApp.with(mycontext)
                            .load(products.get(position).getImgblob())
                            .into(holder.myimg);
                }
                else{
                    GlideApp.with(mycontext)
                            .load(mycontext.getResources().getDrawable(R.drawable.default_product))
                            .into(holder.myimg);
                }

*/



            int language= checkapplanguage();
            if (language==LayoutDirection.RTL)
            {
                String price= convertToArabic( products.get(position).getPrice()) ;

                holder.textprice.setText(price);
            }
            else {
                holder.textprice.setText(products.get(position).getPrice());
            }


            holder.mycard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {




                        checkifproductexsist(products.get(position).getBarcode(),holder.mycard, position);






                }
            });
        }


    }

    @Override
    public int getItemCount() {

        return products.size();
    }

    public  void setproducts(List<Sqlitetable> prducts) {
        products = prducts;
        notifyDataSetChanged();
    }


    private void checkifproductexsist(String barcod, final CardView mycard,int lastpot)
    {
        boolean mycondition=true;

        //  mycard.setClickable(false);

        int lastposition=mycontext.getSharedPreferences("lastpostion",Context.MODE_PRIVATE).getInt("pos",0);
        // Update the cached copy of the words in the adapter.
        int i;


        if (myproducts.size() != 0) {


            for (i = 0; i < myproducts.size(); i++) {
                if (barcod.trim().equals(myproducts.get(i).getPbar().trim())) {

                    mytable current = myproducts.get(i);
                    // int totalitems = current.getPitemn() + Integer.parseInt(numitems);
                    mWordViewModel.updateproduct(current.getPitemn()+1,Long.parseLong(barcod));
                    mycondition=false;

                    //    mycard.setClickable(true);

                    if (lastposition!=i)
                    {



                    }


                }

            }
            if (mycondition)
            {

                getproductbybarcode(barcod,mycard);
                // loginwithenternumber(barcod,mycard);

            }

        }
        else{
            getproductbybarcode(barcod,mycard);

            // loginwithenternumber(barcod,mycard);
        }

    }

    private void getproductbybarcode(final String barcode, final CardView card)
    {
        card.setClickable(true);


        List<Productltable> productltable=mWordViewModel.getAllproductroom().getValue();
        Sqlitetable mytable= mydatabase.getdataforrowinproduct(barcode);
        Sqlitetable mytablee= mydatabase.getdataforrowinfinalproductoffline(barcode);

        if (mytable!=null)
        {
            mWordViewModel.insert(new mytable(mytable.getPid(),mytable.getName(),mytable.getBarcode(),Integer.parseInt("1"),mytable.getImge(),mytable.getDescription(),mytable.getPrice(),null,mytable.getLocalnam(),mytable.getImgblob()));

            //

        }
        else if(mytablee!=null)
        {
            mWordViewModel.insert(new mytable(mytablee.getPid(),mytablee.getName(),mytablee.getBarcode(),Integer.parseInt("1"),mytablee.getImge(),mytablee.getDescription(),mytablee.getPrice(),null,mytablee.getLocalnam(),mytablee.getImgblob()));


        }
        else
        {
            Toast.makeText(mycontext,mycontext.getResources().getString(R.string.notrecorded),Toast.LENGTH_LONG).show();
        }

    }
    private void loginwithenternumber(final String barcode, final CardView card) {


        Retrofitclient myretro= Retrofitclient.getInstance();
        Retrofit retrofittok=  myretro.getretro();
        final Endpoints myendpoints = retrofittok.create(Endpoints.class);

        mcall = myendpoints.getdetails("Bearer "+usertoken,barcode);
        mcall.enqueue(new Callback<Rootproductdetail>() {
            @Override
            public void onResponse(Call<Rootproductdetail> call, Response<Rootproductdetail> response) {

                if (response.isSuccessful()) {

                    String pid,pronam, prodbar, prodimg, broddetail, brodprice, prodcat;
                    String namepr,localnam,arabic;


                    if (response.body().getProduct()!=null) {

                        if (response.body().getProduct().getImage()==null)
                        {
                            pid=response.body().getProduct().getId();



                            namepr=response.body().getProduct().getName();
                            localnam=response.body().getProduct().getLocalname();
                            prodbar = response.body().getProduct().getBarcode();
                            broddetail = response.body().getProduct().getDescription();
                            brodprice = response.body().getProduct().getPrice();
                            prodcat = response.body().getProduct().getCategory().getName();
                            int lan= checkapplanguage();
                            if (lan==LayoutDirection.RTL)
                            {
                                mytable word = new mytable(pid,namepr, prodbar,Integer.parseInt("١"), null, broddetail, brodprice, prodcat,localnam,null);
                                mWordViewModel.insert(word);
                                Handler myhandler=new Handler();
                                myhandler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        ((Shoppingbasket) mycontext).scrolltodown();
                                    }
                                },400);
                            }
                            else {
                                mytable word = new mytable(pid,namepr, prodbar,Integer.parseInt("1"), null, broddetail, brodprice, prodcat,localnam,null);
                                mWordViewModel.insert(word);
                                Handler myhandler=new Handler();
                                myhandler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        ((Shoppingbasket) mycontext).scrolltodown();
                                    }
                                },400);
                            }



                        }
                        else {
                            prodimg = response.body().getProduct().getImage().getFilename();
                            String imgurl=mycontext.getResources().getString(R.string.baseurlimgproduct)+prodimg;


                            pid=response.body().getProduct().getId();
                            namepr=response.body().getProduct().getName();
                            localnam=response.body().getProduct().getLocalname();
                            prodbar = response.body().getProduct().getBarcode();
                            broddetail = response.body().getProduct().getDescription();
                            brodprice = response.body().getProduct().getPrice();
                            prodcat = response.body().getProduct().getCategory().getName();
                            mytable word = new mytable(pid,namepr, prodbar,Integer.parseInt("1"), imgurl, broddetail, brodprice, prodcat,localnam,null);

                            mWordViewModel.insert(word);
                            // int y=((Sales_Screen) mycontext).adapteradded.getItemCount();
                            //     Toast.makeText(mycontext,String.valueOf(y),Toast.LENGTH_LONG).show();
                            //    ((Sales_Screen) mycontext).adapteradded.setWords(myproducts);
                            //   ((Sales_Screen) mycontext).adapteradded.notifycange();
                            Handler myhandler=new Handler();
                            myhandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    ((Shoppingbasket) mycontext).scrolltodown();
                                }
                            },400);


                        }

                        // myrecycle.scrollToPosition(myrecycle.getAdapter().getItemCount() - 1);


                        card.setClickable(true);

                    }
                    else
                    {
                        Toast.makeText(mycontext,mycontext.getResources().getString(R.string.notrecorded),Toast.LENGTH_LONG).show();
                    }

                } else {

                    card.setClickable(true);
                }


            }

            @Override
            public void onFailure(Call<Rootproductdetail> call, Throwable t) {



                card.setClickable(true);




                Sqlitetable mytable= mydatabase.getdataforrowinproduct(barcode);
                Sqlitetable mytablee= mydatabase.getdataforrowinfinalproductoffline(barcode);

                if (mytable!=null)
                {
                    mWordViewModel.insert(new mytable(mytable.getPid(),mytable.getName(),mytable.getBarcode(),Integer.parseInt("1"),mytable.getImge(),mytable.getDescription(),mytable.getPrice(),null,mytable.getLocalnam(),mytable.getImgblob()));

                    ((Shoppingbasket) mycontext).scrolltodown();

                }
                else if(mytablee!=null)
                {
                    mWordViewModel.insert(new mytable(mytablee.getPid(),mytablee.getName(),mytablee.getBarcode(),Integer.parseInt("1"),mytablee.getImge(),mytablee.getDescription(),mytablee.getPrice(),null,mytablee.getLocalnam(),mytablee.getImgblob()));

                    ((Shoppingbasket) mycontext).scrolltodown();
                }
                else
                {
                    Toast.makeText(mycontext,mycontext.getResources().getString(R.string.notrecorded),Toast.LENGTH_LONG).show();
                }






            }
        });





    }

    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager)mycontext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

    public String convertToArabic (String value){

        String newValue = (((((((((((value + "")
                .replaceAll("1", "١")).replaceAll("2", "٢"))
                .replaceAll("3", "٣")).replaceAll("4", "٤"))
                .replaceAll("5", "٥")).replaceAll("6", "٦"))
                .replaceAll("7", "٧")).replaceAll("8", "٨"))
                .replaceAll("9", "٩")).replaceAll("0", "٠"));
        return newValue;
    }

    public int checkapplanguage()
    {
        Configuration configuration;
        int applanguage;
        configuration = mycontext.getResources().getConfiguration();
        applanguage= configuration.getLayoutDirection();
        return applanguage;
    }


    class viewholder extends RecyclerView.ViewHolder{

        ImageView myimg;
        CardView mycard;
        TextView textprice;

        public viewholder(View itemView) {
            super(itemView);

            myimg=itemView.findViewById(R.id.productshow);
            mycard=itemView.findViewById(R.id.productcard);
            textprice=itemView.findViewById(R.id.product_pricee);
        }
    }
}
