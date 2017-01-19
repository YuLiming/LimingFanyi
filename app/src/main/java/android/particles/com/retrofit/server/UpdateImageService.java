package android.particles.com.retrofit.server;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.SystemClock;
import android.particles.com.retrofit.base.BaseApplication;
import android.particles.com.retrofit.common.RetrofitSingleton;

import android.particles.com.retrofit.modules.search.domin.BackImg;
import android.preference.PreferenceManager;
import android.util.Log;


import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by YLM on 2016/12/20.
 */

public class UpdateImageService extends Service {
    private String imgurl;

    @Override
    public IBinder onBind(Intent intent){
        return null;
    }

    @Override
    public int onStartCommand(Intent intent,int flags,int startId){
        loadImg();

        AlarmManager am = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        int oneDay = 24*60*60*1000;
        Intent i = new Intent(this,UpdateImageService.class);
        PendingIntent pi = PendingIntent.getBroadcast(this,0,i,0);
        am.setRepeating(AlarmManager.RTC_WAKEUP,SystemClock.currentThreadTimeMillis(),oneDay,pi);
        return super.onStartCommand(intent,flags,startId);
    }
    private void loadImg(){

        RetrofitSingleton.getImgApiService()
                .getBackgroundImg("js",0,1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BackImg>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(BackImg backImg) {
                        imgurl = "http://cn.bing.com"+backImg.getImages().get(0).getUrl();
                        SharedPreferences.Editor editor = PreferenceManager
                                .getDefaultSharedPreferences(BaseApplication.getContext())
                                .edit();
                        editor.putString("bg_img",imgurl);
                        editor.apply();
                    }
                });
    }
}
