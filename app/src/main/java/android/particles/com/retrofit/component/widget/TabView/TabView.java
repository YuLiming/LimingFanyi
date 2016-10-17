package android.particles.com.retrofit.component.widget.TabView;

import android.content.Context;
import android.particles.com.retrofit.R;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by YLM on 2016/10/16.
 */
public class TabView extends LinearLayout implements View.OnClickListener{

    TextView textView;
    ImageView imageView;

    public TabView(Context context){
        super(context);
        initView(context);
    }

    public TabView(Context context, AttributeSet set){
        super(context,set);
        initView(context);
    }

    private void initView(Context context){
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER);
        LayoutInflater.from(context).inflate(R.layout.tab_item,this,true);
        imageView = (ImageView)findViewById(R.id.tab_item_image);
        textView = (TextView)findViewById(R.id.tab_item_text);
    }

    public void initData(TabItem tabItem){
        imageView.setImageResource(tabItem.imageResId);
        textView.setText(tabItem.lableResId);
    }

    @Override
    public void onClick(View v){

    }

}
