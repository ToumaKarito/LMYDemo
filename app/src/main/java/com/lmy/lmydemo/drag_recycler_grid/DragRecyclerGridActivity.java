package com.lmy.lmydemo.drag_recycler_grid;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lmy.lmydemo.R;

import java.util.ArrayList;

public class DragRecyclerGridActivity extends AppCompatActivity {

    private DragRecyclerView dragRecyclerView;
    private Adapter adapter;
    private ArrayList<Object> data;
    private ItemTouchHelper itemTouchHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag_recycler_grid);
        init();
    }

    private void init() {
        dragRecyclerView = (DragRecyclerView) findViewById(R.id.list);
        dragRecyclerView.setLayoutManager(new GridLayoutManager(this, 5));
        adapter = new Adapter();
        dragRecyclerView.setAdapter(adapter);
        data = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            data.add(new Object());
        }
        itemTouchHelper = dragRecyclerView.getTouchHelper(new DragRecyclerView.DragCallback(data, adapter));
        adapter.notifyDataSetChanged();
    }

    class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

        @Override
        public Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ImageView imageView = new ImageView(getApplicationContext());
            imageView.setImageResource(R.mipmap.ic_launcher_round);
            return new ViewHolder(imageView);
        }

        @Override
        public int getItemCount() {
            return data == null ? 0 : data.size();
        }

        @Override
        public void onBindViewHolder(final Adapter.ViewHolder holder, final int position) {
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (itemTouchHelper != null) {
                        itemTouchHelper.startDrag(holder);
                    }
                    return true;
                }
            });
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(DragRecyclerGridActivity.this, "点击了第"+(position+1)+"个Item", Toast.LENGTH_SHORT).show();
                }
            });
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            public ViewHolder(View itemView) {
                super(itemView);
                RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200);
                itemView.setLayoutParams(layoutParams);
            }
        }
    }
}
