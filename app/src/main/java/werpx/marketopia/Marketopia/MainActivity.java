package werpx.marketopia.Marketopia;

import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;

import java.io.ByteArrayOutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import werpx.marketopia.Downloadimageservice;
import werpx.marketopia.Endpoints;
import werpx.marketopia.GlideApp;
import werpx.marketopia.NetworkBroadcastreciever;
import werpx.marketopia.R;
import werpx.marketopia.Retrofitclient;
import werpx.marketopia.RoomDatabase.Productltable;
import werpx.marketopia.RoomDatabase.Sqlitetable;
import werpx.marketopia.RoomDatabase.mytable;
import werpx.marketopia.RoomDatabase.productViewmodel;
import werpx.marketopia.Utils;
import werpx.marketopia.adapters.showproduct_adapter;
import werpx.marketopia.adapters.showproduct_adaptercat1;
import werpx.marketopia.productdatabase;
import werpx.marketopia.productmodels.Product;
import werpx.marketopia.productmodels.Rootproductdetail;
import werpx.marketopia.productmodels.getallproductsroot;

public class MainActivity extends AppCompatActivity {



    private Utils utils;
    private Call<getallproductsroot> callproducts;
    private productdatabase mydatabase;
    ImageView imgscancamera,imgsearch;
    RecyclerView offersrecycle,cat1recycle,cat2recycle,allproductrecycle;
    SwipeRefreshLayout refreshoffer,refreshcat1,refreshcat2;
    showproduct_adapter adapteroffer;
    showproduct_adaptercat1 adaptercateg;
    Toolbar mytoolbar;
    EditText edittextsearch;
    private Call<Rootproductdetail> mcall;
    private productViewmodel mWordViewModel;
    private Call<getallproductsroot> callproductsearch;
    private ProgressDialog downloadproductprogress;
    private BroadcastReceiver receiver;
    private IntentFilter filter;
    private DrawerLayout mDrawerLayout;
    private NavigationView mynavigation;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private List<Sqlitetable> products;
    private FloatingActionButton floatingActionButton;
    private TextView coasttotaltxt,moreoffer,morecateg1,morecateg2;
    private  List<Productltable> productroom;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        utils=new Utils(this);

        productroom=new ArrayList<>();
        findviewbyid();
        getproductsroomitem();

        if (utils.haveNetworkConnection())
        {

            if (productroom.size() == 0) {
                // refreshcat1.setRefreshing(true);
                //   refreshcat2.setRefreshing(true);
                //  refreshoffer.setRefreshing(true);
                getallproductsbb();
            }
            else{
                //  List<Sqlitetable> offers = utils.getMydatabase().getproductsitems();
                adapteroffer.setproducts(productroom);
                adaptercateg.setproducts(productroom);
                offersrecycle.setAdapter(adapteroffer);
                cat1recycle.setAdapter(adaptercateg);
                cat2recycle.setAdapter(adaptercateg);
                allproductrecycle.setAdapter(adaptercateg);
            }
        }
        else {

            refreshcat1.setRefreshing(false);
            refreshcat2.setRefreshing(false);
            refreshoffer.setRefreshing(false);
            //  List<Sqlitetable> offers = utils.getMydatabase().getproductsitems();
            adapteroffer.setproducts(productroom);
            adaptercateg.setproducts(productroom);
            offersrecycle.setAdapter(adapteroffer);
            cat1recycle.setAdapter(adaptercateg);
            cat2recycle.setAdapter(adaptercateg);
            allproductrecycle.setAdapter(adaptercateg);
        }







