package com.tsa.exam.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.tsa.exam.NOSPracticalActivity;
import com.tsa.exam.NOSPracticalQusActivity;
import com.tsa.exam.R;
import com.tsa.exam.model.NOSPracticalModel;

import java.util.ArrayList;

/**
 * Created by Akhil Tripathi on 07-07-2017.
 */

public class NOSPracticalCanListAdapter extends BaseAdapter{

    private Context context;
    private ArrayList<NOSPracticalModel> nosPracticalModelArrayList;

    public NOSPracticalCanListAdapter(Context context, ArrayList<NOSPracticalModel> nosPracticalModelArrayList) {
        this.context = context;
        this.nosPracticalModelArrayList = nosPracticalModelArrayList;
    }

    @Override
    public int getCount() {
        return nosPracticalModelArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return nosPracticalModelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
     ViewHolder holder;
        if (view != null) {
            holder = (ViewHolder) view.getTag();
        } else {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = mInflater.inflate(R.layout.assessment_details_card, parent, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }

        holder.candiadeUserId.setText(nosPracticalModelArrayList.get(position).getCandidateLoginId());
        holder.candidateName.setText(nosPracticalModelArrayList.get(position).getCandidateName());
        holder.addCandidate.setText("Start");
        holder.addCandidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, NOSPracticalActivity.class);
                intent.putExtra("c_name",nosPracticalModelArrayList.get(position).getCandidateName());
                intent.putExtra("c_login_id",nosPracticalModelArrayList.get(position).getCandidateLoginId());
                intent.putExtra("batch_id",nosPracticalModelArrayList.get(position).getBatchId());
                context.startActivity(intent);
            }
        });

        return view;
    }

    class ViewHolder {
        TextView candiadeUserId;
        TextView candidateName;
        Button addCandidate;

        public  ViewHolder(View view)
        {
            candiadeUserId=(TextView)view.findViewById(R.id.candiade_user_id);
            candidateName=(TextView)view.findViewById(R.id.candidate_name);
            addCandidate=(Button)view.findViewById(R.id.add_candidate);

        }
    }

}
