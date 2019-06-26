package com.tsa.exam.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tsa.exam.R;

import java.util.ArrayList;

/**
 * Created by Akhil Tripathi on 4/14/2017.
 */

public class QuestionsGridAdapter extends BaseAdapter{

    private ArrayList<String> questionNo;
    private Context context;

    public QuestionsGridAdapter(ArrayList<String> questionNo, Context context) {
        this.questionNo = questionNo;
        this.context = context;
    }

    @Override
    public int getCount() {
        return questionNo.size();
    }

    @Override
    public Object getItem(int i) {
        return questionNo.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view==null)
        {
            LayoutInflater inflater=LayoutInflater.from(this.context);
            view=inflater.inflate(R.layout.question_card,viewGroup,false);
        }
        TextView number=(TextView)view.findViewById(R.id.question_number);
        if(Integer.parseInt(questionNo.get(i))%3==0)
        {
            number.setBackground(context.getResources().getDrawable(R.drawable.grid_bg_green));
        }
        else if(Integer.parseInt(questionNo.get(i))%3==1) {
            number.setBackground(context.getResources().getDrawable(R.drawable.grid_bg_gold));
        }
        else
        {
            number.setBackground(context.getResources().getDrawable(R.drawable.grid_bg_red));

        }
        number.setText(questionNo.get(i));

        return view;
    }
}
