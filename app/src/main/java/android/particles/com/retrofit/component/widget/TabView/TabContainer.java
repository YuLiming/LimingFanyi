package android.particles.com.retrofit.component.widget.TabView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;

/**
 * Created by YLM on 2016/10/16.
 */
public class TabContainer extends LinearLayout implements View.OnClickListener {
    private ArrayList<TabItem> tabs;
    private OnTabClickListener listener;

    public TabContainer(Context context){
        super(context);
        initView();
    }
    public TabContainer(Context context, AttributeSet set){
        super(context,set);
        initView();
    }
    private void initView(){
        setOrientation(HORIZONTAL);
    }

    public void initData(ArrayList<TabItem> tabs,OnTabClickListener listener){
        this.tabs = tabs;
        this.listener = listener;
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        params.weight = 1;
        if (tabs!=null&&tabs.size()>0){
            TabView tabView = null;
            for (int i = 0;i<tabs.size();i++){
                tabView = new TabView(getContext());
                tabView.setTag(tabs.get(i));
                tabView.initData(tabs.get(i));
                tabView.setOnClickListener(this);
                addView(tabView,params);
            }
        }else {
            throw new IllegalArgumentException("tabs can not be empty");
        }
    }

    @Override
    public void onClick(View v){
        listener.onTabClick((TabItem) v.getTag());
    }

    public interface OnTabClickListener{
        void onTabClick(TabItem tabItem);
    }

}
