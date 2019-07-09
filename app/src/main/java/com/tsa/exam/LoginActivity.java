package com.tsa.exam;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.tsa.exam.Utill.GLOBAL;
import com.tsa.exam.database.DatabaseHandler;
import com.tsa.exam.model.AddedCandidatesModel;
import com.tsa.exam.model.AssessorsLoginDetails;
import com.tsa.exam.network.NetworkCheck;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;

    private Bundle bundle;

    private Switch aSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_login);

        bundle = getIntent().getExtras();

        // Set up the login form.
        mEmailView = findViewById(R.id.email);

        /////////////////////////////////////////////////////////////////////////////
        if (bundle != null && bundle.get("selection_id").equals("2")) {
            DatabaseHandler databaseHandler = new DatabaseHandler(LoginActivity.this);
            ArrayList canList = new ArrayList();
            ArrayList<AddedCandidatesModel> addedCandidatesModels = databaseHandler.getCandidates();
            for (int i = 0; i < addedCandidatesModels.size(); i++) {
                canList.add(addedCandidatesModels.get(i).getCandidateID());
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_dropdown_item_1line, canList);

            // Set up the login form.
            mEmailView.setThreshold(0);
            mEmailView.setAdapter(adapter);
            /////////////////////////////////////////////////////////////////////////

        }

        aSwitch = findViewById(R.id.switch1);
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    mPasswordView.setInputType(InputType.TYPE_CLASS_TEXT);
                else
                    mPasswordView.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }
        });

        mPasswordView = findViewById(R.id.password);

        Button mEmailSignInButton = findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate("" + mEmailView.getText(), "" + mPasswordView.getText())) {
                    if (bundle != null) {
                        if (bundle.get("selection_id").equals("2")) {

                            offLineLogin("" + mEmailView.getText(), "" + mPasswordView.getText());

                        } else if (bundle.get("selection_id").equals("1")) {
                            if (NetworkCheck.isNetworkAvailable(LoginActivity.this)) {
                                assessorLogin(0, "" + mEmailView.getText(), "" + mPasswordView.getText());
                            } else {
                                Toast.makeText(LoginActivity.this, "No Network Available", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            if (NetworkCheck.isNetworkAvailable(LoginActivity.this)) {
                                assessorLogin(1, "" + mEmailView.getText(), "" + mPasswordView.getText());
                            } else {
                                Toast.makeText(LoginActivity.this, "No Network Available", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            }
        });

        final ImageButton userProfilePhoto = findViewById(R.id.user_profile_photo);

        //logo change

        Glide.with(LoginActivity.this).asBitmap()
                .load(R.drawable.teckutive)
                .centerCrop().into(new BitmapImageViewTarget(userProfilePhoto) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                userProfilePhoto.setImageDrawable(circularBitmapDrawable);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void assessorLogin(final int logInFor, final String userName, final String password) {
        //  Toast.makeText(LoginActivity.this, "Assessor Login", Toast.LENGTH_LONG).show();
        //Showing the progress dialog
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GLOBAL.BASE_URL + "login_assessor.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            JSONObject json = new JSONObject(s);
                            int status = json.getInt("status");
                            String message = json.getString("msg");
                            if (status == 0) {
                                GLOBAL.assname = userName;
                                GLOBAL.assPassword = password;
                                JSONArray jsonArray = json.getJSONArray("data");
                                if (json.getJSONArray("data") == null) {
                                    Toast.makeText(LoginActivity.this, "Exam is Already Conducted", Toast.LENGTH_SHORT).show();
                                }
                                ArrayList<AssessorsLoginDetails> assessorLoginDetailsArrayList = new ArrayList<>();

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    AssessorsLoginDetails assessorLoginDetails = new AssessorsLoginDetails();
                                    JSONObject localJson = jsonArray.getJSONObject(i);
                                    assessorLoginDetails.setExmID(localJson.getString("exam_id"));
                                    assessorLoginDetails.setBatchID(localJson.getString("batch_id"));
                                    assessorLoginDetails.setExmStartDate(localJson.getString("exam_start_date"));
                                    assessorLoginDetails.setExanEndDate(localJson.getString("exam_end_date"));
                                    assessorLoginDetails.setExmRewgDate(localJson.getString("exam_reg_date"));
                                    assessorLoginDetails.setBatchNumber(localJson.getString("batch_number"));
                                    assessorLoginDetails.setAssID(localJson.getString("ass_id"));

                                    assessorLoginDetailsArrayList.add(assessorLoginDetails);
                                }

                                GLOBAL.assessorID = assessorLoginDetailsArrayList.get(0).getAssID();
                                GLOBAL.examID = assessorLoginDetailsArrayList.get(0).getExmID();

                                //GLOBAL.batchID = assessorLoginDetailsArrayList.get(0).getBatchID();
                                GLOBAL.batchNumber = assessorLoginDetailsArrayList.get(0).getBatchNumber();
                                GLOBAL.examStartDate = assessorLoginDetailsArrayList.get(0).getExmStartDate();

                                GLOBAL.assessorLoginDetailsArrayList = assessorLoginDetailsArrayList;

                                if (logInFor == 0) {
                                    Intent intent = new Intent(LoginActivity.this, BatchDetailsActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Intent intent = new Intent(LoginActivity.this, TestSyncActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            } else {

                                @SuppressLint("RestrictedApi") final ContextThemeWrapper con = new ContextThemeWrapper(LoginActivity.this, R.style.AlertS);

                                android.app.AlertDialog dialog = new AlertDialog.Builder(con)
                                        .setIcon(R.drawable.teckutive)
                                        .setMessage(message)
                                        .setPositiveButton("OK", null)
                                        .show();

                                TextView textView = dialog.findViewById(android.R.id.message);
                                textView.setTextSize(30);
                                //Toast.makeText(LoginActivity.this, "Invalid userName or Password", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(LoginActivity.this, "Some issue in loading" + volleyError, Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                //Creating parameters
                Map<String, String> params = new Hashtable<String, String>();

                params.put("ass_username", "" + userName);
                params.put("ass_pwd", "" + password);

                //returning parameters
                return params;
            }
        };

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(stringRequest);
    }

        public void offLineLogin(String userName, String password) {
        DatabaseHandler databaseHandler = new DatabaseHandler(LoginActivity.this);
        ArrayList<AddedCandidatesModel> addedCandidatesModels = databaseHandler.getCandidates();



        boolean loginFlag = false;

        for (int i = 0; i < addedCandidatesModels.size(); i++) {

            String localCanID = addedCandidatesModels.get(i).getCandidateID();
            if (userName.equals(localCanID)) {
                if (password.equals(addedCandidatesModels.get(i).getCandidatePassKey())) {
                    loginFlag = true;
                }
            }

            if (loginFlag) {


                if (databaseHandler.getCandidatesByID(userName).get(0).getStatus().equals("1")) {
                    //TODO candidate Aadhar id is using to store candidate name
                    GLOBAL.candidateName = databaseHandler.getCandidatesByID(userName).get(0).getCandidateAadhar();
                    GLOBAL.loginID = databaseHandler.getCandidatesByID(userName).get(0).getCandidateID();
                    GLOBAL.NoOfQuestions = databaseHandler.getAllQuestions().size();
                    Intent intent = new Intent(LoginActivity.this, InstructionsActivity.class);
                    startActivity(intent);
                    finish();

                } else {
                    GLOBAL.candidateName = databaseHandler.getCandidatesByID(userName).get(0).getCandidateAadhar();
                    Intent intent = new Intent(LoginActivity.this, TestActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }
    }

    public boolean validate(String userName, String password) {
        if (userName.trim().equals("")) {
            //Toast.makeText(this, "SuccessFully Loged in", Toast.LENGTH_SHORT).show();
            android.app.AlertDialog dialog = new AlertDialog.Builder(LoginActivity.this)
                    .setIcon(R.drawable.teckutive)
                    .setMessage("Please Enter userName")
                    .setPositiveButton("OK", null)
                    .show();

            TextView textView = dialog.findViewById(android.R.id.message);
            textView.setTextSize(30);
            //Toast.makeText(this, "Please Enter userName", Toast.LENGTH_SHORT).show();
            return false;
        } else if (password.trim().equals("")) {
            //Toast.makeText(this, "Please Enter Password", Toast.LENGTH_SHORT).show();
            //Toast.makeText(this, "SuccessFully Loged in", Toast.LENGTH_SHORT).show();
            android.app.AlertDialog dialog = new AlertDialog.Builder(LoginActivity.this)
                    .setIcon(R.drawable.teckutive)
                    .setMessage("Please Enter Password")
                    .setPositiveButton("OK", null)
                    .show();

            TextView textView = dialog.findViewById(android.R.id.message);
            textView.setTextSize(30);
            return false;

        } else {
            return true;
        }
    }

}

