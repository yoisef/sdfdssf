package werpx.marketopia.Marketopia;
import android.Manifest;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import werpx.marketopia.CameraSource;
import werpx.marketopia.Endpoints;
import werpx.marketopia.R;
import werpx.marketopia.Retrofitclient;
import werpx.marketopia.RoomDatabase.Sqlitetable;
import werpx.marketopia.RoomDatabase.mytable;
import werpx.marketopia.RoomDatabase.productViewmodel;
import werpx.marketopia.Utils;
import werpx.marketopia.productdatabase;
import werpx.marketopia.productmodels.Rootproductdetail;
import werpx.marketopia.productmodels.getallproductsroot;

public class Camera_activity extends AppCompatActivity {
    private BarcodeDetector detector;
    private SurfaceView cameraView;
    private CameraSource cameraSource;
    private MediaPlayer ring;
    LinearLayout changelay;
    private productViewmodel mWordViewModel;
    private Switch myswitch;
    String conditionn;
    private String usertoken;
    private SharedPreferences prefs;
    private Call<Rootproductdetail> mcall;
    private boolean add;
    private EditText itemsnum;
    private RelativeLayout layoutitems;
    private List<mytable> orderproducts;
    ImageView productimage, add_items, remove_item;
    TextView namee, unitpricee, barcodeee, total_itemscoast, donecamera, cancelcameraa;
    EditText showitems_number;
    private RelativeLayout detailproduct;
    private LinearLayout addnewproduct,showproductdetails;
    private productdatabase mydatabase;
    private ProgressBar progresswaitadd;
    private LinearLayout confirmationlayout;
    private android.app.AlertDialog.Builder builder;
    private android.app.AlertDialog alertDialog;
    private String Categoryid, producerid, unitid, productname, LocalNameValue;
    private Call<getallproductsroot> callproducts;
    Utils utils;

