package android.particles.com.retrofit.modules.search;



import android.os.Bundle;
import android.particles.com.retrofit.R;
import android.particles.com.retrofit.base.BaseActivity;
import android.particles.com.retrofit.base.BaseFragment;
import android.particles.com.retrofit.component.widget.TabView.TabContainer;
import android.particles.com.retrofit.component.widget.TabView.TabItem;
import android.particles.com.retrofit.modules.history.HistoryFragment;
import android.particles.com.retrofit.modules.review.ReviewFragment;
import android.support.v4.app.FragmentManager;


import java.util.ArrayList;

/**
 * Created by YLM on 2016/10/15.
 */
public class SearchActivity extends BaseActivity implements TabContainer.OnTabClickListener{
    private TabContainer tabContainer;
    private FragmentManager fm = getSupportFragmentManager();
    private BaseFragment fragment;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
        initView();
        initData();


    }

    private void initView(){
        SearchFragment searchFragment = new SearchFragment();
        fm.beginTransaction().replace(R.id.fragment,searchFragment).commitAllowingStateLoss();
        tabContainer = (TabContainer)findViewById(R.id.tablayout);
    }

    private void initData(){
        ArrayList<TabItem> tabs = new ArrayList<>();
        tabs.add(new TabItem(R.drawable.search,R.string.tab_search,HistoryFragment.class));
        tabs.add(new TabItem(R.drawable.search,R.string.tab_history,SearchFragment.class));
        tabs.add(new TabItem(R.drawable.search,R.string.tab_review,ReviewFragment.class));
        tabContainer.initData(tabs,this);
    }

    @Override
    public void onTabClick(TabItem tabItem){
        try {
            fragment= tabItem.tagFragmentClz.newInstance();
            fm.beginTransaction().replace(R.id.fragment,fragment).commitAllowingStateLoss();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
