package android.particles.com.retrofit.component.widget.TabView;

import android.particles.com.retrofit.base.BaseFragment;

/**
 * Created by YLM on 2016/10/16.
 */
public class TabItem {
    public boolean isNow = false;
    public int nowImageResId;
    public int imageResId;
    public int lableResId;
    public Class<? extends BaseFragment> tagFragmentClz;

    public TabItem(int nowImageResId,int imageResId, int lableResId, Class<? extends BaseFragment> tagFragment){
        this.nowImageResId = nowImageResId;
        this.imageResId = imageResId;
        this.lableResId = lableResId;
        this.tagFragmentClz = tagFragment;
    }



}
