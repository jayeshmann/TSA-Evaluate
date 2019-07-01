package com.tsa.exam;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.tsa.exam.adapters.VideosyncAdapter;
import com.tsa.exam.database.DatabaseHandler;
import com.tsa.exam.database.EvaluateDB;
import com.tsa.exam.model.ResultModel;

import java.util.ArrayList;

public class VideoActivity extends AppCompatActivity {

    private ArrayList<ResultModel> videoModelArrayList;
    //   private ListView theoryResultListView;
    private ListView videoListView;
    private VideosyncAdapter videoSyncAdaptar;
    private DatabaseHandler databaseHandler;
    private ImageView home;


    private Context context;
    EvaluateDB evaluateDB;

//    RecyclerView recycler_videos;
//    Video_Adapter video_adapter;
//    LinearLayoutManager layoutManager;
//    ArrayList<VideoModel> video_list = new ArrayList<>();

    private Button text_practical;
    private Button text_prac_video_sync;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        context= VideoActivity.this;
        evaluateDB = EvaluateDB.getInstance(context);

        text_practical = (Button)findViewById(R.id.text_theory2) ;

        videoListView = (ListView) findViewById(R.id.sync_video_theory);

//        recycler_videos = (RecyclerView)findViewById(R.id.recycler_videos);
//
//        layoutManager=new LinearLayoutManager(this);
//        recycler_videos.setLayoutManager(layoutManager);
//
//
//        video_adapter=new Video_Adapter(this,video_list);
//        recycler_videos.setAdapter(video_adapter);
//        recycler_videos.setFocusable(false);




        text_practical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                videoListView.setVisibility(View.VISIBLE);


                databaseHandler = new DatabaseHandler(getApplicationContext());

                //////////Refresh Theory Result///////////////
                refresh();
                //////////////////////////////////////////////

            }
        });




        home=(ImageView)findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(VideoActivity.this, SelectLoginActivity.class));
            }
        });


    }



//        text_practical.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                theory.setTextColor(getResources().getColor(R.color.white));
////                practical.setTextColor(getResources().getColor(R.color.green));
//                practicalResultListView.setVisibility(View.VISIBLE);
////                theoryResultListView.setVisibility(View.GONE);
//            }
//        });



    public void refresh()
    {
        if(videoModelArrayList!=null) {
            videoModelArrayList.clear();
        }
        videoModelArrayList = databaseHandler.getAllResult();
        videoSyncAdaptar = new VideosyncAdapter(videoModelArrayList, VideoActivity.this);
        videoListView.setAdapter(videoSyncAdaptar);
    }




}