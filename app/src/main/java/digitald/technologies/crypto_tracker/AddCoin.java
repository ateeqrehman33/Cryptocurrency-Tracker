package digitald.technologies.crypto_tracker;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import digitald.technologies.crypto_tracker.TabFragments.AddCoinAdapter;
import digitald.technologies.crypto_tracker.TabFragments.Api;
import digitald.technologies.crypto_tracker.TabFragments.CoinData;
import digitald.technologies.crypto_tracker.TabFragments.Rates;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddCoin extends AppCompatActivity {



    private RecyclerView recyclerView;
    private ArrayList<CoinData> mArrayList;
    private ArrayList<Rates> currencylist;
    public AddCoinAdapter adapter;

    Double priceaud;
    String curname;



    private TextView coinname;
    private TextView price;
    private EditText quan;
    private Button save;


    SwipeRefreshLayout mSwipeRefreshLayout;


    private Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_coin);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Select a Coin");
        toolbar.setTitleTextColor(Color.WHITE);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });




        recyclerView = findViewById(R.id.recyclerViewac);
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


        initViews();



    }


    private void initViews() {

        //calling the method to display the coins
        getCoinData();
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

                        adapter = new AddCoinAdapter(mArrayList, priceaud, curname);

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
                Toast.makeText(AddCoin.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }


        });
    }
//coindataenshere


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_items2, menu);

        MenuItem search = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) search.getActionView();
        search(searchView);
        searchView.setQueryHint("Search Coins");

        super.onCreateOptionsMenu(menu);

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
        return true;
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
