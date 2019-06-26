package com.tsa.exam;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tsa.exam.Utill.GLOBAL;
import com.tsa.exam.model.PracticalQuestionModel;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.realm.Realm;
import io.realm.RealmResults;

public class PracticalQusActivity extends AppCompatActivity {

    private ListView listView;
    //PracticalCanListAdapter practicalCanListAdapter;
    private Realm mRealm;
    private String selectedBatchID;
    private Bundle bundle;
    private TextView assID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_qus);
        bundle=getIntent().getExtras();

        if(bundle!=null)
        {
            selectedBatchID=bundle.getString("selected_batch_id");
        }

        Realm.init(getApplicationContext());
        mRealm = Realm.getDefaultInstance();
        assID=(TextView)findViewById(R.id.ass_id);

        assID.setText(GLOBAL.assessorID);

        listView=(ListView) findViewById(R.id.root1);

        mRealm.beginTransaction();
        RealmResults<PracticalQuestionModel> practicalQuestionModels = mRealm.where(PracticalQuestionModel.class).contains("mBatchId",selectedBatchID).findAll();

        ArrayList<PracticalQuestionModel> practicalCanDetailsModelArrayList=new ArrayList<>();

        if(!practicalQuestionModels.isEmpty()) {
            for(int i = practicalQuestionModels.size() - 1; i >= 0; i--) {
                practicalCanDetailsModelArrayList.add(practicalQuestionModels.get(i));
            }
        }

        List<String> list;
        list = new ArrayList<String>();

        Set<String> set = new HashSet<String>();

        if (!practicalQuestionModels.isEmpty()) {
            for (int i = 0; i < practicalCanDetailsModelArrayList.size(); i++)

                set.add(practicalQuestionModels.get(i).getCandidateLoginId());
        }

        Enumeration e = Collections.enumeration(set);
        while (e.hasMoreElements()) {
            list.add(e.nextElement().toString());
        }

        practicalCanDetailsModelArrayList.clear();
        for(int i=0;i<list.size();i++)
        {
            practicalCanDetailsModelArrayList.add(mRealm.where(PracticalQuestionModel.class).contains("mCandidateLoginId",list.get(i)).findFirst());
        }
      /*  mRealm.commitTransaction();
        practicalCanListAdapter=new PracticalCanListAdapter(PracticalQusActivity.this,practicalCanDetailsModelArrayList);
        listView.setAdapter(practicalCanListAdapter);*/

    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "You are not allowed to go back", Toast.LENGTH_SHORT).show();
    }
}
