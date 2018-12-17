package com.lmy.lmydemo.drag_recycler_grid;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.AttributeSet;
import android.view.View;

import java.util.Collections;
import java.util.List;

public class DragRecyclerView extends RecyclerView {

    private final String TAG = "拖拽已添加的快捷功能调整顺序";
    private final int TAG_SIZE = 36;
    private final int DIVIDE_SIZE = 100;

    private ItemTouchHelper helper;
    private ItemTouchHelper.Callback callback;
    private Paint paint;
    private Rect tagRect;
    private int tagTop;
    private int tagleft;
    private static int spanCount;
    private int bgTop;

    public DragRecyclerView(Context context) {
        this(context, null);
    }

    public DragRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DragRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setAntiAlias(true);
        paint.setTextSize(TAG_SIZE);
        tagRect = getTagRect(paint, TAG);
        tagleft = (tagRect.right-tagRect.left)/2;
        addItemDecoration(new ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, State state) {
                super.onDraw(c, parent, state);
            }

            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, State state) {
                super.getItemOffsets(outRect, view, parent, state);
                int itemPosition = parent.getChildAdapterPosition(view);
                if (itemPosition < spanCount) {
                    outRect.set(0,0,0,DIVIDE_SIZE);
                } else {
                    outRect.set(0,0,0,0);
                }
            }
        });
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        if (getChildCount() > spanCount) {
            paint.setColor(Color.parseColor("#E8E8E8"));
            canvas.drawRect(0, 0, getWidth(), tagTop+TAG_SIZE, paint);
        }
        if (getChildCount() > 0) {
            int midWidth = getWidth() / 2;
            paint.setColor(Color.parseColor("#8F8E94"));
            canvas.drawText(TAG, 0, TAG.length(), (midWidth - tagleft), tagTop, paint);
        }
        super.dispatchDraw(canvas);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (getChildCount() > 0) {
            //确定 tag文字的  top 的位置
            View view = getChildAt(0);
            int height = view.getHeight();
            int top = view.getTop();
            if (tagTop == 0) {
                tagTop = height + top + (DIVIDE_SIZE-TAG_SIZE);
            }
        }
        if (getChildCount() > spanCount) {
            View child = getChildAt(spanCount);
            int[] loc = new int[2];
            child.getLocationOnScreen(loc);
            int childTop = loc[1];
            this.getLocationOnScreen(loc);
            int thisTop = loc[1];
            if (bgTop == 0) {
                bgTop = childTop-thisTop;
            }
        }
    }

    @Override
    public void setLayoutManager(LayoutManager layout) {
        layout.setAutoMeasureEnabled(true);
        if (layout instanceof GridLayoutManager) {
            GridLayoutManager manager = (GridLayoutManager) layout;
            spanCount = manager.getSpanCount();
        }
        super.setLayoutManager(layout);
    }

    private Rect getTagRect(Paint paint, String str) {
        Rect rect = new Rect();
        paint.getTextBounds(str, 0, str.length(), rect);
        return rect;
    }

    public ItemTouchHelper setCallback(ItemTouchHelper.Callback callback) {
        this.callback = callback;
        helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(this);
        return helper;
    }

    public ItemTouchHelper getTouchHelper(ItemTouchHelper.Callback callback) {
        if (helper == null) {
            helper = new ItemTouchHelper(callback);
            helper.attachToRecyclerView(this);
        }
        return helper;
    }

    public static class DragCallback extends ItemTouchHelper.Callback {
        private final Adapter adapter;
        private final List<?> datas;

        public DragCallback(List<?> datas, Adapter adapter) {
            this.datas = datas;
            this.adapter = adapter;
        }

        @Override
        public int getMovementFlags(RecyclerView recyclerView, ViewHolder viewHolder) {
            if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
                final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN |
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                final int swipeFlags = 0;
                return makeMovementFlags(dragFlags, swipeFlags);
            } else {
                final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                final int swipeFlags = 0;
                return makeMovementFlags(dragFlags, swipeFlags);
            }
        }

        @Override
        public boolean onMove(RecyclerView recyclerView, ViewHolder viewHolder, ViewHolder target) {
            if (adapter == null || datas == null) {
                return false;
            }
            //得到当拖拽的viewHolder的Position
            int fromPosition = viewHolder.getAdapterPosition();
            //拿到当前拖拽到的item的viewHolder
            int toPosition = target.getAdapterPosition();
            if (fromPosition < toPosition) {
                for (int i = fromPosition; i < toPosition; i++) {
                    Collections.swap(datas, i, i + 1);
                }
            } else {
                for (int i = fromPosition; i > toPosition; i--) {
                    Collections.swap(datas, i, i - 1);
                }
            }
            adapter.notifyItemMoved(fromPosition, toPosition);
            return true;
        }

        @Override
        public void onSwiped(ViewHolder viewHolder, int direction) {

        }

        @Override
        public void onSelectedChanged(ViewHolder viewHolder, int actionState) {
            if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
                viewHolder.itemView.setBackgroundColor(Color.WHITE);
            }
            super.onSelectedChanged(viewHolder, actionState);
        }

        @Override
        public void clearView(RecyclerView recyclerView, ViewHolder viewHolder) {
            viewHolder.itemView.setBackgroundColor(0);
            super.clearView(recyclerView, viewHolder);
            recyclerView.getAdapter().notifyDataSetChanged();
        }
    }
}
