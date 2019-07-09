package com.tsa.exam.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;

import android.util.Base64;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tsa.exam.MainActivity;
import com.tsa.exam.R;
import com.tsa.exam.Theory_syncActivity;
import com.tsa.exam.Utill.GLOBAL;
import com.tsa.exam.database.DatabaseHandler;
import com.tsa.exam.model.FeedbackModel;
import com.tsa.exam.model.ResultModel;
import com.tsa.exam.model.TrackModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import io.realm.Realm;

/**
 * Created by Admin on 6/28/2019.
 */

public class VideosyncAdapter extends BaseAdapter {


    public VideosyncAdapter(ArrayList<ResultModel> videoModelArrayList, Context context) {
        this.videoModelArrayList = videoModelArrayList;
        this.context = context;
    }

    public static final String TAG = "ReqTag";

    private ArrayList<ResultModel> videoModelArrayList;
    private Context context;
    private boolean syced;


    @Override
    public int getCount() {
        return videoModelArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return videoModelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        final VideosyncAdapter.ViewHolder holder;
        if (view != null) {
            holder = (ViewHolder) view.getTag();
        } else {
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = mInflater.inflate(R.layout.sync_card, parent, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }

        holder.canID.setText(videoModelArrayList.get(position).getCandidateLoginID());

        holder.sync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String questionID = "";
                String timeVisited = "";
                String loginTime="";
                String logoutTime="";
                String staus="";



                Realm mRealm;
                Realm.init(context.getApplicationContext());
                mRealm = Realm.getDefaultInstance();

                String candidateID=videoModelArrayList.get(position).getCandidateLoginID();

                DatabaseHandler databaseHandler=new DatabaseHandler(context);

                if(databaseHandler.getCandidatesByID(candidateID).size()>=position+1) {
                    loginTime = databaseHandler.getCandidatesByID(candidateID).get(position).getLoginTime();
                    logoutTime = databaseHandler.getCandidatesByID(candidateID).get(position).getLogoutTime();
                }


                if (!mRealm.isInTransaction())
                    mRealm.beginTransaction();
                TrackModel trackModel = mRealm.where(TrackModel.class)
                        .equalTo("candidateID",candidateID).findFirst();

                if (trackModel != null) {
                    questionID = trackModel.getQuestionID();
                    timeVisited = trackModel.getTimeVisited();
                    staus=trackModel.getStatus();
                } else {
                    Toast.makeText(context, "Track Model is Null", Toast.LENGTH_SHORT).show();
                }
                mRealm.commitTransaction();

                submitResult(candidateID, questionID, timeVisited, loginTime, logoutTime, staus);

            }
        });

        return view;
    }

    class ViewHolder {
        TextView canID;
        ImageView sync;


        public ViewHolder(View view) {
            canID = (TextView) view.findViewById(R.id.candidate1);
            sync = (ImageView) view.findViewById(R.id.sync1);
        }
    }


    ////     api calling
    ////////////////////////extra//////////////
    private void addVideoSer(byte [] bytes, final String candidate_id, final String batch_id, final String exam_id) {

        String uploadUrl = "http://tsassessors.in/ISDAT/evaluate_app/assessor_api/upload_candidate_videos.php";
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
                        Log.e("Test done","Abcd");
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
                params.put("candidate_id",candidate_id );
                params.put("batch_id",batch_id);
                params.put("exam_id",exam_id);
                params.put("video", encodedFile);

                return params;
            }

        };
        rQueue = Volley.newRequestQueue(context);
        rQueue.add(stringRequest);
    }


    ///////////////////end///////////

    public void submitResult(final String candidateID, final String questionID, final String timeVisited, final String loginTime, final String logoutTime, final String status) {

        String cd = candidateID;


        //Showing the progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("processing");
        progressDialog.show();
        final DatabaseHandler databaseHandler=new DatabaseHandler(context);
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, GLOBAL.BASE_URL + "submit_exam.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {

                        //Toast.makeText(context, json.getString("msg"), Toast.LENGTH_SHORT).show();
                        @SuppressLint("RestrictedApi") final ContextThemeWrapper con = new ContextThemeWrapper(context, R.style.AlertS);

                        JSONObject json = null;

                        try {
                            json = new JSONObject(s);
                            String status = json.getString("status");
                            String msg = json.getString("msg");

                            new AlertDialog.Builder(con)
                                    .setIcon(R.drawable.teckutive)
                                    .setMessage(msg)
                                    .setPositiveButton("OK", null)
                                    .show();

                            if (status.equals("0")) {
                                //////////////////////////////////////////////////////
                                databaseHandler.clearTableCandidateAdded(candidateID);
                                databaseHandler.clearTableResult(candidateID);
                                /////////////////////////////////////////////////////
                                ((Theory_syncActivity)context).refresh();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(context, "Video Synced", Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Showing toast
                        Toast.makeText(context, "Slow Internet. Please sync result again"+volleyError, Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        syced=false;

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());

                DatabaseHandler databaseHandler = new DatabaseHandler(context);
                ResultModel localResultModel = databaseHandler.getResult(candidateID).get(0);

                String imageAadhar = databaseHandler.getImage(candidateID).get(0).getGetCanAadharImage();
                String imageCandidate = databaseHandler.getImage(candidateID).get(0).getGetCanAadharImage();
                String aadharTime = databaseHandler.getImage(candidateID).get(0).getAadahrTime();
                String aadharDate =  databaseHandler.getImage(candidateID).get(0).getAadahrDate();
                String canTime =  databaseHandler.getImage(candidateID).get(0).getCanTime();
                String canDate =  databaseHandler.getImage(candidateID).get(0).getCanDate();


                FeedbackModel feedbackModel = databaseHandler.getFeedBackByID(candidateID);

                String feedBack = feedbackModel.getFeedback6() + "_" + feedbackModel.getFeedback7() + "_" + feedbackModel.getFeedback8() + "_" +
                        feedbackModel.getFeedback1() + "_" + feedbackModel.getFeedback2() + "_" + feedbackModel.getFeedback3() + "_"
                        + feedbackModel.getFeedback4() + "_" + feedbackModel.getFeedback5() + "_" + feedbackModel.getFeedback9();
                String suggestion = feedbackModel.getFeedback10();
                String feedBackQID = "6_7_8_1_2_3_4_5_9";

                //Creating parameters
                Map<String, String> params = new Hashtable<String, String>();
                //Adding parameters
                params.put("exam_id", localResultModel.getExamID());
                params.put("batch_id", localResultModel.getBatchID());
                params.put("candidate_login_id", localResultModel.getCandidateLoginID());
                params.put("candidate_name", localResultModel.getCandidateName());
                params.put("assessor_id", localResultModel.getAssID());
                params.put("nos", localResultModel.getNos());
              /*  params.put("qid", localResultModel.getQuestionID());
                params.put("correct_ans", localResultModel.getCorrectAnswer());
                params.put("marks", localResultModel.getMarks());
                params.put("attempted_answer", localResultModel.getAttdemptedAnswer());
                params.put("obtained_marks", localResultModel.getObtainedMarks());
                params.put("question_review", localResultModel.getQuestionReview());
                params.put("shuffle_order", localResultModel.getShuffleOrder());
                params.put("pc", "" + localResultModel.getPc());
                params.put("track_qid", "" + questionID);
                params.put("track_time", "" + timeVisited);
                params.put("login_time", "" + loginTime);
                params.put("logout_time", "" + logoutTime);
                params.put("feedback", feedBack);
                params.put("suggestion", suggestion);*/
                params.put("feedback_que_id", "" + feedBackQID);
                params.put("adhar_image", "" + imageAadhar);
                params.put("adhar_img_date", "" + aadharDate);
                params.put("adhar_img_time", "" + aadharTime);
                params.put("can_login_image", ""+ imageCandidate);
                params.put("cand_img_date", "" + canDate);
                params.put("cand_img_time", "" + canTime);
                params.put("que_action",""+status);

                //returning parameters
                return params;
            }
        };

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        stringRequest.setTag(TAG);
        if (stringRequest != null) {
            requestQueue.cancelAll(TAG);
        }
        requestQueue.add(stringRequest);




        /////////////extra///////////


        ResultModel localResultModel = databaseHandler.getResult(candidateID).get(0);
        String candidate_i = localResultModel.getCandidateLoginID();
        Toast.makeText(context,candidate_i, Toast.LENGTH_LONG).show();
        String batch_id = localResultModel.getBatchID();
        Toast.makeText(context,batch_id, Toast.LENGTH_LONG).show();
        String exam_id = localResultModel.getExamID();
        String vid="";

        //String path = Environment.getExternalStorageDirectory()+"//Movies//camera2VideoImage//"+ MainActivity.getFile();
        File file = null;
        try {

            file = new File(MainActivity.getFile());
            Toast.makeText(context, " Exam Video Sync Success", Toast.LENGTH_LONG).show();

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

            addVideoSer(bytes,candidate_i,batch_id,exam_id);

        }catch(Exception e){

            Toast.makeText(context,"Error in calling addvideo", Toast.LENGTH_LONG).show();
            // Toast.makeText(context,path,Toast.LENGTH_LONG).show();
        }
        databaseHandler.addVidDb(candidate_i,batch_id,exam_id,vid);


        ////////////////end///////////////////


    }

}
