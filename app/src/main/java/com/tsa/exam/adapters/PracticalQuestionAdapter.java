package com.tsa.exam.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.tsa.exam.R;
import com.tsa.exam.Utill.GLOBAL;
import com.tsa.exam.model.PracticalQuestionModel;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Akhil Tripathi on 11-07-2017.
 */

public class PracticalQuestionAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<PracticalQuestionModel> practicalQuestionModelArrayList;
    String[] pQR;

    public PracticalQuestionAdapter(Context context, ArrayList<PracticalQuestionModel> practicalQuestionModelArrayList) {
        this.context = context;
        this.practicalQuestionModelArrayList = practicalQuestionModelArrayList;
        GLOBAL.practicalQuestionModelArrayList=practicalQuestionModelArrayList;
        pQR =new String[practicalQuestionModelArrayList.size()];
    }

    @Override
    public int getCount() {
        return practicalQuestionModelArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return practicalQuestionModelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int i, View view, ViewGroup parent) {
      ViewHolder holder;

        if (view != null) {
            holder = (ViewHolder) view.getTag();
        } else {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = mInflater.inflate(R.layout.paractical_question_card, parent, false);
            holder = new ViewHolder(view);
            view.setTag(holder);

        }

        holder.activity.setText(practicalQuestionModelArrayList.get(i).getActivity());
        holder.question.setText(practicalQuestionModelArrayList.get(i).getQuestion());
        holder.marks.setText(practicalQuestionModelArrayList.get(i).getPcMarks());

        holder.yesRb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    pQR[i]=practicalQuestionModelArrayList.get(i).getPcMarks();
                    GLOBAL.obtainedParcMark= Arrays.asList(pQR);
                }
            }
        });

          holder.noRb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    pQR[i]="0";
                    GLOBAL.obtainedParcMark= Arrays.asList(pQR);
                }
            }
        });

        return view;
    }

    class ViewHolder {
        TextView activity;
        TextView question;
        TextView marks;
        RadioButton yesRb;
        RadioButton noRb;
        RadioGroup radioGroup;

        public  ViewHolder(View view)
        {

            activity=(TextView)view.findViewById(R.id.activity_tv);
            question=(TextView)view.findViewById(R.id.practical_q);
            marks=(TextView)view.findViewById(R.id.marks);
            yesRb=(RadioButton) view.findViewById(R.id.yes_rb);
            noRb=(RadioButton)view.findViewById(R.id.no_rb);
            radioGroup=(RadioGroup)view.findViewById(R.id.radio_root);

        }
    }
}
