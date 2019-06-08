package digitald.technologies.crypto_tracker.TabFragments;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Belal on 10/2/2017.
 */

public class CoinData  {

    @SerializedName("name")
    private String name;
    @SerializedName("rank")
    private String rank;
    @SerializedName("price_usd")
    private Double price_usd;
    @SerializedName("symbol")
    private String symbol;
    @SerializedName("percent_change_1h")
    private String percent_change_1h;
    @SerializedName("percent_change_24h")
    private Float percent_change_24h;
    @SerializedName("percent_change_7d")
    private String percent_change_7d;

    @SerializedName("price_btc")
    private String price_btc;
    @SerializedName("24h_volume_usd")
    private String volume_usd_24h;
    @SerializedName("market_cap_usd")
    private String market_cap_usd;
    @SerializedName("total_supply")
    private String total_supply;
    @SerializedName("available_supply")
    private String available_supply;
    @SerializedName("max_supply")
    private String max_supply;
    @SerializedName("last_updated")
    private String last_updated;



    public CoinData(String name,
                    String rank,
                    Double price_usd,
                    String symbol,
                    String percent_change_1h,
                    Float percent_change_24h,
                    String percent_change_7d,
                    String price_btc,
                    String volume_usd_24h,
                    String market_cap_usd,
                    String max_supply,
                    String total_supply,
                    String available_supply,
                    String last_updated

    ) {
        this.name = name;
        this.rank = rank;
        this.price_usd = price_usd;
        this.symbol = symbol;
        this.percent_change_1h = percent_change_1h;
        this.percent_change_24h = percent_change_24h;
        this.percent_change_7d = percent_change_7d;
        this.price_btc = price_btc;
        this.volume_usd_24h = volume_usd_24h;
        this.market_cap_usd = market_cap_usd;
        this.max_supply = max_supply;
        this.total_supply = total_supply;
        this.available_supply = available_supply;
        this.last_updated = last_updated;



    }


    public String getName() {
        return name;
    }

    public String getRank() {
             return rank;
              }

    public Double getPrice_usd() {
            return price_usd;
             }

    public String getSymbol() {
              return symbol;
           }

    public String getPercent_change_1h() {return percent_change_1h;}

    public Float getPercent_change_24h() {return percent_change_24h;}

    public String getPercent_change_7d() {return percent_change_7d;}


    public String getMax_supply(){return  max_supply;}

    public  String  getprice_btc(){       return         price_btc;  }
    public  String  getvolume_usd_24h(){    return       volume_usd_24h;    }
    public  String  getmarket_cap_usd (){      return    market_cap_usd;  }
    public  String  gettotal_supply(){         return    total_supply;    }
    public  String  getavailable_supply()   {    return   available_supply; }
    public  String  getlast_updated(){         return    last_updated;     }





}





