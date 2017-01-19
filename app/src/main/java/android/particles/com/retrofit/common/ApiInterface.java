package android.particles.com.retrofit.common;


import android.particles.com.retrofit.modules.search.domin.BackImg;
import android.particles.com.retrofit.modules.search.domin.Translation;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by YLM on 2016/11/20.
 */
public interface ApiInterface {
    String HOST = "http://fanyi.youdao.com/";
    String IMG_HOST = "http://www.bing.com/";

    @GET("openapi.do")
    Observable<Translation> getTranslation(@Query("keyfrom") String keyfrom,
                                           @Query("key") String key,
                                           @Query("type") String type,
                                           @Query("doctype") String doctype,
                                           @Query("version") String version,
                                           @Query("q") String q);

    @GET("HPImageArchive.aspx")
    Observable<BackImg> getBackgroundImg(@Query("format") String format,
                                         @Query("idx") int idx,
                                         @Query("n") int n);



}
