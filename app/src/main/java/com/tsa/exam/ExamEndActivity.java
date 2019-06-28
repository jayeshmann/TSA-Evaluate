package com.tsa.exam;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.tsa.exam.Utill.GLOBAL;
import com.tsa.exam.database.DatabaseHandler;
import com.tsa.exam.model.FeedbackModel;


public class ExamEndActivity extends AppCompatActivity {
    private String[] feedBack = new String[10];
    private boolean attempted = true;

    private View card1;
    private View card2;
    private View card3;
    private View card4;
    private View card5;
    private View card6;
    private View card7;
    private View card8;
    private View card9;

    private TextView feedbackQTv1;
    private TextView feedbackQTv2;
    private TextView feedbackQTv3;
    private TextView feedbackQTv4;
    private TextView feedbackQTv5;
    private TextView feedbackQTv6;
    private TextView feedbackQTv7;
    private TextView feedbackQTv8;
    private TextView feedbackQTv9;

    private RadioButton yesRb1;
    private RadioButton yesRb2;
    private RadioButton yesRb3;
    private RadioButton yesRb4;
    private RadioButton yesRb5;
    private RadioButton yesRb6;
    private RadioButton yesRb7;

    private RadioButton opyRb1;
    private RadioButton opyRb2;
    private RadioButton opyRb3;

