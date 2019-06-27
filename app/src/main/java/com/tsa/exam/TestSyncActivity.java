package com.tsa.exam;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
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

public class TestSyncActivity extends AppCompatActivity {

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

    private Button practical;
    private Button theory;

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        startActivity(new Intent(TestSyncActivity.this, SelectLoginActivity.class));
    }

    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_sync);

        theory = findViewById(R.id.theory);
        theory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent theory = new Intent(TestSyncActivity.this, Theory_syncActivity.class);
                startActivity(theory);
            }
        });

        practical = findViewById(R.id.practical);
        practical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent practical = new Intent(TestSyncActivity.this, Prac_syncActivity.class);
                startActivity(practical);
            }
        });
//        context=TestSyncActivity.this;
//        evaluateDB = EvaluateDB.getInstance(context);
//        theory=(Button)findViewById(R.id.theory);
//        practical=(Button)findViewById(R.id.practical);
//
        home = findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TestSyncActivity.this,SelectLoginActivity.class));
            }
        });

    }
}