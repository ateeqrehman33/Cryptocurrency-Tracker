package digitald.technologies.crypto_tracker.TabFragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import digitald.technologies.crypto_tracker.AddCoin;
import digitald.technologies.crypto_tracker.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class  TabPortfoFragment extends AppCompatActivity {

    private TextView pfvalue , pftfhc;
    private Toolbar toolbar;
    private ArrayList<CoinData> mArrayList;
    SQLiteDatabase db;
    private RecyclerView recyclerView;
    public MyCoinsAdapter mcadapter;
    SwipeRefreshLayout mSwipeRefreshLayout;
    List<MyCoinModel> myCoinModel = new ArrayList<MyCoinModel>();
    List<MyCoinDataModel> myCoinDataModel = new ArrayList<MyCoinDataModel>();
    String tot;
    String tottfh;
    Double priceaud;
    String curname;
    int index;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_portfo_fragment);



        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(TabPortfoFragment.this);
        editor = sharedPreferences.edit();


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("My Portfolio");
        toolbar.setTitleTextColor(Color.WHITE);



        recyclerView = findViewById(R.id.mycoinsrv);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mSwipeRefreshLayout = findViewById(R.id.swipe);
        mSwipeRefreshLayout.setRefreshing(true);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                initViews();

                }
        });

        loadJSON();
    }



    private void initViews() {

        getCoinData();
    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.pfmenu, menu);


        final MenuItem spinner = menu.findItem(R.id.spinner);
        final Spinner Spinner = (Spinner) spinner.getActionView();
        Spinner.getBackground().setColorFilter(getResources().getColor(R.color.md_white_1000), PorterDuff.Mode.SRC_ATOP);
        ArrayAdapter<CharSequence> adapterspin = ArrayAdapter.createFromResource(TabPortfoFragment.this,
                R.array.Currency, R.layout.your_selected_spinner_item);
        adapterspin.setDropDownViewResource(R.layout.your_dropdown_item);
        Spinner.setDropDownWidth(350);
        Spinner.setAdapter(adapterspin);
        Spinner.setPopupBackgroundResource(R.color.colorPrimary);
        int selpos = sharedPreferences.getInt("spinnerposition", 0);
        Spinner.setSelection(selpos);




        Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {



                int spinnerposition = Spinner.getSelectedItemPosition();

                loadJSON();
                initViews();
                editor.putInt("spinnerposition", spinnerposition);
                editor.commit();


            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_add:


                Intent intent = new Intent( TabPortfoFragment.this, AddCoin.class);
                startActivityForResult(intent,2);

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }





    public void loadJSON() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiRates.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiRates api2 = retrofit.create(ApiRates.class);


        api2.getJSON().enqueue(new Callback<RateClass>() {
            @Override
            public void onResponse(Call<RateClass> call, Response<RateClass> response) {

                Rates ratelist = response.body().getRates();


                Integer mycurrency = sharedPreferences.getInt("spinnerposition", 0);


                switch (mycurrency) {
                    case 0:
                        priceaud = 1.0;
                        curname = "$";

                        break;
                    case 1:
                        priceaud = ratelist.getAUD();
                        curname = "AU$";

                        break;


                    case 2:
                        priceaud = ratelist.getBGN();
                        curname = "Лв.";
                        break;


                    case 3:
                        priceaud = ratelist.getBRL();
                        curname = "R$";
                        break;


                    case 4:
                        priceaud = ratelist.getCAD();
                        curname = "C$";
                        break;


                    case 5:
                        priceaud = ratelist.getCHF();
                        curname = "CHF";
                        break;


                    case 6:
                        priceaud = ratelist.getCNY();
                        curname = "¥";
                        break;


                    case 7:
                        priceaud = ratelist.getCZK();
                        curname = "Kč";
                        break;


                    case 8:
                        priceaud = ratelist.getDKK();
                        curname = "Kr.";
                        break;


                    case 9:
                        priceaud = ratelist.getEUR();
                        curname = "€";
                        break;


                    case 10:
                        priceaud = ratelist.getGBP();
                        curname = "£";
                        break;


                    case 11:
                        priceaud = ratelist.getHKD();
                        curname = "HK$";
                        break;


                    case 12:
                        priceaud = ratelist.getHRK();
                        curname = "kn";
                        break;


                    case 13:
                        priceaud = ratelist.getHUF();
                        curname = "Ft";
                        break;


                    case 14:
                        priceaud = ratelist.getIDR();
                        curname = "Rp";
                        break;


                    case 15:
                        priceaud = ratelist.getILS();
                        curname = "₪";
                        break;


                    case 16:
                        priceaud = ratelist.getINR();
                        curname = "₹";
                        break;


                    case 17:
                        priceaud = ratelist.getISK();
                        curname = "Íkr";
                        break;


                    case 18:
                        priceaud = ratelist.getJPY();
                        curname = "¥";
                        break;


                    case 19:
                        priceaud = ratelist.getKRW();
                        curname = "W";
                        break;


                    case 20:
                        priceaud = ratelist.getMXN();
                        curname = "Mex$";
                        break;


                    case 21:
                        priceaud = ratelist.getMYR();
                        curname = "RM";
                        break;


                    case 22:
                        priceaud = ratelist.getNOK();
                        curname = "kr";
                        break;


                    case 23:
                        priceaud = ratelist.getNZD();
                        curname = "$";
                        break;


                    case 24:
                        priceaud = ratelist.getPHP();
                        curname = "₱";
                        break;


                    case 25:
                        priceaud = ratelist.getPLN();
                        curname = "zł";
                        break;


                    case 26:
                        priceaud = ratelist.getRON();
                        curname = "lei";
                        break;

                    case 27:
                        priceaud = ratelist.getRUB();
                        curname = "\u20BD";
                        break;


                    case 28:
                        priceaud = ratelist.getSEK();
                        curname = "kr";
                        break;


                    case 29:
                        priceaud = ratelist.getSGD();
                        curname = "S$";
                        break;


                    case 30:
                        priceaud = ratelist.getTHB();
                        curname = "฿";
                        break;

                    case 31:
                        priceaud = ratelist.getTRY();
                        curname = "₺";
                        break;

                    case 32:
                        priceaud = ratelist.getZAR();
                        curname = "R";
                        break;


                }


            }

            @Override
            public void onFailure(Call<RateClass> call, Throwable t) {

            }
        });


    }











    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if(requestCode==2)
        {

            initViews();

        }
    }




    public void portfo(){
        Double sum = 0.0;
        Double sumtfh = 0.0;


        for (int i = 0; i < myCoinModel.size(); i++) {
            MyCoinDataModel totoalpf = myCoinDataModel.get(i);
            MyCoinModel quan = myCoinModel.get(i);

            sum += (totoalpf.price)*(quan.quantity);

            sumtfh += (totoalpf.tfh);
        }

        Double finalprice = round(sum*priceaud, 2);
        Double finaltfh = round(sumtfh,2);

        tot = finalprice.toString();
        tottfh = finaltfh.toString();

        pfvalue = findViewById(R.id.pfvalue);
        pftfhc = findViewById(R.id.pftfhc);

        pfvalue.setText(curname+" "+tot);
        pftfhc.setText(tottfh+"%");

    }



    public void getCoinData() {


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().serializeNulls().create())) //Here we are using the GsonConverterFactory to directly convert json data to object
                .build();

        Api api = retrofit.create(Api.class);

        Call<List<CoinData>> call = api.getCoinData();


        call.enqueue(new Callback<List<CoinData>>() {
            @Override
            public void onResponse(Call<List<CoinData>> call, Response<List<CoinData>> response) {


                if (response.isSuccessful()) {

                }
                try {

                    if (response.body() != null) {
                        List<CoinData> coinList = response.body();


                        mArrayList = new ArrayList<>(coinList);


                        myCoinModel.clear();
                        myCoinDataModel.clear();



                            db = openOrCreateDatabase("CoinDB", Context.MODE_PRIVATE, null);

                            db.execSQL("CREATE TABLE IF NOT EXISTS mycoins(ID INTEGER PRIMARY KEY,name VARCHAR,quantity VARCHAR);");
                            Cursor c = db.rawQuery("SELECT * FROM mycoins", null);
                            c.moveToFirst();
                            if (c.getCount() == 0) {





                                AlertDialog alertDialog = new AlertDialog.Builder(TabPortfoFragment.this).create();
                                alertDialog.setTitle("No Coins Added");
                                alertDialog.setMessage("Do you want to add the Coin to Portfolio ??");
                                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Yes",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {

                                                Intent intent = new Intent( TabPortfoFragment.this, AddCoin.class);
                                                startActivityForResult(intent,2);

                                                dialog.dismiss();
                                            }
                                        });
                                alertDialog.show();



                                mSwipeRefreshLayout.setRefreshing(false);
                                return;
                            }

                            int id = 0;

                            if (c.moveToFirst()) {
                                do {

                                    id = c.getInt(0);


                                    for (CoinData coinData : mArrayList) {


                                        if (coinData.getSymbol().equals(c.getString(1))) {

                                            Double price;
                                            Double tfh;
                                            index = mArrayList.indexOf(coinData);


                                            price = Double.valueOf(mArrayList.get(index).getPrice_usd());

                                            tfh = Double.valueOf(mArrayList.get(index).getPercent_change_24h());





                                            myCoinDataModel.add(new MyCoinDataModel(price, tfh
                                            ));


                                            myCoinModel.add(new MyCoinModel(id,
                                                    c.getString(1), Float.valueOf(c.getString(2))


                                            ));

                                            Log.d("Price", c.getString(1) + "");
                                            Log.d("Position", mArrayList.indexOf(coinData) + "");
                                            Log.d("Price", price + "");
                                        }


                                    }


                                } while (c.moveToNext());
                            }
                            c.close();



                            portfo();


                            mcadapter = new MyCoinsAdapter(mArrayList, myCoinModel, myCoinDataModel, priceaud, curname);

                            recyclerView.setAdapter(mcadapter);


                            mSwipeRefreshLayout.setRefreshing(false);





                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }

            }



            @Override
            public void onFailure(Call<List<CoinData>> call, Throwable t) {
                Toast.makeText(TabPortfoFragment.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }


        });
    }



    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }


    }



