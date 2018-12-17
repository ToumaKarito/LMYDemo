package com.lmy.lmydemo.recycler_grid_special_line;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lmy.lmydemo.R;

import java.util.ArrayList;
import java.util.List;

public class SpecialLineAdapter extends RecyclerView.Adapter<SpecialLineAdapter.ViewHolder> {

    private Context context;

    private List<SpecialLineEntiy> dataA;//10
    private List<SpecialLineEntiy> dataB;//10
    private List<SpecialLineEntiy> dataC;//10
    private List<List<SpecialLineEntiy>> allData;

    public SpecialLineAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ImageView imageView = new ImageView(context);
        imageView.setImageResource(R.mipmap.ic_launcher_round);
        return new ViewHolder(imageView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        if (dataA != null && dataB != null && dataC != null) {
            if (i < dataA.size()) {
                //dataA
                viewHolder.itemView.setBackgroundColor(Color.RED);
                if (dataA.get(i).isNull) {
                    viewHolder.itemView.setBackgroundColor(Color.GRAY);
                }
            } else if (i >= dataA.size() && i < (dataA.size() + dataB.size())) {
                //dataB
                viewHolder.itemView.setBackgroundColor(Color.GREEN);
                if (dataB.get(i-dataA.size()).isNull) {
                    viewHolder.itemView.setBackgroundColor(Color.GRAY);
                }
            } else {
                //dataC
                viewHolder.itemView.setBackgroundColor(Color.BLUE);
                if (dataC.get(i-dataA.size()-dataB.size()).isNull) {
                    viewHolder.itemView.setBackgroundColor(Color.GRAY);
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return (dataA==null?0:dataA.size())+
                (dataB==null?0:dataB.size())+
                (dataC==null?0:dataC.size());
    }



    public List<List<SpecialLineEntiy>> getAllData() {
        return allData;
    }

    public void setAllData(List<SpecialLineEntiy> dataA,List<SpecialLineEntiy> dataB,List<SpecialLineEntiy> dataC) {
        this.dataA = dataA;
        this.dataB = dataB;
        this.dataC = dataC;
        allData = new ArrayList<>();
        allData.add(dataA);
        allData.add(dataB);
        allData.add(dataC);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
