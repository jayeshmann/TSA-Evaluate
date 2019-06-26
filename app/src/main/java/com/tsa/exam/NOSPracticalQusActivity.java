package com.tsa.exam;

import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tsa.exam.adapters.NOSPracticalCanListAdapter;
import com.tsa.exam.database.EvaluateDB;
import com.tsa.exam.model.NOSPracticalModel;

import java.util.ArrayList;

public class NOSPracticalQusActivity extends AppCompatActivity {

    private ListView listView;
    NOSPracticalCanListAdapter practicalCanListAdapter;
    private String selectedBatchID;
    private String canLoginID;
    private Bundle bundle;
    private TextView assID;
    private ArrayList<NOSPracticalModel> nosPracticalModelArrayList;
    private EvaluateDB evaluateDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_qus);
        bundle=getIntent().getExtras();

        evaluateDB = EvaluateDB.getInstance(NOSPracticalQusActivity.this);

        if(bundle!=null)
        {
            selectedBatchID=bundle.getString("selected_batch_id");
            canLoginID=bundle.getString("c_login_id");
            Log.e("canLoginID",""+canLoginID);
        }

        assID=(TextView)findViewById(R.id.ass_id);

        listView=(ListView) findViewById(R.id.root1);

        listView.setAdapter(practicalCanListAdapter);

        getQuestionsFromDb();
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "You are not allowed to go back", Toast.LENGTH_SHORT).show();
    }

    public void getQuestionsFromDb(){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                nosPracticalModelArrayList = (ArrayList<NOSPracticalModel>) evaluateDB.getNosDao().getCanByBatch(selectedBatchID);
                practicalCanListAdapter=new NOSPracticalCanListAdapter(NOSPracticalQusActivity.this,nosPracticalModelArrayList);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                listView.setAdapter(practicalCanListAdapter);
            }
        }.execute();

    }

}
