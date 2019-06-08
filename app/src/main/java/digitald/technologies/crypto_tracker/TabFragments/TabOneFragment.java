package digitald.technologies.crypto_tracker.TabFragments;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import digitald.technologies.crypto_tracker.About;
import digitald.technologies.crypto_tracker.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static digitald.technologies.crypto_tracker.R.id;
import static digitald.technologies.crypto_tracker.R.layout;

public class TabOneFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<CoinData> mArrayList;
    private ArrayList<Rates> currencylist;
    public DataAdapter adapter;



    Double priceaud;
    String curname;
    Button buttonpf;

    SwipeRefreshLayout mSwipeRefreshLayout;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        editor = sharedPreferences.edit();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(layout.tab_one_fragment, container, false);


        buttonpf = view.findViewById(id.pfbutton);



        setHasOptionsMenu(true);
        recyclerView = view.findViewById(id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        mSwipeRefreshLayout =
                view.findViewById(id.swipe);
        mSwipeRefreshLayout.setRefreshing(true);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initViews();
            }
        });

        buttonpf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TabPortfoFragment.class);
                getActivity().startActivity(intent);

            }
        });



        initViews();

        return view;


    }


    private void initViews() {

        //calling the method to display the coins
        getCoinData();
        loadJSON();


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


    //fetching coins

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

                        adapter = new DataAdapter(mArrayList, priceaud, curname);
                        recyclerView.setAdapter(adapter);

                        mSwipeRefreshLayout.setRefreshing(false);


                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }

            }

            //displaying the string array into listview


            @Override
            public void onFailure(Call<List<CoinData>> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }


        });
    }
//coindataenshere


    //menu and search

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_items, menu);

        final MenuItem spinner = menu.findItem(id.spinner);
        final Spinner Spinner = (Spinner) spinner.getActionView();
        ArrayAdapter<CharSequence> adapterspin = ArrayAdapter.createFromResource(getContext(),
                R.array.Currency, layout.your_selected_spinner_item);
        adapterspin.setDropDownViewResource(layout.your_dropdown_item);
        Spinner.setDropDownWidth(350);
        Spinner.setAdapter(adapterspin);
        Spinner.setPopupBackgroundResource(R.color.colorPrimary);
        int selpos = sharedPreferences.getInt("spinnerposition", 0);
        Spinner.setSelection(selpos);


        Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                int spinnerposition = Spinner.getSelectedItemPosition();

                initViews();

                editor.putInt("spinnerposition", spinnerposition);
                editor.commit();


            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        MenuItem search = menu.findItem(id.action_search);
        SearchView searchView = (SearchView) search.getActionView();
        search(searchView);
        searchView.setQueryHint("Search Coins");

        super.onCreateOptionsMenu(menu, inflater);

        MenuItemCompat.setOnActionExpandListener(search,
                new MenuItemCompat.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {
                        // Do something when collapsed
                        adapter.getFilter();
                        return true; // Return true to collapse action view
                    }

                    @Override
                    public boolean onMenuItemActionExpand(MenuItem item) {
                        // Do something when expanded
                        return true; // Return true to expand action view
                    }
                });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        if (item.getItemId() == R.id.action_share) {

            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT,
                    "Hey check out this app. You can track all Crypto currencies with this! \n http://play.google.com/store/apps/details?id=" + getActivity().getPackageName());
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        }


        if (item.getItemId() == R.id.action_rate) {
            Uri uri = Uri.parse("market://details?id=" + getActivity().getPackageName());
            Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
            // To count with Play market backstack, After pressing back button,
            // to taken back to our application, we need to add following flags to intent.
            goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                    Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                    Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
            try {
                startActivity(goToMarket);
            } catch (ActivityNotFoundException e) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://play.google.com/store/apps/details?id=" + getActivity().getPackageName())));
            }
        }

        if (item.getItemId() == id.action_about) {
            startActivity(new Intent(getContext(), About.class));
        }
        return super.onOptionsItemSelected(item);

    }

    private void search(SearchView searchView) {

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                newText = newText.toLowerCase();
                if (adapter != null) adapter.getFilter().filter(newText);
                return true;
            }
        });
    }


}





