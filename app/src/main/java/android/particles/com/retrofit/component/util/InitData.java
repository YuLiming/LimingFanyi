package android.particles.com.retrofit.component.util;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.particles.com.retrofit.base.BaseApplication;

import java.util.List;

/**
 * Created by YLM on 2016/7/3.
 */
public class InitData
{
    private static int flag;
    private static MyDatabaseHelper dbHelper;
    private static Context context;
    public static void init(List<String> datasrc,List<String> data)
    {
        flag = 0;
        context = BaseApplication.getContext();
        dbHelper = new MyDatabaseHelper(context,"Fanyi.db",null,1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("fanyi",null,null,null,null,null,null);
        if (cursor.moveToLast())
        {
            do{
                flag++;
                String src = cursor.getString(cursor.getColumnIndex("src"));
                String yi = cursor.getString(cursor.getColumnIndex("yiwen"));
                String id = cursor.getString(cursor.getColumnIndex("id"));
                datasrc.add(src);
                data.add(yi);
                if (flag>=10)
                {
                    db.delete("fanyi","id<?",new String[]{id});//自动清理缓存数据
                    break;
                }
            }while(cursor.moveToPrevious());
        }
        cursor.close();
    }
}
