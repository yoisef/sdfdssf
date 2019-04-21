package werpx.marketopia.Marketopia;

import android.app.DatePickerDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


import com.google.gson.Gson;

import retrofit2.Call;
import werpx.marketopia.R;
import werpx.marketopia.RoomDatabase.historytable;
import werpx.marketopia.RoomDatabase.productViewmodel;
import werpx.marketopia.adapters.salesAdapter;
import werpx.marketopia.adapters.spinneradapter;
import werpx.marketopia.salehistorymodel.Selleshistory;


public class sales_history extends AppCompatActivity {

    private RecyclerView history_recycle;
    private salesAdapter mAdapter;
    private productViewmodel mWordViewModel;
    private TextView orders_coast, remove_history, historytgrba;
    private List<historytable> daytable, weektable, monthtable, choosedaytable,allhist;
    private Spinner sales_spinner;
    private android.support.v7.widget.Toolbar salestool;
    private Gson gson = new Gson();
    private Call<Selleshistory> mcall;
    private ImageView updatesalesdash;
    private Calendar myCalendar;
    private DatePickerDialog date;
    private Double daycoast = 0.0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_history);


        daytable = new ArrayList<>();
        weektable = new ArrayList<>();
        monthtable = new ArrayList<>();
        choosedaytable = new ArrayList<>();
        allhist=new ArrayList<>();

        myCalendar = Calendar.getInstance();
        //getretailersales();

        orders_coast = findViewById(R.id.totalsalescost);
        remove_history = findViewById(R.id.removehistory);


        updatesalesdash = findViewById(R.id.updatesales);
        history_recycle = findViewById(R.id.saleshistoryrecucle);
        history_recycle.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        history_recycle.setLayoutManager(linearLayoutManager);
        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(this, R.anim.layoutanimation);
        history_recycle.setLayoutAnimation(controller);
        mAdapter = new salesAdapter(this);
        history_recycle.setAdapter(mAdapter);
        history_recycle.scheduleLayoutAnimation();
        intlaze_toolbar();
