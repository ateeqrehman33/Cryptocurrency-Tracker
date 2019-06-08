package digitald.technologies.crypto_tracker;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import digitald.technologies.crypto_tracker.TabFragments.TabPortfoFragment;

public class AddQuantity extends AppCompatActivity {

    String quan;
    SQLiteDatabase db;
    Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_quantity);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Enter Quantity");
        toolbar.setTitleTextColor(Color.WHITE);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        final String cname = getIntent().getStringExtra("cname");
        final String csymbol = getIntent().getStringExtra("csymbol");
        String cpusd = getIntent().getStringExtra("pusd");

        TextView coinname = findViewById(R.id.coinname);
        coinname.setText("Name:  "+cname);
        TextView coinsymbol = findViewById(R.id.coinsymbol);
        coinsymbol.setText("Symbol:  "+csymbol);
        TextView coinprice = findViewById(R.id.coinvalue);
        coinprice.setText("Price:  "+cpusd+" USD");
        final EditText quanvalue = findViewById(R.id.quanvalue);
        quan = quanvalue.getText().toString();
        Button save = findViewById(R.id.savecoin);



// Registering event handlers
        db = openOrCreateDatabase("CoinDB", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS mycoins(id INTEGER PRIMARY KEY,name VARCHAR,quantity VARCHAR);");
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(quanvalue.getText().toString().trim().length()==0){

                    showMessage("Error", "Please enter Quantity");
                    return;

                }



                // Searching if coin exists
                Cursor c = db.rawQuery("SELECT * FROM mycoins WHERE name='" + csymbol + "'", null);
                if (c.getCount()>0 && c.moveToFirst()) {
                    // Modifying record if found

                    float oldquan = Float.parseFloat(c.getString(2));

                    float newquan = Float.valueOf(quanvalue.getText().toString());
                    float totquan = oldquan+newquan;
                    String newquantity= String.valueOf(totquan);




                    db.execSQL("UPDATE mycoins SET name='" + csymbol + "',quantity='" + newquantity +
                            "' WHERE name='" + csymbol + "'");


                }


else {
                    db.execSQL("INSERT INTO mycoins VALUES(NULL,'" + csymbol +
                            "','" + quanvalue.getText() + "');");


               }

                Toast.makeText(AddQuantity.this, "Coin Added", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(AddQuantity.this , TabPortfoFragment.class);

                setResult(2,intent);

                finish();//finishing activity





            }
        });

        }








    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}
