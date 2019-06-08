package digitald.technologies.crypto_tracker;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.squareup.picasso.Picasso;

import static android.graphics.Color.RED;

public class CoinInfo extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView textView;
    private ImageView coin_image;
    public AdView mAdView;

    private InterstitialAd mInterstitialAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coin_info);

        String cname = getIntent().getStringExtra("cname");
        String csymbol = getIntent().getStringExtra("csymbol");
        String crank = getIntent().getStringExtra("crank");
        String c1h = getIntent().getStringExtra("1h");
        String c24h = getIntent().getStringExtra("24h");
        String c7d = getIntent().getStringExtra("7d");
        String cmcv = getIntent().getStringExtra("cmv");
        String cpusd = getIntent().getStringExtra("pusd");
        String cas = getIntent().getStringExtra("cas");
        String cts = getIntent().getStringExtra("cts");
        String price_btc = getIntent().getStringExtra("price_btc");

        String cmaxsup = getIntent().getStringExtra("cmaxsup");
        String c24hvusd = getIntent().getStringExtra("c24hvusd");


//        ActionBar actionBar = getSupportActionBar();
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
////        getSupportActionBar().setTitle(cname);
//        getSupportActionBar().setSubtitle(csymbol);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mAdView =  findViewById(R.id.adView);


        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        LayoutInflater inflator = LayoutInflater.from(this);
        View v = inflator.inflate(R.layout.titleview, null);

        getSupportActionBar().setCustomView(v);
        Typeface courgette = Typeface.createFromAsset(getAssets(), "courgette.ttf");
        ((TextView) v.findViewById(R.id.title)).setText(cname + " " + "(" + csymbol + ")");
        ((TextView) v.findViewById(R.id.title)).setTypeface(courgette);


        String url = "https://chasing-coins.com/api/v1/std/logo/" + csymbol;

        mInterstitialAd = new InterstitialAd(getApplicationContext());
        mInterstitialAd.setAdUnitId("ca-app-pub-5749455352203480/9845772844");
        mInterstitialAd.loadAd(new AdRequest.Builder().addTestDevice("99D44C3EBF6EBE59014D219C9B7511F6").build());

        coin_image = findViewById(R.id.coin_img);

        Picasso.with(getApplicationContext())
                .load(url)
                .placeholder(R.drawable.btc)
                .error(R.drawable.btc)
                .into(coin_image);

        textView = findViewById(R.id.btc_price);
        textView.setText("1 " + csymbol + " = " + price_btc + " BTC");
        textView = findViewById(R.id.rankvalue);
        textView.setText(crank);

        textView = findViewById(R.id.onehrvalue2);
        textView.setText(c1h + " %");

        try {
            if (c1h != null || c24h != null || c7d != null) {

                if (c1h.startsWith("-")) {
                    textView.setTextColor(RED);
                } else {
                    textView.setTextColor(getResources().getColor(R.color.green));
                }

                textView = findViewById(R.id.twofourhrvalue);
                textView.setText(c24h + " %");
                if (c24h.startsWith("-")) {
                    textView.setTextColor(RED);
                } else textView.setTextColor(getResources().getColor(R.color.green));


                textView = findViewById(R.id.twofourhrvalue2);
                textView.setText(c7d + " %");
                if (c7d.startsWith("-")) {
                    textView.setTextColor(RED);
                } else {
                    textView.setTextColor(getResources().getColor(R.color.green));
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }


        textView = findViewById(R.id.marketcapvalue);

        textView.setText("$" + cmcv);

        textView = findViewById(R.id.priceusd);
        textView.setText("$" + cpusd);
        textView = findViewById(R.id.asvalue);
        textView.setText(cas + " " + csymbol);
        textView = findViewById(R.id.tsvalue);
        textView.setText(cts + " " + csymbol);

        textView = findViewById(R.id.maxsuppvalue);
        textView.setText(cmaxsup + " " + csymbol);

        textView = findViewById(R.id.tfhvusdvalue);
        textView.setText("$" + c24hvusd);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                if(mInterstitialAd.isLoaded())
                    mInterstitialAd.show();
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(mInterstitialAd.isLoaded())
            mInterstitialAd.show();
    }
}
