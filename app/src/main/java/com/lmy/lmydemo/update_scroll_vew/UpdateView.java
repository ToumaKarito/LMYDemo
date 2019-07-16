package com.lmy.lmydemo.update_scroll_vew;

import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lmy.lmydemo.R;


public class UpdateView extends LinearLayout {
    private Context mContext;
    private TextView tip;
    private String down_tip = null;
    private String relax_tip = null;
    private String relaxing_tip = null;
    private String error_tip = "";
    private final int max_height;
    private final int update_height;
    private final int stage_height;
    private final int down_push_anim;
    private boolean isUpdate = false;
    private Handler mHandler;

//    private HashMap<NewLeftScreenCard,Boolean> cardMap;

    public UpdateView(Context context) {
        this(context,null);
    }

    public UpdateView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public UpdateView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        max_height = 300;
        update_height = max_height / 3 * 2;
        stage_height = max_height / 3;
        down_push_anim = update_height / 32;
        Log.i("lmy_ccc", "max_height="+max_height+",update_height="+update_height+",stage_height="+stage_height);
        down_tip = "下拉刷新";
        relax_tip = "松手刷新";
        relaxing_tip = "正在刷新";
        error_tip ="网络异常，请稍后再试";
        if (attrs != null) {
            TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.UpdateView, defStyleAttr, 0);
            for (int i = 0; i < array.length(); i++) {
                int index = array.getIndex(i);
                if (index == R.styleable.UpdateView_down_tip) {
                    down_tip = array.getString(index);
                    if (TextUtils.isEmpty(down_tip)) {
                        down_tip = "下拉刷新";
                    }
                } else if (index == R.styleable.UpdateView_relax_tip) {
                    relax_tip = array.getString(index);
                    if (TextUtils.isEmpty(relax_tip)) {
                        relax_tip = "松手刷新";
                    }
                } else if (index == R.styleable.UpdateView_relaxing_tip) {
                    relaxing_tip = array.getString(index);
                    if (TextUtils.isEmpty(relaxing_tip)) {
                        relaxing_tip = "正在刷新";
                    }
                }
            }
        }
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        inflate(context, R.layout.view_update, this);
        tip = findViewById(R.id.tip);
        mHandler = new Handler(Looper.getMainLooper());
//        cardMap = new HashMap<>();
    }


    private void changeTip(){
        if (getHeight() > update_height) {
            tip.setText(relax_tip);
        } else {
            tip.setText(down_tip);
        }
    }

    public boolean isUpdate() {
        return isUpdate;
    }

    public void actionUp() {
        if (getHeight() > update_height) {
            Log.d("lmy_uuu","触发刷新动画");
            isUpdate = true;
            tip.setText(relaxing_tip);
            //如果大于200 就缩放到刷新大小的界面
            changeAnim(getHeight(),200,200);
//            ((AnimationDrawable) icon.getBackground()).start();
            if (listener != null) {
                listener.updateStart();
            }
        } else {
            isUpdate = false;
            changeAnim(getHeight(),0,500);
        }
    }

    private void changeAnim(int fromHeight, final int toHeight, int time) {
        final int height = fromHeight-toHeight;
        ValueAnimator scaleY = ObjectAnimator.ofFloat(null, "scaleY", 1f, 0f);
        scaleY.setDuration(time);
        scaleY.setInterpolator(new TimeInterpolator() {
            @Override
            public float getInterpolation(float input) {
                ViewGroup.LayoutParams params = getLayoutParams();
                params.height = (int) ((1 - input) * height+toHeight);
                setLayoutParams(params);
                return input;
            }
        });
        scaleY.start();
    }

    public void updateSize(float lastY, float y) {
        ViewGroup.LayoutParams params = getLayoutParams();
        params.height +=(y-lastY)/(2+params.height/stage_height);

        if (params.height > max_height) {
            params.height = max_height;
        } else if (params.height < 0) {
            params.height = 0;
        }
//        if (params.height > update_height) {
//            Log.i("lmy_zzz","----- 超多了。");
//            icon.setImageResource(R.mipmap.ls_down_push_31);
//        } else {
//            int i = params.height / down_push_anim;
//            i = i > 31 ? 31 : i;
//            Log.i("lmy_zzz","----- i="+i);
//            icon.setImageResource(getImgId("ls_down_push_"+i));
//        }
        //这里是根据下拉的程度切换图片
        setLayoutParams(params);
        changeTip();
    }

    private int getImgId(String name) {
        return getContext().getResources().getIdentifier(name, "mipmap", getContext().getPackageName());
    }


    private UpdateListener listener;

    public void setListener(UpdateListener listener) {
        this.listener = listener;
    }


    public void updateFinish() {
        Log.d("lmy_","1111111---isUpdate="+isUpdate);
        if (tip != null) {
            tip.setText("刷新完成");
        }
        changeAnim(getHeight(),0,500);
        isUpdate = false;
    }

    public void loadError() {
        if (tip != null) {
            tip.setText(error_tip);
        }
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                changeAnim(getHeight(),0,500);
                isUpdate = false;
            }
        }, 1000);
    }

    public void cancel() {
        changeAnim(getHeight(),0,500);
    }

    public interface UpdateListener{
        void updateStart();
    }
}
