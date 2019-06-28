package com.tsa.exam;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.tsa.exam.Utill.GLOBAL;
import com.tsa.exam.Utill.TimeConverter;
import com.tsa.exam.database.DatabaseHandler;
import com.tsa.exam.model.AddedCandidatesModel;
import com.tsa.exam.model.CandidateImages;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class AadharValidationActivity extends AppCompatActivity {

    private int REQUEST_CAMERA = 0;
    private Uri imageUri;
    private Bitmap thumbnail;
    private int number = 0;
    private ImageView candidateImage;
    private ImageView aadharImage;
    private ImageView otherIDImage;

    private EditText aadharID;
    private EditText otherID;
    private EditText name;


    private String aadharImageSt = "";
    private String canImageSt = "";

    private String aadharTime;
    private String aadharDate;
    private String canTime;
    private String canDate;


    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aadhar_validation);
        init();
    }

    private void init() {
        candidateImage = (ImageView)findViewById(R.id.candidate_image);
        aadharImage  = (ImageView)findViewById(R.id.aadhar_image);
        otherIDImage  = (ImageView)findViewById(R.id.other_id_image);
        aadharID   = (EditText) findViewById(R.id.aadhar_number_et);
        otherID  = (EditText)findViewById(R.id.other_id_et);
        name  = (EditText)findViewById(R.id.candidate_name_et);
    }

    public void goToExaminationPage(View view) {
        validate();
    }

    public void capture1(View view) {
        number = 1;
        selectImage();
    }

    public void capture2(View view) {
        number = 2;
        selectImage();
        otherID.setEnabled(false);
        otherIDImage.setEnabled(false);
    }

    public void capture3(View view) {
        number = 3;
        selectImage();
        aadharImage.setEnabled(false);
        aadharID.setEnabled(false);
    }

    public void selectImage() {
        //cameraIntent();
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
        imageUri = getContentResolver().insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        intent.putExtra("android.intent.extras.CAMERA_FACING", 1);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == Activity.RESULT_OK) {
            try {
                thumbnail = MediaStore.Images.Media.getBitmap(
                        getContentResolver(), imageUri);

                switch (number) {
                    case 1:
                        candidateImage.setImageBitmap(thumbnail);
                        aadharDate = TimeConverter.getDateFromMilli();
                        aadharTime = TimeConverter.getCurrentDate();
                        getStringImage(thumbnail, 1);
                        break;
                    case 2:
                        canDate = TimeConverter.getDateFromMilli();
                        canTime = TimeConverter.getCurrentDate();
                        aadharImage.setImageBitmap(thumbnail);
                        getStringImage(thumbnail, 2);
                        break;
                    case 3:
                        canDate = TimeConverter.getDateFromMilli();
                        canTime = TimeConverter.getCurrentDate();
                        otherIDImage.setImageBitmap(thumbnail);
                        getStringImage(thumbnail, 2);
                        break;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    ////////////////////////////////////////////////////////
    public String getStringImage(Bitmap bmp, int seq) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 20, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        if (seq == 1) {
            canImageSt = encodedImage;
        } else {
            aadharImageSt = encodedImage;
        }
        return encodedImage;
    }

    ////////////////////////////
    public void validate() {
        DatabaseHandler databaseHandler = new DatabaseHandler(AadharValidationActivity.this);
        ArrayList<AddedCandidatesModel> addedCandidatesModels = databaseHandler.getCandidates();

        boolean loginFlag = false;

        for (int i = 0; i < addedCandidatesModels.size(); i++) {
            if (("" + aadharID.getText()).equals(addedCandidatesModels.get(i).getCandidateAadhar())) {
                if (!aadharImageSt.trim().equals("")) {
                    if (!canImageSt.trim().equals("")) {
                        loginFlag = true;
                        break;
                    } else {
                    }
                } else {

                }
            } else {

                }

            loginFlag = true;
            }

        if (loginFlag) {
            databaseHandler.addImage(new CandidateImages(GLOBAL.loginID, canImageSt, "" + aadharID.getText(), aadharImageSt,
                    aadharTime, aadharDate, canDate,canTime), AadharValidationActivity.this);
            //Toast.makeText(this, "SuccessFully Loged in", Toast.LENGTH_SHORT).show();
            android.app.AlertDialog dialog= new AlertDialog.Builder(AadharValidationActivity.this)
                    .setIcon(R.drawable.isdatlogo)
                    .setMessage("SuccessFully Loged in")
                    .setPositiveButton("OK", null)
                    .show();

            TextView textView = (TextView) dialog.findViewById(android.R.id.message);
            textView.setTextSize(30);
            startActivity(new Intent(AadharValidationActivity.this, TestActivity.class));

        } else {
            //Toast.makeText(this, "Failed to Login", Toast.LENGTH_SHORT).show();
            android.app.AlertDialog dialog= new AlertDialog.Builder(AadharValidationActivity.this)
                    .setIcon(R.drawable.isdatlogo)
                    .setMessage("Failed to Login")
                    .setPositiveButton("OK", null)
                    .show();

            TextView textView = (TextView) dialog.findViewById(android.R.id.message);
            textView.setTextSize(30);


        }

    }

}
