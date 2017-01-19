package android.particles.com.retrofit.component.widget.cardSlideView;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Rect;
import android.particles.com.retrofit.component.util.Util;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;


/**
 * Created by YLM on 2016/10/23.
 */
public class CardSlideView extends FrameLayout {
    private static final int DEF_MAX_VISIBLE = 4;//item的数量
    private int maxVisible = DEF_MAX_VISIBLE;
    private double itemSpace;
    private OnCardClickListener mListener;
    private SparseArray<View> viewHolder = new SparseArray<View>();
    private Rect topRect;
    private float mTouchSloop;
    private ListAdapter mListAdapter;
    private int mNextAdapterPosition;
    private int topPosition;
    private float downX,downY;
    private float distance;
    private float rawY = 0;
    private float lastY = 0;
    private float screenHeight = 0;


    public interface OnCardClickListener{
        void onCardClick(View view,int position);
    }

    public CardSlideView(Context context, AttributeSet set,int defStyle){
        super(context,set,defStyle);
        init();
    }
    public CardSlideView(Context context,AttributeSet set){
        super(context,set);
        init();
    }
    public CardSlideView(Context context){
        super(context);
        init();
    }
    private void init(){
        screenHeight = Util.getScreenHeight(getContext());
        itemSpace = screenHeight*0.4/11;
        topRect = new Rect();//创建一个矩形
        ViewConfiguration con = ViewConfiguration.get(getContext());//view的一些固定属性
        mTouchSloop = con.getScaledTouchSlop();//最小滑动举例
    }
    public void setMaxVisibleCount(int count){
        maxVisible = count;
    }

    public int getMaxVisibleCount(){
        return maxVisible;
    }




    public ListAdapter getListAdapter(){
        return mListAdapter;
    }

    public void setAdapter(ListAdapter adapter){
        if (mListAdapter!=null){
            mListAdapter.unregisterDataSetObserver(mDataSetObserver);//取消注册
        }
        mNextAdapterPosition = 0;
        mListAdapter = adapter;
        mListAdapter.registerDataSetObserver(mDataSetObserver);//观察者模式，注册
        removeAllViews();//移除子视图
        ensureFull();
    }

    public void setOnCardClickListener(OnCardClickListener listener){
        mListener = listener;
    }

    //确保时刻队列时刻是满的
    private void ensureFull(){
        while (mNextAdapterPosition<mListAdapter.getCount()&&getChildCount()<maxVisible){
            int index = mNextAdapterPosition%maxVisible;
            View convertView = viewHolder.get(index);
            final View view = mListAdapter.getView(mNextAdapterPosition,convertView,this);
            //这里要思考一下为什么
            view.setOnClickListener(null);//只有设置了这个topview之后的view才能被滑动，否则都无法响应

            viewHolder.put(index,view);

            index = Math.min(mNextAdapterPosition,maxVisible-1);
            ViewHelper.setScaleX(view,((maxVisible - index - 1)/(float)maxVisible)*0.2f+0.8f);
            int topMargin = (int)((Math.pow(2,maxVisible - index - 1)-1)*itemSpace);
            ViewHelper.setTranslationY(view,topMargin);
            LayoutParams params = (LayoutParams)view.getLayoutParams();
            if (params==null){
                params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            }
            addViewInLayout(view,0,params);
            mNextAdapterPosition+=1;
        }
    }



