package werpx.marketopia.adapters;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.LayoutDirection;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import werpx.marketopia.R;
import werpx.marketopia.RoomDatabase.historytable;
import werpx.marketopia.RoomDatabase.productViewmodel;
import werpx.marketopia.detailsadapter;


public class salesAdapter extends RecyclerView.Adapter<salesAdapter.viewholder> {

    private Context con;
    private productViewmodel mHistoryViewModel;




    private final LayoutInflater mInflater;
    private List<historytable> mHistory; // Cached copy of words

    public salesAdapter(Context context) {
        this.con=context;
        mInflater = LayoutInflater.from(context);

        mHistory=new ArrayList<>();


        mHistoryViewModel = ViewModelProviders.of((FragmentActivity) context).get(productViewmodel.class);

    }



    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.rowhistory, parent, false);
        return new viewholder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final viewholder holder, int position) {


        if (mHistory!=null)
        {


            holder.myrecycle.setLayoutManager(new LinearLayoutManager(con));
            holder.myrecycle.setAdapter(new detailsadapter(mHistory.get(position).getOrlist(),con));
            holder.myrecycle.scheduleLayoutAnimation();

            historytable mytable=mHistory.get(position);
            holder.orderid.setText(String.valueOf(position+1));
            holder.ordereshowdetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String textcond=holder.ordereshowdetails.getText().toString();

                    if (textcond.equalsIgnoreCase("عرض التفاصيل")||textcond.equalsIgnoreCase("Show Details"))
                    {
                        holder.ordereshowdetails.setText(con.getResources().getString(R.string.hidedetails));
                        holder.myrecycle.setVisibility(View.VISIBLE);


                    }
                    else {
                        holder.ordereshowdetails.setText(con.getResources().getString(R.string.showdetails));
                        holder.myrecycle.setVisibility(View.GONE);


                    }







                }
            });

           int lan= checkapplanguage();
           if (lan== LayoutDirection.RTL)
           {

            // String amount=  convertToArabic(mytable.getOramount());
             String units=convertToArabic(mytable.getOrunits());
                 holder.oredramount.setText(new DecimalFormat("##.##").format(Double.valueOf(mytable.getOramount())));


               holder.orderunits.setText(units);
           }
           else {
               holder.oredramount.setText(new DecimalFormat("##.##").format(Double.valueOf(mytable.getOramount())));

               holder.orderunits.setText(mytable.getOrunits());
           }



            holder.orderdata.setText(mytable.getOrdata());
        }



    }

    public void setHistory(List<historytable> words) {
        mHistory = words;
        notifyDataSetChanged();
    }
    public String convertToArabic (String value){

        String newValue = (((((((((((value + "")
                .replaceAll("1", "١")).replaceAll("2", "٢"))
                .replaceAll("3", "٣")).replaceAll("4", "٤"))
                .replaceAll("5", "٥")).replaceAll("6", "٦"))
                .replaceAll("7", "٧")).replaceAll("8", "٨"))
                .replaceAll("9", "٩")).replaceAll("0", "٠"));
        return newValue;
    }
    public int checkapplanguage()
    {
        Configuration configuration;
        int applanguage;
        configuration = con.getResources().getConfiguration();
        applanguage= configuration.getLayoutDirection();
        return applanguage;
    }

   public void sethistoryfilter(List<historytable> table)
    {
        mHistory=table;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return mHistory.size();
    }

    class viewholder extends RecyclerView.ViewHolder{

        TextView orderdata,orderid,oredramount,orderunits,ordereshowdetails,teste;
        RecyclerView myrecycle;


        public viewholder(View itemView) {
            super(itemView);

            orderdata=itemView.findViewById(R.id.txt_orderDateTime);
            orderid=itemView.findViewById(R.id.id_order);
            oredramount=itemView.findViewById(R.id.totalordercoast);
            orderunits=itemView.findViewById(R.id.txt_orderQuantity);
            ordereshowdetails=itemView.findViewById(R.id.orderdetails);
            myrecycle=itemView.findViewById(R.id.order_list);


        }
    }
}
