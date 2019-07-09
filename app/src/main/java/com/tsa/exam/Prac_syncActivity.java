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
import androidx.recyclerview.widget.RecyclerView;

import com.tsa.exam.adapters.NOSPracSyncAdapter;
import com.tsa.exam.adapters.PracVideoAdapter;
import com.tsa.exam.adapters.TestSyncAdaptar;
/*
import com.tsa.exam.adapters.Video_Adapter;
*/
import com.tsa.exam.database.DatabaseHandler;
import com.tsa.exam.database.EvaluateDB;
import com.tsa.exam.model.NOSPracticalModel;
import com.tsa.exam.model.ResultModel;

import java.util.ArrayList;

/*
import com.tsa.exam.adapters.Video_Adapter;
*/

public class Prac_syncActivity extends Activity {

    /*Video_Adapter video_adapter;*/

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

    //  Recycler view
    /*private RecyclerView recylist;
     PracVideoAdapter pracVideoAdapter;
    LinearLayoutManager layoutManager;
*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prac_sync);

        context = Prac_syncActivity.this;
        evaluateDB = EvaluateDB.getInstance(context);
        text_practical = findViewById(R.id.text_prac_video_sync);
        practicalResultListView = findViewById(R.id.sync_practical);
///  Recyclerview code
       /* recylist = (RecyclerView)findViewById(R.id.recycler_prac);


        pracVideoAdapter=new PracVideoAdapter(this,nosPracticalModelArrayList);
        recylist.setAdapter(pracVideoAdapter);
        recylist.setFocusable(false);
*/
        btn_videosync = findViewById(R.id.btn_videosync);
        btn_videosync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent practical = new Intent(Prac_syncActivity.this, Prac_VideoActivity.class);
                startActivity(practical);

            }
        });


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