        utils=new Utils(this);
        imgscancamera=findViewById(R.id.scancameraa);
        imgscancamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,Camera_activity.class));
            }
        });

        floatingActionButton=findViewById(R.id.fab);
        coasttotaltxt=findViewById(R.id.shoppingcoasttxt);
        floatactionbutton();

        receiver=new NetworkBroadcastreciever();
        filter=new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        products=new ArrayList<>();
        products=utils.getMydatabase().getproductsitems();
        registerNetworkBroadcastForNougat();


        intilizeviews();
        navigtiondrawerevents();





        sharedPreferences=getSharedPreferences("marketids",Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
        editor.putString("categoryid","4BB24B08-6F74-4A30-9806-124293F2D262");
        editor.putString("producerid","E007875D-6CD1-4D3E-9CD0-0AE6DAF1AED7");
        editor.putString("unitid","F5A99CB5-924D-4D40-8404-E31C7EF6020F");
        editor.apply();

        adapteroffer=new showproduct_adapter(this);
        adaptercateg=new showproduct_adaptercat1(this);


        edittextsearch=findViewById(R.id.searcheditM);



        mydatabase = new productdatabase(this);

        offersrecycle=findViewById(R.id.offersrecycleview);
        allproductrecycle=findViewById(R.id.allproductmain);
        offersrecycle.setHasFixedSize(true);
        offersrecycle.setItemAnimator(new DefaultItemAnimator());
        cat1recycle=findViewById(R.id.cat1recycleview);


        cat2recycle=findViewById(R.id.cat2recycleview);
        refreshoffer=findViewById(R.id.swipe_containeroffers);
        refreshcat1=findViewById(R.id.swipe_containercat1);
        refreshcat2=findViewById(R.id.swipe_containercat2);






        edittextsearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                allproductrecycle.setVisibility(View.VISIBLE);

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (editable.length() == 0) {
                    allproductrecycle.setVisibility(View.GONE);
                    adaptercateg.setproducts(productroom);
                }
                else if (editable.toString().matches("\\d+(?:\\.\\d+)?"))
                {
                   String barcodefinal = convertToEnglish(editable.toString());
                    //getrpoductbybarcodeoffline(barcodefinal)    ;
                }
                else {
                    //searchforproductbyNameoffline(editable.toString());
                }

            }
        });








        refreshoffer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                if (utils.haveNetworkConnection()) {
                    getallproducts();

                } else {
                    refreshoffer.setRefreshing(false);
                    refreshcat1.setRefreshing(false);
                    refreshcat2.setRefreshing(false);

                }


            }
        });


        settotalcoast();


    }








    public void getproductsroomitem()
    {
        mWordViewModel = ViewModelProviders.of(this).get(productViewmodel.class);

        mWordViewModel.getAllproductroom().observe(this, new Observer<List<Productltable>>() {
            @Override
            public void onChanged(@Nullable List<Productltable> productltables) {

                productroom=productltables;


            }
        });
    }

    public void findviewbyid()
    {
        moreoffer=findViewById(R.id.moreofferbut);
        morecateg1=findViewById(R.id.morecate1gbut);
        morecateg2=findViewById(R.id.morecat2but);

        moreoffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(MainActivity.this,Moreactivity.class);
                intent.putExtra("more",0);
                startActivity(intent);

            }
        });
        morecateg1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(MainActivity.this,Moreactivity.class);
                intent.putExtra("more",1);
                startActivity(intent);

            }
        });
        morecateg2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(MainActivity.this,Moreactivity.class);
                intent.putExtra("more",2);
                startActivity(intent);

            }
        });
    }

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
               coasttotaltxt.setText(new DecimalFormat("##.##").format(allcoast));



            }


        });
    }


    public void getallproductsdialog() {

        if (mydatabase.getproductsitems().size() == 0) {

            edittextsearch.setFocusable(false);




            downloadproductprogress=new ProgressDialog(this);
            downloadproductprogress.setMessage(getResources().getString(R.string.downloadproduct));
            downloadproductprogress.show();
            String usertoken = getSharedPreferences("token", Context.MODE_PRIVATE).getString("usertoken", "def");
            Retrofitclient myretro = Retrofitclient.getInstance();
            Retrofit retrofittok = myretro.getretro();
            final Endpoints myendpoints = retrofittok.create(Endpoints.class);
            callproducts = myendpoints.getproductdetails("Bearer " + utils.gettoken());
            callproducts.enqueue(new Callback<getallproductsroot>() {
                @Override
                public void onResponse(Call<getallproductsroot> call, Response<getallproductsroot> response) {

                    if (response.isSuccessful())

                    {

                        SharedPreferences preferences = getSharedPreferences("imgposition", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putInt("pos", 0);
                        editor.apply();

                        new insertproducts().execute(response.body().getProducts());


                    } else {
                        downloadproductprogress.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<getallproductsroot> call, Throwable t) {

                    List<Sqlitetable> products = mydatabase.getproductsitems();
                    //   myadapter.setpro(products);

                    Toast.makeText(MainActivity.this, getResources().getString(R.string.connectionfailed), Toast.LENGTH_LONG).show();
                    downloadproductprogress.dismiss();
                }
            });

        }



    }

        public void getallproducts() {


            Toast.makeText(this,getResources().getString(R.string.downloadproduct),Toast.LENGTH_SHORT).show();

            // String usertoken = getSharedPreferences("token", Context.MODE_PRIVATE).getString("usertoken", "def");
            Retrofitclient myretro = Retrofitclient.getInstance();
            Retrofit retrofittok = myretro.getretro();
            final Endpoints myendpoints = retrofittok.create(Endpoints.class);

            callproducts = myendpoints.getproductdetails("Bearer " + utils.gettoken());
            callproducts.enqueue(new Callback<getallproductsroot>() {
                @Override
                public void onResponse(Call<getallproductsroot> call, Response<getallproductsroot> response) {

                    if (response.isSuccessful())

                    {

                        new insertproducts().execute(response.body().getProducts());



                    }
                    else {
                        refreshoffer.setRefreshing(false);
                        refreshcat1.setRefreshing(false);
                        refreshcat2.setRefreshing(false);
                    }
                }

                @Override
                public void onFailure(Call<getallproductsroot> call, Throwable t) {
                    refreshoffer.setRefreshing(false);
                    refreshcat1.setRefreshing(false);
                    refreshcat2.setRefreshing(false);

                    List<Sqlitetable> products = mydatabase.getproductsitems();
                    //   myadapter.setpro(products);

                    Toast.makeText(MainActivity.this, getResources().getString(R.string.connectionfailed), Toast.LENGTH_LONG).show();

                }
            });


        }


        public void floatactionbutton()
        {
           floatingActionButton.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                  startActivity(new Intent(MainActivity.this,Shoppingbasket.class));
               }
           });
        }


    public String convertToEnglish (String value){

        String newValue = (((((((((((value + "")
                .replaceAll("١", "1")).replaceAll("٢", "2"))
                .replaceAll("٣", "3")).replaceAll("٤", "4"))
                .replaceAll("٥", "5")).replaceAll("٦", "6"))
                .replaceAll("٧", "7")).replaceAll("٨", "8"))
                .replaceAll("٩", "9")).replaceAll("٠", "0"));
        return newValue;
    }

