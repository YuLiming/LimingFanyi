package android.particles.com.retrofit.component.widget.TabView;

import android.content.Context;
import android.graphics.Color;
import android.particles.com.retrofit.R;
import android.particles.com.retrofit.base.BaseFragment;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by YLM on 2016/10/16.
 */
public class TabView extends LinearLayout{
    private boolean isNow;
    public int nowImageResId;
    public int imageResId;
    public int lableResId;
    public String s;
    public Class<? extends BaseFragment> tagFragmentClz;

    TextView textView;
    ImageView imageView;

    public TabView(Context context,int nowImageResId,int imageResId, int lableResId, Class<? extends BaseFragment> tagFragment){
        super(context);
        this.nowImageResId = nowImageResId;
        this.imageResId = imageResId;
        this.lableResId = lableResId;
        this.tagFragmentClz = tagFragment;
        initView(context);
    }

    public TabView(Context context, AttributeSet set,int nowImageResId,int imageResId, int lableResId, Class<? extends BaseFragment> tagFragment){
        super(context,set);
        this.nowImageResId = nowImageResId;
        this.imageResId = imageResId;
        this.lableResId = lableResId;
        this.tagFragmentClz = tagFragment;
        initView(context);
    }

    private void initView(Context context){
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER);
        LayoutInflater.from(context).inflate(R.layout.tab_item,this,true);
        imageView = (ImageView)findViewById(R.id.tab_item_image);
        textView = (TextView)findViewById(R.id.tab_item_text);
        textView.setText(lableResId);
        s = (String)textView.getText();
        imageView.setImageResource(imageResId);
    }

    public void setNow(boolean isNow){
        this.isNow = isNow;
    }

    public void drawTab(){
        if (isNow){
            imageView.setImageResource(nowImageResId);
            textView.setTextColor(Color.BLACK);
        }else {
            imageView.setImageResource(imageResId);
            textView.setTextColor(getResources().getColor(R.color.tabTextColor));
        }
        isNow =false;
    }

}
