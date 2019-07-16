package com.lmy.lmydemo.update_scroll_vew;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.lmy.lmydemo.R;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class NewCustomLeftScreen extends LinearLayout {
    private final Context mContext;
    private TextView mSearch;
    private Activity mLuancher;

    static public String LauncherFLAVOR = "";

    private LeftScreenView lsView;
    private TextView lsTitle;
    private View lsTop;
    private View lsTitleView;
    private RadioGroup updateGroup;

    private boolean isSuccess = true;

    public NewCustomLeftScreen(Context context) {
        super(context);
        mContext = context;
        initView();
        initListener();
    }

    public NewCustomLeftScreen(Context context, Activity launcher) {
        this(context);
        this.mLuancher = launcher;
    }

    private void initView() {
        inflate(mContext, R.layout.newcustomleftscreen, this);
        mSearch = (TextView) findViewById(R.id.id_left_screen_search);
        mSearch.setPivotY(0);
//        mTime = (TextView) findViewById(R.id.id_ls_time);
        lsView = findViewById(R.id.ls_view);
        lsTitle = findViewById(R.id.ls_title);
        lsTop = findViewById(R.id.ls_top);
        lsTitleView = findViewById(R.id.ls_title_view);
        updateGroup = findViewById(R.id.update_group);
    }

    private void initListener() {
        mSearch.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                openOrCloseSearchView("open");
            }
        });
        lsView.setUpdateListener(new UpdateView.UpdateListener() {
            @Override
            public void updateStart() {
                Log.d("lmy_uuu","开始刷新");
                Toast.makeText(mContext, "开始刷新", Toast.LENGTH_SHORT).show();

                //执行完刷新逻辑后 成功调用updateFinish,失败调用loadError;
                final UpdateView updateView = lsView.getUpdateView();
                if (updateView != null) {
                    if (isSuccess) {
                        //模拟刷新时间
                        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                updateView.updateFinish();
                            }
                        }, 2000);
                    } else {
                        updateView.loadError();
                    }
                }
            }
        });
        lsView.setScrollChangeListener(new LeftScreenView.ScrollChangeListener() {
            @Override
            public void scrollChangeTitle(int index) {
            }
            boolean isTop;
            @Override
            public void scroll(int scrollY, int changeY, int showTitleY,boolean update) {
                Log.d("lmy_ddd","scrollY="+scrollY+",changeY="+changeY+",showTitleY="+showTitleY+",update="+update);
                if (scrollY <45) {
                    if (isTop) {
                        ObjectAnimator.ofFloat(mSearch,"scaleY",0.1f,1f)
                                .setDuration(500)
                                .start();
                        isTop = false;
                    }
                }else {
                    if (!isTop) {
                        ObjectAnimator.ofFloat(mSearch,"scaleY",1f,0.1f)
                                .setDuration(500)
                                .start();
                        isTop = true;
                    }
                }
                if (showTitleY == 0) {
                    return;
                }
                if (scrollY > changeY) {
                    lsTitleView.setVisibility(VISIBLE);
                } else {
                    lsTitleView.setVisibility(GONE);
                }
                scrollY-=changeY;
                showTitleY -= changeY;
                float f = division(scrollY>showTitleY?showTitleY:scrollY,showTitleY);
                int color = getChangeColor(f);
                changeTopBar(f);
                lsTitleView.setBackgroundColor(color);
                lsTop.setAlpha(f);
            }
        });
        lsTop.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                lsView.fullScroll(ScrollView.FOCUS_UP);
            }
        });
        updateGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.update_success:
                        isSuccess = true;
                        break;
                    case R.id.update_error:
                        isSuccess = false;
                        break;
                }
            }
        });
    }

    /**
     * 打开或者关闭 搜索
     * @param open_status open 打开
     */
    private void openOrCloseSearchView(String open_status){
        Intent sendintent = new Intent("com.android.launcher3.openserach");
        sendintent.putExtra("open_status", open_status);
        LocalBroadcastManager.getInstance(mContext).sendBroadcast(sendintent);//本地广播
    }

    private int getChangeColor(float f) {
        int i = (int) (f * 255);
        if (i > 255) {
            i = 255;
        } else if (i < 0) {
            i = 0;
        }
        String hex = Integer.toHexString(i);
        if (hex.length() < 2) {
            hex = "0" + hex;
        }
        String s = "#" + hex + "F9F9F9";
        Log.d("lmy_ddd","s="+s+",alpha="+hex+",f="+(int) (f * 255));
        return Color.parseColor(s);
    }

    private void changeTopBar(float f) {
        if (mLuancher != null) {
            Window window = mLuancher.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            if (f > 0.5) {
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            } else {
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
            }
        }
    }

    /**
     * 求两个数的百分比
     * @param num1
     * @param num2
     * @return
     */
    private float division(int num1, int num2) {
        String rate="0.00%";
        //定义格式化起始位数
        String format="0.00";
        if(num2 != 0 && num1 != 0){
            DecimalFormat dec = new DecimalFormat(format);
            rate = dec.format((double) num1 / num2*100)+"%";
            while(true){
                if(rate.equals(format+"%")){
                    format=format+"0";
                    DecimalFormat dec1 = new DecimalFormat(format);
                    rate = dec1.format((double) num1 / num2*100)+"%";
                }else {
                    break;
                }
            }
        } else if (num1 != 0 && num2 == 0) {
            rate = "100%";
        } else {
            rate = "0%";
        }
        String decimal = rate.substring(0, rate.indexOf("%"));
        BigDecimal bigDecimal = new BigDecimal(decimal);
        bigDecimal.divide(new BigDecimal("100"), 2, BigDecimal.ROUND_HALF_UP);
        return bigDecimal.floatValue()/100;
    }
}