package com.tsa.exam;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tsa.exam.Utill.GLOBAL;
import com.tsa.exam.adapters.CandidateListAdapter;
import com.tsa.exam.database.DatabaseHandler;
import com.tsa.exam.model.CandidateDetailsModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

public class CandidateListActivity extends AppCompatActivity {

    private RecyclerView candidateListView;
    private CandidateListAdapter candidateListAdapter;
    private ArrayList<CandidateDetailsModel> candidatesDetailsArrayList=new ArrayList<>();

    private DatabaseHandler databaseHandler;

    private Bundle bundle;
    private  String batchID;
    private  String batchNumber;
    private TextView batchIDTV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate_list);
        databaseHandler=new DatabaseHandler(CandidateListActivity.this);

        bundle=getIntent().getExtras();

        if (bundle!=null)
        {
            batchID=bundle.getString("batch_id");
            batchNumber=bundle.getString("batch_number");
        }

        //TODO clearing Exam Table
       // databaseHandler.clearTable();

        init();
        candidateList(batchID);

    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        startActivity(new Intent(CandidateListActivity.this,SelectLoginActivity.class));
    }

    private void init() {
        candidateListView=(RecyclerView)findViewById(R.id.candidate_list);
           }

    public void candidateList(final String batchID) {
        //Showing the progress dialog
       // final ProgressDialog progressBar=new ProgressDialog(getApplicationContext());
        //progressBar.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GLOBAL.BASE_URL+"candidate_Id_list.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {

                            JSONObject json=new JSONObject(s);
                            int status=json.getInt("status");
                            if(status==0)
                            {
                                JSONArray jsonArray=json.getJSONArray("msg");
                                candidatesDetailsArrayList.clear();
                                for(int i=0;i<jsonArray.length();i++)
                                {
                                    CandidateDetailsModel candidatesDetails=new CandidateDetailsModel();
                                    JSONObject localJson=jsonArray.getJSONObject(i);
                                    candidatesDetails.setId(localJson.getString("id"));
                                    candidatesDetails.setBatchID(localJson.getString("batch_id"));
                                    candidatesDetails.setCandidateLoginID(localJson.getString("candidate_login_id"));
                                    candidatesDetails.setCandidateName(localJson.getString("candidate_name"));
                                    candidatesDetails.setEnrollmentNumber(localJson.getString("enrollment_number"));
                                    candidatesDetails.setAadharCardID(localJson.getString("aadhar_card_id"));
                                    candidatesDetails.setCandidateUserID(localJson.getString("candidate_user_id"));
                                    candidatesDetails.setCanStaus(localJson.getString("cand_status"));
                                    candidatesDetailsArrayList.add(candidatesDetails);
                                }
                                candidateListAdapter=new CandidateListAdapter(candidatesDetailsArrayList,CandidateListActivity.this);
                                candidateListView.setHasFixedSize(true);
                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                                candidateListView.setLayoutManager(mLayoutManager);
                                candidateListView.setItemAnimator(new DefaultItemAnimator());
                                candidateListView.setAdapter(candidateListAdapter);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Showing toast
                        Toast.makeText(CandidateListActivity.this, "Some issue in loading" + volleyError, Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                //Creating parameters
                Map<String, String> params = new Hashtable<String, String>();

                //Adding parameters
                params.put("batch_id", batchID);

                //returning parameters
                return params;
            }
        };

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(stringRequest);
    }

    public void refresh(View view)
    {
        candidateList(batchID);
    }
}
