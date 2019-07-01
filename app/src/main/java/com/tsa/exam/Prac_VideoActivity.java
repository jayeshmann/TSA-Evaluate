package com.tsa.exam;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.tsa.exam.adapters.Prac_VideoAdapter;
import com.tsa.exam.database.DatabaseHandler;
import com.tsa.exam.model.ResultModel;

import java.util.ArrayList;

public class Prac_VideoActivity extends AppCompatActivity {

  private Prac_VideoAdapter prac_videoAdapter;
    private ListView sync_video_practical;
    private ArrayList<ResultModel> pracvideoarraylist;
    //   Arraylist for result
    private ArrayList<ResultModel> resultModelArrayList = new ArrayList<>();

    private DatabaseHandler databaseHandler;
    private ImageView home;
    private Button prac_video;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prac__video);
        prac_video = (Button)findViewById(R.id.prac_video);

        sync_video_practical = (ListView)findViewById(R.id.sync_video_practical);
/*

        home=(ImageView)findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Prac_VideoActivity.this,SelectLoginActivity.class));
            }
        });
*/

        prac_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sync_video_practical.setVisibility(View.VISIBLE);


                databaseHandler = new DatabaseHandler(getApplicationContext());
                //////////Refresh Practical Result///////////////
                refresh();

            }
        });







    }

    private void refresh() {

        if(pracvideoarraylist!=null) {
            pracvideoarraylist.clear();
        }

        pracvideoarraylist = databaseHandler.getAllResult();

        prac_videoAdapter = new Prac_VideoAdapter(pracvideoarraylist, Prac_VideoActivity.this);
        sync_video_practical.setAdapter(prac_videoAdapter);
    }


}

