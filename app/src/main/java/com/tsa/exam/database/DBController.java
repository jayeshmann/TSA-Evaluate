package com.tsa.exam.database;

import android.content.Context;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.util.Log;

import com.tsa.exam.NOSPracticalActivity;
import com.tsa.exam.R;
import com.tsa.exam.Utill.TimeConverter;
import com.tsa.exam.model.NOSPracticalModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

/**
 * Created by RajkumarMann on 28-03-2018.
 */

public class DBController {
    EvaluateDB evaluateDB;

    public void addPracExam(final NOSPracticalModel pracData, Context context) {
        evaluateDB = EvaluateDB.getInstance(context);

        //////////////////////////////////////////////////////////////////////
        new AsyncTask<Void, Void, NOSPracticalModel>() {
            @Override
            protected NOSPracticalModel doInBackground(Void... params) {
                evaluateDB.getNosDao().insertNosData(pracData);
                return pracData;
            }
        }.execute();
    }
    ////////////////////////////////////////////////////////////////////////////

    //////////////////////////////////////////////////////////////////////////////
    public List<NOSPracticalModel> getBatchID(Context context) {
        evaluateDB = EvaluateDB.getInstance(context);
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
            }
        }.execute();
        /////////////////////////////////////////////////////////////////////////////
        return nosPracticalModels1;
    }
}
