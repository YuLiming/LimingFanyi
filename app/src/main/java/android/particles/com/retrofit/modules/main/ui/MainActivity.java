package android.particles.com.retrofit.modules.main.ui;

import android.app.AlertDialog;
import android.particles.com.retrofit.*;
import android.particles.com.retrofit.base.BaseActivity;
import android.particles.com.retrofit.component.util.DividerItemDecoration;
import android.particles.com.retrofit.component.util.GetJson;
import android.os.Bundle;
import android.particles.com.retrofit.component.util.ToType;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
/*待完成功能：
* 连接API失败的异常处理（自定义异常）
* UI优化
* 本地缓存
* recyclerView展示缓存
*剪切板管理器获取复制内容
*消息通知栏展示翻译结果
* */
public class MainActivity extends BaseActivity {
    private RecyclerView mRecyclerView;
    private Button button;
    private EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.index);

        data = new ArrayList<String>();
        datasrc = new ArrayList<String>();
        button = (Button)findViewById(R.id.button);
        editText = (EditText)findViewById(R.id.editText);

        mRecyclerView = (RecyclerView)findViewById(R.id.recycleview);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String word = editText.getText().toString();
                getJson = new GetJson(MainActivity.this, datasrc,data, madapter, mRecyclerView);
                if (ToType.isLetter(word)) {
                    getJson.getWord(word, "zh");
                } else {
                    getJson.getWord(word, "en");
                }
            }
        });

    }
}