    @Override
    protected void onMeasure(int widthMeasureSpec,int heightMeasureSpec){
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int childCount = getChildCount();
        int maxHeight = 0;
        int maxWidth = 0;
        for (int i=0;i<childCount;i++){
            View child = getChildAt(i);
            this.measureChild(child,widthMeasureSpec,heightMeasureSpec);//测量子控件
            int width = child.getWidth();
            int height = child.getHeight();
            if (height>maxHeight){
                maxHeight = height;
            }
            if (width>maxWidth){
                maxWidth = width;
            }
        }
        int desireWidth = widthSize;
        int desireHeight = heightSize;
        if (widthMode == MeasureSpec.AT_MOST){
            desireWidth = maxWidth+getPaddingLeft()+getPaddingRight();
        }
        if(heightMode == MeasureSpec.AT_MOST){
            desireHeight = maxHeight+getPaddingTop()+getPaddingBottom()
                    + (maxVisible-1)*(int)itemSpace;
        }
        setMeasuredDimension(desireWidth,desireHeight);
    }
    @Override
    protected void onLayout(boolean changed,int left,int top,int right,int bottom){
        super.onLayout(changed,left,top,right,bottom);
        View topView = getChildAt(getChildCount()-1);
        if (topView!=null){
            topView.setOnClickListener(listener);
        }
    }
    //后面可以修改一下，因为onInterceptTouchEvent有可能不运行
    //这里有一个问题，直接返回true是失败的
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {


        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //获取点击位置
                lastY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                downY = ev.getY();
                distance = downY - lastY;
                //Math.abs()取绝对值，否则向上滑动会失效
                if (Math.abs(distance) > mTouchSloop) {//必须大于最小移动距离才会继续分发
                    return true;
                }
                break;
            default:
                break;
        }
        return false;
    }




    @Override
    public boolean onTouchEvent(MotionEvent event){
        int childcount = getChildCount();

        switch (event.getAction()){

            case MotionEvent.ACTION_DOWN:
                lastY = event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                rawY = event.getRawY();
                float offsetY = rawY - lastY;
                for(int i=childcount-1;i>=0;i--){
                    View topView = getChildAt(i);
                    if (topView!=null){
                        topView.layout(topView.getLeft(),(int)topView.getTop()+(int)distanceInterpolator(topView.getY(),offsetY)
                                ,topView.getRight(),(int)topView.getTop()+topView.getHeight()+(int)distanceInterpolator(topView.getY(),offsetY));
                    }
                }
                lastY = rawY;
                break;
        }
        return super.onTouchEvent(event);
    }
    //插值器
    private float distanceInterpolator(float top,float offset){
        float x = top/screenHeight;
        if (x<=(4/110)*screenHeight){
            return (2/11)*offset;
        }else if (x<=(16/110)*screenHeight&&x>(4/110)*screenHeight){
            return (6/11)*offset;
//            return ((float) ((x-0.01)/0.29))*offset;
        }else if(x<=0.4*screenHeight&&x>(16/110)*screenHeight){
            return (14/11)*offset;
        }else {
            return offset;
        }
    }



    //topView下滑
//    private void goDown(){
//        final View topView = getChildAt(getChildCount()-1);//获取当前的顶层view
//
//        topView.setEnabled(false);//顶层view设置为不可见
//        ViewPropertyAnimator anim = ViewPropertyAnimator
//                .animate(topView)
//                .translationY(ViewHelper.getTranslationY(topView)+topView.getHeight()).alpha(0).scaleX(1)
//                .setListener(null).setDuration(200);
//        //设置动画监听器，当动画结束的时候进行某些操作
//        anim.setListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                topView.setEnabled(true);//topview可获得
//                removeView(topView);//移除顶层view
//                ensureFull();//确保队列是满的
//
//        final int count = getChildCount();
//        for (int i =0;i<count;i++){
//            final View view = getChildAt(i);
//            float scaleX = ViewHelper.getScaleX(view)
//                    +((float)1/maxVisible)*0.2f;
//            float tranlateY = ViewHelper.getTranslationY(view)
//                    +itemSpace;
//            if (i==count-1){
//                bringToTop(view);
//            }else {
//                if ((count == maxVisible&&i!=0)||count<maxVisible){
//                    ViewPropertyAnimator
//                            .animate(view)
//                            .translationY(tranlateY)
//                            .setInterpolator(new AccelerateInterpolator())
//                            .setListener(null)
//                            .scaleX(scaleX)
//                            .setDuration(200);
//                }
//            }
//        }
//    }
//        });
//
//    }

    private boolean isGoDown(){
        final View topView = getChildAt(getChildCount()-1);//获取当前的顶层view
        if (!topView.isEnabled()){//不可见时则移动不会使downY减一
            return false;
        }
        topRect = getHitRect(topRect,topView);//获取topView的范围矩阵
        if (!topRect.contains((int)downX,(int)downY)){//手指点击的位置不在矩阵范围内则移动不会使downY减一
            return false;
        }
        return true;
    }




//    private void bringToTop(final View view){
//        topPosition++;
//        float scaleX = ViewHelper.getScaleX(view) + ((float) 1 / maxVisible)
//                * 0.2f;
//        float tranlateY = ViewHelper.getTranslationY(view) + itemSpace;
//        ViewPropertyAnimator.animate(view).translationY(tranlateY)
//                .scaleX(scaleX).setDuration(200).alpha(1)
//                .setInterpolator(new AccelerateInterpolator());
//    }

    //解决getHitRect的bug
    public static Rect getHitRect(Rect rect, View child) {
        rect.left = child.getLeft();
        rect.right = child.getRight();
        rect.top = (int) (child.getTop() + ViewHelper.getTranslationY(child));
        rect.bottom = (int) (child.getBottom() + ViewHelper
                .getTranslationY(child));
        return rect;
    }

    private final DataSetObserver mDataSetObserver = new DataSetObserver() {
        @Override
        public void onChanged() {
            super.onChanged();
        }

        @Override
        public void onInvalidated() {
            super.onInvalidated();
        }
    };
    private OnClickListener listener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mListener!=null){
                mListener.onCardClick(v,topPosition);
            }
        }
    };
}
