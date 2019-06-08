package digitald.technologies.crypto_tracker.TabFragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import digitald.technologies.crypto_tracker.CoinInfo;
import digitald.technologies.crypto_tracker.R;


public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> implements Filterable {
    private SharedPreferences sharedPreferencesad;
    private SharedPreferences.Editor editorad;

    private ArrayList<CoinData> mArrayList;
    private ArrayList<CoinData> mFilteredList;

    private Context context;
    private Double mycurrency;
    private String currname;


    public DataAdapter(ArrayList<CoinData> arrayList, Double mycurr, String name) {


        this.mArrayList = arrayList;
        this.mFilteredList = arrayList;
        this.mycurrency = mycurr;
        this.currname = name;


    }


    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {


        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view, viewGroup, false);

        context = viewGroup.getContext();

        return new ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(final DataAdapter.ViewHolder viewHolder, final int position) {

        sharedPreferencesad = PreferenceManager.getDefaultSharedPreferences(context);
        editorad = sharedPreferencesad.edit();


        final CoinData pcoinData = mFilteredList.get(position);


        String pcoh = mFilteredList.get(position).getPercent_change_1h();
        Float pctfh = mFilteredList.get(position).getPercent_change_24h();
        String pcsd = mFilteredList.get(position).getPercent_change_7d();


        try {


            if (pctfh != null) {

                String pctfhs = Float.toString(pctfh);
                if (pctfhs == null) {
                    pctfhs = "Null";
                }

                if (pctfhs.startsWith("-")) {
                    viewHolder.Pctfh.setTextColor(Color.RED);
                } else {
                    viewHolder.Pctfh.setTextColor(Color.parseColor("#34a853"));
                }
            }

            if (pcsd == null) {
                pcsd = "Null";
            }
            if (pcsd.startsWith("-")) {
                viewHolder.Pcsd.setTextColor(Color.RED);
            } else {
                viewHolder.Pcsd.setTextColor(Color.parseColor("#34a853"));
            }


            if (pcoh == null) {
                pcoh = "Null";
            }

            if (pcoh.startsWith("-")) {
                viewHolder.Pcoh.setTextColor(Color.RED);
            } else {

                viewHolder.Pcoh.setTextColor(Color.parseColor("#34a853"));
            }


        } catch (NullPointerException e) {
            e.printStackTrace();
        }


        try {


            if (mFilteredList != null) {


                viewHolder.Name.setText((mFilteredList.get(position).getName()));
                viewHolder.Rank.setText(mFilteredList.get(position).getRank() + ". ");
                if (position == 0)
                    viewHolder.Price.setText("Price : $" + mFilteredList.get(position).getPrice_usd());
                else
                    viewHolder.Price.setText("Price : " + mFilteredList.get(position).getprice_btc() + " BTC");
                viewHolder.Symbol.setText("(" + mFilteredList.get(position).getSymbol() + ")");
                viewHolder.Pcoh.setText(mFilteredList.get(position).getPercent_change_1h() + "% :1hr");
                viewHolder.Pctfh.setText(mFilteredList.get(position).getPercent_change_24h() + "% :24hr");
                viewHolder.Pcsd.setText(mFilteredList.get(position).getPercent_change_7d() + "% :7d");
                final Double pricedub = mFilteredList.get(position).getPrice_usd();
                Double finalprice = pricedub * mycurrency;
                finalprice = round(finalprice, 4);
                viewHolder.Price_other.setText(currname + finalprice.toString());

            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }


        final String symbol = mFilteredList.get(position).getSymbol().toString();

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
                Context context = view.getContext();
                Intent intent = new Intent(context, CoinInfo.class);
                try {


                    if (mFilteredList != null) {

                        intent.putExtra("cname", mFilteredList.get(position).getName());

                        intent.putExtra("cmaxsup", mFilteredList.get(position).getMax_supply());
                        intent.putExtra("csymbol", mFilteredList.get(position).getSymbol());
                        intent.putExtra("crank", mFilteredList.get(position).getRank());

                        intent.putExtra("1h", mFilteredList.get(position).getPercent_change_1h());
                        intent.putExtra("24h", Float.toString(mFilteredList.get(position).getPercent_change_24h()));
                        intent.putExtra("7d", mFilteredList.get(position).getPercent_change_7d());

                        intent.putExtra("pusd", mFilteredList.get(position).getPrice_usd().toString());


                        intent.putExtra("c24hvusd", mFilteredList.get(position).getvolume_usd_24h());
                        intent.putExtra("price_btc", mFilteredList.get(position).getprice_btc());
                        intent.putExtra("cts", mFilteredList.get(position).gettotal_supply());

                        intent.putExtra("cas", mFilteredList.get(position).getavailable_supply());
                        intent.putExtra("cmv", mFilteredList.get(position).getmarket_cap_usd());
//                        intent.putExtra("cmaxsup", mFilteredList.get(position).getMax_supply());

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
            super(inflater.inflate(R.layout.tab_one_fragment, parent, false));
        }

        private TextView Name, Rank, Price, Price_other, Symbol, Pcoh, Pctfh, Pcsd;
        private ImageView imageView;
        Spinner spinner;


        public ViewHolder(View view) {
            super(view);
            context = view.getContext();


            Spinner spinner = view.findViewById(R.id.spinner);


            Name = view.findViewById(R.id.Name);
            Rank = view.findViewById(R.id.Rank);
            Price = view.findViewById(R.id.Price);
            Price_other = view.findViewById(R.id.price_other);
            Symbol = view.findViewById(R.id.Symbol);
            imageView = view.findViewById(R.id.ivLogo);
            Pcoh = view.findViewById(R.id.onehr);
            Pctfh = view.findViewById(R.id.twohr);
            Pcsd = view.findViewById(R.id.sevendays);


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

    




