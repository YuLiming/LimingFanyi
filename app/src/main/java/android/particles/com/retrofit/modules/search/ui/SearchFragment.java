package android.particles.com.retrofit.modules.search.ui;


import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.particles.com.retrofit.R;
import android.particles.com.retrofit.base.BaseApplication;
import android.particles.com.retrofit.base.BaseFragment;
import android.particles.com.retrofit.common.RetrofitSingleton;
import android.particles.com.retrofit.component.util.MyDatabaseHelper;
import android.particles.com.retrofit.modules.search.SearchActivity;
import android.particles.com.retrofit.modules.search.adapter.SearchAdapter;
import android.particles.com.retrofit.modules.search.domin.BackImg;
import android.particles.com.retrofit.modules.search.domin.Translation;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


/**
 * Created by YLM on 2016/10/15.
 */
public class SearchFragment extends BaseFragment {
    private View view;
    private Button search_button;
    private EditText search_edit;
    private TextView search_result;
    private TextView search_n;
    private TextView search_phonetic;
    private RecyclerView search_web;
    private String text;
    private List<Translation.WebBean> mData;
    private SearchAdapter adapter;
    private SQLiteDatabase db;
    private ContentValues values;

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup parent, Bundle savedInstanceState){
        super.onCreateView(layoutInflater,parent,savedInstanceState);
        view = layoutInflater.inflate(R.layout.fragmentsearch,parent,false);
        initData();
        initView();
        return view;
    }
    private void initView(){

        search_button = (Button)view.findViewById(R.id.search_button);
        search_edit = (EditText)view.findViewById(R.id.search_editText);
        search_result = (TextView)view.findViewById(R.id.search_result);
        search_n = (TextView)view.findViewById(R.id.search_n);
        search_phonetic = (TextView)view.findViewById(R.id.search_phonetic);
        search_web = (RecyclerView)view.findViewById(R.id.search_web);

        search_web.setLayoutManager(new LinearLayoutManager(getContext()));
        search_web.setAdapter(adapter = new SearchAdapter(getContext(),mData));



        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text = search_edit.getText().toString();
                getTraslation(text);

            }
        });


    }
    private void initData(){

        mData = new ArrayList<>();
        db = BaseApplication.getDbHelper().getWritableDatabase();
        values = new ContentValues();


    }

    private void getTraslation(String text){
        RetrofitSingleton.getApiService()
                .getTranslation("lifanyi","169409030","data","json","1.1",text)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .doOnNext(new Action1<Translation>() {
                    @Override
                    public void call(Translation translation) {


                        values.put("query",translation.getQuery());
                        values.put("translation",translation.getTranslation().get(0));
                        if (translation.getBasic()!=null){
                            values.put("basic",translation.getBasic().getExplains().get(0));
                            values.put("phonetic",translation.getBasic().getPhonetic());
                        }else {
                            values.put("phonetic","");
                            values.put("basic","");
                        }



                        db.insert("translate",null,values);
                        values.clear();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Translation>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Translation translation) {
                        search_result.setText(translation.getTranslation().get(0));

                        if (translation.getBasic()!=null){
                            search_phonetic.setText(translation.getBasic().getPhonetic());
                            search_n.setText(translation.getBasic().getExplains().get(0));
                            mData.clear();
                            for (Translation.WebBean item : translation.getWeb()){
                                mData.add(item);
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
    }


}
