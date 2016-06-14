package android.particles.com.retrofit.component.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.particles.com.retrofit.R;
import android.particles.com.retrofit.common.MyApplication;

/**
 * Created by YLM on 2016/6/14.
 */
public class ShowInNotification
{
    private Context context;
    private String notication;
    private NotificationManager notificationManager;
    private Notification notification;
    private Notification.Builder builder;
    public  ShowInNotification(String results)
    {
        context = MyApplication.getContext();
        notication = results;
    }

    public void show()
    {
        notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        builder = new Notification.Builder(context)
                .setTicker("翻译结果为：").setSmallIcon(R.drawable.ic_launcher);
        notification = builder.setContentTitle("翻译结果：").setContentText(notication).build();

    }
}
