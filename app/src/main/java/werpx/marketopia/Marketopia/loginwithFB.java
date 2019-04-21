package werpx.marketopia.Marketopia;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.security.ProviderInstaller;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.net.ssl.SSLContext;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import werpx.marketopia.Endpoints;
import werpx.marketopia.R;
import werpx.marketopia.Retrofitclient;
import werpx.marketopia.Utils;
import werpx.marketopia.modelsauth.Roottoken;

public class loginwithFB extends AppCompatActivity {


    private static final String EMAIL = "email";
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private ProgressDialog mdialog;
    Utils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginwith_fb);
        utils = new Utils(this);

        sslconnection();



            utils.sslconnection();
            callbackManager = CallbackManager.Factory.create();
            requestPermission();
            loginButton = (LoginButton) findViewById(R.id.login_button);
            loginButton.setReadPermissions(Arrays.asList("email", "public_profile"));


            //  loginButton.setReadPermissions(Arrays.asList("first_name","email","user_birthday"));
            // If you are using in a fragment, call loginButton.setFragment(this);

            // Callback registration
            loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {



                    mdialog = new ProgressDialog(loginwithFB.this);
                    mdialog.setMessage(getResources().getString(R.string.recievdata));
                    mdialog.show();


                    GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(JSONObject object, GraphResponse response) {
                            mdialog.cancel();


                            try {
                              String accountname= object.getString("name");
                              String accountemail= object.getString("email");
                              String accountid=object.getString("id");
                              String accountimg="https://graph.facebook.com/" + accountid+ "/picture?type=large";

                                SharedPreferences sharedPreferencess=getSharedPreferences("consumerinfo",Context.MODE_PRIVATE);
                                SharedPreferences.Editor editorr=sharedPreferencess.edit();
                                editorr.putString("email",accountemail);
                                editorr.putString("img",accountimg);
                                editorr.putString("name",accountname);
                                editorr.apply();
                                Toast.makeText(loginwithFB.this,accountname+"---"+accountemail,Toast.LENGTH_SHORT).show();
                              registerconsumer(accountname,null,null,accountemail,null,null,accountimg);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }



                            Log.e("responsefacebook", response.toString());



                        }
                    });
                    // App code

                    Bundle parameters = new Bundle();
                    parameters.putString("fields", "id,name,birthday,email,picture");
                    graphRequest.setParameters(parameters);
                    graphRequest.executeAsync();
                }

                @Override
                public void onCancel() {
                    // App code
                    Log.e("responsefacebook", "cancel");
                    Toast.makeText(loginwithFB.this, "cancel", Toast.LENGTH_LONG).show();

                }

                @Override
                public void onError(FacebookException exception) {
                    // App code
                    Log.e("responsefacebook", exception.toString());
                    Toast.makeText(loginwithFB.this, exception.toString(), Toast.LENGTH_LONG).show();

                }
            });


        }


        private void requestPermission () {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.ACCESS_WIFI_STATE},
                    1);
        }

        @Override
        public void onRequestPermissionsResult ( int requestCode, String permissions[],
        int[] grantResults){
            switch (requestCode) {
                case 1:
                    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_SHORT).show();

                        // main logic
                    } else {
                        Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                                    != PackageManager.PERMISSION_GRANTED) {
                                showMessageOKCancel("You need to allow access permissions",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermission();
                                                }
                                            }
                                        });
                            }
                        }
                    }
                    break;
            }
        }

        private void showMessageOKCancel (String message, DialogInterface.OnClickListener okListener)
        {
            new AlertDialog.Builder(loginwithFB.this)
                    .setMessage(message)
                    .setPositiveButton("OK", okListener)
                    .setNegativeButton("Cancel", null)
                    .create()
                    .show();
        }


        @Override
        protected void onActivityResult ( int requestCode, int resultCode, Intent data){
            callbackManager.onActivityResult(requestCode, resultCode, data);
            super.onActivityResult(requestCode, resultCode, data);
        }

    public void sslconnection()
    {
        try {
            ProviderInstaller.installIfNeeded(getApplicationContext());
            SSLContext sslContext;
            sslContext = SSLContext.getInstance("TLSv1.2");
            sslContext.init(null, null, null);
            sslContext.createSSLEngine();
        } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException
                | NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
        }
    }

        public void registerconsumer (final String name, String mobile, final String password, final String email, String city, String address, final String imgurl)
        {
            Call<ResponseBody> mcall;
            Retrofitclient myretro = Retrofitclient.getInstance();
            Retrofit retrofittok = myretro.getretro();
            final Endpoints myendpoints = retrofittok.create(Endpoints.class);
            mcall = myendpoints.registerconsumer("application/x-www-form-urlencoded", name, mobile, password,email,address,city,null);
            mcall.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    if (response.isSuccessful())
                    {
                        try {
                            String registerresponse=response.body().string();
                            JSONObject registerobject=new JSONObject(registerresponse);
                           String operation= registerobject.getString("operation");
                           if (operation.trim().equals("success"))
                           {
                               Toast.makeText(loginwithFB.this,"success",Toast.LENGTH_SHORT).show();




                               consumerlogin(email,password,name,imgurl);
                           }
                           else {
                               consumerlogin(email,password,name,imgurl);

                           }

                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });
        }



        public void consumerlogin(final String email, String password, final String name, final String img)
        {
           // final ProgressDialog dialog=new ProgressDialog(this);
           // dialog.setMessage(getResources().getString(R.string.login)+"...");
            //dialog.show();
            Call<Roottoken> mcall;
            Retrofitclient myretro = Retrofitclient.getInstance();
            Retrofit retrofittok = myretro.getretro();
            final Endpoints myendpoints = retrofittok.create(Endpoints.class);
           mcall= myendpoints.signuser("application/x-www-form-urlencoded",email,password);
            mcall.enqueue(new Callback<Roottoken>() {
                @Override
                public void onResponse(Call<Roottoken> call, Response<Roottoken> response) {
                    if (response.isSuccessful())
                    {
                        if (response.body().getData()!=null)
                        {
                            String token=response.body().getData().getToken();
                            SharedPreferences sharedPreferences=getSharedPreferences("login",Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor=sharedPreferences.edit();
                            editor.putString("token",token);
                            editor.apply();
                          //  dialog.dismiss();
                            startActivity(new Intent(loginwithFB.this, MainActivity.class));
                            finish();

                        }
                    }
                }

                @Override
                public void onFailure(Call<Roottoken> call, Throwable t) {

                }
            });
        }
    }


