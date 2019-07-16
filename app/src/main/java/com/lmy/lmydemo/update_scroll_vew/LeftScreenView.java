package com.lmy.lmydemo.update_scroll_vew;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.lmy.lmydemo.R;

public class LeftScreenView extends ScrollView implements View.OnTouchListener{
    private final String TAG = "lmy_zzz";

    private UpdateView update;
    private final int title_height;

    public LeftScreenView(Context context) {
        this(context,null);
    }

    public LeftScreenView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LeftScreenView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
//        title_height = (int) context.getResources().getDimension(R.dimen.ls_title_height);
        title_height = 186;
    }

    private void init(Context context) {
        setOnTouchListener(this);
        setOnScrollChangeListener(new OnScrollChangeListener() {
            @Override
            public void onScrollChange(View view, int x, int y, int oldX, int oldY) {
                if (listener != null) {
                    listener.scroll(getScrollY(), 0, 400, false);
                }
            }
        });
    }

    private void initView() {
        update = getChildAt(0).findViewById(R.id.update);
        update.setListener(updateListener);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        initView();
    }

    private boolean isScrolledToTop = true;//是否滑动到顶部
    private boolean isScrolledToBottom = false;//是否滑动到尾部
    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
        if (android.os.Build.VERSION.SDK_INT >= 9) {
            if (getScrollY() == 0) {
                isScrolledToTop = true;
                isScrolledToBottom = false;
            } else if (getScrollY() + getHeight() - getPaddingTop()-getPaddingBottom() == getChildAt(0).getHeight()) {
                isScrolledToBottom = true;
                isScrolledToTop = false;
            } else {
                isScrolledToTop = false;
                isScrolledToBottom = false;
            }
        }
        Log.i(TAG, "onScrollChanged: getScrollY="+getScrollY()+",isScrolledToTop="+isScrolledToTop+",isScrolledToBottom="+isScrolledToBottom);
    }

    private float lastY;
    private boolean isCanRelax=false;//是否可以刷新
    private boolean isFirstMove = false;//第一次移动
    boolean isIntercept = false;
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
//        mDetector.onTouchEvent(event);
        Log.d("lmy_l;;","dispatchTouchEvent "+event.getAction());
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastY = event.getY();
                isFirstMove = true;
                isIntercept = false;
                Log.d("lmy_","isUpdate="+update.isUpdate());
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i(TAG, "dispatchTouchEvent:M isMove="+isMove+",isUpdate="+update.isUpdate()+",isTop="+isScrolledToTop+",isCanRelax="+isCanRelax+",isIntercept="+isIntercept+",Y="+event.getY()+",lastY="+lastY+",差="+(event.getY()-lastY)+",scrollY="+getScrollY());
                if (isFirstMove) {
                    isFirstMove = false;
                    if (lastY - event.getY() > 0) {
                        Log.d(TAG,"lastY大于eventY,向上滑动，不能触发下拉刷新");
                        isCanRelax = false;
                    } else if (!isScrolledToTop&&getScrollY()>0) {
                        Log.d(TAG,"不在顶部，不能触发下拉刷新");
                        isCanRelax = false;
                    } else if (getScrollY() == 0 && isScrolledToTop) {
                        Log.d(TAG, "可以下拉刷新");
                        isMove = false;
                        isCanRelax = true;
                        isIntercept = true;
                    } else {
                        isFirstMove = true;
                        Log.d(TAG, "没有判断出是什么情况，再次判断");
                    }
                    Log.i("lmy_l;;","isMove="+isMove+",isCanRelax="+isCanRelax+",isIntercept="+isIntercept);
                    if (isIntercept) {

                    }
                }
                Log.d("lmy_kkk","update="+update+",isCanRelax="+isCanRelax+",update.isUpdate()+"+update.isUpdate());
                if (update != null&&isCanRelax&&!update.isUpdate()) {
                    Log.i(TAG,"isMove="+isMove);
                    update.updateSize(lastY,event.getY());
                }
                lastY = event.getY();
                return isIntercept?true:super.dispatchTouchEvent(event);
            case MotionEvent.ACTION_UP:
                if (update != null&&isCanRelax&&!update.isUpdate()) {
                    Log.d(TAG,"---- 抬起来了");
                    update.actionUp();
                }
                lastY = 0;
                isMove = true;
                isCanRelax = false;
                if (getScrollY() == 0) {
                    isScrolledToTop = true;
                }
                return isIntercept?true:super.dispatchTouchEvent(event);
            case MotionEvent.ACTION_CANCEL:
                update.cancel();
                break;
        }
        return super.dispatchTouchEvent(event);
    }

    public UpdateView getUpdateView() {
        return update;
    }

    private boolean isMove = true;//表示本scrollView 是否可以滑动 true 可以滑动 false 不可以滑动
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return !isMove;
    }

    public void setUpdateListener(UpdateView.UpdateListener listener) {
        updateListener = listener;
    }
    private UpdateView.UpdateListener updateListener;
    ScrollChangeListener listener;

    public void setScrollChangeListener(ScrollChangeListener listener) {
        this.listener = listener;
    }

    public interface ScrollChangeListener{
        void scrollChangeTitle(int index);
        void scroll(int scrollY, int changeY, int showTitleY, boolean update);
    }
}
