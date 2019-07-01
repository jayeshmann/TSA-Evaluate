package com.tsa.exam.adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tsa.exam.NOSPracticalActivity;
import com.tsa.exam.Prac_syncActivity;
import com.tsa.exam.R;
import com.tsa.exam.database.DatabaseHandler;
import com.tsa.exam.database.EvaluateDB;
import com.tsa.exam.model.NOSPracticalModel;
import com.tsa.exam.model.ResultModel;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Video_Adapter extends BaseAdapter {
    //    private ArrayList<NOSPracticalModel> resultModels;
    Context context;
    EvaluateDB evaluateDB;
    private ArrayList<NOSPracticalModel> pracResultModels;
    private ArrayList<NOSPracticalModel> nosPracticalModelArrayList;


    public Video_Adapter(ArrayList<NOSPracticalModel> pracResultModels, Context context) {
        this.pracResultModels = pracResultModels;
        this.context = context;
        evaluateDB = EvaluateDB.getInstance(context);


    }

//    public Video_Adapter(Context context, ArrayList<NOSPracticalModel> resultModels) {
//        this.context = context;
//        this.resultModels = resultModels;
//        evaluateDB = EvaluateDB.getInstance(context);
//    }


    @Override
    public int getCount() {
        return pracResultModels.size();
    }

    @Override
    public Object getItem(int position) {
        return pracResultModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
           final ViewHolder holder;
        final NOSPracticalModel nosPracticalModel=pracResultModels.get(position);
        if (view != null) {
            holder = (ViewHolder) view.getTag();
        } else {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = mInflater.inflate(R.layout.sync_card, parent, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }


        holder.canID.setText(pracResultModels.get(position).getCandidateLoginId());

        holder.sync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                getQuestionsFromDb(nosPracticalModel.getCandidateLoginId());

                // rashmi
                holder.sync.setVisibility(View.GONE);
                holder.test_synced.setVisibility(View.VISIBLE);

            }
        });
        return view;
    }

    public void submit() {

    /* /int i = 0; i < nosPracticalModelArrayList.size(); i++) {
              NOSPracticalModel localModel = nosPracticalModelArrayList.get(i);
              question_id = question_id + "*" + localModel.getQid();
              nos = nos + "*" + localModel.getNos();

              ////////////////////////////////////////////////////////////////////////////////////////
              if (localModel.getStepObtMarks1() == 1) {
                  step1_marks = step1_marks + "*" + localModel.getStep1Marks();
                  totalMarks1 = totalMarks1 + Integer.parseInt(localModel.getStep1Marks());
              } else {
                  step1_marks = step1_marks + "*" + 0;
                  totalMarks1 = totalMarks1 + 0;
              }
              ////////////////////////////////////////////////////////////////////////////////////////
              if (localModel.getStepObtMarks2() == 1) {
                  step2_marks = step2_marks + "*" + localModel.getStep2Marks();
                  totalMarks2 = totalMarks2 + Integer.parseInt(localModel.getStep2Marks());
              } else {
                  step2_marks = step2_marks + "*" + 0;
                  totalMarks2 = totalMarks2 + 0;
              }
              ////////////////////////////////////////////////////////////////////////////////////////
              if (localModel.getStepObtMarks3() == 1) {
                  step3_marks = step3_marks + "*" + localModel.getStep3Marks();
                  totalMarks3 = totalMarks3 + Integer.parseInt(localModel.getStep3Marks());
              } else {
                  step3_marks = step3_marks + "*" + 0;
                  totalMarks3 = totalMarks3 + 0;
              }
              ////////////////////////////////////////////////////////////////////////////////////////
              if (localModel.getStepObtMarks4() == 1) {
                  step4_marks = step4_marks + "*" + localModel.getStep4Marks();
                  totalMarks4 = totalMarks4 + Integer.parseInt(localModel.getStep4Marks());
              } else {
                  step4_marks = step4_marks + "*" + 0;
                  totalMarks4 = totalMarks4 + 0;
              }
              ////////////////////////////////////////////////////////////////////////////////////////
              if (localModel.getStepObtMarks5() == 1) {
                  totalMarks5 = totalMarks5 + Integer.parseInt(localModel.getStep5Marks());
                  step5_marks = step5_marks + "*" + localModel.getStep5Marks();
              } else {
                  step5_marks = step5_marks + "*" + 0;
                  totalMarks5 = totalMarks5 + 0;
              }
              ////////////////////////////////////////////////////////////////////////////////////////
              if (localModel.getStepObtMarks6() == 1) {
                  step6_marks = step6_marks + "*" + localModel.getStep6Marks();
                  totalMarks6 = totalMarks6 + Integer.parseInt(localModel.getStep6Marks());
              } else {
                  step6_marks = step6_marks + "*" + 0;
                  totalMarks6 = totalMarks6 + 0;
              }
          }

          ////////////////////////////////////////////////////////////////////////////////////////
          Log.e("step1_marks", "" + totalMarks1);
          Log.e("step2_marks", "" + totalMarks2);
          Log.e("step3_marks", "" + totalMarks3);
          Log.e("step4_marks", "" + totalMarks4);
          Log.e("step5_marks", "" + totalMarks5);
          Log.e("step6_marks", "" + totalMarks6);
          ////////////////////////////////////////////////////////////////////////////////////////

          Map<String, String> params = new Hashtable<String, String>();
          //Adding parameters
          params.put("exam_id", exam_id);
          params.put("batch_id", batch_id);
          params.put("candidate_id", candidate_id);
          params.put("nos", nos);
          params.put("question_id", question_id);
          params.put("total_marks", "" + totalMarks1 + "*" + totalMarks2 + "*" + totalMarks3 + "*" + totalMarks4 + "*" + totalMarks5 + "*" + totalMarks6);
          params.put("step1_marks", step1_marks);
          params.put("step2_marks", step2_marks);
          params.put("step3_marks", step3_marks);
          params.put("step4_marks", step4_marks);
          params.put("step5_marks", step5_marks);
          params.put("step6_marks", step6_marks);

          Log.e("params", params.toString());
*/
        submitResult();
    }

    public void submitResult() {
        //Showing the progress dialog
       /* final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("processing");
        progressDialog.show();




        StringRequest stringRequest = new StringRequest(Request.Method.POST, GLOBAL.BASE_URL + "practical/submit_nos_practical_result.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Log.e("Result", s);
                        JSONObject json = null;
                        progressDialog.dismiss();
                        try {
                            json = new JSONObject(s);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(context, "Some issue in creating Result", Toast.LENGTH_SHORT).show();
                        }
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
                return paramas;
            }
        };

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        //Adding request to the queue
        requestQueue.add(stringRequest);*/

        /////////////////////////////////extra///////////////////////////////\


        DatabaseHandler databaseHandler = new DatabaseHandler(context);
        //ResultModel localResultModel = databaseHandler.getResult(candidateID).get(0);
        String exam_id = nosPracticalModelArrayList.get(0).getExamId();
        String batch_id = nosPracticalModelArrayList.get(0).getBatchId();
        String candidate_i = nosPracticalModelArrayList.get(0).getCandidateLoginId();
        //String candidate_i = localResultModel.getCandidateLoginID();
        Toast.makeText(context, candidate_i, Toast.LENGTH_LONG).show();
        /// String batch_id = localResultModel.getBatchID();
        Toast.makeText(context, batch_id, Toast.LENGTH_LONG).show();
        // String exam_id = localResultModel.getExamID();
        String vid = "";
        //String path = Environment.getExternalStorageDirectory()+"//Movies//camera2VideoImage//"+ MainActivity.getFile();
        File file = null;
        try {


            file = new File(NOSPracticalActivity.getFileName());
            Toast.makeText(context, "" +
                    "Synced Success both Exam And Video", Toast.LENGTH_LONG).show();

            FileInputStream is = new FileInputStream(file);
            byte[] bytes = new byte[(int) file.length()];
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            int nRead;
            byte[] data = new byte[1024];
            while ((nRead = is.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }
            buffer.flush();
            bytes = buffer.toByteArray();
            vid = new String(bytes);

            addVideoSer(bytes, candidate_i, batch_id, exam_id);
        } catch (Exception e) {
            Toast.makeText(context, "Error in calling addvideo", Toast.LENGTH_LONG).show();
            // Toast.makeText(context,path,Toast.LENGTH_LONG).show();
        }
        databaseHandler.addVidDb(candidate_i, batch_id, exam_id, vid);


        ////////////////////////////////end///////////////////////////////


    }

    /////////////////////////////////////extra////////////////////////
    private void addVideoSer(byte[] bytes, final String candidate_id, final String batch_id, final String exam_id) {

        String uploadUrl = "http://tsassessors.in/ISDAT/evaluate_app/assessor_api/upload_candidate_pvideos.php";
        final RequestQueue rQueue;
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            os.write(bytes);
            os.close();
        } catch (IOException e) {
            Toast.makeText(context, "Unable to write file", Toast.LENGTH_LONG).show();
        }
        final String encodedFile = Base64.encodeToString(os.toByteArray(), Base64.DEFAULT);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, uploadUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(context, response, Toast.LENGTH_LONG).show();
                        Log.e("Test done", "Abcd");

//                        try {
//                            JSONObject json = new JSONObject(response);
//                            String status = json.getString("status");
//                            String message = json.getString("msg");
//                           if (status.equals("0")){
//
//
//                            }
//
//                        }
//                        catch (Exception e){
//                            e.printStackTrace();
//
//                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("candidate_id", candidate_id);
                params.put("batch_id", batch_id);
                params.put("exam_id", exam_id);
                params.put("video", encodedFile);

                return params;
            }

        };
        rQueue = Volley.newRequestQueue(context);
        rQueue.add(stringRequest);
    }

    class ViewHolder {
        TextView canID;
        ImageView sync;
        TextView test_synced;

        public ViewHolder(View view) {
            canID = view.findViewById(R.id.candidate1);
            sync = view.findViewById(R.id.sync1);

            test_synced = view.findViewById(R.id.test_synced);
        }
    }
    ///////////////////////////end//////////////////////////////////////

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

}










