package com.tsa.exam;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;

import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tsa.exam.Utill.GLOBAL;
import com.tsa.exam.database.DatabaseHandler;
import com.tsa.exam.databinding.ActivitySelectLoginBinding;
import com.tsa.exam.model.PracticalQuestionModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import android.Manifest;
import android.util.Size;
import android.graphics.Matrix;
import java.util.concurrent.TimeUnit;
import io.realm.Realm;
import io.realm.RealmResults;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;


public class SelectLoginActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {

    ActivitySelectLoginBinding binding;
    private Realm mRealm;
    private DatabaseHandler databaseHandler;
    SelectLoginActivity slActivity;

    private static final String TAG = "SelectLoginActivity";
    private static final int All_PERM = 123;
    private static final String[] permissions = {
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.GET_ACCOUNTS,
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.SYSTEM_ALERT_WINDOW,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_NETWORK_STATE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_select_login);
        binding.setActivity(this);
        Realm.init(getApplicationContext());
        mRealm = Realm.getDefaultInstance();
        databaseHandler=new DatabaseHandler(SelectLoginActivity.this);

        //Ask Permissions
        permissionTask();

        if(GLOBAL.assname!=null)
            addAssesser();
    }

    @AfterPermissionGranted(All_PERM)
    public void permissionTask() {
        if (hasAppPermissions()) {

            // Have permissions, do the thing!
            //Toast.makeText(this, "TODO: things", Toast.LENGTH_LONG).show();
        } else {
            // Ask for both permissions
            EasyPermissions.requestPermissions(
                    this,
                    getString(R.string.rationale_ask),
                    All_PERM,
                    permissions);
        }
    }

    private boolean hasAppPermissions() {
        return EasyPermissions.hasPermissions(this, permissions);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        Log.d(TAG, "onPermissionsGranted:" + requestCode + ":" + perms.size());
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        Log.d(TAG, "onPermissionsDenied:" + requestCode + ":" + perms.size());

        // (Optional) Check whether the user denied any permissions and checked "NEVER ASK AGAIN."
        // This will display a dialog directing them to enable the permission in app settings.
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }



    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }

    public void candidateLogin() {
        if(databaseHandler.getQuestionsSize()>0) {
            Toast.makeText(this, "Candidate LogIn", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(SelectLoginActivity.this, LoginActivity.class);
            intent.putExtra("selection_id", "2");
            startActivity(intent);
        }
        else
        {

            //Toast.makeText(context, json.getString("msg"), Toast.LENGTH_SHORT).show();
            @SuppressLint("RestrictedApi") final ContextThemeWrapper con = new ContextThemeWrapper(SelectLoginActivity.this, R.style.AlertS);

            new AlertDialog.Builder(con)
                    .setIcon(R.drawable.logonew)
                    .setMessage(getResources().getString(R.string.login_popup1_eng) +
                            "\n" + getResources().getString(R.string.login_popup1_hin) )
                    .setPositiveButton("OK", null)
                    .show();
        }
    }

    public void assessorLogin() {
        ///////////////////////////////////////////////////////////////////
        Toast.makeText(this, "Assessor LogIn", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(SelectLoginActivity.this, LoginActivity.class);
        intent.putExtra("selection_id", "1");
        startActivity(intent);
        ////////////////////////////////////////////////////////////////////
    }

    public void syncLogin() {
        Intent intent = new Intent(SelectLoginActivity.this, LoginActivity.class);
        intent.putExtra("selection_id", "3");
        startActivity(intent);
    }

    public void addToRelam(ArrayList<PracticalQuestionModel> practicalQuestionModelArrayList) {

        if(!mRealm.isInTransaction())
            mRealm.beginTransaction();

        RealmResults<PracticalQuestionModel> pQM = mRealm.where(PracticalQuestionModel.class).findAll();


        if (!pQM.isEmpty()) {
            for (int i = pQM.size() - 1; i >= 0; i--) {
                pQM.get(i).deleteFromRealm();
            }
        }
        for (int i = 0; i < practicalQuestionModelArrayList.size(); i++) {
            PracticalQuestionModel practicalQuestionModel = mRealm.createObject(PracticalQuestionModel.class);
            practicalQuestionModel.setActivity(practicalQuestionModelArrayList.get(i).getActivity());
            practicalQuestionModel.setAssessorId(practicalQuestionModelArrayList.get(i).getAssessorId());
            practicalQuestionModel.setBatchId(practicalQuestionModelArrayList.get(i).getBatchId());
            practicalQuestionModel.setBatchNumber(practicalQuestionModelArrayList.get(i).getBatchNumber());
            practicalQuestionModel.setCandidateLoginId(practicalQuestionModelArrayList.get(i).getCandidateLoginId());
            practicalQuestionModel.setExamId(practicalQuestionModelArrayList.get(i).getExamId());
            practicalQuestionModel.setExamName(practicalQuestionModelArrayList.get(i).getExamName());
            practicalQuestionModel.setJobrole(practicalQuestionModelArrayList.get(i).getJobrole());
            practicalQuestionModel.setNos(practicalQuestionModelArrayList.get(i).getNos());
            practicalQuestionModel.setNosMarks(practicalQuestionModelArrayList.get(i).getNosMarks());
            practicalQuestionModel.setPasskey(practicalQuestionModelArrayList.get(i).getPasskey());
            practicalQuestionModel.setPc(practicalQuestionModelArrayList.get(i).getPc());
            practicalQuestionModel.setPcMarks(practicalQuestionModelArrayList.get(i).getPcMarks());
            practicalQuestionModel.setQid(practicalQuestionModelArrayList.get(i).getQid());
            practicalQuestionModel.setQuestion(practicalQuestionModelArrayList.get(i).getQuestion());
            practicalQuestionModel.setSector(practicalQuestionModelArrayList.get(i).getSector());
            practicalQuestionModel.setCandidateName(practicalQuestionModelArrayList.get(i).getCandidateName());
        }

        mRealm.commitTransaction();

    }

    public void addAssesser() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GLOBAL.BASE_URL + "practical/practicle_login_assessor.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {

                            JSONObject json = new JSONObject(s);
                            int status = json.getInt("status");
                            String msg = json.getString("msg");
                            Toast.makeText(SelectLoginActivity.this, msg, Toast.LENGTH_SHORT).show();
                            //TODO
                            if (status == 0) {
                                JSONArray qJsonArray = json.getJSONArray("data");

                                ArrayList<PracticalQuestionModel> pracLoginAssModelArrayList = new ArrayList<>();

                                for (int i = 0; i < qJsonArray.length(); i++) {

                                    PracticalQuestionModel practicalQuestionModel = new PracticalQuestionModel();

                                    practicalQuestionModel.setExamId(qJsonArray.getJSONObject(i).getString("exam_id"));
                                    practicalQuestionModel.setExamName(qJsonArray.getJSONObject(i).getString("exam_name"));
                                    practicalQuestionModel.setPasskey(qJsonArray.getJSONObject(i).getString("passkey"));

                                    practicalQuestionModel.setAssessorId(qJsonArray.getJSONObject(i).getString("assessor_id"));
                                    practicalQuestionModel.setBatchId(qJsonArray.getJSONObject(i).getString("batch_id"));
                                    practicalQuestionModel.setBatchNumber(qJsonArray.getJSONObject(i).getString("batch_number"));

                                    practicalQuestionModel.setQid(qJsonArray.getJSONObject(i).getString("qid"));
                                    practicalQuestionModel.setSector(qJsonArray.getJSONObject(i).getString("sector"));
                                    practicalQuestionModel.setJobrole(qJsonArray.getJSONObject(i).getString("jobrole"));

                                    practicalQuestionModel.setNos(qJsonArray.getJSONObject(i).getString("nos"));
                                    practicalQuestionModel.setActivity(qJsonArray.getJSONObject(i).getString("activity")
                                            +"\n"+qJsonArray.getJSONObject(i).getString("activity_language"));
                                    practicalQuestionModel.setNosMarks(qJsonArray.getJSONObject(i).getString("nos_marks"));

                                    practicalQuestionModel.setPc(qJsonArray.getJSONObject(i).getString("pc"));
                                    practicalQuestionModel.setPcMarks(qJsonArray.getJSONObject(i).getString("pc_marks"));
                                    practicalQuestionModel.setQuestion(qJsonArray.getJSONObject(i).getString("question")
                                            +"\n"+qJsonArray.getJSONObject(i).getString("other_language"));

                                    practicalQuestionModel.setCandidateLoginId(qJsonArray.getJSONObject(i).getString("candidate_login_id"));
                                    practicalQuestionModel.setCandidateName(qJsonArray.getJSONObject(i).getString("candidate_name"));

                                    pracLoginAssModelArrayList.add(practicalQuestionModel);

                                }
                                addToRelam(pracLoginAssModelArrayList);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {}
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                //Creating parameters
                Map<String, String> params = new Hashtable<String, String>();

                params.put("ass_username", GLOBAL.assname);
                params.put("ass_pwd", GLOBAL.assPassword);

                //returning parameters
                return params;
            }
        };

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(stringRequest);
    }

    public void practicalLogin() {
        startActivity(new Intent(SelectLoginActivity.this,PracticalActivity.class));
    }

    public void practicalSyncLogin(){
        startActivity(new Intent(SelectLoginActivity.this,TestSyncActivity.class));
    }
}


