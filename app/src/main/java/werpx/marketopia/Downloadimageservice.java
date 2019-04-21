package werpx.marketopia;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.bumptech.glide.request.target.Target;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;


import werpx.marketopia.RoomDatabase.Sqlitetable;

public class Downloadimageservice extends IntentService {

    private productdatabase mydatabase;
    private Boolean downloadcondition,firsttime;
    barcodevalue mvalue;
    int i=0;
    historydata mydata;


    public Downloadimageservice() {
        super("download");

        mydatabase = new productdatabase(this);
        downloadcondition=true;
        firsttime=true;
        mydata=new historydata(this);
        mydata=new historydata(this);
        mvalue=new barcodevalue();
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {



        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

         new downladasync().execute();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();


    }


    class downladasync extends AsyncTask<Void,Void,Void>
    {




        @Override
        protected Void doInBackground(Void... voids) {
            List<Sqlitetable> products= mydatabase.getproductsitems();
            Log.e("image","intentservice");

            if (haveNetworkConnection())
            {
                mydata.syncedata();


                if (products!=null)
                {
                    while (i < products.size()) {
                        String url = products.get(i).getImge();
                        GlideApp.with(getApplicationContext())
                                .load(url)
                                .downloadOnly(Target.SIZE_ORIGINAL,Target.SIZE_ORIGINAL);
                        i++;

                    }

                }
            }



                              //  editor.putInt("pos",i);
                              //  editor.appl
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
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
    public void downloadimagesforproducts() {

        //   downloadconditiontext.setText(getResources().getString(R.string.downloadimges));
        //  imgdownloadlayout.setVisibility(View.VISIBLE);
        List<Sqlitetable> myproducts = mydatabase.getproductsitems();

        if (myproducts.size()!=0)
        {
            Toast.makeText(this,"not null", Toast.LENGTH_SHORT).show();

            while (i<myproducts.size())
            {

                String img = myproducts.get(i).getImge();
                Toast.makeText(this,img, Toast.LENGTH_SHORT).show();
                if (img!=null)
                {
                    if (firsttime)
                    {
                        Toast.makeText(this,"download", Toast.LENGTH_SHORT).show();
                        String barcode = myproducts.get(i).getBarcode();
                        mvalue.setBarcodeimg(barcode);
                        try {
                            URL url = new URL(img);
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                        Convertimg convertimg = new Convertimg();
                        convertimg.execute(img);
                        firsttime=false;
                    }
                    if (!downloadcondition)
                    {
                        Log.e("downloadornot", String.valueOf(i));

                        String barcode = myproducts.get(i).getBarcode();
                        mvalue.setBarcodeimg(barcode);
                        try {
                            URL url = new URL(img);
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                        Convertimg convertimg = new Convertimg();
                        convertimg.execute(img);


                    }



                }
                else{
                    i++;

                    downloadcondition=false;
                }
            }



        }










    }

    public byte[] convertbitmaptobyte(Bitmap bitmap) {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        bitmap.recycle();
        return byteArray;
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
                //  Toast.makeText(downloadimagesyrvice.this, String.valueOf(val), Toast.LENGTH_SHORT).show();

                downloadcondition=false;
                i++;



            }


            //   return myBitmap;
            // TODO: check this.exception
            // TODO: do something with the feed
        }


    }

}
