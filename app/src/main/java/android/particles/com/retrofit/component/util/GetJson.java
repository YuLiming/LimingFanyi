package android.particles.com.retrofit.component.util;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.particles.com.retrofit.common.APIValue;
import android.particles.com.retrofit.common.ApiStores;
import android.particles.com.retrofit.common.AppClient;
import android.particles.com.retrofit.common.MyApplication;
import android.particles.com.retrofit.modules.main.adapter.HomeAdapter;
import android.particles.com.retrofit.modules.main.domain.MyError;
import android.particles.com.retrofit.modules.main.domain.TransResult;
import android.particles.com.retrofit.modules.main.domain.Word;
import android.particles.com.retrofit.modules.main.ui.MainActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;


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
    protected HomeAdapter madapter;
    private SQLiteDatabase db;

    public GetJson(List<String> from,List<String> datas,HomeAdapter adapter,RecyclerView recyclerView)
    {
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
                //数据持久化
                db = MyApplication.getDb();
                ContentValues values = new ContentValues();
                values.put("src", srcs);
                values.put("yiwen", yi);
                db.insert("fanyi", null, values);
                values.clear();
                //end
                fanyi.add(yi);
                src.add(srcs);
                Collections.reverse(src);
                Collections.reverse(fanyi);
                mRecyclerView.setAdapter(madapter = new HomeAdapter(MyApplication.getContext(),src,fanyi));
            }
            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
                Toast.makeText(MyApplication.getContext(),"网络连接失败",Toast.LENGTH_SHORT).show();
            }
        });
    }


}
