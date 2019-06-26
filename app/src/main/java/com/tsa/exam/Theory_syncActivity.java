package com.tsa.exam;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.tsa.exam.adapters.TestSyncAdaptar;

import com.tsa.exam.database.DatabaseHandler;
import com.tsa.exam.database.EvaluateDB;
import com.tsa.exam.model.ResultModel;

import java.util.ArrayList;

public class Theory_syncActivity extends Activity {
    private ArrayList<ResultModel> resultModelArrayList;
    private ListView theoryResultListView;
    private TestSyncAdaptar testSyncAdaptar;
    private DatabaseHandler databaseHandler;
    private ImageView home;


    private Context context;
    EvaluateDB evaluateDB;

    private Button text_theory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theory_sync);
        context=Theory_syncActivity.this;
        evaluateDB = EvaluateDB.getInstance(context);

        text_theory = (Button)findViewById(R.id.text_theory);

        theoryResultListView = (ListView) findViewById(R.id.sync_theory);



        text_theory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                theoryResultListView.setVisibility(View.VISIBLE);


            }
        });




        home=(ImageView)findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Theory_syncActivity.this,SelectLoginActivity.class));


            }
        });
        databaseHandler = new DatabaseHandler(Theory_syncActivity.this);

        refresh();


    }


    public void refresh() {
        if(resultModelArrayList!=null) {
            resultModelArrayList.clear();
        }
        resultModelArrayList = databaseHandler.getAllResult();
        testSyncAdaptar = new TestSyncAdaptar(resultModelArrayList, Theory_syncActivity.this);
        theoryResultListView.setAdapter(testSyncAdaptar);
    }




}
