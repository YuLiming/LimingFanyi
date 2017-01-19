package android.particles.com.retrofit.component.widget.TabView;


import android.content.Context;
import android.particles.com.retrofit.R;
import android.particles.com.retrofit.base.BaseFragment;
import android.support.v4.app.FragmentManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;

/**
 * Created by YLM on 2016/10/16.
 */
public class TabContainer extends LinearLayout implements View.OnClickListener {
    private ArrayList<TabView> tabs;
    private OnTabClickListener listener;

    public TabContainer(Context context){
        super(context);
        initView();
    }
    public TabContainer(Context context, AttributeSet set){
        super(context,set);
    }
    private void initView(){
        setOrientation(HORIZONTAL);

    }

    public void setTabListener(OnTabClickListener listener){
        this.listener = listener;
    }
    public void initData(ArrayList<TabView> tabs){
        this.tabs = tabs;
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        params.weight = 1;
        if (tabs!=null&&tabs.size()>0){
            TabView tabView = null;
            for (int i = 0;i<tabs.size();i++){
                tabView = tabs.get(i);
                tabView.setOnClickListener(this);
                addView(tabView,i,params);

            }
        }else {
            throw new IllegalArgumentException("tabs can not be empty");
        }
    }

    private void reDraw(){
        for (TabView tabView:tabs){
            tabView.drawTab();

        }
    }

    @Override
    public void onClick(View v){
        listener.onTabClick((TabView) v);
        reDraw();
    }


    public interface OnTabClickListener{
        void onTabClick(TabView tabView);
    }

    public void setFirst(int size){
        TabView mainTab = tabs.get(size);
        mainTab.setNow(true);
        reDraw();
    }

}
