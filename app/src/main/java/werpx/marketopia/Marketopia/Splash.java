package werpx.marketopia.Marketopia;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.facebook.FacebookSdk;

import werpx.marketopia.R;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        FacebookSdk.sdkInitialize(this.getApplicationContext());


        Handler myhandler=new Handler();
        myhandler.postDelayed(new Runnable() {
            @Override
            public void run() {

               String token= getSharedPreferences("login", Context.MODE_PRIVATE).getString("token","");
               choosebeginingactivity(token);
                finishAffinity();
            }
        },2000);
    }


    public void choosebeginingactivity(String condition)
    {



        if (condition.trim().equals(""))
        {
            startActivity(new Intent(Splash.this, loginwithFB.class));
        }
        else
        {
            startActivity(new Intent(Splash.this, MainActivity.class));
        }

    }
}
