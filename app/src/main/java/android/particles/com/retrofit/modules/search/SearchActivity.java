package android.particles.com.retrofit.modules.search;



import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.particles.com.retrofit.R;
import android.particles.com.retrofit.base.BaseActivity;
import android.particles.com.retrofit.base.BaseFragment;
import android.particles.com.retrofit.common.RetrofitSingleton;
import android.particles.com.retrofit.component.widget.TabView.TabContainer;
import android.particles.com.retrofit.component.widget.TabView.TabView;
import android.particles.com.retrofit.modules.history.ui.HistoryFragment;
import android.particles.com.retrofit.modules.review.ReviewFragment;
import android.particles.com.retrofit.modules.search.domin.BackImg;
import android.particles.com.retrofit.modules.search.ui.SearchFragment;
import android.particles.com.retrofit.server.SearchService;
import android.particles.com.retrofit.server.UpdateImageService;
import android.preference.PreferenceManager;
import android.provider.AlarmClock;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;


import com.bumptech.glide.Glide;

import java.util.ArrayList;

import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by YLM on 2016/10/15.
 */
public class SearchActivity extends BaseActivity implements TabContainer.OnTabClickListener{
    private TabContainer tabContainer;
    private FragmentManager fm = getSupportFragmentManager();
    private BaseFragment fragment;
    private BaseFragment nowFragment;
    private BaseFragment tempSearch;
    private BaseFragment tempHistroy;
    private BaseFragment tempSetting;
    private ImageView image;
    private String imgurl;
    private SharedPreferences pref;
    private ArrayList<TabView> tabs;




    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
        Intent startIntent = new Intent(this,SearchService.class);
        startService(startIntent);
        Intent updateIntent = new Intent(this,UpdateImageService.class);
        startService(updateIntent);
        initData();
        initView();




    }

    private void initView(){
        if (Build.VERSION.SDK_INT>=21){
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            |View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            );
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        image = (ImageView)findViewById(R.id.bg);
        tempSearch = new SearchFragment();
        tempHistroy = new HistoryFragment();
        tempSetting = new ReviewFragment();
        fm.beginTransaction().add(R.id.fragment,tempSearch).commit();
        nowFragment = tempSearch;
        tabContainer = (TabContainer)findViewById(R.id.tablayout);
        tabContainer.initData(tabs);
        tabContainer.setFirst(1);
        tabContainer.setTabListener(this);




        imgurl = pref.getString("bg_img",null);
        if (imgurl!=null){
            Glide.with(this).load(imgurl)
                            .placeholder(R.drawable.city)
                            .error(R.drawable.sea)
                            .into(image);
        }else {
            loadImg();
        }
    }

    private void initData(){
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        tabs = new ArrayList<>();

        tabs.add(new TabView(this,R.drawable.nowhistroy,R.drawable.histroy,R.string.tab_search,HistoryFragment.class));
        tabs.add(new TabView(this,R.drawable.nowsearch,R.drawable.search,R.string.tab_history,SearchFragment.class));
        tabs.add(new TabView(this,R.drawable.nowsetting,R.drawable.setting,R.string.tab_review,ReviewFragment.class));

    }

    @Override
    public void onTabClick(TabView tabView){
        try {
            fragment= tabView.tagFragmentClz.newInstance();
            if (fragment instanceof SearchFragment){
                if (tempSearch.isAdded()){
                    Log.d("ylm","search+is");
                    fm.beginTransaction().hide(nowFragment).show(tempSearch).commit();

                }else {
                    Log.d("ylm","search+not");
                    fm.beginTransaction().hide(nowFragment).add(R.id.fragment,tempSearch).commit();

                }
                nowFragment = tempSearch;
                getWindow().setStatusBarColor(Color.TRANSPARENT);
            }

            if (fragment instanceof HistoryFragment){
                if (tempHistroy.isAdded()){
                    Log.d("ylm","histroy+is");
                    fm.beginTransaction().hide(nowFragment).show(tempHistroy).commit();
                }else {
                    Log.d("ylm","histroy+not");
                    fm.beginTransaction().hide(nowFragment).add(R.id.fragment,tempHistroy).commit();
                }
                nowFragment = tempHistroy;
                getWindow().setStatusBarColor(Color.TRANSPARENT);
            }

            if (fragment instanceof ReviewFragment){
                if (tempSetting.isAdded()){
                    Log.d("ylm","setting+is");
                    fm.beginTransaction().hide(nowFragment).show(tempSetting).commit();
                }else {
                    Log.d("ylm","setting+not");
                    fm.beginTransaction().hide(nowFragment).add(R.id.fragment,tempSetting).commit();
                }
                nowFragment = tempSetting;

                getWindow().setStatusBarColor(Color.BLACK);

            }



            tabView.setNow(true);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    private void loadImg(){
        RetrofitSingleton.getImgApiService()
                .getBackgroundImg("js",0,1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BackImg>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BackImg backImg) {
                        imgurl = "http://cn.bing.com"+backImg.getImages().get(0).getUrl();
                        SharedPreferences.Editor editor = PreferenceManager
                                .getDefaultSharedPreferences(SearchActivity.this)
                                .edit();
                        editor.putString("bg_img",imgurl);
                        editor.apply();

                        Glide.with(SearchActivity.this).load(imgurl)
                                .placeholder(R.drawable.city)
                                .error(R.drawable.sea)
                                .into(image);
                    }
                });
    }




}
