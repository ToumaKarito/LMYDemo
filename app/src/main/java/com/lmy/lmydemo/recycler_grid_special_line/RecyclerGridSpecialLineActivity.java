package com.lmy.lmydemo.recycler_grid_special_line;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;

import com.lmy.lmydemo.R;

import java.util.ArrayList;

public class RecyclerGridSpecialLineActivity extends AppCompatActivity {

    private final int spanSize = 5;
    private SpecialLineRecyclerView list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_grid_special_line);
        init();
    }

    private void init() {
        list = (SpecialLineRecyclerView)findViewById(R.id.list);
        list.setLayoutManager(new GridLayoutManager(this, spanSize));
        SpecialLineAdapter adapter = new SpecialLineAdapter(this);
        ArrayList<SpecialLineEntiy> dataA = new ArrayList<>();
        for (int i = 0; i < 17; i++) {
            SpecialLineEntiy entiy = new SpecialLineEntiy();
            dataA.add(entiy);
        }
        if (dataA.size() % spanSize != 0) {
            for (int i = 0; i < dataA.size()%spanSize; i++) {
                SpecialLineEntiy entiy = new SpecialLineEntiy();
                entiy.isNull = true;
                dataA.add(entiy);
            }
        }
        ArrayList<SpecialLineEntiy> dataB = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            SpecialLineEntiy entiy = new SpecialLineEntiy();
            dataB.add(entiy);
        }
        if (dataB.size() % spanSize != 0) {
            for (int i = 0; i < dataB.size()%spanSize; i++) {
                SpecialLineEntiy entiy = new SpecialLineEntiy();
                entiy.isNull = true;
                dataB.add(entiy);
            }
        }
        ArrayList<SpecialLineEntiy> dataC = new ArrayList<>();
        for (int i = 0; i < 19; i++) {
            SpecialLineEntiy entiy = new SpecialLineEntiy();
            dataC.add(entiy);
        }
        if (dataC.size() % spanSize != 0) {
            for (int i = 0; i < dataC.size()%spanSize; i++) {
                SpecialLineEntiy entiy = new SpecialLineEntiy();
                entiy.isNull = true;
                dataC.add(entiy);
            }
        }
        adapter.setAllData(dataA, dataB, dataC);
        list.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
