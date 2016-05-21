package android.particles.com.retrofit.common;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.particles.com.retrofit.component.util.MyDatabaseHelper;

/**
 * Created by YLM on 2016/5/21.
 */
public class MyApplication extends Application
{
    private static Context context;
    private static MyDatabaseHelper dbHelper;
    private static SQLiteDatabase db;
    @Override
    public void onCreate()
    {
        super.onCreate();
        context = getApplicationContext();
        dbHelper = new MyDatabaseHelper(this,"Fanyi.db",null,1);
        db = dbHelper.getWritableDatabase();
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
