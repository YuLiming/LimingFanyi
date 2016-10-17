package android.particles.com.retrofit.modules.review;

import android.os.Bundle;
import android.particles.com.retrofit.R;
import android.particles.com.retrofit.base.BaseFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by YLM on 2016/10/17.
 */
public class ReviewFragment extends BaseFragment {
    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
        super.onCreateView(inflater,parent,savedInstanceState);
        view = inflater.inflate(R.layout.review,parent,false);
        return view;
    }
}
