package android.particles.com.retrofit.component.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.particles.com.retrofit.R;
import android.particles.com.retrofit.base.BaseApplication;
import android.particles.com.retrofit.common.APIValue;
import android.particles.com.retrofit.common.ApiStores;
import android.particles.com.retrofit.common.AppClient;
import android.particles.com.retrofit.modules.main.adapter.HomeAdapter;
import android.particles.com.retrofit.modules.main.domain.TransResult;
import android.particles.com.retrofit.modules.main.domain.Word;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;


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
    private static StringBuilder md5 = new StringBuilder();
    private static String appid = APIValue.appid;
    private static String salt = APIValue.salt;
    private static String key = APIValue.key;
    private RecyclerView mRecyclerView;
    private List<String> fanyi;
    private List<String> src;
    protected HomeAdapter madapter;
    private static SQLiteDatabase db;
    private static Context context;

    public GetJson(List<String> from,List<String> datas,RecyclerView recyclerView)
    {
        fanyi = datas;
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
                db = BaseApplication.getDb();
                ContentValues values = new ContentValues();
                values.put("src", srcs);
                values.put("yiwen", yi);
                db.insert("fanyi", null, values);
                values.clear();
                //end
                Collections.reverse(src);
                Collections.reverse(fanyi);
                fanyi.add(yi);
                src.add(srcs);
                ShowInScreen showInScreen = new ShowInScreen(src,fanyi,mRecyclerView);
                showInScreen.showInFan();
            }
            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
                Log.d("getjson","获取数据失败");
                Toast.makeText(BaseApplication.getContext(),"请检查网络连接",Toast.LENGTH_SHORT).show();
            }
        });
    }
    public static void showResultInNotication(String word,String to)
    {
        context = BaseApplication.getContext();
        md5.append(appid).append(word).append(salt).append(key);
        final String token = MD5.stringToMD5(md5.toString());
        ApiStores apiStores = AppClient.retrofit().create(ApiStores.class);
        Call<Word> call = apiStores.getWord(word, token, "auto", to);
        call.enqueue(new Callback<Word>() {
            @Override
            public void onResponse(Response<Word> response) {
                List<TransResult> results = response.body().getTransResult();
                String srcs = results.get(0).getSrc();
                String yi = results.get(0).getDst();
                //数据持久化
                db = BaseApplication.getDb();
                ContentValues values = new ContentValues();
                values.put("src", srcs);
                values.put("yiwen", yi);
                db.insert("fanyi", null, values);
                values.clear();
                //end
                NotificationManager manager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
                Notification.Builder builder = new Notification.Builder(context).setTicker("显示于屏幕顶端状态栏的文本")
                        .setSmallIcon(R.mipmap.ic_launcher);
                Notification note = builder.setContentTitle("原文："+srcs).setContentText("译文："+yi).build();
                manager.notify(1, note);
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
                Log.d("getjson", "通知栏更新失败");
            }
        });
    }


}
