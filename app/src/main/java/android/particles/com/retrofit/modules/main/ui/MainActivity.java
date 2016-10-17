package android.particles.com.retrofit.modules.main.ui;

import android.content.Intent;
import android.particles.com.retrofit.*;
import android.particles.com.retrofit.base.BaseActivity;
import android.particles.com.retrofit.common.MyService;
import android.particles.com.retrofit.component.util.DividerItemDecoration;
import android.particles.com.retrofit.component.util.GetJson;
import android.os.Bundle;
import android.particles.com.retrofit.component.util.InitData;
import android.particles.com.retrofit.component.util.ShowInScreen;
import android.particles.com.retrofit.component.util.ToType;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
/*待完成功能：
* 服务新开线程
* 7月2号* 可以重复连续提交多次
* */
public class MainActivity extends BaseActivity {
    private RecyclerView mRecyclerView;
    private Button button;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.index);

        Intent startIntent = new Intent(this,MyService.class);
        startService(startIntent);

        data = new ArrayList<String>();
        datasrc = new ArrayList<String>();
        button = (Button)findViewById(R.id.button);
        editText = (EditText)findViewById(R.id.editText);

        InitData.init(datasrc,data);

        mRecyclerView = (RecyclerView)findViewById(R.id.recycleview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST));
        ShowInScreen showInScreen = new ShowInScreen(datasrc,data,mRecyclerView);
        showInScreen.showInShun();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String word = editText.getText().toString();
                getJson = new GetJson(datasrc, data, mRecyclerView);
                if (ToType.isLetter(word)) {
                    getJson.getWord(word, "zh");
                } else {
                    getJson.getWord(word, "en");
                }
            }
        });


    }

}
