package com.tsa.exam;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.tsa.exam.database.EvaluateDB;
import com.tsa.exam.model.NOSPracticalModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PracticalActivity extends AppCompatActivity {

    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical);
        spinner = (Spinner) findViewById(R.id.spinner);
        addSpinnerItems();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(PracticalActivity.this, SelectLoginActivity.class);
        startActivity(intent);
    }

    public void addSpinnerItems() {
          getBatchID(PracticalActivity.this);
           }

    public void practical(View view) {
        Intent intent = new Intent(PracticalActivity.this, NOSPracticalQusActivity.class);
        intent.putExtra("selected_batch_id",spinner.getSelectedItem().toString() );
        startActivity(intent);
    }

    public List<NOSPracticalModel> getBatchID(Context context) {
        final EvaluateDB evaluateDB = EvaluateDB.getInstance(context);
        final List<NOSPracticalModel> nosPracticalModels1 = new ArrayList() {
        };
        new AsyncTask<Void, Void, List<NOSPracticalModel>>() {
            @Override
            protected List<NOSPracticalModel> doInBackground(Void... params) {
                return evaluateDB.getNosDao().getBatchList();
            }

            @Override
            protected void onPostExecute(List<NOSPracticalModel> nosPracticalModels) {
                super.onPostExecute(nosPracticalModels);
                Log.e("Tile", nosPracticalModels.toString());
                nosPracticalModels1.addAll(nosPracticalModels);
                final List<String> list=new ArrayList<>();
                Set<String> set = new HashSet<String>();

                if (!nosPracticalModels1.isEmpty()) {
                    for (int i = 0; i < nosPracticalModels1.size(); i++)

                        set.add(nosPracticalModels1.get(i).getBatchId());
                }

                Enumeration e = Collections.enumeration(set);
                while (e.hasMoreElements()) {
                    list.add(e.nextElement().toString());
                }

                ArrayAdapter<String> adapter;
                adapter = new ArrayAdapter<String>(getApplicationContext(),
                        R.layout.spinner_item, list);
                spinner.setAdapter(adapter);

            }
        }.execute();
        /////////////////////////////////////////////////////////////////////////////
        return nosPracticalModels1;
    }

}
