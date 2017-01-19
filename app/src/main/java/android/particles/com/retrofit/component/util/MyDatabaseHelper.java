package android.particles.com.retrofit.component.util;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by YLM on 2016/5/21.
 */
public class MyDatabaseHelper extends SQLiteOpenHelper
{
    public static final String CREATE_DATA = "create table translate("
            +"id integer primary key autoincrement,"
            +"query text,"
            +"translation text,"
            +"basic text,"
            +"phonetic text)";

    private Context mcontext;

    public MyDatabaseHelper(Context context,String name,SQLiteDatabase.CursorFactory factory,int version)
    {
        super(context,name,factory,version);
        mcontext = context;
    }
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(CREATE_DATA);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion)
    {

    }
}
