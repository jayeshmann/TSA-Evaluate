package com.tsa.exam;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.tsa.exam.adapters.NOSPracSyncAdapter;
import com.tsa.exam.adapters.TestSyncAdaptar;

import com.tsa.exam.database.DatabaseHandler;
import com.tsa.exam.database.EvaluateDB;
import com.tsa.exam.model.NOSPracticalModel;
import com.tsa.exam.model.ResultModel;

import java.util.ArrayList;

public class Prac_syncActivity extends Activity {

    private ArrayList<ResultModel> resultModelArrayList;
    //   private ListView theoryResultListView;
    private ListView practicalResultListView;
    private TestSyncAdaptar testSyncAdaptar;
    private DatabaseHandler databaseHandler;
    private ImageView home;

    private NOSPracSyncAdapter nosPracSyncAdapter;
    private ArrayList<NOSPracticalModel> nosPracticalModelArrayList;
    private Context context;
    EvaluateDB evaluateDB;

//    RecyclerView recycler_videos;
//    Video_Adapter video_adapter;
//    LinearLayoutManager layoutManager;
//    ArrayList<VideoModel> video_list = new ArrayList<>();

    private Button text_practical;
    private Button text_prac_video_sync;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prac_sync);

        context=Prac_syncActivity.this;
        evaluateDB = EvaluateDB.getInstance(context);

        text_practical = (Button)findViewById(R.id.text_prac_video_sync) ;

        practicalResultListView = (ListView) findViewById(R.id.sync_practical);

//        recycler_videos = (RecyclerView)findViewById(R.id.recycler_videos);
//
//        layoutManager=new LinearLayoutManager(this);
//        recycler_videos.setLayoutManager(layoutManager);
//
//
//        video_adapter=new Video_Adapter(this,video_list);
//        recycler_videos.setAdapter(video_adapter);
//        recycler_videos.setFocusable(false);




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




        home=(ImageView)findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Prac_syncActivity.this,SelectLoginActivity.class));
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






