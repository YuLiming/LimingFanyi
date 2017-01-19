package android.particles.com.retrofit.common;


import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by YLM on 2016/11/20.
 */
public class RetrofitSingleton{
    private static ApiInterface apiService = null;
    private static ApiInterface imgApiService = null;
    private static Retrofit retrofit = null;
    private static Retrofit imgRetrofit = null;

    private RetrofitSingleton() {
        initImgRetrofit();
        initRetrofit();
        apiService = retrofit.create(ApiInterface.class);
        imgApiService = imgRetrofit.create(ApiInterface.class);

    }


    private static void initRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl(ApiInterface.HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }
    private static void initImgRetrofit(){
        imgRetrofit = new Retrofit.Builder()
                .baseUrl(ApiInterface.IMG_HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    public static ApiInterface getApiService() {
        if (apiService == null) {
            throw new NullPointerException("get apiService must be called after init");
        }
        return apiService;
    }
    public static ApiInterface getImgApiService() {
        if (apiService == null) {
            throw new NullPointerException("get apiService must be called after init");
        }
        return imgApiService;
    }

    public static RetrofitSingleton getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final RetrofitSingleton INSTANCE = new RetrofitSingleton();
    }
}
