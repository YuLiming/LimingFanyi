package android.particles.com.retrofit.component.util;

import android.particles.com.retrofit.common.MyApplication;
import android.particles.com.retrofit.modules.main.adapter.HomeAdapter;
import android.support.v7.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

/**
 * Created by YLM on 2016/7/2.
 */
public class ShowInScreen
{
    private List<String> fanyi;
    private List<String> fansrc;
    private RecyclerView mRecyclerView;
    private HomeAdapter madapter;

    public ShowInScreen(List<String> src,List<String> yi,HomeAdapter adapter,RecyclerView recyclerView)
    {
        fansrc = src;
        fanyi = yi;
        mRecyclerView = recyclerView;
        madapter = adapter;

    }
    public void showInFan()
    {
        Collections.reverse(fansrc);
        Collections.reverse(fanyi);
        mRecyclerView.setAdapter(madapter = new HomeAdapter(MyApplication.getContext(), fansrc, fanyi));
    }
    public void showInShun()
    {
        mRecyclerView.setAdapter(madapter = new HomeAdapter(MyApplication.getContext(), fansrc, fanyi));
    }
}
