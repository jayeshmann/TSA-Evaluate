package com.tsa.exam;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.tsa.exam.Utill.GLOBAL;
import com.tsa.exam.Utill.TimeConverter;
import com.tsa.exam.customView.GravityCompoundDrawable;
import com.tsa.exam.database.DatabaseHandler;

public class InstructionsActivity extends AppCompatActivity {


    private TextView examQT;
    private TextView examTime;

    private String noOfQt="";
    private String time="";

    private int instructionsID[]=new int[]{R.id.i1,R.id.instruction1,R.id.instruction2,R.id.instruction3,
            R.id.instruction4,R.id.instruction5,R.id.instruction6,R.id.instruction7,R.id.instruction8,
            R.id.instruction9,R.id.instruction10,R.id.instruction11,R.id.number_of_qt,R.id.instruction13,
            R.id.instruction14,R.id.instruction15,R.id.instruction16,R.id.instruction17,R.id.instruction18,
            R.id.instruction19,R.id.instruction20,R.id.instruction21,R.id.instruction22,R.id.instruction23,
            R.id.exam_timming};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);

        GLOBAL.cameraFlag=true;

        DatabaseHandler databaseHandler=new DatabaseHandler(InstructionsActivity.this);
        noOfQt=""+databaseHandler.getAllQuestions().size();
        if(!databaseHandler.getAllQuestions().isEmpty())
            time=TimeConverter.getDate(Long.parseLong(databaseHandler.getAllQuestions().get(0).getExamduration()));
        init();
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }

    private void init() {

        examTime = (TextView) findViewById(R.id.exam_timming);
        examQT = (TextView) findViewById(R.id.number_of_qt);

        examQT.setText("Question Paper contains " + noOfQt + " questions " +
                "\n" + "प्रश्न पत्र में " + noOfQt + " प्रश्न शामिल हैं।");

        examTime.setText("Timing of the exam " + time + "\n" +
                "परीक्षा का समय " + time + " होगा।");

        Drawable innerDrawable=null;

        if (android.os.Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT){
        innerDrawable=getResources().getDrawable(R.drawable.ic_brightness_1_black_24dp);
        } else{
            innerDrawable=getResources().getDrawable(R.drawable.dot);
        }

        GravityCompoundDrawable gravityDrawable = new GravityCompoundDrawable(innerDrawable);
        //NOTE: next 2 lines are important!
        innerDrawable.setBounds(0, 0, innerDrawable.getIntrinsicWidth(), innerDrawable.getIntrinsicHeight());
        gravityDrawable.setBounds(0, 0, innerDrawable.getIntrinsicWidth(), innerDrawable.getIntrinsicHeight());

        for(int i=0;i<instructionsID.length;i++) {
            TextView instruction=(TextView)findViewById(instructionsID[i]);
            instruction.setCompoundDrawables(gravityDrawable, null, null, null);
        }
    }


    public void goToValidation(View view)
    {
        startActivity(new Intent(InstructionsActivity.this,AadharValidationActivity.class));

    }
}

