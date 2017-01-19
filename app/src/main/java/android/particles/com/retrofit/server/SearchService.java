package android.particles.com.retrofit.server;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ClipboardManager;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.IBinder;
import android.particles.com.retrofit.R;
import android.particles.com.retrofit.base.BaseApplication;
import android.particles.com.retrofit.common.RetrofitSingleton;
import android.particles.com.retrofit.component.util.GetCopyResult;
import android.particles.com.retrofit.modules.search.SearchActivity;
import android.particles.com.retrofit.modules.search.domin.Translation;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by YLM on 2016/12/1.
 */
public class SearchService extends Service
{
    private String query;
    private MyBlinder myBlinder = new MyBlinder();
    private SQLiteDatabase db;
    private ContentValues values;
    private Notification notification;
    private NotificationCompat.Builder builder;
    private PendingIntent pi;
    private Bitmap icon;
    class MyBlinder extends Binder
    {
        public void getsomething()
        {
            Log.d("ylm","something");
        }
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        return myBlinder;
    }
    @Override
    public void onCreate()
    {
        super.onCreate();


        Intent intent = new Intent(this,SearchActivity.class);
        pi = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        icon =  BitmapFactory.decodeResource(getResources(), R.drawable.smallicon);
        builder = new NotificationCompat.Builder(this);
        notification = builder.setContentTitle("原文:暂无")
                .setContentText("译文:暂无")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.white)
                .setLargeIcon(icon)
                .setContentIntent(pi)
                .setContent(getRemoteView("原文:暂无","译文:暂无"))
                .build();
        startForeground(1,notification);
        db = BaseApplication.getDbHelper().getWritableDatabase();
        values = new ContentValues();

    }

    @Override
    public int onStartCommand(Intent intent,int flags,int startId)
    {
        ClipboardManager cm = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        cm.addPrimaryClipChangedListener(new ClipboardManager.OnPrimaryClipChangedListener() {
            @Override
            public void onPrimaryClipChanged() {
                GetCopyResult getCopyResult = new GetCopyResult();
                query = getCopyResult.getResult();

                RetrofitSingleton.getApiService()
                        .getTranslation("lifanyi","169409030","data","json","1.1",query)
                        .subscribeOn(Schedulers.io())
                        .observeOn(Schedulers.io())
                        .doOnNext(new Action1<Translation>() {
                            @Override
                            public void call(Translation translation) {
                                values.put("query",translation.getQuery());
                                values.put("translation",translation.getTranslation().get(0));
                                if (translation.getBasic()!=null){
                                    values.put("basic",translation.getBasic().getExplains().get(0));
                                    values.put("phonetic",translation.getBasic().getPhonetic());
                                }else {
                                    values.put("phonetic","");
                                    values.put("basic","");
                                }



                                db.insert("translate",null,values);
                                values.clear();
                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<Translation>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(Translation translation) {
                                notification = builder.setContentTitle("原文:"+translation.getQuery())
                                        .setContentText("译文:"+translation.getTranslation().get(0))
                                        .setWhen(System.currentTimeMillis())
                                        .setSmallIcon(R.drawable.white)
                                        .setLargeIcon(icon)
                                        .setContentIntent(pi)
                                        .setContent(getRemoteView("原文:"+translation.getQuery(),"译文:"+translation.getTranslation().get(0)))
                                        .build();
                                startForeground(1,notification);
//                                builder.contentView.setTextViewText(R.id.notification_query,translation.getQuery());
//                                builder.contentView.setTextViewText(R.id.notification_translation,translation.getTranslation().get(0));
                            }
                        });
            }
        });
        return  super.onStartCommand(intent,flags,startId);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }

    private RemoteViews getRemoteView(String query,String traslation) {
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.notication);
        remoteViews.setTextViewText(R.id.notication_query, query);
        remoteViews.setTextViewText(R.id.notication_translatation, traslation);
        return remoteViews;
    }
}

