package werpx.marketopia;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.InetAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import werpx.marketopia.RoomDatabase.Sqlieofflinehistory;
import werpx.marketopia.RoomDatabase.Sqlitetable;
import werpx.marketopia.RoomDatabase.productViewmodel;
import werpx.marketopia.salemodel.Saleroot;

public class historydata {


    private productViewmodel mWordViewModel;
    private Context context;
    private Call<Saleroot> salecall;
    private productdatabase mydatabase;
    private String mainretailerid;
    private Call<ResponseBody> callsell;
    private String Categoryid, producerid, unitid;
    private Configuration configuration;
    private int applanguage;
    private String productname, LocalNameValue;
    Boolean imgcondition = true;
    int i = 0;
    public NumberProgressBar numberProgressBar;
    public TimerTask doAsynchronousTask;
    public Boolean downloadconditionl = true;
    public Boolean firstrun = true;


    barcodevalue mvalue;

    public historydata(Context con) {
        this.context = con;


        mydatabase = new productdatabase(context);
        //mainretailerid = con.getSharedPreferences("retailerdata", Context.MODE_PRIVATE).getString("id", "n");

    }


    public void syncedata() {

        Categoryid = context.getSharedPreferences("storedetails", Context.MODE_PRIVATE).getString("categoryid", "");
        producerid = context.getSharedPreferences("storedetails", Context.MODE_PRIVATE).getString("producerid", "");
        unitid = context.getSharedPreferences("storedetails", Context.MODE_PRIVATE).getString("unitid", "");

        configuration = context.getResources().getConfiguration();
        applanguage = configuration.getLayoutDirection();
        if (applanguage == 0) {

            productname = "Product";
            LocalNameValue = "Unregistered Product";
        } else {
            productname = "منتج";
            LocalNameValue = "منتج غير مسجل ";
        }


        //   new Asyntask().execute();

        if (haveNetworkConnection()) {
            Handler myhandlerr = new Handler(Looper.getMainLooper());
            myhandlerr.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, context.getResources().getString(R.string.syncestart), Toast.LENGTH_LONG).show();

                }
            });



            ArrayList<String> barcodes = new ArrayList<>();
            ArrayList<String> names = new ArrayList<>();
            ArrayList<String> prices = new ArrayList<>();
            ArrayList<String> details = new ArrayList<>();
            ArrayList<String> producers = new ArrayList<>();
            ArrayList<String> categories = new ArrayList<>();
            ArrayList<String> units = new ArrayList<>();
            ArrayList<String> localvalues = new ArrayList<>();
            ArrayList<String> reorders = new ArrayList<>();


            List<Sqlitetable> offlineproducts = mydatabase.getofflinefinalproducts();
            if (offlineproducts.size() != 0) {
                for (int y = 0; y < offlineproducts.size(); y++) {
                    Sqlitetable product = offlineproducts.get(y);
                    String bar = product.getBarcode();
                    String name = product.getName();
                    String price = product.getPrice();
                    String detail = product.getDescription();
                    String img = product.getImge();

                    barcodes.add(bar);
                    names.add(productname);
                    prices.add(price);
                    details.add(detail);
                    producers.add(producerid);
                    categories.add(Categoryid);
                    units.add(unitid);
                    localvalues.add(LocalNameValue);
                    reorders.add("distributor");


                }
                addnewproducts(barcodes, names, prices, details, producers, categories, units, localvalues, reorders);
                // syncesales();
                mydatabase.deleteofflinefinalproducts();


            } else {




/*
                Handler myhandler = new Handler(Looper.getMainLooper());
                myhandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        syncesales();

                    }
                }, 7000);

*/
            }

        }


    }

    public boolean isInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("google.com");
            //You can replace it with your name
            return !ipAddr.equals("");

        } catch (Exception e) {
            return false;
        }
    }

    private void syncesales() {
        List<Sqlieofflinehistory> allsales = mydatabase.getofflinesales();
        ArrayList<String> offproductsid = mydatabase.getofflineproductsid();


        ArrayList<String> allbarcodes = new ArrayList<>();
        ArrayList<String> allquantites = new ArrayList<>();
        ArrayList<String> allretailersids = new ArrayList<>();
        ArrayList<String> allproductids = new ArrayList<>();
        ArrayList<Boolean> alllives = new ArrayList<>();
        ArrayList<String> productidslist = new ArrayList<>();

        if (allsales.size() != 0) {


            Double allordercoast = 0.0;
            for (int i = 0; i < allsales.size(); i++) {

                String barcodes = allsales.get(i).getOrbarcodes();
                String Prodids = allsales.get(i).getOrproductids();
                String livess = allsales.get(i).getOrlives();
                String retaileridss = allsales.get(i).getOrretailerids();
                String amountor = allsales.get(i).getOramount();
                String units = allsales.get(i).getOrunits();
                String numberor = allsales.get(i).getOrnum();
                String ordataa = allsales.get(i).getOrdata();
                String quantities = allsales.get(i).getQuantities();
                Gson gson = new Gson();
                Type type = new TypeToken<List<String>>() {
                }.getType();
                Type typeB = new TypeToken<List<Boolean>>() {
                }.getType();
                ArrayList<String> barcodlist = gson.fromJson(barcodes, type);
                productidslist = gson.fromJson(Prodids, type);
                ArrayList<String> retailerlist = gson.fromJson(retaileridss, type);
                ArrayList<Boolean> lives = gson.fromJson(livess, typeB);
                ArrayList<String> quan = gson.fromJson(quantities, type);

                allbarcodes.addAll(barcodlist);
                allquantites.addAll(quan);
                allretailersids.addAll(retailerlist);
                allproductids.addAll(productidslist);
                alllives.addAll(lives);

                if (allsales.size() == 0) {

                } else {

                }
                if (i == allsales.size() - 1) {

                }


            }
            int z = 0;

            for (int m = 0; m < allproductids.size(); m++) {

                if (allproductids.get(m) == null) {
                    allproductids.set(m, offproductsid.get(z));
                    z++;
                }

            }
            //allproductids.addAll(offproductsid);

            saletgrba(allbarcodes, allquantites, allretailersids, allproductids, alllives);
        }
    }


    private void saletgrba(ArrayList<String> barcode, ArrayList<String> quantity, ArrayList<String> retailerid, ArrayList<String> productid, ArrayList<Boolean> live) {
     Retrofitclient myretro = Retrofitclient.getInstance();
        Retrofit retrofit = myretro.getretro();

        final Endpoints myendpoints = retrofit.create(Endpoints.class);
        callsell = myendpoints.salearray(barcode, quantity, retailerid, productid, live);
        callsell.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.isSuccessful()) {
                    // mWordViewModel.deleteallproduct();
                    //  mydatabase.deleteofflinesales();
                    try {
                        String saleresponse = response.body().string();
                        JSONObject saleobject = new JSONObject(saleresponse);
                        String opercon = saleobject.getString("operation");
                        if (opercon.trim().equals("success")) {


                            Toast.makeText(context, context.getResources().getString(R.string.syncecompleted), Toast.LENGTH_LONG).show();
                            mydatabase.deleteproductsids();
                            mydatabase.deleteofflinesales();
                        } else {
                            Toast.makeText(context, context.getResources().getString(R.string.failedscynce), Toast.LENGTH_LONG).show();

                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                } else {
                    mydatabase.deleteproductsids();

                }


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                mydatabase.deleteproductsids();


            }
        });
    }


    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
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


    public void addnewproducts(ArrayList<String> bar, ArrayList<String> name, ArrayList<String> price,
                               ArrayList<String> description, ArrayList<String> producer,
                               ArrayList<String> category, ArrayList<String> units, ArrayList<String> localname, ArrayList<String> reorders) {


        String token = context.getSharedPreferences("token", Context.MODE_PRIVATE).getString("usertoken", "");

        Call<ResponseBody> calladd;
        Retrofitclient myretro = Retrofitclient.getInstance();
        Retrofit retrofittok = myretro.getretro();
        final Endpoints myendpoints = retrofittok.create(Endpoints.class);
        calladd = myendpoints.addproductsasarray("Bearer " + token, bar, category, description
                , name, price, producer, reorders,
                units, localname);
        calladd.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.isSuccessful()) {
                }
                if (response.isSuccessful()) {
                    try {
                        String addresponse = response.body().string();
                        JSONObject addobject = new JSONObject(addresponse);
                        String opercon = addobject.getString("operation");
                        if (opercon.trim().equals("success")) {
                            JSONArray storeid = addobject.getJSONArray("id");
                            for (int o = 0; o < storeid.length(); o++) {
                                Boolean res = mydatabase.insertofflineproductsids(storeid.get(o).toString());

                            }


                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {


            }
        });


    }


    public byte[] convertbitmaptobyte(Bitmap bitmap) {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        bitmap.recycle();
        return byteArray;
    }

    public void downloadimagesforproducts() {

        //   downloadconditiontext.setText(getResources().getString(R.string.downloadimges));
        //  imgdownloadlayout.setVisibility(View.VISIBLE);


        if (haveNetworkConnection()) {
            if (firstrun) {
                Toast.makeText(context, "firt", Toast.LENGTH_SHORT).show();

                firstrun = false;
                List<Sqlitetable> myproducts = mydatabase.getproductsitems();
                SharedPreferences preferences = context.getSharedPreferences("progressvalues", Context.MODE_PRIVATE);
                final SharedPreferences.Editor editor = preferences.edit();
                boolean run = true;
                i = context.getSharedPreferences("progressvalues", Context.MODE_PRIVATE).getInt("current", 0);
                if (myproducts.size() != 0) {
                    Toast.makeText(context, "product", Toast.LENGTH_SHORT).show();

                    numberProgressBar.setMax(myproducts.size());
                    editor.putInt("max", myproducts.size());
                    editor.apply();
                    String img = myproducts.get(i).getImge();
                    Toast.makeText(context, img, Toast.LENGTH_SHORT).show();
                    String barcode = myproducts.get(i).getBarcode();
                    mvalue.setBarcodeimg(barcode);
                    mvalue.setProgresscurrent(i);
                    mvalue.setProgressmax(myproducts.size());
                    final Convertimg convertimg = new Convertimg();
                    convertimg.execute(img);
                    imgcondition = true;
                    final Handler handler = new Handler();
                    final Timer timer = new Timer();
                    doAsynchronousTask = new TimerTask() {
                        @Override
                        public void run() {
                            handler.post(new Runnable() {
                                public void run() {
                                    try {

                                        if (!imgcondition) {
                                            i++;
                                            editor.putInt("current", i);
                                            editor.apply();

                                            numberProgressBar.setProgress(i);
                                            List<Sqlitetable> myproducts = mydatabase.getproductsitems();
                                            if (i >= myproducts.size()) {

                                                doAsynchronousTask.cancel();

                                            } else {
                                                String img = myproducts.get(i).getImge();
                                                Toast.makeText(context, img, Toast.LENGTH_SHORT).show();
                                                Toast.makeText(context, i, Toast.LENGTH_SHORT).show();
                                                String barcode = myproducts.get(i).getBarcode();
                                                mvalue.setBarcodeimg(barcode);
                                                mvalue.setProgresscurrent(i);
                                                mvalue.setProgressmax(myproducts.size());
                                                final Convertimg convertimg = new Convertimg();
                                                convertimg.execute(img);
                                                imgcondition = true;

                                            }

                                        } else {
                                            downloadconditionl = false;
                                        }

                                    } catch (Exception e) {

                                    }
                                }
                            });
                        }
                    };
                    timer.schedule(doAsynchronousTask, 0, 2000); //ex


                }

            } else {
                if (!downloadconditionl) {
                    List<Sqlitetable> myproducts = mydatabase.getproductsitems();
                    SharedPreferences preferences = context.getSharedPreferences("progressvalues", Context.MODE_PRIVATE);
                    final SharedPreferences.Editor editor = preferences.edit();
                    boolean run = true;
                    i = context.getSharedPreferences("progressvalues", Context.MODE_PRIVATE).getInt("current", 0);
                    if (myproducts.size() != 0) {


                        numberProgressBar.setMax(myproducts.size());
                        editor.putInt("max", myproducts.size());
                        editor.apply();
                        String img = myproducts.get(i).getImge();
                        Toast.makeText(context, img, Toast.LENGTH_SHORT).show();
                        String barcode = myproducts.get(i).getBarcode();
                        mvalue.setBarcodeimg(barcode);
                        mvalue.setProgresscurrent(i);
                        mvalue.setProgressmax(myproducts.size());
                        final Convertimg convertimg = new Convertimg();
                        convertimg.execute(img);
                        imgcondition = true;
                        final Handler handler = new Handler();
                        final Timer timer = new Timer();
                        doAsynchronousTask = new TimerTask() {
                            @Override
                            public void run() {
                                handler.post(new Runnable() {
                                    public void run() {
                                        try {

                                            if (!imgcondition) {
                                                i++;
                                                editor.putInt("current", i);
                                                editor.apply();

                                                numberProgressBar.setProgress(i);
                                                List<Sqlitetable> myproducts = mydatabase.getproductsitems();
                                                if (i >= myproducts.size()) {

                                                    doAsynchronousTask.cancel();

                                                } else {
                                                    String img = myproducts.get(i).getImge();
                                                    Toast.makeText(context, img, Toast.LENGTH_SHORT).show();
                                                    Toast.makeText(context, i, Toast.LENGTH_SHORT).show();
                                                    String barcode = myproducts.get(i).getBarcode();
                                                    mvalue.setBarcodeimg(barcode);
                                                    mvalue.setProgresscurrent(i);
                                                    mvalue.setProgressmax(myproducts.size());
                                                    final Convertimg convertimg = new Convertimg();
                                                    convertimg.execute(img);
                                                    imgcondition = true;

                                                }

                                            } else {
                                                downloadconditionl = false;
                                            }

                                        } catch (Exception e) {

                                        }
                                    }
                                });
                            }
                        };
                        timer.schedule(doAsynchronousTask, 0, 2000); //ex


                    }
                }

            }
        }


    }

    class Convertimg extends AsyncTask<String, Integer, Bitmap> {



        private Exception exception;
        private Bitmap myBitmap;


        protected Bitmap doInBackground(String... urls) {
            try {
                URL url = new URL(urls[0]);
                Bitmap image = BitmapFactory.decodeStream(url.openConnection().getInputStream());




                return image;

            } catch (IOException e) {
                // Log exception
                return null;
            }

        }

        protected void onPostExecute(Bitmap feed) {

            if (feed != null) {
                byte[] myimg = convertbitmaptobyte(feed);
                Boolean val = mydatabase.updateimagevalues(myimg, mvalue.getBarcodeimg());
                Toast.makeText(context, String.valueOf(val), Toast.LENGTH_LONG).show();
                imgcondition = false;
            } else {
                Toast.makeText(context, "nul", Toast.LENGTH_LONG).show();
            }


            //   return myBitmap;
            // TODO: check this.exception
            // TODO: do something with the feed
        }


    }


}








