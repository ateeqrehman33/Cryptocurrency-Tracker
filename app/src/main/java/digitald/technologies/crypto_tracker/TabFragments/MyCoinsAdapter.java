package digitald.technologies.crypto_tracker.TabFragments;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import digitald.technologies.crypto_tracker.R;



public class MyCoinsAdapter extends RecyclerView.Adapter<MyCoinsAdapter.ViewHolder>  {

    private ArrayList<CoinData> mArrayList;
    Context context;
    SQLiteDatabase db;

    List<MyCoinModel> myCoinModel = new ArrayList<>();

    List<MyCoinDataModel> myCoinDataModel = new ArrayList<>();

    private Double mycurrency;
    private String currname;







    public MyCoinsAdapter(ArrayList<CoinData> arrayList,List<MyCoinModel> mycoinmodel,List<MyCoinDataModel> myCoinDataModel, Double mycurr, String name) {


        this.mArrayList = arrayList;
        this.myCoinModel = mycoinmodel;
        this.myCoinDataModel=myCoinDataModel;

        this.mycurrency = mycurr;
        this.currname = name;



    }











    @Override
    public MyCoinsAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {


        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.my_coins_card, viewGroup, false);

        context = viewGroup.getContext();

        return new ViewHolder(view);



    }

    @Override
    public void onBindViewHolder(final MyCoinsAdapter.ViewHolder viewHolder, final int position) {




        viewHolder.Holdings.setText(myCoinModel.get(position).quantity.toString());
        viewHolder.Symbol.setText(myCoinModel.get(position).symbol);



        Double cprice = Double.valueOf(myCoinDataModel.get(position).price);
        Double cpricefinal = round(cprice*mycurrency,2);
        viewHolder.Price.setText(currname+cpricefinal.toString());

        Double ctfh = Double.valueOf(myCoinDataModel.get(position).tfh);
        Double ctfhfinal = round(ctfh,2);



        if (ctfhfinal< 0){
                    viewHolder.Pctfh.setTextColor(Color.RED);
                } else {
                    viewHolder.Pctfh.setTextColor(Color.parseColor("#34a853"));
                }








        viewHolder.Pctfh.setText(ctfhfinal+"%");



        Double pholding = Double.valueOf(myCoinModel.get(position).quantity)*(myCoinDataModel.get(position).price);
        Double finalprice = round(pholding*mycurrency, 2);
        viewHolder.Pholdings.setText(currname+finalprice.toString());









        final String symbol = myCoinModel.get(position).symbol;

        String url = "https://chasing-coins.com/api/v1/std/logo/" + symbol;

        Picasso.with(context)
                .load(url)
                .error(R.drawable.ic_img_error)
                .into(viewHolder.imageView);


//image loader ends here
        //onclick for views and sening data to another activity


        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Context context = view.getContext();


                AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                alertDialog.setTitle("Delete Coin");
                alertDialog.setMessage("Do you want to delete this Coin ??");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {







                                db = context.openOrCreateDatabase("CoinDB", Context.MODE_PRIVATE, null);
                                Cursor c = db.rawQuery("SELECT * FROM mycoins WHERE id='" + myCoinModel.get(position).indexx + "'", null);
                                c.moveToFirst();
                                db.execSQL("DELETE FROM mycoins WHERE id='" + myCoinModel.get(position).indexx + "'");


                                Toast.makeText(context, "Coin "+myCoinModel.get(position).symbol+" Deleted", Toast.LENGTH_SHORT).show();





                                myCoinModel.remove(position);
                                myCoinDataModel.remove(position);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, myCoinModel.size());
                                notifyItemRangeChanged(position, myCoinDataModel.size());


                                ((TabPortfoFragment)context).recreate();







                                dialog.dismiss();
                            }
                        });
                alertDialog.show();



            }
        });


//ends here


    }








    @Override
    public int getItemCount() {
        return myCoinModel.size();
    }




    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.activity_tab_portfo_fragment, parent, false));
        }

        private TextView Holdings ,Symbol ,Pctfh ,Price,Pholdings;
        private ImageView imageView;



        public ViewHolder(View view) {
            super(view);
            context = view.getContext();





            Holdings = view.findViewById(R.id.holdings);
            Price = view.findViewById(R.id.priceof);
            Symbol = view.findViewById(R.id.Symbol);
            imageView = view.findViewById(R.id.ivLogo);
            Pctfh = view.findViewById(R.id.twohrof);
            Pholdings = view.findViewById(R.id.priceholdings);



        }

    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
}






