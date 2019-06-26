//package com.tsa.exam.adapters;
//
//import android.app.ProgressDialog;
//import android.content.Context;
//import android.os.Environment;
//import android.support.v7.widget.RecyclerView;
//import android.util.Base64;
//import android.view.LayoutInflater;
//import android.view.TextureView;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.StringRequest;
//import com.android.volley.toolbox.Volley;
//import com.tsa.exam.R;
//import com.tsa.exam.Theory_syncActivity;
//import com.tsa.exam.Utill.GLOBAL;
//import com.tsa.exam.database.DatabaseHandler;
//import com.tsa.exam.model.ResultModel;
//import com.tsa.exam.model.VideoModel;
//
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Map;
//
//import io.realm.Realm;
//
//
//public  class Video_Adapter extends RecyclerView.Adapter<Video_Adapter.ItemViewHolder> {
//    ArrayList<VideoModel> video_list = new ArrayList<>();
//    public LayoutInflater inflater;
//    Context context;
//
//
//    Map<String, String> paramsCount;
//
//    public Video_Adapter(Context applicationContext, ArrayList<VideoModel> video_list) {
//        this.video_list = video_list;
//        this.context = applicationContext;
//        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//    }
//
//    @Override
//    public Video_Adapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//
//        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_sync, parent, false);
//        ItemViewHolder viewHolder = new ItemViewHolder(v);
//        return viewHolder;
//
//    }
//
//    @Override
//    public void onBindViewHolder(final ItemViewHolder itemViewHolder, final int position) {
//
//        final DatabaseHandler databaseHandler = new DatabaseHandler(context);
//        itemViewHolder.text_cand_id.setText(video_list.get(position).getCandidate_id());
////        itemViewHolder.video_sync_texture.set(video_list.get(position).getVideo());
//
//
//        itemViewHolder.sync4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                String candidate_id = "76";
//                String batch_id = "11";
//                String exam_id = "15";
//                String vid="";
//                String path = Environment.getExternalStorageDirectory()+"//FingerData//1501/ISOTemplate1501.iso";
//
//              try {
//                  File file = new File(path);
//                  FileInputStream is = new FileInputStream(file);
//                  byte[] bytes = new byte[(int) file.length()];
//                  ByteArrayOutputStream buffer = new ByteArrayOutputStream();
//                  int nRead;
//                  byte[] data = new byte[1024];
//                  while ((nRead = is.read(data, 0, data.length)) != -1) {
//                      buffer.write(data, 0, nRead);
//                  }
//                  buffer.flush();
//                  bytes = buffer.toByteArray();
//                  vid = new String(bytes);
//
//                  addVideoSer(bytes,candidate_id,batch_id,exam_id);
//              }catch(Exception e){
//                  Toast.makeText(context,"Error in calling addvideo",Toast.LENGTH_LONG).show();
//            }
//              databaseHandler.addVidDb(candidate_id,batch_id,exam_id,vid);
//
//        }});
//
////        VideoModel videoModel = video_list.get(position);
////        itemViewHolder.text_cand_id.setText(video_list.get(position).getCandidate_id());
//
////        itemViewHolder.sync4.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////
////                String batch_id = "";
////                String exam_id = "";
////                String candidate_id = "";
////                String video = "";
////                Realm mRealm;
////                Realm.init(context.getApplicationContext());
////                mRealm = Realm.getDefaultInstance();
////
////                String text_cand_id = video_list.get(position).getCandidate_id();
////
////                DatabaseHandler databaseHandler = new DatabaseHandler(context);
////
////                submitresult(text_cand_id, batch_id, exam_id, candidate_id, video);
////
////                //  submitResult(candidateID, questionID, timeVisited, loginTime, logoutTime, staus);staus
////            }
////        });
//
//    }
//
//    private void addVideoSer(byte [] bytes, final String candidate_id, final String batch_id, final String exam_id) {
//
//        String uploadUrl = "http://tsassessors.in/ISDAT/evaluate_app/assessor_api/upload_candidate_videos.php";
//        final RequestQueue rQueue;
//        ByteArrayOutputStream os = new ByteArrayOutputStream();
//        try {
//            os.write(bytes);
//            os.close();
//        } catch (IOException e) {
//            Toast.makeText(context, "Unable to write file", Toast.LENGTH_LONG).show();
//        }
//        final String encodedFile = Base64.encodeToString(os.toByteArray(), Base64.DEFAULT);
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, uploadUrl,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        Toast.makeText(context, response, Toast.LENGTH_LONG).show();
//
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
//                    }
//                }) {
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("candidate_id",candidate_id );
//                params.put("batch_id",batch_id);
//                params.put("exam_id",exam_id);
//                params.put("video", encodedFile);
//
//                return params;
//            }
//
//        };
//        rQueue = Volley.newRequestQueue(context);
//        rQueue.add(stringRequest);
//    }
//
//
////    private void submitresult(final String text_cand_id, final String batch_id, final String exam_id, final String candidate_id, final String video) {
////
////        final ProgressDialog progressDialog = new ProgressDialog(context);
////        progressDialog.setTitle("processing");
////        progressDialog.show();
////        final DatabaseHandler databaseHandler = new DatabaseHandler(context);
//////        final StringRequest stringRequest = new StringRequest(Request.Method.POST, GLOBAL.BASE_URL + "upload_candidate_videos.php", new Response.Listener<String>() {
//////            @Override
//////            public void onResponse(String response) {
//////
//////            }
//////        });
////    }
//
//
//    @Override
//    public int getItemCount() {
//       return video_list.size();
//       // return  1;
//    }
//
//
//
////    public class ItemViewHolder extends RecyclerView.ViewHolder{
////        public ItemViewHolder(View itemView) {
////            super(itemView);
////        }
////    }
//
//
//class ItemViewHolder extends RecyclerView.ViewHolder {
//        TextureView video_sync_texture;
//        ImageView sync4;
//        TextView  text_cand_id;
//
//        public ItemViewHolder(View itemView) {
//
//
//            super(itemView);
//            video_sync_texture = (TextureView)itemView.findViewById(R.id.video_sync_texture);
//             sync4 = (ImageView)itemView.findViewById(R.id.sync4);
//             text_cand_id = (TextView)itemView.findViewById(R.id.text_cand_id);
//        }
//
//
//
//
//    }
//}
//
