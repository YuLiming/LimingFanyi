package android.particles.com.retrofit.common;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.particles.com.retrofit.R;
import android.particles.com.retrofit.component.util.GetCopyResult;
import android.particles.com.retrofit.component.util.GetJson;
import android.particles.com.retrofit.component.util.ToType;
import android.particles.com.retrofit.modules.main.ui.MainActivity;
import android.util.Log;

/**
 * Created by YLM on 2016/6/29.
 */
public class MyService extends Service
{
    private String src;
    private MyBlinder myBlinder = new MyBlinder();
    private Notification.Builder builder;
    private Notification note;
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
        builder = new Notification.Builder(this).setTicker("显示于屏幕顶端状态栏的文本")
                .setSmallIcon(R.drawable.ic_launcher);
        Intent intent = new Intent(this,MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        note = builder.setContentIntent(pi).setContentTitle("原文：暂无").setContentText("译文：暂无").build();
        startForeground(1,note);

    }

    @Override
    public int onStartCommand(Intent intent,int flags,int startId)
    {
        ClipboardManager cm = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        cm.addPrimaryClipChangedListener(new ClipboardManager.OnPrimaryClipChangedListener() {
            @Override
            public void onPrimaryClipChanged() {
                GetCopyResult getCopyResult = new GetCopyResult();
                src = getCopyResult.getResult();
                if (ToType.isLetter(src)) {
                    GetJson.showResultInNotication(src, "zh");
                } else {
                    GetJson.showResultInNotication(src, "en");
                }
            }
        });
        return  super.onStartCommand(intent,flags,startId);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }
}
