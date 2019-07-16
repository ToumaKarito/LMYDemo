package com.lmy.lmydemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.lmy.lmydemo.drag_recycler_grid.DragRecyclerGridActivity;
import com.lmy.lmydemo.recycler_grid_special_line.RecyclerGridSpecialLineActivity;
import com.lmy.lmydemo.recycler_grid_special_line.SpecialLineRecyclerView;
import com.lmy.lmydemo.update_scroll_vew.UpdateSearchActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void dragRecyclerGrid(View view) {
        startActivity(new Intent(this,DragRecyclerGridActivity.class));
    }

    public void recyclerGridSpecialLine(View view) {
        startActivity(new Intent(this,RecyclerGridSpecialLineActivity.class));
    }

    public void updateSearch(View view) {
        startActivity(new Intent(this,UpdateSearchActivity.class));
    }
}
