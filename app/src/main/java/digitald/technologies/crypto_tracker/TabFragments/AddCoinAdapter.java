package digitald.technologies.crypto_tracker.TabFragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import digitald.technologies.crypto_tracker.AddQuantity;
import digitald.technologies.crypto_tracker.R;


public class AddCoinAdapter extends RecyclerView.Adapter<AddCoinAdapter.ViewHolder> implements Filterable {
    private SharedPreferences sharedPreferencesad;
    private SharedPreferences.Editor editorad;

    private ArrayList<CoinData> mArrayList;
    private ArrayList<CoinData> mFilteredList;

    private Context context;
    private Double mycurrency;
    private String currname;
    public String cname;
    public String csymbol;
    public String cprice;


    SQLiteDatabase db;



    public AddCoinAdapter(ArrayList<CoinData> arrayList, Double mycurr, String name) {


        this.mArrayList = arrayList;
        this.mFilteredList = arrayList;
        this.mycurrency = mycurr;
        this.currname = name;


    }


    @Override
    public AddCoinAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {


        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardaddcoin, viewGroup, false);

        context = viewGroup.getContext();

        return new ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(final AddCoinAdapter.ViewHolder viewHolder, final int position) {

        sharedPreferencesad = PreferenceManager.getDefaultSharedPreferences(context);
        editorad = sharedPreferencesad.edit();


        final CoinData pcoinData = mFilteredList.get(position);





        try {


            if (mFilteredList != null) {



                 viewHolder.Name.setText((mFilteredList.get(position).getName()));
                 viewHolder.Rank.setText(mFilteredList.get(position).getRank() + ". ");
                 viewHolder.Symbol.setText("(" + mFilteredList.get(position).getSymbol() + ")");

            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }



        //onclick for views and sening data to another activity



        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                Context context = view.getContext();
                Intent intent = new Intent(context,AddQuantity.class);
                try {


                    if (mFilteredList != null) {

                        intent.putExtra("cname", mFilteredList.get(position).getName());
                        intent.putExtra("csymbol", mFilteredList.get(position).getSymbol());
                        intent.putExtra("pusd", mFilteredList.get(position).getPrice_usd().toString());

                        }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }



                context.startActivity(intent);
            }
        });





//ends here


    }

    @Override
    public int getItemCount() {
        return mFilteredList.size();
    }




    //search view starts
    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                    mFilteredList = mArrayList;
                } else {

                    ArrayList<CoinData> filteredlist = new ArrayList<>();

                    for (CoinData coinData : mArrayList) {


                        if (coinData.getName().toLowerCase().contains(charString) ||
                                coinData.getSymbol().toLowerCase().contains(charString)) {
                            filteredlist.add(coinData);
                        }

                    }
                    mFilteredList = filteredlist;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilteredList;
                return filterResults;

            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilteredList = (ArrayList<CoinData>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }


//search view ends



    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.activity_add_coin, parent, false));
        }

        private TextView Name, Rank, Symbol;


        Spinner spinner;


        public ViewHolder(View view) {
            super(view);
            context = view.getContext();


            Spinner spinner = view.findViewById(R.id.spinner);


            Name = view.findViewById(R.id.Name);
            Rank = view.findViewById(R.id.Rank);

            Symbol = view.findViewById(R.id.Symbol);



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






