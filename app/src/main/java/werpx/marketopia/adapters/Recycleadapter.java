package werpx.marketopia.adapters;


import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.daimajia.swipe.SwipeLayout;

import java.text.DecimalFormat;
import java.util.List;


import werpx.marketopia.GlideApp;
import werpx.marketopia.R;
import werpx.marketopia.RoomDatabase.mytable;
import werpx.marketopia.RoomDatabase.productViewmodel;
import werpx.marketopia.productdatabase;


public class Recycleadapter extends RecyclerView.Adapter<Recycleadapter.viewholder> {

    private   Context con;
    private productViewmodel mWordViewModel;

    private Thread mythread ;
    private Configuration configuration;
    private int applanguage;


    private final LayoutInflater mInflater;
    private List<mytable> mWords; // Cached copy of words
    private  int position;
    private productdatabase mydatabase;

    public Recycleadapter(Context context) {
        this.con=context;
        mInflater = LayoutInflater.from(context);
        mythread=new Thread();
        mydatabase = new productdatabase(context);

        configuration = con.getResources().getConfiguration();
        applanguage= configuration.getLayoutDirection();

        mWordViewModel = ViewModelProviders.of((FragmentActivity) context).get(productViewmodel.class);

    }

    @Override
    public viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.rowcashier, parent, false);
        return new viewholder(itemView);
    }



    @Override
    public void onBindViewHolder(final viewholder holder,   int position) {


        if (mWords != null) {

            final mytable current = mWords.get(position);
            if (applanguage==0)
            {
                holder.namee.setText(current.getPname());
                holder.unitprice.setText(current.getPprice());
                holder.barcodeee.setText(current.getPbar());
                holder.showitems_number.setText(String.valueOf(current.getPitemn()));
            }
            else{
                holder.namee.setText(current.getPlocalname());
                String pitemnumar= convertToArabic(String.valueOf(current.getPitemn()));
                String barcodear= convertToArabic(current.getPbar());
                String unitpricear= convertToArabic(current.getPprice());

                holder.showitems_number.setText(pitemnumar);
                holder.unitprice.setText(unitpricear);
                holder.barcodeee.setText(barcodear);

            }




            if (current.getPimg()!=null)
            {
                GlideApp
                        .with(con)
                        .load(current.getPimg())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(holder.productimage);
            }
            else{
                GlideApp
                        .with(con)
                        .load(con.getResources().getDrawable(R.drawable.default_product))
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(holder.productimage);
            }





            holder.showitems_number.setText(String.valueOf(current.getPitemn()));
            holder.showitems_number.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                    if (actionId==EditorInfo.IME_ACTION_DONE)
                    {
                        if (!holder.showitems_number.getText().toString().equals(""))
                        {
                            mWordViewModel.updateproduct(Long.parseLong(holder.showitems_number.getText().toString()),Long.parseLong(current.getPbar()));
                            InputMethodManager inputManager = (InputMethodManager)
                                    con.getSystemService(Context.INPUT_METHOD_SERVICE);
                            inputManager.toggleSoftInput(0, 0);

                            return true;
                        }

                    }
                    return false;
                }
            });




            Double coast=current.getPitemn()*Double.parseDouble(current.getPprice());
            // String convercoast= convertToEnglish(String.valueOf(coast));

            holder.total_itemscoast.setText(new DecimalFormat("##.##").format(coast));

            holder.add_items.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int num=Integer.parseInt(holder.showitems_number.getText().toString());
                    int currentnum=num+1;
                    holder.showitems_number.setText(String.valueOf(currentnum));
                    Double Uprice=Double.parseDouble(current.getPprice());
                    Double totalpriceP=currentnum*Uprice;
                    holder.total_itemscoast.setText(String.valueOf(totalpriceP));
                    mWordViewModel.updateproduct(Long.parseLong(holder.showitems_number.getText().toString()),Long.parseLong(current.getPbar()));


                }
            });
            holder.remove_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int num=Integer.parseInt(holder.showitems_number.getText().toString());
                    if (num <= 1) {
                        holder.showitems_number.setText(String.valueOf(1));

                    } else {
                        int cunum=num-1;
                        holder.showitems_number.setText(String.valueOf(cunum));
                        Double Uprice=Double.parseDouble(current.getPprice());
                        Double totalp=Uprice*cunum;
                        holder.total_itemscoast.setText(String.valueOf(totalp));
                        mWordViewModel.updateproduct(Long.parseLong(holder.showitems_number.getText().toString()),Long.parseLong(current.getPbar()));

                    }
                }
            });




        } else {
            // Covers the case of data not being ready yet.
            holder.namee.setText("No Product");
        }

    }


   public void setWords(List<mytable> words) {
        mWords = words;
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

    void notifycange()
    {
        notifyDataSetChanged();
    }
    public void deleteItem(int position) {

        mydatabase.deleterowofofflineproducts(mWords.get(position).getPbar());
        mWordViewModel.delterow(mWords.get(position));
        mWords.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }

    public Context getChontext()
    {
        return con;
    }

    void deleterow(int i)
    {
        if (mWords.size()!=0){
            mWordViewModel.delterow(mWords.get(i));
            mWords.remove(i);
            notifyItemRemoved(i);

        }

    }

    int getadapterposition()
    {
        int i = position;
        return i;

    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mWords != null)
            return mWords.size();
        else return 0;
    }




    class viewholder extends RecyclerView.ViewHolder {

        ImageView productimage,add_items,remove_item,xremove;
        SwipeLayout rowrecycle;
        TextView namee,unitprice,barcodeee,total_itemscoast,delete_product;
        EditText showitems_number;

        public viewholder(View itemView) {
            super(itemView);


            namee = itemView.findViewById(R.id.nameproduct);
            barcodeee=itemView.findViewById(R.id.numberproduct);
            total_itemscoast=itemView.findViewById(R.id.totalitemsc);
            productimage=itemView.findViewById(R.id.productimg);
            add_items=itemView.findViewById(R.id.additemsc);
            remove_item=itemView.findViewById(R.id.removeitemsc);
            showitems_number=itemView.findViewById(R.id.itemsnumberc);

            unitprice=itemView.findViewById(R.id.unitpricec);


            int pos= getadapterposition();
            mytable table=  mWords.get(pos);
            //  showitems_number.setText(String.valueOf(table.getPitemn()));







        }


    }

    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager)con.getSystemService(Context.CONNECTIVITY_SERVICE);
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