package com.tsa.exam;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tsa.exam.Utill.GLOBAL;
import com.tsa.exam.Utill.TimeConverter;
import com.tsa.exam.adapters.BatchDetailsAdapter;
import com.tsa.exam.database.EvaluateDB;
import com.tsa.exam.model.ExamDateModel;
import com.tsa.exam.model.NOSPracticalModel;
import com.tsa.exam.service.GPSTracker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import io.realm.Realm;

public class BatchDetailsActivity extends AppCompatActivity {
    private ListView listView;
    private BatchDetailsAdapter batchDetailsAdapter;
    private ArrayList<NOSPracticalModel> nosPracticalModelArrayList;
    EvaluateDB evaluateDB;
    Context context;

    private Realm mRealm;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_screen);
        init();

        Realm.init(getApplicationContext());
        mRealm = Realm.getDefaultInstance();

        batchDetailsAdapter = new BatchDetailsAdapter(GLOBAL.assessorLoginDetailsArrayList, BatchDetailsActivity.this);
        if (GLOBAL.assessorLoginDetailsArrayList != null)
            listView.setAdapter(batchDetailsAdapter);
    }

    private void init() {
        nosPracticalModelArrayList=new ArrayList<>();
        listView = (ListView) findViewById(R.id.candidate_list);
        context=BatchDetailsActivity.this;
        evaluateDB = EvaluateDB.getInstance(context);
    }

    public void enterLatLng(View view, int flag) {

        // GPSTracker class
        GPSTracker gps;
        // create class object
        gps = new GPSTracker(BatchDetailsActivity.this);

        // check if GPS enabled
        if (gps.canGetLocation()) {

            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();

            // \n is for new line

            Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
            if (flag == 0)
                insertLatLongIn("" + latitude, "" + longitude);
            else
                insertLatLongOut("" + latitude, "" + longitude);
            getAddress(latitude, longitude);

        } else {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }
    }

    public void insertLatLongIn(final String lat, final String lng) {
        //Showing the progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(BatchDetailsActivity.this);
        progressDialog.setTitle("processing");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GLOBAL.BASE_URL + "assessor_geo_tag.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {


                        try {
                            JSONObject json = new JSONObject(s);

                            String status = json.getString("status");

                            @SuppressLint("RestrictedApi") final ContextThemeWrapper con = new ContextThemeWrapper(BatchDetailsActivity.this, R.style.AlertS);
                            new AlertDialog.Builder(con)
                                    .setIcon(R.drawable.teckutive)
                                    .setMessage(json.getString("msg"))
                                    .setPositiveButton("OK", null)
                                    .show();

                            if (status.equals("0")) {
                                JSONArray msg = json.getJSONArray("msg");
                            }
                        } catch (Exception e) {

                        }

                        progressDialog.dismiss();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Showing toast
                        progressDialog.dismiss();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                //Creating parameters
                Map<String, String> params = new Hashtable<String, String>();
                //Adding parameters
                params.put("exam_id", GLOBAL.examID);
                params.put("ass_id", GLOBAL.assessorID);
                params.put("batch_id", GLOBAL.batchID);
                params.put("lat", lat);
                params.put("lng", lng);
                params.put("time", "" + TimeConverter.getDateFromMilli());
                params.put("geo_tag_status", "login");

                //returning parameters
                return params;
            }
        };

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(stringRequest);
    }

    public void insertLatLongOut(final String lat, final String lng) {
        //Showing the progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(BatchDetailsActivity.this);
        progressDialog.setTitle("processing");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GLOBAL.BASE_URL + "assessor_geo_tag.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {

                        progressDialog.dismiss();
                        try {
                            JSONObject json = new JSONObject(s);

                            String status = json.getString("status");

                            @SuppressLint("RestrictedApi") final ContextThemeWrapper con = new ContextThemeWrapper(BatchDetailsActivity.this, R.style.AlertS);
                            new AlertDialog.Builder(con)
                                    .setIcon(R.drawable.teckutive)
                                    .setMessage(json.getString("msg"))
                                    .setPositiveButton("OK", null)
                                    .show();

                            if (status.equals("0")) {
                                JSONArray msg = json.getJSONArray("msg");
                            }
                        } catch (Exception e) {

                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Showing toast
                        progressDialog.dismiss();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                //Creating parameters
                Map<String, String> params = new Hashtable<String, String>();
                //Adding parameters
                params.put("exam_id", GLOBAL.examID);
                params.put("ass_id", GLOBAL.assessorID);
                params.put("batch_id", GLOBAL.batchID);
                params.put("lat", lat);
                params.put("lng", lng);
                params.put("time", "" + TimeConverter.getDateFromMilli());
                params.put("geo_tag_status", "logout");

                //returning parameters
                return params;
            }
        };

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(stringRequest);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mRealm.beginTransaction();
        ExamDateModel startDate = mRealm.createObject(ExamDateModel.class);
        startDate.setExamDate(GLOBAL.examStartDate);
        mRealm.commitTransaction();
    }

    public void getAddress(double latitude, double longitude) {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            String knownName = addresses.get(0).getFeatureName();

            @SuppressLint("RestrictedApi") final ContextThemeWrapper con = new ContextThemeWrapper(BatchDetailsActivity.this, R.style.AlertS);
            new AlertDialog.Builder(con)
                    .setIcon(R.drawable.teckutive)
                    .setMessage(address + ":" + address)
                    .setPositiveButton("OK", null)
                    .show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void getPracticalQuestions(View view) {
        //Showing the progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("processing");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, GLOBAL.BASE_URL + "practical/practicle_login_assessor_v1.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {

                        JSONObject json = null;

                        try {
                            json = new JSONObject(s);
                            //String status = json.getString("status");
                            //String msg = json.getString("msg");
                            JSONArray jsonArray = json.getJSONArray("data");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                NOSPracticalModel nosPracticalModel = new NOSPracticalModel();
                                JSONObject localJson = jsonArray.getJSONObject(i);
                                nosPracticalModel.setExamId(localJson.getString("exam_id"));
                                nosPracticalModel.setExamName(localJson.getString("exam_name"));
                                nosPracticalModel.setPasskey(localJson.getString("passkey"));
                                nosPracticalModel.setAssessorId(localJson.getString("assessor_id"));
                                nosPracticalModel.setBatchId(localJson.getString("batch_id"));
                                nosPracticalModel.setBatchNumber(localJson.getString("batch_number"));
                                nosPracticalModel.setCandidateLoginId(localJson.getString("candidate_login_id"));
                                nosPracticalModel.setCandidateName(localJson.getString("candidate_name"));
                                nosPracticalModel.setQid(localJson.getString("qid"));
                                nosPracticalModel.setSector(localJson.getString("sector"));
                                nosPracticalModel.setJobrole(localJson.getString("jobrole"));
                                nosPracticalModel.setNos(localJson.getString("nos"));
                                nosPracticalModel.setNosMarks(localJson.getString("nos_marks"));

                                ///////////////////////////////////////////////////////////////////////////
                                nosPracticalModel.setStep1(localJson.getString("step1"));
                                nosPracticalModel.setStep1Marks(localJson.getString("step1_marks"));
                                nosPracticalModel.setStep2(localJson.getString("step2"));
                                nosPracticalModel.setStep2Marks(localJson.getString("step2_marks"));
                                nosPracticalModel.setStep3(localJson.getString("step3"));
                                nosPracticalModel.setStep3Marks(localJson.getString("step3_marks"));
                                nosPracticalModel.setStep4(localJson.getString("step4"));
                                nosPracticalModel.setStep4Marks(localJson.getString("step4_marks"));
                                nosPracticalModel.setStep5(localJson.getString("step5"));
                                nosPracticalModel.setStep5Marks(localJson.getString("step5_marks"));
                                nosPracticalModel.setStep6(localJson.getString("step6"));
                                nosPracticalModel.setStep6Marks(localJson.getString("step6_marks"));
                                nosPracticalModel.setQuestion(localJson.getString("question"));
                                ///////////////////////////////////////////////////////////////////////////////
                                nosPracticalModel.setStepObtMarks1(0);
                                nosPracticalModel.setStepObtMarks2(0);
                                nosPracticalModel.setStepObtMarks3(0);
                                nosPracticalModel.setStepObtMarks4(0);
                                nosPracticalModel.setStepObtMarks5(0);
                                nosPracticalModel.setStepObtMarks6(0);
                                ////////////////////////////////////////////////////////////////////////////////
                                nosPracticalModelArrayList.add(nosPracticalModel);
                                Log.e("nosPracticalModel", nosPracticalModel.toString());
                                addPracExam(nosPracticalModel);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(context, "Some issue in creating Result", Toast.LENGTH_SHORT).show();
                        }

                        progressDialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Showing toast
                        Toast.makeText(context, "" + volleyError, Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Creating parameters
                Map<String, String> params = new Hashtable<String, String>();
                //Adding parameters
                params.put("ass_pwd", "demo");
                params.put("ass_username", "demo@gmail.com");
                return params;
            }
        };

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        //Adding request to the queue
        requestQueue.add(stringRequest);
    }

    public void addPracExam(final NOSPracticalModel pracData) {
        new AsyncTask<Void, Void, NOSPracticalModel>() {
            @Override
            protected NOSPracticalModel doInBackground(Void... params) {
                evaluateDB.getNosDao().insertNosData(pracData);
                return pracData;
            }
        }.execute();
    }

}
