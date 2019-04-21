package werpx.marketopia.Marketopia;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;


import werpx.marketopia.R;
import werpx.marketopia.RoomDatabase.productViewmodel;
import werpx.marketopia.adapters.spinneradapter;

public class Setting extends AppCompatActivity {


    String CurrentLang;
    private Spinner spinner_language;
    private productViewmodel mWordViewModel;
    android.support.v7.widget.Toolbar mytoolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        mytoolbar=findViewById(R.id.toolbarsetting);
        setSupportActionBar(mytoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        mWordViewModel = ViewModelProviders.of(this).get(productViewmodel.class);



        spinner_language=findViewById(R.id.languagespinner);
        List<CharSequence> list=new ArrayList<>();
        list.addAll(Arrays.asList(getResources().getStringArray(R.array.language)));

        spinneradapter madapter=new spinneradapter(this,android.R.layout.simple_spinner_item,list);
        madapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        spinner_language.setAdapter(madapter);

        spinner_language.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position)
                {
                    case 1:{

                        changelanguage("ar");
                        mWordViewModel.deleteallmenu();
                        restart();

                        break;
                    }
                    case 2:{

                        changelanguage("en");
                        mWordViewModel.deleteallmenu();
                        restart();


                        break;
                    }
                    case 3:{


                        break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        CurrentLang = Locale.getDefault().getLanguage();




    }


    public void changelanguage(String localcode)
    {



        Resources res=getResources();
        DisplayMetrics dm=res.getDisplayMetrics();
        Configuration conf=res.getConfiguration();
        if (Build.VERSION.SDK_INT >= 17)
        {
            conf.setLocale(new Locale(localcode.toLowerCase()));
        }
        else{
            conf.locale=new Locale(localcode.toLowerCase());
        }
        res.updateConfiguration(conf,dm);
    }

    public void restart ()
    {
        Intent i = getBaseContext().getPackageManager()
                .getLaunchIntentForPackage( getBaseContext().getPackageName() );
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