    private RadioButton opyRb4;
    private RadioButton opyRb5;
    private RadioButton opyRb6;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_end);
        init();
    }

    @Override
    protected void onStart() {
        super.onStart();
        GLOBAL.cameraFlag = false;
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }

    private void init() {

        card1 = findViewById(R.id.card1);

        card2 = findViewById(R.id.card2);

        card3 = findViewById(R.id.card3);

        card4 = findViewById(R.id.card4);

        card5 = findViewById(R.id.card5);

        card6 = findViewById(R.id.card6);

        card7 = findViewById(R.id.card7);

        card8 = findViewById(R.id.card8);

        card9 = findViewById(R.id.card9);

        feedbackQTv1 = card1.findViewById(R.id.feedback_q_tv);
        feedbackQTv2 = card2.findViewById(R.id.feedback_q_tv);
        feedbackQTv3 = card3.findViewById(R.id.feedback_q_tv);
        feedbackQTv4 = card4.findViewById(R.id.feedback_q_tv);
        feedbackQTv5 = card5.findViewById(R.id.feedback_q_tv);
        feedbackQTv6 = card6.findViewById(R.id.feedback_q_tv);
        feedbackQTv7 = card7.findViewById(R.id.feedback_q_tv);
        feedbackQTv8 = card8.findViewById(R.id.feedback_q_tv);
        feedbackQTv9 = card9.findViewById(R.id.feedback_q_tv);

        opyRb1 = card8.findViewById(R.id.opy_rb1);
        opyRb2 = card8.findViewById(R.id.opy_rb2);
        opyRb3 = card8.findViewById(R.id.opy_rb3);

        opyRb4 = card9.findViewById(R.id.opy_rb1);
        opyRb5 = card9.findViewById(R.id.opy_rb2);
        opyRb6 = card9.findViewById(R.id.opy_rb3);

        yesRb1 = card1.findViewById(R.id.yes_rb);
        yesRb2 = card2.findViewById(R.id.yes_rb);
        yesRb3 = card3.findViewById(R.id.yes_rb);
        yesRb4 = card4.findViewById(R.id.yes_rb);
        yesRb5 = card5.findViewById(R.id.yes_rb);
        yesRb6 = card6.findViewById(R.id.yes_rb);
        yesRb7 = card7.findViewById(R.id.yes_rb);

        feedbackQTv1.setText(getResources().getText(R.string.feedback_1));

        feedbackQTv2.setText(getResources().getText(R.string.feedback_2));

        feedbackQTv3.setText(getResources().getText(R.string.feedback_3));

        feedbackQTv4.setText(getResources().getText(R.string.feedback_4));

        feedbackQTv5.setText(getResources().getText(R.string.feedback_5));

        feedbackQTv6.setText(getResources().getText(R.string.feedback_6));

        feedbackQTv7.setText(getResources().getText(R.string.feedback_7));

        feedbackQTv8.setText(getResources().getText(R.string.feedback_8));
        opyRb1.setText(getResources().getText(R.string.eassy));
        opyRb2.setText(getResources().getText(R.string.mediam));
        opyRb3.setText(getResources().getText(R.string.hard));

        feedbackQTv9.setText(getResources().getText(R.string.feedback_9));
        opyRb4.setText(getResources().getText(R.string.satisfactory));
        opyRb5.setText(getResources().getText(R.string.good));
        opyRb6.setText(getResources().getText(R.string.excellent));
    }

    public void submitExam(View view) {
        attempted = true;
        validation();
    }

    public void validation() {

        if (yesRb1.isChecked()) {
            feedBack[0] = "yes";

        } else if (yesRb1.isChecked()) {
            feedBack[0] = "no";
        } else {
            feedBack[0] = "";
            attempted = false;
        }

        if (yesRb2.isChecked()) {
            feedBack[1] = "yes";
        } else if (yesRb2.isChecked()) {
            feedBack[1] = "no";

        } else {
            feedBack[1] = "";
            attempted = false;
        }

        if (yesRb3.isChecked()) {
            feedBack[2] = "yes";
        } else if (yesRb3.isChecked()) {
            feedBack[2] = "no";

        } else {
            feedBack[2] = "";
            attempted = false;
        }

        if (yesRb4.isChecked()) {
            feedBack[3] = "yes";
        } else if (yesRb4.isChecked()) {
            feedBack[3] = "no";
        } else {
            feedBack[3] = "";
            attempted = false;
        }


        if (yesRb5.isChecked()) {
            feedBack[4] = "yes";
        } else if (yesRb5.isChecked()) {
            feedBack[4] = "no";

        } else {
            feedBack[4] = "";
            attempted = false;
        }


        if (yesRb6.isChecked()) {
            feedBack[5] = "yes";
        } else if (yesRb6.isChecked()) {
            feedBack[5] = "no";

        } else {
            feedBack[5] = "";
            attempted = false;
        }


        if (yesRb7.isChecked()) {
            feedBack[6] = "yes";
        } else if (yesRb7.isChecked()) {
            feedBack[6] = "no";

        } else {
            feedBack[6] = "";
            attempted = false;
        }

        if (opyRb1.isChecked()) {
            feedBack[7] = "" + opyRb1.getText();
        } else if (opyRb2.isChecked()) {
            feedBack[7] = "" + opyRb2.getText();

        } else if (opyRb3.isChecked()) {
            feedBack[7] = "" + opyRb3.getText();
        } else {
            feedBack[7] = "";
            attempted = false;
        }

        if (opyRb4.isChecked()) {
            feedBack[8] = "" + opyRb4.getText();
        } else if (opyRb5.isChecked()) {
            feedBack[8] = "" + opyRb5.getText();

        } else if (opyRb6.isChecked()) {
            feedBack[8] = "" + opyRb6.getText();

        } else {
            feedBack[8] = "";
            attempted = false;
        }

        feedBack[9] = "" + feedbackQTv9.getText();
        attempted = true;

        if (attempted) {

            FeedbackModel feedbackModels = new FeedbackModel();
            feedbackModels.setCanID(GLOBAL.loginID);
            feedbackModels.setFeedback1(feedBack[0]);
            feedbackModels.setFeedback2(feedBack[1]);
            feedbackModels.setFeedback3(feedBack[2]);
            feedbackModels.setFeedback4(feedBack[3]);
            feedbackModels.setFeedback5(feedBack[4]);
            feedbackModels.setFeedback6(feedBack[5]);
            feedbackModels.setFeedback7(feedBack[6]);
            feedbackModels.setFeedback8(feedBack[7]);
            feedbackModels.setFeedback9(feedBack[8]);
            EditText feedBack = findViewById(R.id.feedback_q_et);
            feedbackModels.setFeedback10(""+feedBack.getText());

            DatabaseHandler databaseHandler = new DatabaseHandler(ExamEndActivity.this);
            databaseHandler.addFeedBack(feedbackModels);

            //Toast.makeText(this, "You Attempted All Questions", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ExamEndActivity.this, SelectLoginActivity.class);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();

        } else {
            new android.app.AlertDialog.Builder(ExamEndActivity.this)
                    .setIcon(R.drawable.logo)
                    .setMessage("Please give your feedback on the below asked questions before submitting\n" +
                            "सबमिट करने से पहले कृपया नीचे दिए गए प्रश्नों पर अपनी प्रतिक्रिया दें")
                    .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .show();
        }

    }
}
