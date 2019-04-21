package werpx.marketopia;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;



public class NetworkBroadcastreciever extends BroadcastReceiver {

    Context context;

    private productdatabase mydatabase;
    private Boolean downloadcondition,firsttime;
    barcodevalue mvalue;
    int i=0;


    @Override
    public void onReceive(Context context, Intent intent) {

        mydatabase = new productdatabase(context);
        downloadcondition=true;
        firsttime=true;
        mvalue=new barcodevalue();


        this.context=context;


        if (haveNetworkConnection())
        {

            context.startService(new Intent(context,Downloadimageservice.class));


        }
        else{
         context.stopService(new Intent(context,Downloadimageservice.class));

        }

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














}
