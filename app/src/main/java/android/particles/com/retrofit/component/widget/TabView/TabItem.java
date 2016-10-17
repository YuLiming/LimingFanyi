package android.particles.com.retrofit.component.widget.TabView;

import android.particles.com.retrofit.base.BaseFragment;

/**
 * Created by YLM on 2016/10/16.
 */
public class TabItem {
    public int imageResId;
    public int lableResId;
    public Class<? extends BaseFragment> tagFragmentClz;

    public TabItem(int imageResId, int lableResId, Class<? extends BaseFragment> tagFragment){
        this.imageResId = imageResId;
        this.lableResId = lableResId;
        this.tagFragmentClz = tagFragment;
    }



}
