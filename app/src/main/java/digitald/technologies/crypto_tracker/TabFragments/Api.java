package digitald.technologies.crypto_tracker.TabFragments;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;



public interface Api {


    String BASE_URL = "https://api.coinmarketcap.com/";


    @GET("v1/ticker/?limit=0")
    Call<List<CoinData>> getCoinData();





}