    EditText addnam,addpric;
    TextView addbut, cancel;
    ImageView incre,decre;



    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_activity);

        utils=new Utils(this);

        Categoryid=getSharedPreferences("marketids", Context.MODE_PRIVATE).getString("categoryid","");
        producerid=getSharedPreferences("marketids", Context.MODE_PRIVATE).getString("producerid","");
        unitid=getSharedPreferences("marketids", Context.MODE_PRIVATE).getString("unitid","");
        String CurrentLang = Locale.getDefault().getLanguage();

        if (CurrentLang.equalsIgnoreCase("en"))
        {
            productname="Product";
            LocalNameValue="Unregistered Product";
        }
        else{
            productname="منتج";
            LocalNameValue="منتج غير مسجل ";
        }


        productimage=findViewById(R.id.imgcam);
        addnewproduct=findViewById(R.id.addnewproductlayuout);
        showproductdetails=findViewById(R.id.productshowdetailslayout);
        add_items=findViewById(R.id.addcam);
        remove_item=findViewById(R.id.removecam);
        namee=findViewById(R.id.nampcam);
        unitpricee=findViewById(R.id.pricecam);
        barcodeee=findViewById(R.id.barcam);
        total_itemscoast=findViewById(R.id.totalcam);
        showitems_number=findViewById(R.id.itemnumbercam);
        donecamera=findViewById(R.id.donecam);
        cancelcameraa=findViewById(R.id.cancelcam);
        confirmationlayout=findViewById(R.id.confirmlayout);
        detailproduct=findViewById(R.id.showprodetail);

        progresswaitadd=findViewById(R.id.waitwhileadd);
        addpric=findViewById(R.id.addprice);
        addbut=findViewById(R.id.addproductt);
        cancel=findViewById(R.id.cancelproduct);


        prefs = getSharedPreferences("token", Context.MODE_PRIVATE);
        usertoken = prefs.getString("usertoken", "def");
        mWordViewModel = ViewModelProviders.of(this).get(productViewmodel.class);

        mWordViewModel = ViewModelProviders.of(this).get(productViewmodel.class);
        mWordViewModel.getAllWords().observe(Camera_activity.this, new Observer<List<mytable>>() {
            @Override
            public void onChanged(@Nullable final List<mytable> words) {


                orderproducts=words;


            }
        });
        mydatabase=new productdatabase(this);






        ring = MediaPlayer.create(Camera_activity.this, R.raw.store);
        cameraView = (SurfaceView) findViewById(R.id.camera_view);



        changelay = findViewById(R.id.mydetect);



        myswitch = findViewById(R.id.switchtorch);
        if (this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {


            myswitch.post(new Runnable() {
                @Override
                public void run() {

                    myswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        public void onCheckedChanged(CompoundButton buttonView, final boolean isChecked) {
                            // do something, the isChecked will be
                            if (isChecked) {

                                conditionn = "true";
                                SharedPreferences prefs = getSharedPreferences("condition", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = prefs.edit();
                                editor.putString("mycon", conditionn);
                                editor.apply();
                                restart();

                            } else {

                                conditionn = "false";
                                SharedPreferences prefs = getSharedPreferences("condition", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = prefs.edit();
                                editor.putString("mycon", conditionn);
                                editor.apply();
                                restart();

                            }


                        }
                    });


                }
            });

        } else {
            myswitch.setVisibility(View.GONE);
        }






        donecamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                SharedPreferences sharedPreferences = getSharedPreferences("productbar", Context.MODE_PRIVATE);
                String barcode=sharedPreferences.getString("bar","11");

                showproductdetails.setVisibility(View.GONE);
                resumecamera();

                checkifproductexsist(showitems_number.getText().toString(),barcode);

            }
        });

        add_items.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmationlayout.setVisibility(View.VISIBLE);
                int num=Integer.parseInt(showitems_number.getText().toString());
                int currentnum=num+1;
                showitems_number.setText(String.valueOf(currentnum));
                Double Uprice=Double.parseDouble(unitpricee.getText().toString());

                Double totalpriceP=currentnum*Uprice;
                total_itemscoast.setText(String.valueOf(totalpriceP));
                //mWordViewModel.updateproduct(Long.parseLong(showitems_number.getText().toString()),Long.parseLong(current.getPbar()));
            }
        });

        remove_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int num=Integer.parseInt(showitems_number.getText().toString());
                if (num <= 1) {
                    showitems_number.setText(String.valueOf(1));

                } else {
                    int cunum=num-1;
                    showitems_number.setText(String.valueOf(cunum));
                    Double Uprice=Double.parseDouble(unitpricee.getText().toString());
                    Double totalp=Uprice*cunum;
                    total_itemscoast.setText(String.valueOf(totalp));
                    //  mWordViewModel.updateproduct(Long.parseLong(showitems_number.getText().toString()),Long.parseLong(current.getPbar()));

                }
            }
        });

        cancelcameraa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences sharedPreferences = getSharedPreferences("productbar", Context.MODE_PRIVATE);
                String barcode=sharedPreferences.getString("bar","11");
                showproductdetails.setVisibility(View.GONE);
                resumecamera();
                mydatabase.deleterowofofflineproducts(barcode);

            }
        });

        showitems_number.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId==EditorInfo.IME_ACTION_DONE)
                {
                    int num=Integer.parseInt(showitems_number.getText().toString());
                    if (num <= 1) {
                        showitems_number.setText(String.valueOf(1));

                    } else {
                        int cunum=num-1;
                        showitems_number.setText(String.valueOf(cunum));
                        Double Uprice=Double.parseDouble(unitpricee.getText().toString());
                        Double totalp=Uprice*cunum;
                        total_itemscoast.setText(String.valueOf(totalp));
                        //  mWordViewModel.updateproduct(Long.parseLong(showitems_number.getText().toString()),Long.parseLong(current.getPbar()));

                    }
                    mWordViewModel.updateproduct(Long.parseLong(showitems_number.getText().toString()),Long.parseLong(barcodeee.getText().toString()));
                    InputMethodManager inputManager = (InputMethodManager)
                            getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.toggleSoftInput(0, 0);

                    return true;
                }
                return false;
            }
        });



    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = getSharedPreferences("condition", Context.MODE_PRIVATE);
        String conde = prefs.getString("mycon", "false");

        if (conde.equals("true")) {
            //myswitch.setOnCheckedChangeListener (null);
            myswitch.setChecked(true);
            instalcamerawithflash();
        } else {
            instalcamerawirhoutflash();
        }







    }


    public void instalcamerawithflash() {

        detector =
                new BarcodeDetector.Builder(getApplicationContext())
                        .setBarcodeFormats(0)
                        .build();
        if (!detector.isOperational()) {
            //  txtView.setText("Could not set up the detector!");
            Toast.makeText(Camera_activity.this, "Could not set up the detector!", Toast.LENGTH_LONG).show();

            return;
        }


        cameraSource = new CameraSource
                .Builder(Camera_activity.this, detector)
                .setRequestedPreviewSize(640, 480)
                .setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)
                .setscanmode(Camera.Parameters.SCENE_MODE_BARCODE)
                .setFlashMode(Camera.Parameters.FLASH_MODE_TORCH)
                .build();


        cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {

                try {


                    if (ActivityCompat.checkSelfPermission(Camera_activity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    cameraSource.start(cameraView.getHolder());


                } catch (IOException ie) {
                    Log.e("CAMERA SOURCE", ie.getMessage());
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

                cameraSource.stop();
                //  cameraSource.release();
            }
        });

        detector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
            }

            @Override
            public void receiveDetections(final Detector.Detections<Barcode> detections) {

                final SparseArray<Barcode> barcodes = detections.getDetectedItems();


                if (barcodes.size() != 0) {
                    changelay.post(new Runnable() {
                        @Override
                        public void run() {
                            synchronized (this) {

                                ring.start();
                                SharedPreferences sharedPreferences = getSharedPreferences("productbar", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("bar", barcodes.valueAt(0).displayValue);
                                editor.apply();
                                getproductdetailsbest(barcodes.valueAt(0).displayValue);





                            }

                        }

                    });


                }

            }
        });


    }

    public void instalcamerawirhoutflash() {

        detector =
                new BarcodeDetector.Builder(getApplicationContext())
                        .setBarcodeFormats(0)
                        .build();
        if (!detector.isOperational()) {
            //  txtView.setText("Could not set up the detector!");
            Toast.makeText(Camera_activity.this, "Could not set up the detector!", Toast.LENGTH_LONG).show();

            return;
        }


        cameraSource = new CameraSource
                .Builder(Camera_activity.this, detector)
                .setRequestedPreviewSize(640, 480)
                .setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)
                .setscanmode(Camera.Parameters.SCENE_MODE_BARCODE)
                .build();


        cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {

                try {


                    if (ActivityCompat.checkSelfPermission(Camera_activity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    cameraSource.start(cameraView.getHolder());

                } catch (IOException ie) {
                    Log.e("CAMERA SOURCE", ie.getMessage());
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

                cameraSource.stop();
                //         cameraSource.release();
            }
        });


        detector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {

                final SparseArray<Barcode> barcodes = detections.getDetectedItems();


                if (barcodes.size() != 0) {
                    changelay.post(new Runnable() {
                        @Override
                        public void run() {
                            synchronized (this) {

                                ring.start();
                                SharedPreferences sharedPreferences = getSharedPreferences("productbar", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("bar", barcodes.valueAt(0).displayValue);
                                editor.apply();
                                getproductdetailsbest(barcodes.valueAt(0).displayValue);




                            }
                        }



                    });


                }


            }


        });


    }


    private void loginwithbarcode(final String barcodedataa, final int itemsnum) {


        Retrofitclient myretro = Retrofitclient.getInstance();
        Retrofit retrofitt = myretro.getretro();


        final Endpoints myendpoints = retrofitt.create(Endpoints.class);

        mcall = myendpoints.getdetails("Bearer " + utils.gettoken(), barcodedataa);
        mcall.enqueue(new Callback<Rootproductdetail>() {
            @Override
            public void onResponse(Call<Rootproductdetail> call, Response<Rootproductdetail> response) {

                if (response.isSuccessful()) {
                    mytable MainTable;
                    final String pid,pronam, prodbar, prodimg, broddetail, brodprice, prodcat,pronamlocal;



                    if (response.body().getProduct() != null) {

                        if (response.body().getProduct().getImage()==null)
                        {


                            pid=response.body().getProduct().getId();
                            pronam = response.body().getProduct().getName();
                            pronamlocal = response.body().getProduct().getName();
                            prodbar = response.body().getProduct().getBarcode();
                            broddetail = response.body().getProduct().getDescription();
                            brodprice = response.body().getProduct().getPrice();
                            prodcat = response.body().getProduct().getCategory().getName();

                           MainTable = new mytable(pid,pronam, prodbar,itemsnum, null, broddetail, brodprice, prodcat,pronamlocal,null);
                           mWordViewModel.insert(MainTable);
                            resumecamera();

                           // Toast.makeText(Camera_activity.this, "" + getResources().getString(R.string.addedtocart), Toast.LENGTH_LONG).show();

                        }
                        else{

                            pid=response.body().getProduct().getId();
                            pronam = response.body().getProduct().getName();
                            pronamlocal = response.body().getProduct().getName();
                            prodbar = response.body().getProduct().getBarcode();
                            prodimg = response.body().getProduct().getImage().getFilename();
                            String imgurl=getResources().getString(R.string.baseurlimg)+prodimg;
                            broddetail = response.body().getProduct().getDescription();
                            brodprice = response.body().getProduct().getPrice();
                            prodcat = response.body().getProduct().getCategory().getName();
                            MainTable = new mytable(pid,pronam, prodbar,itemsnum, imgurl, broddetail, brodprice, prodcat,pronamlocal,null);
                            mWordViewModel.insert(MainTable);
                            resumecamera();

                          //  Toast.makeText(Camera_activity.this, "" + getResources().getString(R.string.addedtocart), Toast.LENGTH_LONG).show();
                        }


                    }
                    else {
                        Toast.makeText(Camera_activity.this,getResources().getString(R.string.notrecorded),Toast.LENGTH_LONG).show();
                        resumecamera();
                        addnewproductbest(barcodedataa);

                    }
                }

            }

            @Override
            public void onFailure(Call<Rootproductdetail> call, Throwable t) {

                Sqlitetable mytable= mydatabase.getdataforrowinproduct(barcodedataa);
                Sqlitetable mytablee=mydatabase.getdataforrowinproductoffline(barcodedataa);
                Sqlitetable mytableee=mydatabase.getdataforrowinfinalproductoffline(barcodedataa);


                if (mytable!=null)
                {
                    mWordViewModel.insert(new mytable(mytable.getPid(),mytable.getName(),mytable.getBarcode(),Integer.parseInt(showitems_number.getText().toString()),mytable.getImge(),mytable.getDescription(),mytable.getPrice(),null,mytable.getLocalnam(),mytable.getImgblob()));
                    Toast.makeText(Camera_activity.this, "" + getResources().getString(R.string.addedtocart), Toast.LENGTH_LONG).show();

                    resumecamera();

                }
                else if(mytablee!=null)
                {
                    mWordViewModel.insert(new mytable(mytablee.getPid(),mytablee.getName(),mytablee.getBarcode(),Integer.parseInt(showitems_number.getText().toString()),mytablee.getImge(),mytablee.getDescription(),mytablee.getPrice(),null,mytable.getLocalnam(),mytablee.getImgblob()));
                    Toast.makeText(Camera_activity.this, "" + getResources().getString(R.string.addedtocart), Toast.LENGTH_LONG).show();

                    resumecamera();
                }
                else if(mytableee!=null)
                {
                    mWordViewModel.insert(new mytable(mytableee.getPid(),mytableee.getName(),mytableee.getBarcode(),Integer.parseInt(showitems_number.getText().toString()),mytableee.getImge(),mytableee.getDescription(),mytableee.getPrice(),null,mytable.getLocalnam(),mytableee.getImgblob()));
                    Toast.makeText(Camera_activity.this, "" + getResources().getString(R.string.addedtocart), Toast.LENGTH_LONG).show();

                    resumecamera();
                }
                else
                {
                    Toast.makeText(Camera_activity.this,getResources().getString(R.string.notrecorded),Toast.LENGTH_LONG).show();
                    resumecamera();
                    addnewproductbest(barcodedataa);
                }

            }

        });


    }

    private void loginwithbarcodebest(final String barcodedataa, final int itemsnum)
    {
        Sqlitetable mytable= mydatabase.getdataforrowinproduct(barcodedataa);
        Sqlitetable mytablee=mydatabase.getdataforrowinproductoffline(barcodedataa);
        Sqlitetable mytableee=mydatabase.getdataforrowinfinalproductoffline(barcodedataa);


        if (mytable!=null)
        {
            mWordViewModel.insert(new mytable(mytable.getPid(),mytable.getName(),mytable.getBarcode(),Integer.parseInt(showitems_number.getText().toString()),mytable.getImge(),mytable.getDescription(),mytable.getPrice(),null,mytable.getLocalnam(),mytable.getImgblob()));
            Toast.makeText(Camera_activity.this, "" + getResources().getString(R.string.addedtocart), Toast.LENGTH_LONG).show();
            showitems_number.setText("1");
            resumecamera();

        }
        else if(mytablee!=null)
        {
            mWordViewModel.insert(new mytable(mytablee.getPid(),mytablee.getName(),mytablee.getBarcode(),Integer.parseInt(showitems_number.getText().toString()),mytablee.getImge(),mytablee.getDescription(),mytablee.getPrice(),null,mytable.getLocalnam(),mytablee.getImgblob()));
            Toast.makeText(Camera_activity.this, "" + getResources().getString(R.string.addedtocart), Toast.LENGTH_LONG).show();
            showitems_number.setText("1");
            resumecamera();
        }
        else if(mytableee!=null)
        {
            mWordViewModel.insert(new mytable(mytableee.getPid(),mytableee.getName(),mytableee.getBarcode(),Integer.parseInt(showitems_number.getText().toString()),mytableee.getImge(),mytableee.getDescription(),mytableee.getPrice(),null,mytable.getLocalnam(),mytableee.getImgblob()));
            Toast.makeText(Camera_activity.this, "" + getResources().getString(R.string.addedtocart), Toast.LENGTH_LONG).show();
            showitems_number.setText("1");
            resumecamera();
        }
        else {
            loginwithbarcode(barcodedataa,itemsnum);
        }


    }
    private void getproductdetails(final String barcodedata)
    {
        Retrofitclient myretro = Retrofitclient.getInstance();
        Retrofit retrofitt = myretro.getretro();


        final Endpoints myendpoints = retrofitt.create(Endpoints.class);

        mcall = myendpoints.getdetails("Bearer " + utils.gettoken(), barcodedata);
        mcall.enqueue(new Callback<Rootproductdetail>() {
            @Override
            public void onResponse(Call<Rootproductdetail> call, Response<Rootproductdetail> response) {


                if (response.isSuccessful()) {

                    if (response.body().getProduct() != null) {
                        if (response.body().getProduct().getImage()==null)
                        {
                            final String pronam, prodbar, prodimg, broddetail, brodprice, prodcat;
                            pronam = response.body().getProduct().getName();
                            prodbar = response.body().getProduct().getBarcode();
                            broddetail = response.body().getProduct().getDescription();
                            brodprice = response.body().getProduct().getPrice();
                            prodcat = response.body().getProduct().getCategory().getName();


                            namee.setText(pronam);
                            barcodeee.setText(prodbar);
                            unitpricee.setText(brodprice);

                            int curnumunit = Integer.parseInt(showitems_number.getText().toString());


                            final Double priceunit = Double.parseDouble(brodprice);
                            total_itemscoast.setText(String.valueOf(priceunit * curnumunit));
                            detailproduct.setVisibility(View.VISIBLE);



                        }
                        else{
                            final String pronam, prodbar, prodimg, broddetail, brodprice, prodcat;
                            pronam = response.body().getProduct().getName();
                            prodbar = response.body().getProduct().getBarcode();
                            prodimg = response.body().getProduct().getImage().getFilename();
                            String imgurl=getResources().getString(R.string.baseurlimg)+prodimg;


                            broddetail = response.body().getProduct().getDescription();
                            brodprice = response.body().getProduct().getPrice();
                            prodcat = response.body().getProduct().getCategory().getName();

                            Glide.with(Camera_activity.this)
                                    .load(imgurl)
                                    .into(productimage);
                            namee.setText(pronam);
                            barcodeee.setText(prodbar);
                            unitpricee.setText(brodprice);

                            int curnumunit = Integer.parseInt(showitems_number.getText().toString());


                            final Double priceunit = Double.parseDouble(brodprice);
                            total_itemscoast.setText(String.valueOf(priceunit * curnumunit));
                            detailproduct.setVisibility(View.VISIBLE);



                        }
                    }


                    else {
                        addnewproduct(barcodedata);
                        Toast.makeText(Camera_activity.this, "" + getResources().getString(R.string.notrecorded), Toast.LENGTH_LONG).show();
                        // resumecamera();
                    }


                } else {
                    addnewproduct(barcodedata);
                    Toast.makeText(Camera_activity.this, "" + getResources().getString(R.string.notrecorded), Toast.LENGTH_LONG).show();
                    //  resumecamera();

                }


            }
            @Override
            public void onFailure(Call<Rootproductdetail> call, Throwable t) {

                Sqlitetable mytable= mydatabase.getdataforrowinproduct(barcodedata);
                Sqlitetable mytablee= mydatabase.getdataforrowinproductoffline(barcodedata);

                String name,pricee,imagee;
                if(mytable!=null)
                {
                    imagee=  mytable.getImge();
                    pricee= mytable.getPrice();
                    name=   mytable.getName();

                    Glide.with(Camera_activity.this)
                            .load(imagee)
                            .into(productimage);
                    namee.setText(name);
                    barcodeee.setText(barcodedata);
                    unitpricee.setText(pricee);
                    int curnumunit=Integer.parseInt(showitems_number.getText().toString());
                    Double priceunit=Double.parseDouble(pricee);
                    total_itemscoast.setText(String .valueOf(priceunit*curnumunit));
                    detailproduct.setVisibility(View.VISIBLE);

                }
                else if(mytablee!=null)
                {
                    pricee= mytablee.getPrice();
                    name=   mytablee.getName();


                    namee.setText(name);
                    barcodeee.setText(barcodedata);
                    unitpricee.setText(pricee);
                    int curnumunit=Integer.parseInt(showitems_number.getText().toString());
                    Double priceunit=Double.parseDouble(pricee);
                    total_itemscoast.setText(String .valueOf(priceunit*curnumunit));
                    detailproduct.setVisibility(View.VISIBLE);
                }
                else
                {
                    Toast.makeText(Camera_activity.this,getResources().getString(R.string.notrecorded),Toast.LENGTH_LONG).show();
                    resumecamera();
                    addnewproduct(barcodedata);
                }




            }
        });
    }

    private void getproductdetailsbest(final String barcodedata)
    {
        Sqlitetable mytable= mydatabase.getdataforrowinproduct(barcodedata);
        Sqlitetable mytablee= mydatabase.getdataforrowinproductoffline(barcodedata);

        String name,pricee,imagee;
        if(mytable!=null)
        {

            imagee=  mytable.getImge();
            pricee= mytable.getPrice();
            name=   mytable.getName();

            Glide.with(Camera_activity.this)
                    .load(imagee)
                    .into(productimage);
            namee.setText(name);
            barcodeee.setText(barcodedata);
            unitpricee.setText(pricee);
            int curnumunit=Integer.parseInt(showitems_number.getText().toString());
            Double priceunit=Double.parseDouble(pricee);
            total_itemscoast.setText(String .valueOf(priceunit*curnumunit));
            showproductdetails.setVisibility(View.VISIBLE);
            addnewproduct.setVisibility(View.GONE);
            cameraSource.stop();

        }
        else if(mytablee!=null)
        {

            pricee= mytablee.getPrice();
            name=   mytablee.getName();


            namee.setText(name);
            barcodeee.setText(barcodedata);
            unitpricee.setText(pricee);
            int curnumunit=Integer.parseInt(showitems_number.getText().toString());
            Double priceunit=Double.parseDouble(pricee);
            total_itemscoast.setText(String .valueOf(priceunit*curnumunit));
            showproductdetails.setVisibility(View.VISIBLE);
            addnewproduct.setVisibility(View.GONE);
            cameraSource.stop();

        }
        else
        {
           loginwithbarcode(barcodedata,1);
        }

    }

    private void addnewproductbest(final String barcode)
    {
       addnewproduct.setVisibility(View.VISIBLE);
       showproductdetails.setVisibility(View.GONE);
        cameraSource.stop();

        addpric.setText(null);




        addbut.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {

                                          ArrayList<String> barcodes = new ArrayList<>();
                                          ArrayList<String> names = new ArrayList<>();
                                          ArrayList<String> prices = new ArrayList<>();
                                          ArrayList<String> details = new ArrayList<>();
                                          ArrayList<String> producers = new ArrayList<>();
                                          ArrayList<String> categories = new ArrayList<>();
                                          ArrayList<String> units = new ArrayList<>();
                                          ArrayList<String> localvalues = new ArrayList<>();
                                          ArrayList<String> reorders = new ArrayList<>();

                                          if (addpric.getText().toString().equals("")) {
                                              Toast.makeText(Camera_activity.this, getResources().getString(R.string.addprice), Toast.LENGTH_LONG).show();
                                          } else {

                                              barcodes.add(barcode);
                                              names.add(productname);
                                              prices.add(addpric.getText().toString());
                                              details.add("description");
                                              producers.add(producerid);
                                              categories.add(Categoryid);
                                              units.add(unitid);
                                              localvalues.add(LocalNameValue);
                                              reorders.add("distributor");

                                              //  werpx.cashiery.RoomDatabase.mytable word = new werpx.cashiery.RoomDatabase.mytable("1","product", barcode,Integer.parseInt("1"),null, null, addpric.getText().toString(), null);
                                              //  mWordViewModel.insert(word);

                                              addnewproducts(barcodes, names, prices, details, producers, categories, units, localvalues, reorders);

                                          }
                                      }
                                  });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addnewproduct.setVisibility(View.GONE);

                resumecamera();

            }
        });

    }

    public void addnewproducts(final ArrayList<String> bar, ArrayList<String> name, ArrayList<String> price,
                               ArrayList<String> description, ArrayList<String> producer,
                               ArrayList<String> category, ArrayList<String> units, ArrayList<String> localname, ArrayList<String> reorders) {


        String token = getSharedPreferences("login", Context.MODE_PRIVATE).getString("token", "");

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
                    try {
                        String addresponse = response.body().string();
                        JSONObject addobject = new JSONObject(addresponse);
                        String opercon = addobject.getString("operation");
                        if (opercon.trim().equals("success")) {

                            addnewproduct.setVisibility(View.GONE);
                            resumecamera();
                            mytable word = new mytable(null, productname, bar.get(0), 1, null, null, addpric.getText().toString(), null,productname,null);

                            mWordViewModel.insert(word);
                            Toast.makeText(Camera_activity.this, getResources().getString(R.string.addedtocart), Toast.LENGTH_LONG).show();



                        }
                        else {
                            addnewproduct.setVisibility(View.GONE);
                            resumecamera();

                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {

                    addnewproduct.setVisibility(View.GONE);
                    resumecamera();

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                Toast.makeText(Camera_activity.this, getResources().getString(R.string.addedtocart), Toast.LENGTH_LONG).show();


                mytable word = new mytable(null, productname, bar.get(0), 1, null, null, addpric.getText().toString(), null,productname,null);
                Boolean val = addofflinesproducts(productname, bar.get(0), addpric.getText().toString(), null, "detail",productname);
                Log.e("condition",String.valueOf(val));
                if (val)
                {
                    Toast.makeText(Camera_activity.this, getResources().getString(R.string.addedtocart), Toast.LENGTH_LONG).show();
                }

                mWordViewModel.insert(word);
                addnewproduct.setVisibility(View.GONE);

                resumecamera();




            }
        });








    }


    private void resumecamera() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        try {
            cameraSource.start(cameraView.getHolder());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private void checkifproductexsist(String numitems,String barcod)
    {
        boolean mycondition=true;


        // Update the cached copy of the words in the adapter.
        int i;
        if (orderproducts.size() != 0) {


            for (i = 0; i < orderproducts.size(); i++) {
                if (barcod.trim().equals(orderproducts.get(i).getPbar().trim())) {


                    mytable current = orderproducts.get(i);
                    // int totalitems = current.getPitemn() + Integer.parseInt(numitems);


                    int newitems=current.getPitemn()+Integer.parseInt(numitems);

                    mWordViewModel.updateproduct(newitems,Long.parseLong(barcod));
                    Toast.makeText(Camera_activity.this, "" + getResources().getString(R.string.addedtocart), Toast.LENGTH_LONG).show();
                    showitems_number.setText("1");

                    mycondition=false;
                }

            }
            if (mycondition)
            {
                loginwithbarcodebest(barcod,Integer.parseInt(numitems));



            }



        }
        else {
            loginwithbarcodebest(barcod,Integer.parseInt(numitems));

        }

    }


    public void restart ()
    {
        this.recreate();
    }



    @Override
    protected void onDestroy () {
        super.onDestroy();
        // for ActivityCompat#requestPermissions for more details.
        cameraSource.stop();
        cameraSource.release();



    }

    public void addnewproduct(final String barcode)
    {
        cameraSource.stop();
        final EditText addnam,addpric;
        TextView add, cancel;
        ImageView incre,decre;


        builder = new android.app.AlertDialog.Builder(Camera_activity.this);

        View myview = LayoutInflater.from(Camera_activity.this.getApplicationContext()).inflate(R.layout.addnewproduct, null);

        addpric=myview.findViewById(R.id.addprice);
        add=myview.findViewById(R.id.addproductt);
        cancel=myview.findViewById(R.id.cancelproduct);

        builder.setView(myview);
        alertDialog = builder.create();

        if (alertDialog.isShowing())
        {

        }
        else{
            alertDialog.show();

        }



        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (addpric.getText().toString().equals(""))
                {
                    Toast.makeText(Camera_activity.this,getResources().getString(R.string.addprice),Toast.LENGTH_LONG).show();
                }
                else{
                    barcodeee.setText(barcode);
                    unitpricee.setText(addpric.getText().toString());
                    total_itemscoast.setText(addpric.getText().toString());
                    Glide.with(Camera_activity.this)
                            .load(getResources().getDrawable(R.drawable.default_product))
                            .into(productimage);


                    //  werpx.cashiery.RoomDatabase.mytable word = new werpx.cashiery.RoomDatabase.mytable("1","product", barcode,Integer.parseInt("1"),null, null, addpric.getText().toString(), null);
                    //  mWordViewModel.insert(word);
                    alertDialog.cancel();

                    String token = getSharedPreferences("token", Context.MODE_PRIVATE).getString("usertoken", "");

                    Call<ResponseBody> calladd;
                   Retrofitclient myretro = Retrofitclient.getInstance();
                    Retrofit retrofittok = myretro.getretro();
                    final Endpoints myendpoints = retrofittok.create(Endpoints.class);
                    calladd = myendpoints.addproduct("Bearer " + token, barcode, Categoryid, "description"
                            ,productname, addpric.getText().toString(), producerid, "distributor",
                            unitid, LocalNameValue);
                    calladd.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                            if (response.isSuccessful()) {
                                loginwithbarcode(barcode,Integer.parseInt(showitems_number.getText().toString()));
                                //updateallproducts();
                            } else {

                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                            mytable word = new mytable(null, productname, barcode, 1, null, null, addpric.getText().toString(), null,productname,null);
                            Boolean val = addofflinesproducts(productname, barcode, addpric.getText().toString(), null, "detail",productname);
                            if (val)
                            {
                                Toast.makeText(Camera_activity.this, getResources().getString(R.string.addedtocart), Toast.LENGTH_LONG).show();
                            }

                            mWordViewModel.insert(word);
                            alertDialog.cancel();
                            resumecamera();




                        }
                    });


                    alertDialog.cancel();
                    resumecamera();

                }

            }

        });


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
                resumecamera();

            }
        });

    }

    public Boolean addofflinesproducts(String name ,String bar,String price ,String img,String detail,String localnam )
    {
        return mydatabase.insertofflieproducts(name,bar,price,img,detail,localnam);
    }








}
