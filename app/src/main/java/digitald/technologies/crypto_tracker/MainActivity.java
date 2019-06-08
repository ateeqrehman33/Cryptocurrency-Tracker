package digitald.technologies.crypto_tracker;


import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import digitald.technologies.crypto_tracker.TabFragments.TabFourFragment;
import digitald.technologies.crypto_tracker.TabFragments.TabOneFragment;
import digitald.technologies.crypto_tracker.TabFragments.TabThreeFragment;


public class MainActivity extends AppCompatActivity {


    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    boolean doubleBackToExitPressedOnce = false;

    private int[] tabIcons = {
            R.drawable.list,

            R.drawable.inc,
            R.drawable.dec
    };

    String quantity;
    String cname ;
    String csymbol   ;
    String crank     ;
    String c1h     ;
    String c24h   ;
    String c7d    ;
    String cmcv   ;
    String cpusd  ;
    String cas     ;
    String cts     ;
    String price_btc   ;
    String cmaxsup   ;
    String c24hvusd   ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        TextView mTitle = toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        Typeface courgette = Typeface.createFromAsset(getAssets(), "courgette.ttf");
        mTitle.setText("Crypto Tracker");
        mTitle.setTypeface(courgette);
//


        viewPager = findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        viewPager.setOffscreenPageLimit(3);


        tabLayout = findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);


        quantity = getIntent().getStringExtra("quan");

        cname    = getIntent().getStringExtra("cname");
        csymbol  = getIntent().getStringExtra("csymbol");
        crank    = getIntent().getStringExtra("crank");
        c1h      = getIntent().getStringExtra("1h");
        c24h     = getIntent().getStringExtra("24h");
        c7d      = getIntent().getStringExtra("7d");
        cmcv     = getIntent().getStringExtra("cmv");
        cpusd    = getIntent().getStringExtra("pusd");
        cas      = getIntent().getStringExtra("cas");
        cts      = getIntent().getStringExtra("cts");
        price_btc= getIntent().getStringExtra("price_btc");
        cmaxsup  = getIntent().getStringExtra("cmaxsup");
        c24hvusd = getIntent().getStringExtra("c24hvusd");






    }







    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());


        adapter.addFragment(new TabOneFragment(), "All Coins");
        adapter.addFragment(new TabThreeFragment(), "Coin Inc");
        adapter.addFragment(new TabFourFragment(), "Coin Dec");
        viewPager.setAdapter(adapter);

    }

    @Override
    public void onBackPressed() {

        /*if(isDrawerOpen){
            drawerLayout.closeDrawer(GravityCompat.START);
            isDrawerOpen=false;
            return;
        }*/

        if (doubleBackToExitPressedOnce) {
            moveTaskToBack(true);
            finish();
            //  interstitial.show();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(getApplicationContext(), "Press again to exit", Toast.LENGTH_LONG).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }






}

