package com.tsa.exam;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;
import android.view.View;
import android.widget.TextView;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
           }

           public void startPinning(View view)
           {
               if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                   startLockTask();
               }
               else
               {
                   startActivity(new Intent(TestActivity.this,MainActivity.class));
               }
           }

    public void startExam(View view)
    {
        startPin();
    }

    public boolean isAppInLockTaskMode() {
        ActivityManager activityManager;

        activityManager = (ActivityManager)
                this.getSystemService(Context.ACTIVITY_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // For SDK version 23 and above.
            return activityManager.getLockTaskModeState()
                    != ActivityManager.LOCK_TASK_MODE_NONE;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return activityManager.isInLockTaskMode();
        }

        return false;
    }

    public void startPin() {
        if (isAppInLockTaskMode()) {
        startActivity(new Intent(TestActivity.this,MainActivity.class));        }
        else{
            //Toast.makeText(context, json.getString("msg"), Toast.LENGTH_SHORT).show();
            @SuppressLint("RestrictedApi") final ContextThemeWrapper con = new ContextThemeWrapper(TestActivity.this, R.style.AlertS);
            android.app.AlertDialog dialog=new AlertDialog.Builder(con)
                    .setIcon(R.drawable.logo)
                    .setMessage(getResources().getString(R.string.pin_instruction2))
                    .setPositiveButton("OK", null)
                    .show();

            TextView textView = dialog.findViewById(android.R.id.message);
            textView.setTextSize(18);
        }
    }


}
