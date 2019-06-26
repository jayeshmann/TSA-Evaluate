package com.tsa.exam;

import android.content.Context;
import android.os.AsyncTask;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.tsa.exam.adapters.NOSPracSyncAdapter;
import com.tsa.exam.database.EvaluateDB;
import com.tsa.exam.model.NOSPracticalModel;

import java.util.ArrayList;

public class NOSTestActivity extends AppCompatActivity {
    private NOSPracSyncAdapter nosPracSyncAdapter;
    private ArrayList<NOSPracticalModel> nosPracticalModelArrayList;
    private Context context;
    EvaluateDB evaluateDB;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nostest);
        context = NOSTestActivity.this;
        evaluateDB = EvaluateDB.getInstance(context);
        init();
    }

    private void init() {
        evaluateDB = EvaluateDB.getInstance(context);
        listView=(ListView) findViewById(R.id.prac_nos_list);
        //nosPracSyncAdapter = new NOSPracSyncAdapter();
        getQuestionsFromDb();
    }

    public void getQuestionsFromDb(){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                nosPracticalModelArrayList = (ArrayList<NOSPracticalModel>) evaluateDB.getNosDao().getSubmitedCanList("1");
                nosPracSyncAdapter=new NOSPracSyncAdapter(context,nosPracticalModelArrayList);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                listView.setAdapter(nosPracSyncAdapter);
            }
        }.execute();

    }
}
