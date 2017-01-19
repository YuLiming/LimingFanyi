package android.particles.com.retrofit.modules.history.ui;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.particles.com.retrofit.R;
import android.particles.com.retrofit.base.BaseApplication;
import android.particles.com.retrofit.base.BaseFragment;
import android.particles.com.retrofit.common.RetrofitSingleton;
import android.particles.com.retrofit.modules.history.HistroyItem;
import android.particles.com.retrofit.modules.history.adapter.HistoryAdapter;
import android.particles.com.retrofit.modules.search.domin.BackImg;
import android.preference.PreferenceManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by YLM on 2016/10/17.
 */
public class HistoryFragment extends BaseFragment{
    private View view;
    private RecyclerView mRecyclerView;
    private List<HistroyItem> mData;
    private SQLiteDatabase db;
    private HistoryAdapter adapter;
    private int flag;
    private SwipeRefreshLayout swipeRefreshLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
        super.onCreateView(inflater,parent,savedInstanceState);
        view = inflater.inflate(R.layout.history,parent,false);
        initData();
        initUI();
        return view;
    }
    private void initUI() {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.history_rv);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(adapter = new HistoryAdapter(mData));
        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.refresh_histroy);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();

            }
        });


    }
    private void initData(){

        mData = new ArrayList<>();
        db = BaseApplication.getDbHelper().getReadableDatabase();
        getData();




    }
    private void getData(){
        mData.clear();
        flag = 0;
        Observable.create(new Observable.OnSubscribe<HistroyItem>() {
            @Override
            public void call(Subscriber<? super HistroyItem> subscriber) {
                Cursor cursor = db.query("translate",null,null,null,null,null,null);
                if (cursor.moveToLast())
                {
                    do{
                        flag++;
                        String id = cursor.getString(cursor.getColumnIndex("id"));
                        String query = cursor.getString(cursor.getColumnIndex("query"));
                        String translation = cursor.getString(cursor.getColumnIndex("translation"));
                        HistroyItem histroyItem = new HistroyItem(id,query,translation);
                        subscriber.onNext(histroyItem);

                        if (flag>=10)
                        {
                            db.delete("translate","id<?",new String[]{id});//自动清理缓存数据
                            break;
                        }
                    }while(cursor.moveToPrevious());
                }
                cursor.close();
                subscriber.onCompleted();
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HistroyItem>() {
                    @Override
                    public void onCompleted() {
                        adapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(HistroyItem histroyItem) {
                        mData.add(histroyItem);
                    }
                });
    }





}
