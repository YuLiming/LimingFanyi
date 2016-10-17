package android.particles.com.retrofit.modules.history;

import android.os.Bundle;
import android.particles.com.retrofit.R;
import android.particles.com.retrofit.base.BaseFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by YLM on 2016/10/17.
 */
public class HistoryFragment extends BaseFragment {
    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
        super.onCreateView(inflater,parent,savedInstanceState);
        view = inflater.inflate(R.layout.history,parent,false);
        return view;
    }
}
