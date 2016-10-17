package android.particles.com.retrofit.modules.search;


import android.os.Bundle;
import android.particles.com.retrofit.R;
import android.particles.com.retrofit.base.BaseApplication;
import android.particles.com.retrofit.base.BaseFragment;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * Created by YLM on 2016/10/15.
 */
public class SearchFragment extends BaseFragment {
    private View view;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup parent, Bundle savedInstanceState){
        super.onCreateView(layoutInflater,parent,savedInstanceState);
        view = layoutInflater.inflate(R.layout.fragmentsearch,parent,false);
        initView();
        return view;
    }
    private void initView(){
        //侧滑栏设置
        drawerLayout = (DrawerLayout)view.findViewById(R.id.drawerlayout);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);//关闭手势开启drawer

        //toolbar设置
        toolbar = (Toolbar)view.findViewById(R.id.search_toolbar);
        toolbar.setNavigationIcon(R.drawable.menu);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });


    }
}
