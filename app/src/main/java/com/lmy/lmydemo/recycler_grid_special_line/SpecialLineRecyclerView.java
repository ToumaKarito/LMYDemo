package com.lmy.lmydemo.recycler_grid_special_line;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.List;

public class SpecialLineRecyclerView extends RecyclerView {

    private int spanCount=0;
    private SpecialLineAdapter lineAdapter;
    private List<List<SpecialLineEntiy>> allData;
    private Paint paint;
    private List<SpecialLineEntiy> dataA;
    private List<SpecialLineEntiy> dataB;
    private List<SpecialLineEntiy> dataC;

    public SpecialLineRecyclerView(Context context) {
        super(context);
        init();
    }

    public SpecialLineRecyclerView(Context context,AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SpecialLineRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setAntiAlias(true);
        addItemDecoration(new ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect,View view, RecyclerView parent, State state) {
                super.getItemOffsets(outRect, view, parent, state);
                int position = parent.getChildAdapterPosition(view);
                List<SpecialLineEntiy> listA = allData.get(0);
                List<SpecialLineEntiy> listB = allData.get(1);
                List<SpecialLineEntiy> listC = allData.get(2);
                if (position < listA.size() && position >= listA.size() - spanCount) {
                    outRect.set(0,0,0,200);
                } else if (position<listA.size()+listB.size()&&position>=((listA.size()+listB.size())-spanCount)) {
                    outRect.set(0,0,0,200);
                } else {
                    outRect.set(0, 0, 0, 0);
                }
            }
        });
    }

    @Override
    public void setLayoutManager(LayoutManager layout) {
        if (layout != null && layout instanceof GridLayoutManager) {
            spanCount = ((GridLayoutManager) layout).getSpanCount();
        }
        super.setLayoutManager(layout);
    }

    @Override
    public void setAdapter(Adapter adapter) {
        if (adapter != null && adapter instanceof SpecialLineAdapter) {
            lineAdapter = (SpecialLineAdapter) adapter;
            allData = lineAdapter.getAllData();
            dataA = allData.get(0);
            dataB = allData.get(1);
            dataC = allData.get(2);
        }
        super.setAdapter(adapter);
    }

    @Override
    public void dispatchDraw(Canvas c) {
        if (dataA!=null) {
            drawTextAndRect(c, dataA.size() - 1, "第一部分模块");
        }
        if (dataB != null) {
            drawTextAndRect(c, dataA.size()+dataB.size()-1, "第二部分模块");
        }
        super.dispatchDraw(c);
    }

    private void drawTextAndRect(Canvas c,int position,String tag) {
        View view = getLayoutManager().findViewByPosition(position);
        int top = view.getTop();
        int height = view.getHeight();
        Log.d("lmyLL", "dispatchDraw: topA="+top+",heightA="+height);
        paint.setColor(Color.BLACK);
        paint.setTextSize(36);
        Rect rect = new Rect();
        paint.getTextBounds(tag, 0, tag.length(), rect);
        int textH = rect.bottom - rect.top;
        c.drawText(tag,0,tag.length(),50,top+height+textH,paint);
        paint.setColor(Color.RED);
        c.drawRect(0,top+height+textH*2,getWidth(),top + height + textH * 2+100,paint);
    }
}
