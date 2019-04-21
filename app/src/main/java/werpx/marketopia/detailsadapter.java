package werpx.marketopia;

import android.content.Context;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.text.DecimalFormat;
import java.util.List;

import werpx.marketopia.RoomDatabase.mytable;


public class detailsadapter extends RecyclerView.Adapter<detailsadapter.viewholder> {

    public List<mytable> details;
    public Context context;
    private Configuration configuration;
    private int applanguage;

    public detailsadapter(List<mytable> listdetails, Context con)
    {
        this.details=listdetails;
        this.context=con;

        configuration = con.getResources().getConfiguration();
        applanguage= configuration.getLayoutDirection();
    }


    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myview= LayoutInflater.from(context).inflate(R.layout.rowrecycledetails,parent,false);
        viewholder viewholder=new viewholder(myview);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {

        Double total= Double.parseDouble(details.get(position).getPprice())*details.get(position).getPitemn();

        if (applanguage==0)
        {
            holder.namee.setText(details.get(position).getPname());
            holder.unitprice.setText(details.get(position).getPprice());
            holder.itemsnumber.setText(String.valueOf(details.get(position).getPitemn()));
            holder.barcodeee.setText(details.get(position).getPbar());

            holder.total_itemscoast.setText(new DecimalFormat("##.##").format(total));

        }
        else{

            String unitpricear=convertToArabic(details.get(position).getPprice());
            String itemsnumar=convertToArabic(String.valueOf(details.get(position).getPitemn()));
            String barcodear=convertToArabic(details.get(position).getPbar());

            holder.unitprice.setText(unitpricear);
            holder.itemsnumber.setText(itemsnumar);
            holder.barcodeee.setText(barcodear);

           String format=new DecimalFormat("##.##").format(total);
            String totalitemcoastar=convertToArabic(format);

            holder.total_itemscoast.setText(totalitemcoastar);
            holder.namee.setText(details.get(position).getPlocalname());
        }



        if (details.get(position).getPimg()==null)
        {
            GlideApp.with(context)
                    .load(context.getResources().getDrawable(R.drawable.default_product))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.productimage);
        }
        else{
            GlideApp.with(context)
                    .load(details.get(position).getPimg())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.productimage);
        }


    }

    @Override
    public int getItemCount() {
        return details.size();
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

    class viewholder extends RecyclerView.ViewHolder{

        ImageView productimage;

        TextView namee,unitprice,barcodeee,total_itemscoast,itemsnumber;


        public viewholder(View itemView) {
            super(itemView);

            productimage=itemView.findViewById(R.id.productimgd);
            namee=itemView.findViewById(R.id.nameproductd);
            unitprice=itemView.findViewById(R.id.unitpricecd);
            barcodeee=itemView.findViewById(R.id.numberproductd);
            total_itemscoast=itemView.findViewById(R.id.totalitemscd);
            itemsnumber=itemView.findViewById(R.id.numitemsd);



        }
    }
}
