package android.particles.com.retrofit.common;

import android.particles.com.retrofit.modules.main.domain.Word;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by YLM on 2016/5/7.
 */
public interface ApiStores
{
    @GET("translate?appid=20160503000020163&salt=1435660288")
    Call<Word> getWord(
            @Query("q") String danci,
            @Query("sign") String md,
            @Query("from") String from,
            @Query("to") String to) ;
}
