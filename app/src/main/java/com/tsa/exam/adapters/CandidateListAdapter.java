package com.tsa.exam.adapters;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tsa.exam.CandidateListActivity;
import com.tsa.exam.R;
import com.tsa.exam.Utill.GLOBAL;
import com.tsa.exam.Utill.TimeConverter;
import com.tsa.exam.database.DatabaseHandler;
import com.tsa.exam.model.AddedCandidatesModel;
import com.tsa.exam.model.CandidateDetailsModel;
import com.tsa.exam.model.QuestionModel;
import com.tsa.exam.phoneProperties.MACIP;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.Map;
import java.util.TimeZone;

/**
 * Created by Akhil Tripathi on 30-06-2017.
 */

public class CandidateListAdapter extends RecyclerView.Adapter<CandidateListAdapter.ViewHolder> {

    private ArrayList<CandidateDetailsModel> candidateDetailsModelArrayList;
    private Context context;
    private BroadcastReceiver mDLCompleteReceiver;

    public CandidateListAdapter(ArrayList<CandidateDetailsModel> candidateDetailsModelArrayList, Context context) {
        this.candidateDetailsModelArrayList = candidateDetailsModelArrayList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.candidate_list_card, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        CandidateDetailsModel candidateDetailsModel = candidateDetailsModelArrayList.get(position);
        holder.candiadeUserId.setText(candidateDetailsModel.getCandidateLoginID());
        holder.candidateName.setText(candidateDetailsModel.getCandidateName());
        holder.batchID.setText(GLOBAL.batchNumber);

        if (candidateDetailsModel.getCanStaus().equals("1")) {
            holder.addCandidate.setBackground(context.getResources().getDrawable(R.drawable.grid_bg_green));
            holder.addCandidate.setText("Added");
        } else {
            holder.addCandidate.setBackground(context.getResources().getDrawable(R.drawable.rounded_corner));
            holder.addCandidate.setText("Add");
        }

        holder.addCandidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insaertCandidate(position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return candidateDetailsModelArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView candiadeUserId;
        TextView candidateName;
        Button addCandidate;
        TextView batchID;

        public ViewHolder(View view) {
            super(view);
            candiadeUserId = (TextView) view.findViewById(R.id.candiade_user_id);
            candidateName = (TextView) view.findViewById(R.id.candidate_name);
            batchID = (TextView) view.findViewById(R.id.batch_id);
            addCandidate = (Button) view.findViewById(R.id.add_candidate);
        }
    }

    public void insaertCandidate(final int position) {
        //Showing the progress dialog
        final ProgressDialog progress = new ProgressDialog(context);
        progress.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GLOBAL.BASE_URL + "add_candidate_v3.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {

                            JSONObject json = new JSONObject(s);
                            @SuppressLint("RestrictedApi") final ContextThemeWrapper con = new ContextThemeWrapper(context, R.style.AlertS);
                            ArrayList<AddedCandidatesModel> candidateListForTab = new ArrayList<>();

                            parseJson("" + json.getJSONArray("qstion_list"));

                            JSONArray jsonArray = json.getJSONArray("data");
                            //////////////////////////////////////////////////////////////////////////////////////////////
                            String msg=json.getString("msg");
                            if(jsonArray.length()==0||jsonArray==null)
                            {
                                msg="Candidate Is not Added Due To Slow Network";
                            }
                            android.app.AlertDialog dialog = new AlertDialog.Builder(con)
                                    .setIcon(R.drawable.isdatlogo)
                                    .setMessage(msg)
                                    .setPositiveButton("OK", null)
                                    .show();

                            //Refreshing the candidate list
                            ((CandidateListActivity) context).refresh(null);

                            TextView textView = (TextView) dialog.findViewById(android.R.id.message);
                            textView.setTextSize(30);

                            /////////////////////////////////////////////////////////////////////////////////////////////
                            for (int i = 0; i < jsonArray.length(); i++) {
                                candidateListForTab.add(new AddedCandidatesModel(jsonArray.getJSONObject(i).getString("candidate_id"), json.getString("passkey"),
                                        jsonArray.getJSONObject(i).getString("candidate_name"), "1", "0", TimeConverter.getDateFromMilli(), "0", "unlocked", "0", "0", "0"));
                            }

                            DatabaseHandler databaseHandler = new DatabaseHandler(context);
                            databaseHandler.addCandidates(candidateListForTab);
                            progress.dismiss();

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
                        progress.dismiss();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                //Creating parameters
                Map<String, String> params = new Hashtable<String, String>();

                //Adding parameters
                params.put("exam_id", GLOBAL.examID);
                params.put("batch_id", candidateDetailsModelArrayList.get(position).getBatchID());
                params.put("candidate_id", candidateDetailsModelArrayList.get(position).getId());
                params.put("mac_address", new MACIP().macGetter(context));
                params.put("ass_id", GLOBAL.assessorID);

                //returning parameters
                return params;
            }
        };

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        //Adding request to the queue
        requestQueue.add(stringRequest);
    }

    public void parseJson(String myJson) throws JSONException {
        JSONArray question = new JSONArray(myJson);


        ArrayList<QuestionModel> questionModelArrayList = new ArrayList<>();
        for (int i = 0; i < question.length(); i++) {

            QuestionModel questionModel = new QuestionModel();
            String engQuestion = question.getJSONObject(i).getString("question_eng");
            String hQuestion = question.getJSONObject(i).getString("question_hindi");

            JSONArray jsonArray1 = question.getJSONObject(i).getJSONArray("option_eng");
            JSONArray jsonArray2 = question.getJSONObject(i).getJSONArray("option_hindi");

            String image = question.getJSONObject(i).getString("image");
            String optionImage = question.getJSONObject(i).getString("option_image");
            String imagePath = question.getJSONObject(i).getString("image_path");
            String marks = question.getJSONObject(i).getString("marks");
            String examDuration = question.getJSONObject(i).getString("examduration");

            String sector = question.getJSONObject(i).getString("sector") + " Sector Skill council";
            String jobrole = question.getJSONObject(i).getString("jobrole");
            String examId = question.getJSONObject(i).getString("exam_id");
            String batchId = question.getJSONObject(i).getString("batch_id");

            String cId = question.getJSONObject(i).getString("cid");
            String cName = question.getJSONObject(i).getString("cname");
            String assessorId = question.getJSONObject(i).getString("assessor_id");
            String nos = question.getJSONObject(i).getString("nos");

            String correctAns = question.getJSONObject(i).getString("correct_ans");
            String cLoginID = question.getJSONObject(i).getString("candidate_login_id");
            String pc = question.getJSONObject(i).getString("pc");
            String pcMarks = question.getJSONObject(i).getString("pc_marks");

            String shuffleOrder = question.getJSONObject(i).getString("shuffle_order");
            String questioID = question.getJSONObject(i).getString("qid");

            //String currAnsText=question.getJSONObject(i).getString("correct_ans_text");

            questionModel.setQuestion(engQuestion + "\n" + hQuestion);
            final String BASE_URL = "http://tsassessors.in/";
            if (optionImage.equals("yes")) {
                questionModel.setOptionA(getImage(BASE_URL + jsonArray1.get(0).toString()));
                questionModel.setOptionB(getImage(BASE_URL + jsonArray1.get(1).toString()));
                questionModel.setOptionC(getImage(BASE_URL + jsonArray1.get(2).toString()));
                questionModel.setOptionD(getImage(BASE_URL + jsonArray1.get(3).toString()));
            } else {
                questionModel.setOptionA(jsonArray1.get(0).toString() + "\n*" + jsonArray2.get(0).toString());
                questionModel.setOptionB(jsonArray1.get(1).toString() + "\n*" + jsonArray2.get(1).toString());
                questionModel.setOptionC(jsonArray1.get(2).toString() + "\n*" + jsonArray2.get(2).toString());
                questionModel.setOptionD(jsonArray1.get(3).toString() + "\n*" + jsonArray2.get(3).toString());
            }
            if (image.equals("yes")) {
               questionModel.setImage_path(getImage(BASE_URL + imagePath));
            }

            questionModel.setChecked(0);
            questionModel.setQuestionStatus("unanswered");
            questionModel.setqId(i);
            questionModel.setImage(image);
            questionModel.setOptionImage(optionImage);
            questionModel.setMarks(marks);
            questionModel.setExamduration(examDuration);

            questionModel.setSector(sector);
            questionModel.setJobrole(jobrole);
            questionModel.setExam_id(examId);
            questionModel.setBatch_id(batchId);
            questionModel.setCid(cId);
            questionModel.setCname(cName);
            questionModel.setAssessor_id(assessorId);
            questionModel.setNos(nos);
            questionModel.setCorrect_ans(correctAns);
            questionModel.setCandidate_login_id(cLoginID);

            questionModel.setPc("" + pc);
            questionModel.setPc_marks(pcMarks);
            questionModel.setShuffle(shuffleOrder);

            questionModel.setQuestionID(questioID);

            questionModelArrayList.add(questionModel);
            GLOBAL.NoOfQuestions = questionModelArrayList.size();
        }
        DatabaseHandler databaseHandler = new DatabaseHandler(context);
        databaseHandler.addQuestions(questionModelArrayList, context);
    }

    public String getImage(String url) {
        final String[] path = {""};
        DownloadManager.Request request = null;

        try {
            request = new DownloadManager.Request(Uri.parse(url));
        } catch (IllegalArgumentException e) {
        }
                /* allow mobile and WiFi downloads */
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
        request.setTitle("DM Example");
        request.setDescription("Downloading file");

                /* we let the user see the download in a notification */
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
        String[] allowedTypes = {"png", "jpg", "jpeg", "gif", "webp"};
        String suffix = url.substring(url.lastIndexOf(".") + 1).toLowerCase();
        if (!Arrays.asList(allowedTypes).contains(suffix)) {
            for (String s : allowedTypes) {

            }
        }

        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        long time = cal.getTimeInMillis();
        String imageName = "" + time + "." + suffix;
                /* set the destination path for this download */
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS +
                File.separator + "exam_db", imageName);
                /* allow the MediaScanner to scan the downloaded file */
        request.allowScanningByMediaScanner();
        final DownloadManager dm = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
                /* this is our unique download id */
        final long DL_ID = dm.enqueue(request);

                /* get notified when the download is complete */
        mDLCompleteReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                        /* our download */
                if (DL_ID == intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1L)) {
                    /* get the path of the downloaded file */
                    DownloadManager.Query query = new DownloadManager.Query();
                    query.setFilterById(DL_ID);
                    Cursor cursor = dm.query(query);
                    if (!cursor.moveToFirst()) {
                        return;
                    }

                    if (cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))
                            != DownloadManager.STATUS_SUCCESSFUL) {
                        return;
                    }

                    path[0] = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
                }
            }
        };
        //mDLCompleteReceiver.abortBroadcast();
        /* register receiver to listen for ACTION_DOWNLOAD_COMPLETE action */
        context.registerReceiver(mDLCompleteReceiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        return imageName;

    }
}
