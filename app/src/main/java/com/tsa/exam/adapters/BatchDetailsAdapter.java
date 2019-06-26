package com.tsa.exam.adapters;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tsa.exam.BatchDetailsActivity;
import com.tsa.exam.CandidateListActivity;
import com.tsa.exam.NOSPracticalQusActivity;
import com.tsa.exam.R;
import com.tsa.exam.Utill.GLOBAL;
import com.tsa.exam.database.DBController;
import com.tsa.exam.database.EvaluateDB;
import com.tsa.exam.model.AssessorsLoginDetails;
import com.tsa.exam.model.NOSPracticalModel;
import com.tsa.exam.model.ShowCandidatesModel;
import com.tsa.exam.phoneProperties.MACIP;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;

/**
 * Created by Akhil Tripathi on 04-07-2017.
 */

public class BatchDetailsAdapter extends BaseAdapter {

    private ArrayList<AssessorsLoginDetails> assessorsLoginDetailsArrayList;
    private Context context;

    TableLayout tl;
    TableRow tr;
    TextView companyTV, valueTV;

    private ArrayList<ShowCandidatesModel> viewModelArrayList = new ArrayList<>();
    private ArrayList<ShowCandidatesModel> trackModelArrayList = new ArrayList<>();

    public BatchDetailsAdapter(ArrayList<AssessorsLoginDetails> assessorsLoginDetailsArrayList, Context context) {
        this.assessorsLoginDetailsArrayList = assessorsLoginDetailsArrayList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return assessorsLoginDetailsArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return assessorsLoginDetailsArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view != null) {
            holder = (ViewHolder) view.getTag();
        } else {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = mInflater.inflate(R.layout.batch_list_card, viewGroup, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }

        String start = assessorsLoginDetailsArrayList.get(i).getExmStartDate();
        String reg = assessorsLoginDetailsArrayList.get(i).getExmRewgDate();
        Date startDate=null;
        Date regDate=null;

        SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
        try {
             startDate = format.parse(start);
             regDate = format.parse(reg);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        ////////////////////////////////////////////////////////////////////////////////////////////
        holder.regDate.setText(new SimpleDateFormat("dd-mm-yyyy").format(regDate));
        holder.examAssement.setText(new SimpleDateFormat("dd-mm-yyyy").format(startDate));
        holder.assID.setText(assessorsLoginDetailsArrayList.get(i).getAssID());
        holder.batchID.setText(assessorsLoginDetailsArrayList.get(i).getBatchNumber());
        ////////////////////////////////////////////////////////////////////////////////////////////

        holder.addCandidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CandidateListActivity.class);
                intent.putExtra("batch_id", assessorsLoginDetailsArrayList.get(i).getBatchID());
                intent.putExtra("batch_num", assessorsLoginDetailsArrayList.get(i).getBatchNumber());
                GLOBAL.batchNumber=assessorsLoginDetailsArrayList.get(i).getBatchNumber();
                context.startActivity(intent);
            }
        });

        holder.viewCandidates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewCandidates(assessorsLoginDetailsArrayList.get(i).getBatchID(), new MACIP().macGetter(context));
            }
        });
        holder.trackCandidates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trackCandidates(assessorsLoginDetailsArrayList.get(i).getBatchID());
            }
        });
        holder.geoTagIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((BatchDetailsActivity)context).enterLatLng(null,0);
                GLOBAL.batchID=assessorsLoginDetailsArrayList.get(i).getBatchID();
            }
        });
        holder.geoTagOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((BatchDetailsActivity)context).enterLatLng(null,1);
                GLOBAL.batchID=assessorsLoginDetailsArrayList.get(i).getBatchID();
            }
        });

        holder.addPracticalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getQuestionsFromDb(assessorsLoginDetailsArrayList.get(i).getBatchID());
            }
        });

        return view;
    }

    class ViewHolder {
        TextView regDate;
        TextView examAssement;
        TextView assID;
        TextView batchID;
        Button addCandidate;
        Button viewCandidates;
        Button trackCandidates;
        Button addPracticalBtn;
        ImageView geoTagIn;
        ImageView geoTagOut;


        public ViewHolder(View view) {
            regDate = (TextView) view.findViewById(R.id.reg_date);
            examAssement = (TextView) view.findViewById(R.id.exam_assement);
            assID = (TextView) view.findViewById(R.id.ass_id);
            batchID = (TextView) view.findViewById(R.id.batch_id);
            addCandidate = (Button) view.findViewById(R.id.add_candidate);
            viewCandidates = (Button) view.findViewById(R.id.view_candidates);
            trackCandidates=(Button)view.findViewById(R.id.track_button);
            geoTagIn = (ImageView) view.findViewById(R.id.geotag_in);
            geoTagOut = (ImageView) view.findViewById(R.id.geotag_out);
            addPracticalBtn=(Button)view.findViewById(R.id.add_prac);
        }
    }

    public void addHeaders() {

        /** Create a TableRow dynamically **/
        tr = new TableRow(context);
        tr.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));

        /** Creating a TextView to add to the row **/
        TextView companyTV = new TextView(context);
        companyTV.setText("Candidate Name");
        companyTV.setTextSize(20);
        companyTV.setTextColor(Color.YELLOW);
        companyTV.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        companyTV.setPadding(5, 5, 5, 0);
        tr.addView(companyTV);  // Adding textView to tablerow.

        /** Creating another textview **/
        TextView valueTV = new TextView(context);
        valueTV.setText("Candidate ID");
        valueTV.setTextColor(Color.YELLOW);
        valueTV.setTextSize(20);
        valueTV.setPadding(5, 5, 5, 0);
        valueTV.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        tr.addView(valueTV); // Adding textView to tablerow.

        // Add the TableRow to the TableLayout
        tl.addView(tr, new TableLayout.LayoutParams(
                TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));

        // we are adding two textviews for the divider because we have two columns
        tr = new TableRow(context);
        tr.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        tr.setBackgroundColor(context.getResources().getColor(R.color.green));

               // Add the TableRow to the TableLayout
        tl.addView(tr, new TableLayout.LayoutParams(
                TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));
    }

    public void addData(ArrayList<ShowCandidatesModel> viewModelArrayList) {

        for (int i = 0; i < viewModelArrayList.size(); i++) {
            /** Create a TableRow dynamically **/
            tr = new TableRow(context);
            tr.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.FILL_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));

            tr.setBackgroundColor(context.getResources().getColor(R.color.green));

            /** Creating a TextView to add to the row **/
            companyTV = new TextView(context);
            companyTV.setText(viewModelArrayList.get(i).getCandidateName());
            companyTV.setTextColor(Color.WHITE);
            companyTV.setTextSize(20);

            companyTV.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            companyTV.setPadding(5, 5, 5, 5);
            tr.addView(companyTV);  // Adding textView to tablerow.

            /** Creating another textview **/
            valueTV = new TextView(context);
            valueTV.setText(viewModelArrayList.get(i).getLofinID());
            valueTV.setTextColor(Color.WHITE);
            valueTV.setTextSize(20);
            valueTV.setPadding(5, 5, 5, 5);
            valueTV.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            tr.addView(valueTV); // Adding textView to tablerow.

            // Add the TableRow to the TableLayout
            tl.addView(tr, new TableLayout.LayoutParams(
                    TableRow.LayoutParams.FILL_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
        }
    }

    public void showBookingDialogue(ArrayList<ShowCandidatesModel> showCandidatesModel) {
        // TODO Create custom dialog
        final Dialog dialog = new Dialog(context);

        //for no Title bar
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.added_candidate_layout);
        dialog.setCancelable(true);

        tl = (TableLayout) dialog.findViewById(R.id.maintable);

        addHeaders();
        addData(showCandidatesModel);

        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        dialog.show();

    }

    public void viewCandidates(final String batchID, final String macAddress) {

        //Showing the progress dialog
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GLOBAL.BASE_URL + "view_reg_candidate_list.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {

                            JSONObject json = new JSONObject(s);

                            String status = json.getString("status");

                            if (status.equals("0")) {
                                JSONArray msg = json.getJSONArray("msg");
                                viewModelArrayList.clear();
                                for (int i = 0; i < msg.length(); i++) {
                                    String candidateName=msg.getJSONObject(i).getString("candidate_name");
                                    String candidateLoginID=msg.getJSONObject(i).getString("candidate_login_id");
                                    ShowCandidatesModel showCandidatesModel=new ShowCandidatesModel();
                                    showCandidatesModel.setCandidateName(candidateName);
                                    showCandidatesModel.setLofinID(candidateLoginID);
                                    viewModelArrayList.add(showCandidatesModel);
                                }
                                showBookingDialogue(viewModelArrayList);
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
                        Toast.makeText(context, "Some issue in loading" + volleyError, Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                //Creating parameters
                Map<String, String> params = new Hashtable<String, String>();

                params.put("batch_id", "" + batchID);
                params.put("mac_address", "" + macAddress);

                //returning parameters
                return params;
            }
        };

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        //Adding request to the queue
        requestQueue.add(stringRequest);
    }

    public void trackCandidates(final String batchID) {

        //Showing the progress dialog
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GLOBAL.BASE_URL + "track_candidate.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {

                            JSONObject json = new JSONObject(s);

                            String status = json.getString("status");

                            if (status.equals("0")) {
                                JSONArray msg = json.getJSONArray("msg");
                                trackModelArrayList.clear();
                                for (int i = 0; i < msg.length(); i++) {
                                    String candidateName=msg.getJSONObject(i).getString("candidate_name");
                                    String candidateLoginID=msg.getJSONObject(i).getString("candidate_login_id");
                                    ShowCandidatesModel showCandidatesModel=new ShowCandidatesModel();
                                    showCandidatesModel.setCandidateName(candidateName);
                                    showCandidatesModel.setLofinID(candidateLoginID);
                                    trackModelArrayList.add(showCandidatesModel);
                                }
                                showBookingDialogue(trackModelArrayList);
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
                        Toast.makeText(context, "Some issue in loading" + volleyError, Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                //Creating parameters
                Map<String, String> params = new Hashtable<String, String>();

                params.put("batch_id", "" + batchID);

                //returning parameters
                return params;
            }
        };

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        //Adding request to the queue
        requestQueue.add(stringRequest);
    }

    public void getPracticalQuestions() {
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
                                nosPracticalModel.setExamStatus("0");
                                ////////////////////////////////////////////////////////////////////////////////
                                //nosPracticalModelArrayList.add(nosPracticalModel);
                                Log.e("nosPracticalModel",nosPracticalModel.toString());
                                DBController dbController=new DBController();
                                dbController.addPracExam(nosPracticalModel,context);
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
                params.put("ass_username", GLOBAL.assname);
                params.put("ass_pwd", GLOBAL.assPassword);
                return params;
            }
        };

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Adding request to the queue
        requestQueue.add(stringRequest);

    }

    public void getQuestionsFromDb(final String selectedBatchID){
        final ArrayList<NOSPracticalModel>[] nosPracticalModelArrayList = new ArrayList[1];
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                EvaluateDB evaluateDB = EvaluateDB.getInstance(context);
                nosPracticalModelArrayList[0] = (ArrayList<NOSPracticalModel>) evaluateDB.getNosDao().getCanByBatch(selectedBatchID);

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                if(nosPracticalModelArrayList[0].isEmpty())
                {
                    getPracticalQuestions();
                }
                else
                {
                    Toast.makeText(context, "Practical Questions Are Already Added", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();

    }
}

