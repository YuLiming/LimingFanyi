package android.particles.com.retrofit.base;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.particles.com.retrofit.common.RetrofitSingleton;
import android.particles.com.retrofit.component.util.MyDatabaseHelper;

/**
 * Created by YLM on 2016/10/15.
 */
public class BaseApplication extends Application {
    private static Context context;
    private static MyDatabaseHelper dbHelper;
    private static SQLiteDatabase db;
    @Override
    public void onCreate()
    {
        super.onCreate();
        context = getApplicationContext();
        dbHelper = new MyDatabaseHelper(this,"Traslation.db",null,1);
        db = dbHelper.getWritableDatabase();
        RetrofitSingleton.getInstance();
    }
    public static Context getContext()
    {
        return context;
    }
    public static MyDatabaseHelper getDbHelper()
    {
        return dbHelper;
    }
    public static SQLiteDatabase getDb()
    {
        return db;
    }
}
