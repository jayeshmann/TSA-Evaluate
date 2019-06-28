package com.tsa.exam;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.tsa.exam.adapters.NOSPracSyncAdapter;
import com.tsa.exam.adapters.TestSyncAdaptar;
import com.tsa.exam.adapters.Video_Adapter;
import com.tsa.exam.database.DatabaseHandler;
import com.tsa.exam.database.EvaluateDB;
import com.tsa.exam.model.NOSPracticalModel;
import com.tsa.exam.model.ResultModel;

import java.util.ArrayList;

public class Prac_syncActivity extends Activity {

    Video_Adapter video_adapter;
    LinearLayoutManager layoutManager;
    //   private ListView theoryResultListView;
    private ListView practicalResultListView;
    private ArrayList<ResultModel> resultModelArrayList = new ArrayList<>();
    private TestSyncAdaptar testSyncAdaptar;
    private DatabaseHandler databaseHandler;
    private ImageView home;

    private NOSPracSyncAdapter nosPracSyncAdapter;
    private ArrayList<NOSPracticalModel> resultModels;
    private Context context;
    EvaluateDB evaluateDB;
    private ListView sync_videolistview;
    private ArrayList<NOSPracticalModel> nosPracticalModelArrayList = new ArrayList<>();

    private Button text_practical;
    private Button btn_videosync;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prac_sync);

        context = Prac_syncActivity.this;
        evaluateDB = EvaluateDB.getInstance(context);
        sync_videolistview = findViewById(R.id.sync_video);
        text_practical = findViewById(R.id.text_prac_video_sync);
        practicalResultListView = findViewById(R.id.sync_practical);

        btn_videosync = findViewById(R.id.btn_videosync);
        btn_videosync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sync_videolistview.setVisibility(View.VISIBLE);
                databaseHandler = new DatabaseHandler(getApplicationContext());

                //////////Refresh Theory Result///////////////
                refresh();
                //////////////////////////////////////////////
//                getQuestionsFromDb();

            }
        });

        // resultModelArrayList = databaseHandler.getAllResult();
        video_adapter = new Video_Adapter(nosPracticalModelArrayList, Prac_syncActivity.this);
        sync_videolistview.setAdapter(video_adapter);

        text_practical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                practicalResultListView.setVisibility(View.VISIBLE);
                databaseHandler = new DatabaseHandler(getApplicationContext());

                //////////Refresh Theory Result///////////////
                refresh();
                //////////////////////////////////////////////
                getQuestionsFromDb();

            }
        });


        home = findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Prac_syncActivity.this, SelectLoginActivity.class));
            }
        });


        }



//        text_practical.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                theory.setTextColor(getResources().getColor(R.color.white));
////                practical.setTextColor(getResources().getColor(R.color.green));
//                practicalResultListView.setVisibility(View.VISIBLE);
////                theoryResultListView.setVisibility(View.GONE);
//            }
//        });



    public void refresh()
    {
        if(resultModelArrayList!=null) {
            resultModelArrayList.clear();
        }
        resultModelArrayList = databaseHandler.getAllResult();
        testSyncAdaptar = new TestSyncAdaptar(resultModelArrayList, Prac_syncActivity.this);
        practicalResultListView.setAdapter(testSyncAdaptar);
    }


    public void getQuestionsFromDb(){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                nosPracticalModelArrayList = (ArrayList<NOSPracticalModel>) evaluateDB.getNosDao().getCanList();
                nosPracSyncAdapter=new NOSPracSyncAdapter(context,nosPracticalModelArrayList);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                practicalResultListView.setAdapter(nosPracSyncAdapter);

            }
        }.execute();

    }


}






