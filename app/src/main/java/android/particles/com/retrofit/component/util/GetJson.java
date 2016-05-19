package android.particles.com.retrofit.component.util;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.particles.com.retrofit.common.APIValue;
import android.particles.com.retrofit.common.ApiStores;
import android.particles.com.retrofit.common.AppClient;
import android.particles.com.retrofit.modules.main.adapter.HomeAdapter;
import android.particles.com.retrofit.modules.main.domain.MyError;
import android.particles.com.retrofit.modules.main.domain.TransResult;
import android.particles.com.retrofit.modules.main.domain.Word;
import android.particles.com.retrofit.modules.main.ui.MainActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by YLM on 2016/5/1.
 */
public class GetJson
{
    StringBuilder md5 = new StringBuilder();
    String appid = APIValue.appid;
    String salt = APIValue.salt;
    String key = APIValue.key;
    private RecyclerView mRecyclerView;
    private List<String> fanyi;
    private List<String> src;
    private Context context;
    protected HomeAdapter madapter;

    public GetJson(Context mcontext,List<String> from,List<String> datas,HomeAdapter adapter,RecyclerView recyclerView)
    {
        context = mcontext;
        fanyi = datas;
        madapter = adapter;
        mRecyclerView = recyclerView;
        src = from;
    }

    public void getWord(final String word,String to)
    {
        md5.append(appid).append(word).append(salt).append(key);
        final String token = MD5.stringToMD5(md5.toString());
        ApiStores apiStores = AppClient.retrofit().create(ApiStores.class);
        Call<Word> call = apiStores.getWord(word,token,"auto",to);
        call.enqueue(new Callback<Word>() {
            @Override
            public void onResponse(Response<Word> response) {

                List<TransResult> results = response.body().getTransResult();
                String yi = results.get(0).getDst();
                String srcs = results.get(0).getSrc();
                fanyi.add(yi);
                src.add(srcs);
                Collections.reverse(src);
                Collections.reverse(fanyi);
                mRecyclerView.setAdapter(madapter = new HomeAdapter(context,src,fanyi));
            }
            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
                Log.d("ylm","failed"
                );
            }
        });
    }


}
