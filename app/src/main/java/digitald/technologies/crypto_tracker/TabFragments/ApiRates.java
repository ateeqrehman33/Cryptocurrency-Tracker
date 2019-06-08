package digitald.technologies.crypto_tracker.TabFragments;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Streaming;

/**
 * Created by root on 2/23/18.
 */

public interface ApiRates {

    String BASE_URL = "http://data.fixer.io/";

    @Streaming
    @GET("latest?access_key=90120d0efb67bdfade18fc14aa0473fc")
    Call<RateClass> getJSON();
}
