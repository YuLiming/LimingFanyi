package android.particles.com.retrofit.modules.main.ui;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.particles.com.retrofit.*;
import android.particles.com.retrofit.base.BaseActivity;
import android.particles.com.retrofit.common.MyApplication;
import android.particles.com.retrofit.component.util.DividerItemDecoration;
import android.particles.com.retrofit.component.util.GetJson;
import android.os.Bundle;
import android.particles.com.retrofit.component.util.MyDatabaseHelper;
import android.particles.com.retrofit.component.util.ToType;
import android.particles.com.retrofit.modules.main.adapter.HomeAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import java.util.Collections;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
/*待完成功能：
* 连接API失败的异常处理
*获取剪切板管理器获取复制内容(完成)
*在消息通知栏展示翻译结果
*翻译结果自动更新机制
* */
public class MainActivity extends BaseActivity {
    private RecyclerView mRecyclerView;
    private Button button;
    private EditText editText;
    private int flag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.index);

        data = new ArrayList<String>();
        datasrc = new ArrayList<String>();
        button = (Button)findViewById(R.id.button);
        editText = (EditText)findViewById(R.id.editText);

        initData();

        mRecyclerView = (RecyclerView)findViewById(R.id.recycleview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST));
        Collections.reverse(datasrc);
        Collections.reverse(data);
        mRecyclerView.setAdapter(madapter = new HomeAdapter(MainActivity.this, datasrc, data));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String word = editText.getText().toString();
                getJson = new GetJson(datasrc,data, madapter, mRecyclerView);
                if (ToType.isLetter(word)) {
                    getJson.getWord(word, "zh");
                } else {
                    getJson.getWord(word, "en");
                }
            }
        });

    }
    private void initData()
    {
        flag = 0;
        dbHelper = new MyDatabaseHelper(this,"Fanyi.db",null,1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("fanyi",null,null,null,null,null,null);
        if (cursor.moveToFirst())
        {
            do{
                flag++;
                String src = cursor.getString(cursor.getColumnIndex("src"));
                String yi = cursor.getString(cursor.getColumnIndex("yiwen"));
                datasrc.add(src);
                data.add(yi);
                if (flag>=10)
                {break;}
            }while(cursor.moveToNext());
        }
        cursor.close();
    }
}
