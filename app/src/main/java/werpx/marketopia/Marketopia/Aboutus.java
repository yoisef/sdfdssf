package werpx.marketopia.Marketopia;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import werpx.marketopia.R;


public class Aboutus extends AppCompatActivity {

    private  ImageView logoimg;
    private TextView description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus);

    }
}