/*
    public void getrpoductbybarcodeoffline(String barcode)
    {
       Sqlitetable mytable = mydatabase.getdataforrowinproduct(barcode);
        Sqlitetable mytablee = mydatabase.getdataforrowinproductoffline(barcode);
        Sqlitetable mytableee = mydatabase.getdataforrowinfinalproductoffline(barcode);



        List<Sqlitetable> allproducts = utils.getMydatabase().getproductsitems();
        List<Productltable> searchproducts = new ArrayList<>();
        searchproducts.clear();

        if (mytable != null) {
            searchproducts.add(mytable);

        } else if (mytablee != null) {
            searchproducts.add(mytablee);
        } else if (mytableee != null) {
            searchproducts.add(mytableee);
        } else {

        }

        if (searchproducts.size()==0)
        {
            allproductrecycle.setVisibility(View.GONE);
        }
        else {
            adaptercateg.setproducts(searchproducts);

        }




    }
    */

    public void getallproductsbb() {


        if (productroom.size() != 0) {
            mWordViewModel.deleteallroom();

        }

            Call<getallproductsroot> callproducts;
            downloadproductprogress=new ProgressDialog(this);
            downloadproductprogress.setMessage(getResources().getString(R.string.downloadproduct));
            downloadproductprogress.show();
            Retrofitclient myretro = Retrofitclient.getInstance();
            Retrofit retrofittok = myretro.getretro();
            final Endpoints myendpoints = retrofittok.create(Endpoints.class);
            callproducts = myendpoints.getproductdetails("Bearer " + utils.gettoken());
            callproducts.enqueue(new Callback<getallproductsroot>() {
                @Override
                public void onResponse(Call<getallproductsroot> call, Response<getallproductsroot> response) {

                    if (response.isSuccessful())

                    {

                        SharedPreferences preferences = getSharedPreferences("imgposition", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putInt("pos", 0);
                        editor.apply();

                        new insertproducts().execute(response.body().getProducts());


                    } else {
                        downloadproductprogress.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<getallproductsroot> call, Throwable t) {

                    List<Sqlitetable> products = utils.getMydatabase().getproductsitems();
                    //   myadapter.setpro(products);

                    Toast.makeText(MainActivity.this, getResources().getString(R.string.connectionfailed), Toast.LENGTH_LONG).show();
                    downloadproductprogress.dismiss();
                }
            });

        }




    private void registerNetworkBroadcastForNougat() {

        receiver=new NetworkBroadcastreciever();
        filter=new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        this.registerReceiver(receiver,filter);

    }
/*
    private void searchforproductbyNameoffline (String searchname){
        List<Sqlitetable> allproducts = utils.getMydatabase().getproductsitems();
        List<Sqlitetable> searchproducts = new ArrayList<>();
        searchproducts.clear();

        if (allproducts.size() != 0) {
            for (int t = 0; t < allproducts.size(); t++) {

                if (allproducts.get(t).getName().trim().toLowerCase().contains(searchname.toLowerCase())) {
                    searchproducts.add(allproducts.get(t));

                } else if (allproducts.get(t).getLocalnam().trim().toLowerCase().contains(searchname.toLowerCase())) {
                    searchproducts.add(allproducts.get(t));

                }


            }
            if (searchproducts.size()==0)
            {
                allproductrecycle.setVisibility(View.GONE);
            }
            else {
                adaptercateg.setproducts(searchproducts);

            }
        }

    }
    */

    protected void unregisterNetworkChanges() {
        try {
            unregisterReceiver(receiver);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unregisterNetworkChanges();
    }

    @Override
    public void onBackPressed() {
        if (this.mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);

    }


    public void intilizeviews()
    {
        final TextView headerstorenam,headerconsumernam ,headeraccountdata,ordercoast;
        ImageView headerstoreimg;
        RecyclerView orderrecycle;





        mDrawerLayout=findViewById(R.id.drawer_layout);
        ordercoast=findViewById(R.id.totalorder);
        mytoolbar = findViewById(R.id.toolbarmain);
        mynavigation = findViewById(R.id.nav_view);
        View header = mynavigation.getHeaderView(0);

        headerconsumernam = header.findViewById(R.id.consumernam);
        headeraccountdata = header.findViewById(R.id.accountdatanav);
        headerstoreimg = header.findViewById(R.id.consumerimg);

        headeraccountdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // startActivity(new Intent(Shoppingbasket.this, store_details.class));
            }
        });

        SharedPreferences sharedPreferences=getSharedPreferences("consumerinfo",Context.MODE_PRIVATE);
        String name=sharedPreferences.getString("name","");
        String img=sharedPreferences.getString("img","");
        headerconsumernam.setText(name);

        GlideApp.with(this)
                .load(img)
                .into(headerstoreimg);

        setSupportActionBar(mytoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.menumain);
        getSupportActionBar().setDisplayShowTitleEnabled(false);








    }


    public void navigtiondrawerevents()
    {
        mynavigation.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight

                        switch (menuItem.getItemId()) {
                            case R.id.shopingbasketnav: {

                                startActivity(new Intent(MainActivity.this, Shoppingbasket.class));

                                break;
                            }

                            case R.id.orderlistnav: {

                                startActivity(new Intent(MainActivity.this, sales_history.class));

                                break;
                            }

                            case R.id.favouritnav: {
                                mDrawerLayout.closeDrawers();

                                 startActivity(new Intent(MainActivity.this, Favourit.class));
                                break;
                            }


                            case R.id.settingnav: {
                                startActivity(new Intent(MainActivity.this, Setting.class));
                                mDrawerLayout.closeDrawers();

                                //  startActivity(new Intent(Shoppingbasket.this, MainActivity.class));
                                break;
                            }

                            case R.id.latestnav: {
                                mDrawerLayout.closeDrawers();

                                //   startActivity(new Intent(MainActivity.this, werpx.cashiery.Cashiery.Camera_activity.class));

                                break;
                            }


                            case R.id.aboutnav: {

                                startActivity(new Intent(MainActivity.this,Aboutus.class));

                                break;
                            }

                            case R.id.readernav: {
                                mDrawerLayout.closeDrawers();

                                startActivity(new Intent(MainActivity.this,Camera_activity.class));

                                break;

                            }
                            case R.id.logoutnav: {

                                mDrawerLayout.closeDrawers();
                                SharedPreferences prefs = getSharedPreferences("login", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = prefs.edit();
                                editor.clear().apply();
                                LoginManager.getInstance().logOut();
                                startActivity(new Intent(MainActivity.this, loginwithFB.class));
                                finish();
                                break;

                            }



                            default:
                                menuItem.setChecked(true);
                                // close drawer when item is tapped
                                mDrawerLayout.closeDrawers();
                                break;
                        }
                        return true;


                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here


                    }
                });

    }









    class insertproducts extends AsyncTask<List<Product>,Void,List<Productltable> > {

       List<Productltable> products=new ArrayList<>();

        @Override
        protected List<Productltable> doInBackground(List<Product>... lists) {


            List<Product> myproducts=lists[0];
            for (int i = 0; i < myproducts.size(); i++) {
                if (myproducts.get(i).getImage() == null) {
                    String barcode = myproducts.get(i).getBarcode();
                    String  price = myproducts.get(i).getPrice();
                    String namepr = myproducts.get(i).getName();
                    String localnam = myproducts.get(i).getLocalname();
                    String desc = myproducts.get(i).getDescription();
                    String id = myproducts.get(i).getId();
                    Productltable productltable=new Productltable(namepr,price,barcode,desc,null,localnam,0,id);
                    mWordViewModel.insertroom(productltable);
                   // utils.getMydatabase().insertdatalistproducts(namepr, barcode, price, null, desc, id, localnam, null);
                } else {
                    String barcode = myproducts.get(i).getBarcode();
                    String price = myproducts.get(i).getPrice();
                    String imgfilename = myproducts.get(i).getImage().getFilename();
                    String imgurl = getResources().getString(R.string.baseurlimgproduct) + imgfilename;


                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    String name = myproducts.get(i).getName();
                    String namepr = myproducts.get(i).getName();
                    String localnam = myproducts.get(i).getLocalname();

                    String desc = myproducts.get(i).getDescription();
                    String id = myproducts.get(i).getId();
                    Productltable productltable=new Productltable(namepr,price,barcode,desc,imgurl,localnam,0,id);
                    mWordViewModel.insertroom(productltable);
                   // utils.getMydatabase().insertdatalistproducts(namepr, barcode, price, imgurl, desc, id, localnam, null);

                }

            }
            return mWordViewModel.getAllproductroom().getValue();
        }

        @Override
        protected void onPostExecute(List<Productltable> aVoid) {


            startService(new Intent(MainActivity.this, Downloadimageservice.class));
           // List<Sqlitetable> offers = utils.getMydatabase().getproductsitems();
            adapteroffer.setproducts(aVoid);
            adaptercateg.setproducts(aVoid);
            offersrecycle.setAdapter(adapteroffer);
            cat1recycle.setAdapter(adaptercateg);
            cat2recycle.setAdapter(adaptercateg);

            edittextsearch.setFocusable(true);
            downloadproductprogress.dismiss();

            refreshoffer.setRefreshing(false);
            refreshcat1.setRefreshing(false);
            refreshcat2.setRefreshing(false);


            super.onPostExecute(aVoid);
        }
    }




}






