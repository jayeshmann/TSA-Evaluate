package com.tsa.exam.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tsa.exam.Prac_syncActivity;
import com.tsa.exam.database.EvaluateDB;
import com.tsa.exam.model.NOSPracticalModel;
import com.tsa.exam.model.ResultModel;

import java.util.ArrayList;

public class PracVideoAdapter extends RecyclerView.Adapter<PracVideoAdapter.ItemViewHolder> {
    EvaluateDB evaluateDB;
    private Context context;
    private ArrayList<NOSPracticalModel> pracResultModels;
    private ArrayList<NOSPracticalModel> nosPracticalModelArrayList;

    public PracVideoAdapter(Context context, ArrayList<NOSPracticalModel> resultModelArrayList) {
        super();
    }

    @NonNull
    @Override
    public PracVideoAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull PracVideoAdapter.ItemViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
