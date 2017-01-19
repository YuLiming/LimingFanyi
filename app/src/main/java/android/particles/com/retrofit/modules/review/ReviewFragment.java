package android.particles.com.retrofit.modules.review;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.particles.com.retrofit.R;
import android.particles.com.retrofit.base.BaseApplication;
import android.particles.com.retrofit.base.BaseFragment;
import android.particles.com.retrofit.common.RetrofitSingleton;
import android.particles.com.retrofit.modules.history.HistroyItem;
import android.particles.com.retrofit.modules.search.SearchActivity;
import android.particles.com.retrofit.modules.search.domin.BackImg;
import android.particles.com.retrofit.server.SearchService;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;


import com.bumptech.glide.Glide;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by YLM on 2016/10/17.
 */
public class ReviewFragment extends BaseFragment implements View.OnClickListener{
    private View view;
    private RelativeLayout clean;
    private RelativeLayout close;
    private RelativeLayout connect;
    private RelativeLayout finish;
    private SQLiteDatabase db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
        super.onCreateView(inflater,parent,savedInstanceState);
        view = inflater.inflate(R.layout.review,parent,false);
        initData();
        initUI();

        return view;
    }
    private void initData(){
        db = BaseApplication.getDbHelper().getReadableDatabase();
    }
    private void initUI(){
        Toolbar toolbar = (Toolbar)view.findViewById(R.id.toolbar);
        toolbar.setTitle("");
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        clean = (RelativeLayout)view.findViewById(R.id.setting_clean);
        close = (RelativeLayout)view.findViewById(R.id.setting_close);
        connect = (RelativeLayout)view.findViewById(R.id.setting_connection);
        finish = (RelativeLayout)view.findViewById(R.id.setting_finish);
        clean.setOnClickListener(this);
        close.setOnClickListener(this);
        finish.setOnClickListener(this);
        connect.setOnClickListener(this);


    }
    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.setting_clean:
                clean();
                break;
            case R.id.setting_close:
                Intent intent = new Intent(getContext(), SearchService.class);
                getActivity().stopService(intent);
                Toast.makeText(getContext(),"全屏搜词已关闭",Toast.LENGTH_SHORT).show();
                break;
            case R.id.setting_connection:
                Toast.makeText(getContext(),"QQ:939179453",Toast.LENGTH_LONG).show();
                break;
            case R.id.setting_finish:
                getActivity().finish();
                break;
            default:
                break;
        }
    }
    private void clean(){
        Cursor cursor = db.query("translate",null,null,null,null,null,null);
        if (cursor.moveToLast())
        {

            String id = cursor.getString(cursor.getColumnIndex("id"));
            db.delete("translate","id<=?",new String[]{id});//自动清理缓存数据


        }
        cursor.close();
        SharedPreferences.Editor editor = PreferenceManager
                .getDefaultSharedPreferences(getContext())
                .edit();
        editor.clear();
        editor.apply();
        Toast.makeText(getContext(),"清除成功",Toast.LENGTH_SHORT).show();
    }

}