/*
        updatesalesdash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.werpx.net";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
        */

        mWordViewModel = ViewModelProviders.of(this).get(productViewmodel.class);

        mWordViewModel.getAllhistory().observe(this, new Observer<List<historytable>>() {
            @Override
            public void onChanged(@Nullable List<historytable> historytables) {


                allhist=historytables;
                Double allordercoast = 0.0;
                for (int i = 0; i < historytables.size(); i++) {
                    historytable mtable = historytables.get(i);
                    Double currentamount = Double.parseDouble(mtable.getOramount());
                    allordercoast = allordercoast + currentamount;
                }
                mAdapter.setHistory(historytables);

                orders_coast.setText(new DecimalFormat("##.##").format(allordercoast));


            }
        });

        remove_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mWordViewModel.deleteallhist();
            }
        });


    }


    private void intlaze_toolbar() {
        salestool = findViewById(R.id.salestoolbar);
        setSupportActionBar(salestool);


        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);


        setSupportActionBar(salestool);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        // getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);





        sales_spinner = findViewById(R.id.salesviewway);


        sales_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 1: {

                        datapickerr();
                        sales_spinner.setSelection(0);


                        break;
                    }
                    case 2: {
                        choosefilter(1);
                        sales_spinner.setSelection(0);
                        break;
                    }
                    case 3: {
                        choosefilter(2);
                        sales_spinner.setSelection(0);

                        break;
                    }
                    case 4: {
                        choosefilter(3);
                        sales_spinner.setSelection(0);
                        break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        List<CharSequence> list = new ArrayList<>();
        list.addAll(Arrays.asList(getResources().getStringArray(R.array.salesway)));

        spinneradapter madapter = new spinneradapter(this, android.R.layout.simple_spinner_item, list);
        madapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        sales_spinner.setAdapter(madapter);


    }


    private void datapickerr() {

        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR); // current year
        int mMonth = c.get(Calendar.MONTH); // current month
        int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
        // date picker dialog
        date = new DatePickerDialog(sales_history.this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        // set day of month , month and year value in the edit text
                        if (view.isShown())
                        {

                            choosedaytable.clear();
                            filterdaychoose(dayOfMonth,monthOfYear,year);
                        }


                    }
                }, mYear, mMonth, mDay);
        date.show();



    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void choosefilter(final int i) {


        mWordViewModel = ViewModelProviders.of(this).get(productViewmodel.class);

        mWordViewModel.getAllhistory().observe(this, new Observer<List<historytable>>() {
            @Override
            public void onChanged(@Nullable final List<historytable> historytables) {

                Double monthcoast = 0.0;
                Double daycoast = 0.0;
                Double weekcoast = 0.0;
                Double allordercoast = 0.0;
                SimpleDateFormat sdf = new SimpleDateFormat("E, dd-MMMM-yy");

                //  String dataformat = sdf.format(dateBefore7Days);
                for (int i = 0; i < historytables.size(); i++) {
                    Log.e("historyrow", "for");


                    historytable myhis = historytables.get(i);
                    Locale locale = new Locale("ar", "KW");

                    Date currDate = new Date();
                    String formattedDate = sdf.format(currDate);

                    if (myhis.getOrdata().trim().equals(formattedDate.trim())) {
                        Double currentamount = Double.parseDouble(myhis.getOramount());
                        daycoast = daycoast + currentamount;
                        daytable.add(myhis);
                    }

                    String rowhis = myhis.getOrdata();
                    Date data = new Date();
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(data);
                    cal.add(Calendar.DATE, -7);
                    Date dateBefore7Days = cal.getTime();


                    SimpleDateFormat df = new SimpleDateFormat("E, dd-MMMM-yy");
                    try {
                        Date hisdata = df.parse(rowhis);


                        if (hisdata.after(dateBefore7Days)) {

                            Double currentamount = Double.parseDouble(myhis.getOramount());
                            weekcoast = weekcoast + currentamount;

                            weektable.add(myhis);
                        }
                    } catch (ParseException e) {
                        Log.e("exceptionyabne", e.getMessage() + "weekcon");
                    }


                    Calendar calm = Calendar.getInstance();
                    calm.setTime(data);
                    calm.add(Calendar.DATE, -30);
                    Date dateBefore30Days = calm.getTime();


                    SimpleDateFormat dfm = new SimpleDateFormat("E, dd-MMMM-yy");
                    try {
                        Date datamonth = dfm.parse(rowhis);
                        Log.e("trymonth", datamonth.toString());


                        if (datamonth.after(dateBefore30Days)) {
                            Log.e("addmonth", dateBefore30Days.toString());

                            Double currentamount = Double.parseDouble(myhis.getOramount());
                            monthcoast = monthcoast + currentamount;

                            monthtable.add(myhis);
                        }
                    } catch (ParseException e) {
                        Log.e("exceptionyabne", e.getMessage() + "weekcon");
                    }


                }

                switch (i) {
                    case 1: {
                        weektable.clear();
                        choosedaytable.clear();
                        monthtable.clear();
                        mAdapter.sethistoryfilter(daytable);
                        orders_coast.setText(new DecimalFormat("##.##").format(daycoast));
                        break;
                    }

                    case 2: {
                        daytable.clear();
                        choosedaytable.clear();
                        monthtable.clear();
                        mAdapter.sethistoryfilter(weektable);
                        orders_coast.setText(new DecimalFormat("##.##").format(weekcoast));
                        break;
                    }
                    case 3: {
                        weektable.clear();
                        choosedaytable.clear();
                        daytable.clear();
                        mAdapter.sethistoryfilter(monthtable);
                        orders_coast.setText(new DecimalFormat("##.##").format(monthcoast));
                        break;
                    }


                    default:
                        orders_coast.setText(new DecimalFormat("##.##").format(allordercoast));
                        mAdapter.setHistory(historytables);

                }


            }
        });


    }

    private void filterdaychoose(final int Tday, final int Tmonth, final int Tyear)
    {


        daycoast=0.0;

        for (int i = 0; i < allhist.size(); i++) {


            historytable myhis = allhist.get(i);
            Locale locale = new Locale("ar", "KW");

            Calendar cal = Calendar.getInstance();
            cal.set(Tyear, Tmonth, Tday);
            String format = "E, dd-MMMM-yy";
            SimpleDateFormat sdf = new SimpleDateFormat("E, dd-MMMM-yy");
            String formattedDate = sdf.format(cal.getTime());

            if (myhis.getOrdata().trim().equals(formattedDate.trim())) {
                Double currentamount = Double.parseDouble(myhis.getOramount());
                daycoast = daycoast+currentamount;

                choosedaytable.add(myhis);


            }

        }



        weektable.clear();
        daytable.clear();
        monthtable.clear();

        mAdapter.sethistoryfilter(choosedaytable);
        orders_coast.setText(new DecimalFormat("##.##").format(daycoast));

        // choosedaytable.clear();

    }


}



