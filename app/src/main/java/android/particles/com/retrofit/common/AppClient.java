package android.particles.com.retrofit.common;

import android.particles.com.retrofit.modules.main.domain.MyError;
import android.particles.com.retrofit.modules.main.domain.Word;

import retrofit2.Call;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by YLM on 2016/5/1.
 */
public class AppClient
{
    static Retrofit mRetrofit;

    public static Retrofit retrofit() {
        if (mRetrofit == null) {
            mRetrofit = new Retrofit.Builder()
                    .baseUrl("http://api.fanyi.baidu.com/api/trans/vip/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return mRetrofit;
    }


}
