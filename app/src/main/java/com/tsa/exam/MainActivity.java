package com.tsa.exam;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.SurfaceTexture;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.Image;
import android.media.ImageReader;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.SystemClock;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.util.Size;
import android.util.SparseIntArray;
import android.view.Gravity;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;

import com.tsa.exam.Utill.GLOBAL;
import com.tsa.exam.Utill.TimeConverter;
import com.tsa.exam.customView.CustomTypefaceSpan;
import com.tsa.exam.database.DatabaseHandler;
import com.tsa.exam.databinding.ActivityMainBinding;
import com.tsa.exam.model.QuestionModel;
import com.tsa.exam.model.ResultModel;
import com.tsa.exam.model.TrackModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import io.realm.Realm;

//for cameraX

public class MainActivity extends AppCompatActivity {



    private boolean nextFlag;
    private boolean preFlag;

    private QuestionModel questionModel;

    private Button button;

    //////////////////////////////
    private RadioButton optARb;
    private RadioButton optBRb;
    private RadioButton optCRb;
    private RadioButton optDRb;
    ///////////////////////////////

    private ImageView optAIv;
    private ImageView optBIv;
    private ImageView optCIv;
    private ImageView optDIv;

    private TextView totalQTv;

    private Realm mRealm;

    /*public static int cameraID = 0;*/
    public static boolean isBlack = true;

    private LinearLayout imageRoot;

    private final int NUMBER_OF_QUESTIONS = GLOBAL.NoOfQuestions;

    private DrawerLayout drawer;

    private int questionId = 0;

    private TextView qtyTv;

    private DatabaseHandler databaseHandler;

    private TextView chronometer;

    private Toolbar toolbar;

    private TextView questionNumber;

    ActivityMainBinding binding;

    private TextView questionTv;

    private String timeTRacker = "";
    private String myQuestionIDLatest = "";
    private String attemptedSatus = "";

    private String timeLeft = "";

    private long examDuration = 0;
    private CountDownTimer MyCountDownTimer;

    private boolean attemptedAll;

    private int noOfQ = 0;

    int startFromQ = 0;
    public static String fileName;
    public static int nOrm;

    public SeekBar seekbar;
    private String C_ID, B_ID;


    // video camera called
    private static final String TAG = "Camera(Main)";

    private static final int REQUEST_CAMERA_PERMISSION_RESULT = 0;
    private static final int REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION_RESULT = 1;
    private static final int STATE_PREVIEW = 0;
    private static final int STATE_WAIT_LOCK = 1;
    private int mCaptureState = STATE_PREVIEW;
    private static final int SENSOR_ORIENTATION_DEFAULT_DEGREES = 90;
    private static final int SENSOR_ORIENTATION_INVERSE_DEGREES = 270;
    private static final SparseIntArray DEFAULT_ORIENTATIONS = new SparseIntArray();
    private static final SparseIntArray INVERSE_ORIENTATIONS = new SparseIntArray();

    static {
        DEFAULT_ORIENTATIONS.append(Surface.ROTATION_0, 90);
        DEFAULT_ORIENTATIONS.append(Surface.ROTATION_90, 0);
        DEFAULT_ORIENTATIONS.append(Surface.ROTATION_180, 270);
        DEFAULT_ORIENTATIONS.append(Surface.ROTATION_270, 180);
    }

    static {
        INVERSE_ORIENTATIONS.append(Surface.ROTATION_0, 270);
        INVERSE_ORIENTATIONS.append(Surface.ROTATION_90, 180);
        INVERSE_ORIENTATIONS.append(Surface.ROTATION_180, 90);
        INVERSE_ORIENTATIONS.append(Surface.ROTATION_270, 0);
    }

    //Changes & Fixes by Jayesh
    private AutoFitTextureView mTextureView;
    private Semaphore mCameraOpenCloseLock = new Semaphore(1);
    private HandlerThread mBackgroundThread;

    private CameraDevice mCameraDevice;
    private CameraCaptureSession mPreviewSession;
    private CaptureRequest.Builder mPreviewBuilder;
    private Handler mBackgroundHandler;
    private String mCameraId;
    private Size mPreviewSize;
    private Size mVideoSize;


    private MediaRecorder mMediaRecorder;
    private Chronometer mChronometer;
    private int mTotalRotation;
    private CameraDevice.StateCallback mStateCallback = new CameraDevice.StateCallback() {
        @Override
        public void onOpened(@NonNull CameraDevice cameraDevice) {
            mCameraDevice = cameraDevice;
            startPreview();
            mCameraOpenCloseLock.release();
            if (null != mTextureView) {
                configureTransform(mTextureView.getWidth(), mTextureView.getHeight());
            }

            /*mMediaRecorder = new MediaRecorder();
            if(mIsRecordingVideo) {
                try {
                    createVideoFileName();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                startRecord();
                mMediaRecorder.start();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mChronometer.setBase(SystemClock.elapsedRealtime());
                        mChronometer.setVisibility(View.VISIBLE);
                        mChronometer.start();
                    }
                });
            } else {
                startPreview();
            }*/
            // Toast.makeText(getApplicationContext(),
            //         "Camera connection made!", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onDisconnected(@NonNull CameraDevice cameraDevice) {
            mCameraOpenCloseLock.release();
            cameraDevice.close();
            mCameraDevice = null;
        }

        @Override
        public void onError(@NonNull CameraDevice cameraDevice, int error) {
            mCameraOpenCloseLock.release();
            cameraDevice.close();
            mCameraDevice = null;

            // TODO: do something here on camera error
        }
    };
    private CameraCaptureSession.CaptureCallback mPreviewCaptureCallback = new
            CameraCaptureSession.CaptureCallback() {

                private void process(CaptureResult captureResult) {
                    switch (mCaptureState) {
                        case STATE_PREVIEW:
                            // Do nothing
                            break;
                        case STATE_WAIT_LOCK:
                            mCaptureState = STATE_PREVIEW;
                            Integer afState = captureResult.get(CaptureResult.CONTROL_AF_STATE);
                            if(afState == CaptureResult.CONTROL_AF_STATE_FOCUSED_LOCKED ||
                                    afState == CaptureResult.CONTROL_AF_STATE_NOT_FOCUSED_LOCKED) {
                                Toast.makeText(getApplicationContext(), "AF Locked!", Toast.LENGTH_SHORT).show();

                            }
                            break;
                    }
                }

                @Override
                public void onCaptureCompleted(CameraCaptureSession session, CaptureRequest request, TotalCaptureResult result) {
                    super.onCaptureCompleted(session, request, result);

                    process(result);
                }
            };
    private CameraCaptureSession mRecordCaptureSession;
    private CameraCaptureSession.CaptureCallback mRecordCaptureCallback = new
            CameraCaptureSession.CaptureCallback() {

                private void process(CaptureResult captureResult) {
                    switch (mCaptureState) {
                        case STATE_PREVIEW:
                            // Do nothing
                            break;
                        case STATE_WAIT_LOCK:
                            mCaptureState = STATE_PREVIEW;
                            Integer afState = captureResult.get(CaptureResult.CONTROL_AF_STATE);
                            if(afState == CaptureResult.CONTROL_AF_STATE_FOCUSED_LOCKED ||
                                    afState == CaptureResult.CONTROL_AF_STATE_NOT_FOCUSED_LOCKED) {
                                Toast.makeText(getApplicationContext(), "AF Locked!", Toast.LENGTH_SHORT).show();

                            }
                            break;
                    }
                }

                @Override
                public void onCaptureCompleted(CameraCaptureSession session, CaptureRequest request, TotalCaptureResult result) {
                    super.onCaptureCompleted(session, request, result);

                    process(result);
                }
            };
    private Integer mSensorOrientation;
    private TextureView.SurfaceTextureListener mSurfaceTextureListener
            = new TextureView.SurfaceTextureListener() {

        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture,
                                              int width, int height) {
            openCamera(width, height);

        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture,
                                                int width, int height) {
            configureTransform(width, height);
        }

        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
            return true;
        }

        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
        }

    };
    private String mNextVideoAbsolutePath;
    private ImageButton mButtonVideo;
    private ImageButton mStillImageButton;
    private boolean mIsRecordingVideo;
    private boolean mIsTimelapse = false;

    private File mVideoFolder;
    private String mVideoFileName;
    private File mImageFolder;
    private String mImageFileName;

    //Coded with Love by Jayesh
    private static Size chooseVideoSize(Size[] choices) {
        for (Size size : choices) {
            if (size.getWidth() == size.getHeight() * 4 / 3 && size.getWidth() <= 1080) {
                return size;
            }
        }
        Log.e(TAG, "chooseVideoSize(): Couldn't find any suitable video size");
        return choices[choices.length - 1];
    }


/////////////////////////////////////////////////////////////////////////////////////////////////////////

    private static Size chooseOptimalSize(Size[] choices, int width, int height, Size aspectRatio) {
        List<Size> bigEnough = new ArrayList<>();
        int w = aspectRatio.getWidth();
        int h = aspectRatio.getHeight();
        for (Size option : choices) {
            if (option.getHeight() == option.getWidth() * h / w &&
                    option.getWidth() >= width && option.getHeight() >= height) {
                bigEnough.add(option);
            }
        }
        if (bigEnough.size() > 0) {
            return Collections.min(bigEnough, new MainActivity.CompareSizesByArea());
        } else {
            return choices[0];
        }
    }
////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }

    @SuppressLint("WrongViewCast")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        mChronometer = findViewById(R.id.ivdeo_chronometer);
        mTextureView = findViewById(R.id.textureView);




        init();
        startExam();
        totalQTv = findViewById(R.id.total_questions);
        totalQTv.setText("" + NUMBER_OF_QUESTIONS);


        createVideoFolder();



        //Coded with Love by Jayesh
        mButtonVideo = findViewById(R.id.videoOnlineImageButtons);
        mButtonVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mIsRecordingVideo) {
                    mButtonVideo.setImageResource(R.mipmap.btn_video_online);

                    Intent mediaStoreUpdateIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    mediaStoreUpdateIntent.setData(Uri.fromFile(new File(mVideoFileName)));
                    sendBroadcast(mediaStoreUpdateIntent);
                    stopRecordingVideo();

                    mChronometer.stop();
                    mChronometer.setVisibility(View.INVISIBLE);






                } else {

                    mButtonVideo.setImageResource(R.mipmap.btn_video_busy);
                    try {
                        createVideoFileName();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    startRecordingVideo();

                    mChronometer.setBase(SystemClock.elapsedRealtime());
                    mChronometer.setVisibility(View.VISIBLE);
                    mChronometer.start();


                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        startBackgroundThread();

        if(mTextureView.isAvailable()) {
            openCamera(mTextureView.getWidth(), mTextureView.getHeight());

        } else {
            mTextureView.setSurfaceTextureListener(mSurfaceTextureListener);
        }
    }


/*
    @Override
    protected void onPause() {
        closeCamera();

        stopBackgroundThread();

        super.onPause();
    }*/

    @Override
    public void onWindowFocusChanged(boolean hasFocas) {
        super.onWindowFocusChanged(hasFocas);
        View decorView = getWindow().getDecorView();
        if(hasFocas) {
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE




                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    }

    private void stopRecordingVideo() {
        // UI
        mIsRecordingVideo = false;

        // Stop recording
        mMediaRecorder.stop();
        mMediaRecorder.reset();


        Toast.makeText(getApplicationContext(), "Video saved: " + mNextVideoAbsolutePath,
                Toast.LENGTH_SHORT).show();
        Log.d(TAG, "Video saved: " + mNextVideoAbsolutePath);

        mNextVideoAbsolutePath = null;
        startPreview();
    }

    //Coded with Love by Jayesh
    @SuppressWarnings("MissingPermission")
    private void openCamera(int width, int height) {
        CameraManager manager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {

            Log.d(TAG, "tryAcquire");
            if (!mCameraOpenCloseLock.tryAcquire(2500, TimeUnit.MILLISECONDS)) {
                throw new RuntimeException("Time out waiting to lock camera opening.");
            }

            assert manager != null;
            String cameraId = manager.getCameraIdList()[1];

            CameraCharacteristics characteristics = manager.getCameraCharacteristics(cameraId);

            StreamConfigurationMap map = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
            mSensorOrientation = characteristics.get(CameraCharacteristics.SENSOR_ORIENTATION);
            if (map == null) {
                throw new RuntimeException("Cannot get available preview/video sizes");
            }
            mVideoSize = chooseVideoSize(map.getOutputSizes(MediaRecorder.class));

            mPreviewSize = chooseOptimalSize(map.getOutputSizes(SurfaceTexture.class), width, height, mVideoSize);
            int orientation = getResources().getConfiguration().orientation;
            if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                mTextureView.setAspectRatio(mPreviewSize.getWidth(), mPreviewSize.getHeight());
            } else {
                mTextureView.setAspectRatio(mPreviewSize.getHeight(), mPreviewSize.getWidth());
            }
            configureTransform(width, height);
            mMediaRecorder = new MediaRecorder();

            manager.openCamera(cameraId, mStateCallback, null);
        } catch (CameraAccessException e) {
            Toast.makeText(getApplicationContext(), "Cannot access the camera.", Toast.LENGTH_SHORT).show();
        } catch (NullPointerException e) {
            // Currently an NPE is thrown when the Camera2API is used but not supported on the
            // device this code runs.
            Toast.makeText(getApplicationContext(), "Camera2API isn't supported on this device", Toast.LENGTH_SHORT).show();

        } catch (InterruptedException e) {
            throw new RuntimeException("Interrupted while trying to lock camera opening.");

        }
    }

    private void startRecordingVideo() {
        //Coded with love by Jayesh
        if (null == mCameraDevice || !mTextureView.isAvailable() || null == mPreviewSize) {
            return;
        }
        try {
            closePreviewSession();

            setUpMediaRecorder();

            SurfaceTexture texture = mTextureView.getSurfaceTexture();

            assert texture != null;
            texture.setDefaultBufferSize(mPreviewSize.getWidth(), mPreviewSize.getHeight());
            mPreviewBuilder = mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_RECORD);

            List<Surface> surfaces = new ArrayList<>();

            Surface previewSurface = new Surface(texture);
            surfaces.add(previewSurface);
            mPreviewBuilder.addTarget(previewSurface);

            Surface recorderSurface = mMediaRecorder.getSurface();
            surfaces.add(recorderSurface);
            mPreviewBuilder.addTarget(recorderSurface);

            mCameraDevice.createCaptureSession(surfaces, new CameraCaptureSession.StateCallback() {
                        @Override
                        public void onConfigured(@NonNull CameraCaptureSession cameraCaptureSession) {
                            mPreviewSession = cameraCaptureSession;
                            updatePreview();

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    // UI

                                    mIsRecordingVideo = true;

                                    // Start recording
                                    mMediaRecorder.start();
                                }
                            });
                        }

                        @Override
                        public void onConfigureFailed(@NonNull CameraCaptureSession cameraCaptureSession) {
                            Log.d(TAG, "onConfigureFailed: startRecord");
                        }
            }, mBackgroundHandler);

        } catch (CameraAccessException | IOException e) {
            e.printStackTrace();
        }
    }

    //Coded with love by Jayesh
    private void closePreviewSession() {
        if (mPreviewSession != null) {
            mPreviewSession.close();
            mPreviewSession = null;
        }
    }

    private void startPreview() {
        if (null == mCameraDevice || !mTextureView.isAvailable() || null == mPreviewSize) {
            return;
        }




        try {
            //Coded with love by Jayesh
            closePreviewSession();
            SurfaceTexture texture = mTextureView.getSurfaceTexture();
            assert texture != null;
            texture.setDefaultBufferSize(mPreviewSize.getWidth(), mPreviewSize.getHeight());
            mPreviewBuilder = mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);

            Surface previewSurface = new Surface(texture);

            mPreviewBuilder.addTarget(previewSurface);

            mCameraDevice.createCaptureSession(Collections.singletonList(previewSurface),
                    new CameraCaptureSession.StateCallback() {
                        @Override
                        public void onConfigured(@NonNull CameraCaptureSession session) {
                            mPreviewSession = session;
                            updatePreview();
                        }

                        @Override
                        public void onConfigureFailed(@NonNull CameraCaptureSession session) {
                            Log.d(TAG, "onConfigureFailed: startPreview");

                        }
                    }, mBackgroundHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    //Coded with love by Jayesh
    private void updatePreview() {
        if (null == mCameraDevice) {
            return;
        }
        try {
            setUpCaptureRequestBuilder(mPreviewBuilder);
            HandlerThread thread = new HandlerThread("CameraPreview");
            thread.start();
            mPreviewSession.setRepeatingRequest(mPreviewBuilder.build(), null, mBackgroundHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    //Coded with love by Jayesh
    private void setUpCaptureRequestBuilder(CaptureRequest.Builder builder) {
        builder.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO);
    }

    //Coded with love by Jayesh
    private void configureTransform(int viewWidth, int viewHeight) {
        if (null == mTextureView || null == mPreviewSize) {
            return;
        }
        int rotation = getWindowManager().getDefaultDisplay().getRotation();
        Matrix matrix = new Matrix();
        RectF viewRect = new RectF(0, 0, viewWidth, viewHeight);
        RectF bufferRect = new RectF(0, 0, mPreviewSize.getHeight(), mPreviewSize.getWidth());
        float centerX = viewRect.centerX();
        float centerY = viewRect.centerY();
        if (Surface.ROTATION_90 == rotation || Surface.ROTATION_270 == rotation) {
            bufferRect.offset(centerX - bufferRect.centerX(), centerY - bufferRect.centerY());
            matrix.setRectToRect(viewRect, bufferRect, Matrix.ScaleToFit.FILL);
            float scale = Math.max(
                    (float) viewHeight / mPreviewSize.getHeight(),
                    (float) viewWidth / mPreviewSize.getWidth());
            matrix.postScale(scale, scale, centerX, centerY);
            matrix.postRotate(90 * (rotation - 2), centerX, centerY);
        }
        mTextureView.setTransform(matrix);
    }


    //Coded with love by Jayesh
    private void closeCamera() {
        try {
            mCameraOpenCloseLock.acquire();
            closePreviewSession();
            if (null != mCameraDevice) {
                mCameraDevice.close();
                mCameraDevice = null;
            }
            if (null != mMediaRecorder) {
                mMediaRecorder.release();
                mMediaRecorder = null;
            }
        } catch (InterruptedException e) {
            throw new RuntimeException("Interrupted while trying to lock camera closing.");
        } finally {
            mCameraOpenCloseLock.release();
        }
    }

    //Coded with Love by Jayesh
    private void startBackgroundThread() {
        mBackgroundThread = new HandlerThread("CameraBackground");
        mBackgroundThread.start();
        mBackgroundHandler = new Handler(mBackgroundThread.getLooper());
    }

    private void stopBackgroundThread() {
        mBackgroundThread.quitSafely();
        try {
            mBackgroundThread.join();
            mBackgroundThread = null;
            mBackgroundHandler = null;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //Coded with love by Jayesh
    private void setUpMediaRecorder() throws IOException {


        mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.SURFACE);
        mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        if (mNextVideoAbsolutePath == null || mNextVideoAbsolutePath.isEmpty()) {
            mNextVideoAbsolutePath = getFile();
        }

        mMediaRecorder.setOutputFile(mNextVideoAbsolutePath);
        mMediaRecorder.setVideoEncodingBitRate(10000000);
        mMediaRecorder.setVideoFrameRate(30);
        mMediaRecorder.setVideoSize(mVideoSize.getWidth(), mVideoSize.getHeight());
        mMediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
        mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        int rotation = getWindowManager().getDefaultDisplay().getRotation();
        switch (mSensorOrientation) {
            case SENSOR_ORIENTATION_DEFAULT_DEGREES:
                mMediaRecorder.setOrientationHint(DEFAULT_ORIENTATIONS.get(rotation));
                break;
            case SENSOR_ORIENTATION_INVERSE_DEGREES:
                mMediaRecorder.setOrientationHint(INVERSE_ORIENTATIONS.get(rotation));
                break;
        }
        mMediaRecorder.prepare();
    }

    private void createVideoFolder() {
        File movieFile = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);
        mVideoFolder = new File(movieFile, "camera2VideoImage");
        if(!mVideoFolder.exists()) {
            mVideoFolder.mkdirs();
        }
    }

    private File createVideoFileName() throws IOException {
        //String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        //String timestamp = NOSPracticalActivity.getCid();
        String prepend = "VIDEO_";
        //fileName=fileName+1;

        //String path = Environment.getExternalStorageDirectory()+"//Movies//camera2VideoImage//"+NOSPracticalActivity.getCid();
        File videoFile = File.createTempFile(prepend, ".mp4", mVideoFolder);
         mVideoFileName = videoFile.getAbsolutePath();
        fileName= mVideoFileName;
        //=1;
        Toast.makeText(this,mVideoFileName,Toast.LENGTH_LONG).show();
        return videoFile;
    }
    public static String getFile()
    {
        return fileName;
    }

    /* public static int getnOrm()
     {
         return nOrm;
     }*/
    /*public static void setnOrm(int a){
        nOrm=a;
    }*/
    /*private void createImageFolder() {
        File imageFile = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        mImageFolder = new File(imageFile, "camera2VideoImage");
        if(!mImageFolder.exists()) {
            mImageFolder.mkdirs();
        }
    }


    private File createImageFileName() throws IOException {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String prepend = "IMAGE_" + timestamp + "_";
        File imageFile = File.createTempFile(prepend, ".jpg", mImageFolder);
        mImageFileName = imageFile.getAbsolutePath();
        return imageFile;
    }
*/


    //Coded with love by Jayesh
    private String getVideoFilePath(Context context) {
        Log.d(TAG, String.valueOf(getExternalFilesDir(null)));
        final File dir = context.getExternalFilesDir(null);


        return (dir == null ? "" : (dir.getAbsolutePath() + "/"))
                + System.currentTimeMillis() + ".mp4";


    }

    private void lockFocus() {
        mCaptureState = STATE_WAIT_LOCK;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mPreviewBuilder.set(CaptureRequest.CONTROL_AF_TRIGGER, CaptureRequest.CONTROL_AF_TRIGGER_START);
        }
        try {
            if (mIsRecordingVideo) {
                mRecordCaptureSession.capture(mPreviewBuilder.build(), mRecordCaptureCallback, mBackgroundHandler);
            } else {
                mPreviewSession.capture(mPreviewBuilder.build(), mPreviewCaptureCallback, mBackgroundHandler);
            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    //Coded with Love by Jayesh
    static class CompareSizesByArea implements Comparator<Size> {

        @Override
        public int compare(Size lhs, Size rhs) {

            return Long.signum((long) lhs.getWidth() * lhs.getHeight() -
                    (long) rhs.getWidth() * rhs.getHeight());
        }

    }


    public void init() {
        button = findViewById(R.id.submit);
        @SuppressLint("RestrictedApi") final ContextThemeWrapper con = new ContextThemeWrapper(this, R.style.AlertS);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isSubmitable()) {
                    android.app.AlertDialog dialog = new AlertDialog.Builder(con)
                            .setIcon(R.drawable.logonew)
                            .setMessage("You have attempted all questions\n" +
                                    "आपने सभी प्रश्नों का जवाब दे दिया है?\n" +
                                    "Do you want to Submit?\n" +
                                    "क्या आप परीक्षा को सपूर्ण करना चाहते है?")
                            .setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    submitExam();
                                }
                            })
                            .setPositiveButton("No", null)
                            .show();


                    TextView textView = dialog.findViewById(android.R.id.message);
                    textView.setTextSize(18);
                } else {
                    Toast.makeText(MainActivity.this, "Please Attempt All Questions", Toast.LENGTH_SHORT).show();
                }
            }
        });

        seekbar = findViewById(R.id.seekBar);
        seekbar.setProgress(15);
        seekbar.setMax(25);
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progress = progress + 10;
                binding.questionTv.setTextSize(progress);
                binding.optARb.setTextSize(progress);
                binding.optBRb.setTextSize(progress);
                binding.optCRb.setTextSize(progress);
                binding.optDRb.setTextSize(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        imageRoot = binding.imageRoot;

        binding.candidateName.setText(GLOBAL.candidateName);

        optARb = binding.optARb;
        optARb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });
        optBRb = binding.optBRb;
        optBRb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });
        optCRb = binding.optCRb;
        optCRb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });
        optDRb = binding.optDRb;
        optDRb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });

        optAIv = binding.optAIv;
        optBIv = binding.optBIv;
        optCIv = binding.optCIv;
        optDIv = binding.optDIv;

        questionTv = binding.questionTv;

        drawer = binding.drawerLayout1;

        drawer.closeDrawer(GravityCompat.START);

        questionNumber = binding.qtyTv1;

        toolbar = findViewById(R.id.toolbar);

        chronometer = findViewById(R.id.chronometer1);

        qtyTv = binding.questionTv;

    }

    public void setCheked(int position) {
        RadioGroup radioGroup = binding.radioRoot;
        radioGroup.clearCheck();
        switch (position) {
            case 0:
                optARb.setChecked(false);
                optBRb.setChecked(false);
                optCRb.setChecked(false);
                optDRb.setChecked(false);
                break;
            case 1:
                optARb.setChecked(true);
                break;
            case 2:
                optBRb.setChecked(true);
                break;
            case 3:
                optCRb.setChecked(true);
                break;
            case 4:
                optDRb.setChecked(true);
                break;
            case 5:
                break;
        }
    }

    public void updateChecked(int questionId) {
        if (nextFlag)
            questionId = noOfQ - 1;
        long endTime = 0;
        if (optARb.isChecked()) {
            databaseHandler.updateChecked(questionId, "1");
            databaseHandler.updateStatus(questionId, "answered");
            endTime = System.currentTimeMillis();
        } else if (optBRb.isChecked()) {
            databaseHandler.updateChecked(questionId, "2");
            databaseHandler.updateStatus(questionId, "answered");
            endTime = System.currentTimeMillis();
        } else if (optCRb.isChecked()) {
            databaseHandler.updateChecked(questionId, "3");
            databaseHandler.updateStatus(questionId, "answered");
            endTime = System.currentTimeMillis();
        } else if (optDRb.isChecked()) {
            databaseHandler.updateChecked(questionId, "4");
            databaseHandler.updateStatus(questionId, "answered");
            endTime = System.currentTimeMillis();
        }

        if (endTime == 0) {
            endTime = System.currentTimeMillis();
            String timeSpent = TimeConverter.getTimeDefference(GLOBAL.startTime, endTime);
            GLOBAL.startTime = endTime;
            trackLog(timeSpent, "visit", "" + (questionId + 1));
        } else {
            String timeSpent = TimeConverter.getTimeDefference(GLOBAL.startTime, endTime);
            GLOBAL.startTime = endTime;
            trackLog(timeSpent, "save", "" + (questionId + 1));
        }
    }

    public void setPallate(ArrayList<QuestionModel> questionModelArrayList) {
        int[] qStatusId = new int[]{R.id.row1_bt1, R.id.row1_bt2, R.id.row1_bt3, R.id.row1_bt4, R.id.row1_bt5,
                R.id.row2_bt1, R.id.row2_bt2, R.id.row2_bt3, R.id.row2_bt4, R.id.row2_bt5,
                R.id.row3_bt1, R.id.row3_bt2, R.id.row3_bt3, R.id.row3_bt4, R.id.row3_bt5,
                R.id.row4_bt1, R.id.row4_bt2, R.id.row4_bt3, R.id.row4_bt4, R.id.row4_bt5,
                R.id.row5_bt1, R.id.row5_bt2, R.id.row5_bt3, R.id.row5_bt4, R.id.row5_bt5,
                R.id.row6_bt1, R.id.row6_bt2, R.id.row6_bt3, R.id.row6_bt4, R.id.row6_bt5,
                R.id.row7_bt1, R.id.row7_bt2, R.id.row7_bt3, R.id.row7_bt4, R.id.row7_bt5,
                R.id.row8_bt1, R.id.row8_bt2, R.id.row8_bt3, R.id.row8_bt4, R.id.row8_bt5,
                R.id.row9_bt1, R.id.row9_bt2, R.id.row9_bt3, R.id.row9_bt4, R.id.row9_bt5,
                R.id.row10_bt1, R.id.row10_bt2, R.id.row10_bt3, R.id.row10_bt4, R.id.row10_bt5,
                R.id.row11_bt1, R.id.row11_bt2, R.id.row11_bt3, R.id.row11_bt4, R.id.row11_bt5,
                R.id.row12_bt1, R.id.row12_bt2, R.id.row12_bt3, R.id.row12_bt4, R.id.row12_bt5};

        Button[] qStatus = new Button[qStatusId.length];

        for (int i = 0; i < qStatusId.length; i++) {
            qStatus[i] = findViewById(qStatusId[i]);
            if (i < NUMBER_OF_QUESTIONS) {
                qStatus[i].setVisibility(View.VISIBLE);
                qStatus[i].setClickable(true);
                qStatus[i].setEnabled(true);
                qStatus[i].setText("" + (i + 1));
                if (questionModelArrayList.get(i).getQuestionStatus().equals("review")) {
                    qStatus[i].setBackgroundResource(R.color.Gold);
                } else if (questionModelArrayList.get(i).getQuestionStatus().equals("answered")) {
                    qStatus[i].setBackgroundResource(R.color.Green);
                }
            } else {
                qStatus[i].setEnabled(false);
                qStatus[i].setText("0");
                qStatus[i].setBackgroundResource(R.color.white_greyish);
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void review() {

        if (isAnyChecked()) {
            //onFrontClick(view);
            if (questionId < (NUMBER_OF_QUESTIONS - 1)) {

                reviewEvent();
            }
        } else {
            Toast.makeText(this, "Please Select Atleast One Option to Review", Toast.LENGTH_SHORT).show();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void pre() {
        if (questionId > 0) {
            preFlag = false;
            preEvent();
        } else {
            preFlag = true;
            questionId = noOfQ;
            preEvent();
        }
    }

    @SuppressLint("WrongConstant")
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void next(View view) {
        //onFrontClick(view);
        if (isAnyChecked()) {
            if (questionId < (NUMBER_OF_QUESTIONS - 1)) {
                nextFlag = false;
                nextEvent();
            } else {
                drawer.openDrawer(Gravity.START);
                nextFlag = true;
                questionId = -1;
                nextEvent();
            }
        } else {
            Toast.makeText(this, "please select atleast one option", Toast.LENGTH_SHORT).show();
        }
    }

    //////////////////////////////////////
    public void nextEvent() {

        if (nextFlag)
            updateChecked(questionId + 1);
        else
            updateChecked(questionId);

        setPallate(databaseHandler.getAllQuestions());
        ++questionId;
        loadingImage();
        //TODO
        //test(questionId);
        radioHideShow(questionId);
    }

    public void reviewEvent() {
        updateChecked(questionId);
        databaseHandler.updateStatus(questionId, "review");
        setPallate(databaseHandler.getAllQuestions());
        ++questionId;
        loadingImage();
        radioHideShow(questionId);
    }

    public void preEvent() {
        if (preFlag)
            updateChecked(questionId - 1);
        else
            updateChecked(questionId);

        --questionId;
        setPallate(databaseHandler.getAllQuestions());
        loadingImage();
        radioHideShow(questionId);
    }

    //////////////////////////////////////
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void getFromImage(int code, String imageName) {

        File imgFile = new File(Environment.getExternalStorageDirectory() + "/Download/exam_db/" + imageName);

        if (imgFile.exists()) {

            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

            switch (code) {
                case 0:
                    optAIv.setImageBitmap(myBitmap);
                    break;

                case 1:
                    optBIv.setImageBitmap(myBitmap);
                    break;

                case 2:
                    optCIv.setImageBitmap(myBitmap);
                    break;

                case 3:
                    optDIv.setImageBitmap(myBitmap);
                    break;

                case 4:
                    Drawable img = new BitmapDrawable(getResources(), myBitmap);
                    img.setBounds(0, 0, 100, 100);
                    questionTv.setCompoundDrawables(img, null, null, null);
                    break;

            }
        }
    }

    boolean isSubmitable() {
        ArrayList<QuestionModel> modelArrayList = databaseHandler.getAllQuestions();
        boolean imageBoolean = true;
        for (int i = 0; i < modelArrayList.size(); i++) {
            if (modelArrayList.get(i).getQuestionStatus().equals("review") || modelArrayList.get(i).getQuestionStatus().equals("unanswered")) {
                imageBoolean = false;
            }
        }
        return imageBoolean;
    }


    /*public void onFrontClick(View v) {

        isBlack = true;
        cameraID = 1;
        Intent i = new Intent(MainActivity.this, CameraView.class);
        startActivityForResult(i, 999);
    }
*/
    public String getAttemptedAnswer(QuestionModel questionModel) {
        if (questionModel.getChecked() == 1) {
            return "A";
        } else if (questionModel.getChecked() == 2) {
            return "B";

        } else if (questionModel.getChecked() == 3) {
            return "C";

        } else if (questionModel.getChecked() == 4) {
            return "D";
        } else {
            return "none";
        }
    }

    public String getCorrectAnswer(QuestionModel questionModel) {
        String[] part1 = questionModel.getOptionA().split("\\*");
        String[] part2 = questionModel.getOptionB().split("\\*");
        String[] part3 = questionModel.getOptionC().split("\\*");
        String[] part4 = questionModel.getOptionD().split("\\*");
        if (questionModel.getCorrect_ans().replace("\n", "").equalsIgnoreCase(part1[0].replace("\n", ""))) {
            return part1[0];
        } else if (questionModel.getCorrect_ans().replace("\n", "").equalsIgnoreCase(part2[0].replace("\n", ""))) {
            return part2[0];

        } else if (questionModel.getCorrect_ans().replace("\n", "").equalsIgnoreCase(part3[0].replace("\n", ""))) {
            return part3[0];

        } else if (questionModel.getCorrect_ans().replace("\n", "").equalsIgnoreCase(part4[0].replace("\n", ""))) {
            return part4[0];
        } else {
            return "none";
        }
    }

    public void trackLog(String time, String response, String myQuestionId) {
        timeTRacker = timeTRacker + "*" + time;
        myQuestionIDLatest = myQuestionIDLatest + "*" + myQuestionId;
        attemptedSatus = attemptedSatus + "*" + response;
    }

    @Override
    protected void onPause() {

        closeCamera();

        stopBackgroundThread();

        super.onPause();
        //MyCountDownTimer.cancel();

        if (!attemptedAll) {
            databaseHandler.updateCanStatus(GLOBAL.loginID, "2");
        }
        databaseHandler.updateCanTimeLeft(GLOBAL.loginID, timeLeft);
        databaseHandler.updateQVisited(GLOBAL.loginID, "" + questionId);

        // Toast.makeText(this, "onPause", Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onStop() {
        super.onStop();
        //MyCountDownTimer.cancel();
        if (!attemptedAll) {
            databaseHandler.updateCanStatus(GLOBAL.loginID, "2");
        }
        databaseHandler.updateCanTimeLeft(GLOBAL.loginID, timeLeft);
        //Toast.makeText(this, "onStop", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Toast.makeText(this, "onStart", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Toast.makeText(this, "onDestroy", Toast.LENGTH_SHORT).show();
    }

    boolean isAnyChecked() {
        if (optARb.isChecked()) {
            return true;
        } else if (optBRb.isChecked()) {
            return true;
        } else if (optCRb.isChecked()) {
            return true;
        } else return optDRb.isChecked();

    }

    public void submitExam() {
        /*GLOBAL.cameraFlag = false;*/
        StringBuffer questionID = new StringBuffer();
        StringBuffer correctAnswer = new StringBuffer();
        StringBuffer questionMarks = new StringBuffer();
        StringBuffer attemptedAnswer = new StringBuffer();
        StringBuffer marks = new StringBuffer();
        StringBuffer obtainedMarks = new StringBuffer();
        StringBuffer pc = new StringBuffer();
        StringBuffer nos = new StringBuffer();
        StringBuffer reviewed = new StringBuffer();
        StringBuffer shuffle = new StringBuffer();

        attemptedAll = true;
        databaseHandler.updateLogoutTime(GLOBAL.loginID, TimeConverter.getDateFromMilli());

        for (int i = 0; i < databaseHandler.getAllQuestions().size(); i++) {

            questionID.append("" + databaseHandler.getAllQuestions().get(i).getQuestionID() + "*");
            correctAnswer.append("" + getCorrectAnswer(databaseHandler.getAllQuestions().get(i)).trim() + "*");
            questionMarks.append("" + databaseHandler.getAllQuestions().get(i).getMarks() + "*");
            attemptedAnswer.append("" + getAttemptedAnswer(databaseHandler.getAllQuestions().get(i)) + "*");
            pc.append("" + databaseHandler.getAllQuestions().get(i).getPc() + "*");
            nos.append("" + databaseHandler.getAllQuestions().get(i).getNos() + "*");
            shuffle.append("" + databaseHandler.getAllQuestions().get(i).getShuffle() + "*");

            if (databaseHandler.getAllQuestions().get(i).getQuestionStatus().equals("reviewed"))
                reviewed.append("" + databaseHandler.getAllQuestions().get(i).getQuestionID() + "*");


            String selectedOption = "";

            if (databaseHandler.getAllQuestions().get(i).getChecked() == 1) {
                String[] part1 = databaseHandler.getAllQuestions().get(i).getOptionA().split("\\*");
                selectedOption = part1[0];
            } else if (databaseHandler.getAllQuestions().get(i).getChecked() == 2) {
                String[] part1 = databaseHandler.getAllQuestions().get(i).getOptionB().split("\\*");
                selectedOption = part1[0];
            } else if (databaseHandler.getAllQuestions().get(i).getChecked() == 3) {
                String[] part1 = databaseHandler.getAllQuestions().get(i).getOptionC().split("\\*");
                selectedOption = part1[0];
            } else {
                String[] part1 = databaseHandler.getAllQuestions().get(i).getOptionD().split("\\*");
                selectedOption = part1[0];
            }

            marks.append(databaseHandler.getAllQuestions().get(i).getMarks() + "*");

            if (databaseHandler.getAllQuestions().get(i).getCorrect_ans().replace("\n", "").equalsIgnoreCase(selectedOption.replace("\n", ""))) {
                obtainedMarks.append(databaseHandler.getAllQuestions().get(i).getMarks() + "*");
            } else {
                obtainedMarks.append("0" + "*");
            }
        }

        ResultModel resultModel = new ResultModel();
        resultModel.setExamID(databaseHandler.getAllQuestions().get(0).getExam_id());
        resultModel.setBatchID(databaseHandler.getAllQuestions().get(0).getBatch_id());
        resultModel.setCandidateID(databaseHandler.getAllQuestions().get(0).getCid());
        resultModel.setCandidateName(databaseHandler.getAllQuestions().get(0).getCname());
        resultModel.setNos("" + nos.toString());
        resultModel.setQuestionID(questionID.toString());
        resultModel.setShuffleOrder("" + shuffle.toString());
        resultModel.setCorrectAnswer(correctAnswer.toString());
        resultModel.setQuestionMarks(questionMarks.toString());
        resultModel.setAttdemptedAnswer(attemptedAnswer.toString());
        resultModel.setAssID(databaseHandler.getAllQuestions().get(0).getAssessor_id());
        resultModel.setQuestionReview("");
        resultModel.setCandidateLoginID(GLOBAL.loginID);
        resultModel.setMarks("" + marks);
        resultModel.setObtainedMarks("" + obtainedMarks);
        resultModel.setPc("" + pc.toString());
        resultModel.setQuestionReview("" + reviewed.toString());

        //databaseHandler.clearTableResult();
        databaseHandler.saveResult(resultModel);


        mRealm.beginTransaction();
        TrackModel trackModel = mRealm.createObject(TrackModel.class);
        trackModel.setQuestionID(myQuestionIDLatest);
        trackModel.setTimeVisited(timeTRacker);
        trackModel.setStatus(attemptedSatus);
        trackModel.setCandidateID(GLOBAL.loginID);
        mRealm.commitTransaction();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (isAppInLockTaskMode()) {
                stopLockTask();
            }
        }

        databaseHandler.updateCanStatus(GLOBAL.loginID, "3");

        MyCountDownTimer.cancel();

        Intent intent = new Intent(MainActivity.this, ExamEndActivity.class);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void goUpdateQty(int questionId) {
        updateChecked(this.questionId);
        setPallate(databaseHandler.getAllQuestions());
        questionModel = databaseHandler.getQuestionByID(questionId, GLOBAL.loginID).get(0);
        if (questionModel.getImage().equals("yes")) {
            getFromImage(4, questionModel.getImage_path());

        } else {
            questionTv.setCompoundDrawables(null, null, null, null);
        }
        if (questionModel.getOptionImage().equals("yes")) {
            imageRoot.setVisibility(View.VISIBLE);
            optARb.setText("");
            optBRb.setText("");
            optCRb.setText("");
            optDRb.setText("");

            getFromImage(0, questionModel.getOptionA());
            getFromImage(1, questionModel.getOptionB());
            getFromImage(2, questionModel.getOptionC());
            getFromImage(3, questionModel.getOptionD());

        } else {
            imageRoot.setVisibility(View.GONE);
            binding.setQuestionModel(questionModel);
        }
        questionNumber.setText("Question No." + (questionId + 1));
        qtyTv.setText(questionModel.getQuestion());
        setCheked(questionModel.getChecked());
        Log.e("Exam", questionModel.toString());
        this.questionId = questionId;
        drawer.closeDrawer(Gravity.START);
    }

    /////////////////////////////////////
    public void loadingImage() {
        if (databaseHandler.getQuestionByID(questionId, GLOBAL.loginID).size() > 0) {
            questionModel = databaseHandler.getQuestionByID(questionId, GLOBAL.loginID).get(0);
            questionNumber.setText("Question No." + (questionId + 1));
            qtyTv.setText(questionModel.getQuestion());
            setCheked(questionModel.getChecked());
            if (questionModel.getImage().equals("yes")) {
                getFromImage(4, questionModel.getImage_path());
            } else {
                questionTv.setCompoundDrawables(null, null, null, null);
            }
            if (questionModel.getOptionImage().equals("yes")) {
                imageRoot.setVisibility(View.VISIBLE);
                optARb.setText("");
                optBRb.setText("");
                optCRb.setText("");
                optDRb.setText("");

                getFromImage(0, questionModel.getOptionA());
                getFromImage(1, questionModel.getOptionB());
                getFromImage(2, questionModel.getOptionC());
                getFromImage(3, questionModel.getOptionD());

            } else {
                imageRoot.setVisibility(View.GONE);
                binding.setQuestionModel(questionModel);
            }
        }
    }
    /////////////////////////////////////


    private void startExam() {

        databaseHandler = new DatabaseHandler(MainActivity.this);
        /////////////////////////////////////////////////////////////////////////////////////////////////////////
        if(databaseHandler.getCandidatesByID(GLOBAL.loginID).size()>0)
            startFromQ = Integer.parseInt(databaseHandler.getCandidatesByID(GLOBAL.loginID).get(0).getLastVistedQ());
        ///////////////////////////////////////////////////////////////////////////////

        /////////////////////////////////////////////////////////////////////////////////////////////////////////
        questionId = startFromQ;
        questionModel = databaseHandler.getQuestionByID(questionId, GLOBAL.loginID).get(0);
        noOfQ = databaseHandler.getAllQuestions().size();
        GLOBAL.batchID = questionModel.getBatch_id();
        GLOBAL.examID = questionModel.getExam_id();
        binding.setQuestionModel(questionModel);
        binding.setActivity(this);
        binding.candidateId.setText("" + GLOBAL.candidatekey);
        setCheked(questionModel.getChecked());

        //////////////////////////////////////////////////////////////////////////////////////////////////////////


        /////////////////////////////////////////////////////////////////////////////////
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/new.ttf");
        binding.questionTv.setTypeface(typeface, Typeface.BOLD);
        binding.optARb.setTypeface(typeface, Typeface.BOLD);
        binding.optBRb.setTypeface(typeface, Typeface.BOLD);
        binding.optCRb.setTypeface(typeface, Typeface.BOLD);
        binding.optDRb.setTypeface(typeface, Typeface.BOLD);
        /////////////////////////////////////////////////////////////////////////////////

        /////////////////////////////////////
        Realm.init(getApplicationContext());
        mRealm = Realm.getDefaultInstance();
        //////////////////////////////////////
        GLOBAL.startTime = System.currentTimeMillis();

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        if (!databaseHandler.getCandidatesByID(GLOBAL.loginID).get(0).getTimeLeft().equals("0")) {
            long timeLeftLocal = Long.parseLong(databaseHandler.getCandidatesByID(GLOBAL.loginID).get(0).getTimeLeft());
            examDuration = timeLeftLocal;
        } else {
            examDuration = Long.parseLong(questionModel.getExamduration());
            //examDuration=10000;
        }
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        /////////////////////
        loadingImage();
        ////////////////////

        ///////////////////////////////////////////////////////////////
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ///////////////////////////////////////////////////////////////
            DevicePolicyManager myDevicePolicyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
            ComponentName mDeviceAdminSample = DeviceAdminReceiver.getComponentName(this);

            if (myDevicePolicyManager.isDeviceOwnerApp(this.getPackageName())) {
                // Device owner
                String[] packages = {this.getPackageName()};
                myDevicePolicyManager.setLockTaskPackages(mDeviceAdminSample, packages);
            } else {
                //Toast.makeText(this, "Not owner", Toast.LENGTH_SHORT).show();
                // Not a device owner - prompt user or show error
            }

            if (myDevicePolicyManager.isLockTaskPermitted(this.getPackageName())) {
                // Lock allowed
                startLockTask();
            } else {
                //Toast.makeText(this, "Lock Not allowed", Toast.LENGTH_SHORT).show();
            }
            /////////////////////////////////////////////////////////////////////////////////////
        }
////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////

        ////////////////////////////////////////////////////////////////////////
        MyCountDownTimer = new CountDownTimer(examDuration, 1000) {
            public void onTick(long millisUntilFinished) {
                if (millisUntilFinished < 5 * 60 * 1000)
                    chronometer.setTextColor(getResources().getColor(R.color.red));
                chronometer.setText(TimeConverter.getDate(millisUntilFinished));
                timeLeft = "" + millisUntilFinished;

                if (millisUntilFinished % 300 == 0) {}

            }

            public void onFinish() {
                submitExam();
                chronometer.setText("Time Over");
            }
        }.start();
        /////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            public void onDrawerClosed(View view) {

                // calling onPrepareOptionsMenu() to show action bar icons
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                invalidateOptionsMenu();
            }
        };
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        /////////////////////////////////////////////////
        setPallate(databaseHandler.getAllQuestions());
        /////////////////////////////////////////////////

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
            // When SDK version >= 21. This API is deprecated in 23.
            return activityManager.isInLockTaskMode();
        }

        return false;
    }

    public void test(int qID) {

        ////////////////////////////////////////////////////////////////////////////////
        String question = "" + databaseHandler.getAllQuestions().get(qID).getQuestion();
        String opt1 = "" + databaseHandler.getAllQuestions().get(qID).getOptionA();
        String opt2 = "" + databaseHandler.getAllQuestions().get(qID).getOptionB();
        String opt3 = "" + databaseHandler.getAllQuestions().get(qID).getOptionC();
        String opt4 = "" + databaseHandler.getAllQuestions().get(qID).getOptionD();
        ///////////////////////////////////////////////////////////////////////////////

        ///////////////////////////////////////////////////
        int position1;
        int position2;
        int position3;
        int position4;
        int position5;
        ///////////////////////////////////////////////////

        position1 = pos(question);
        position2 = pos(opt1);
        position3 = pos(opt2);
        position4 = pos(opt3);
        position5 = pos(opt4);

        // Create a new spannable with the two strings
        Spannable spannable1 = new SpannableString(question);
        Spannable spannable2 = new SpannableString(opt1);
        Spannable spannable3 = new SpannableString(opt2);
        Spannable spannable4 = new SpannableString(opt3);
        Spannable spannable5 = new SpannableString(opt4);


        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/k_dev.ttf");


        // Set the custom typeface to span over a section of the spannable object
        spannable1.setSpan(new CustomTypefaceSpan("sans-serif", typeface), 30, question.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        // Set the custom typeface to span over a section of the spannable object
        spannable2.setSpan(new CustomTypefaceSpan("sans-serif", typeface), position2,opt1.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        // Set the custom typeface to span over a section of the spannable object
        spannable3.setSpan(new CustomTypefaceSpan("sans-serif", typeface), position3, opt2.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);


        // Set the custom typeface to span over a section of the spannable object
        spannable4.setSpan(new CustomTypefaceSpan("sans-serif", typeface), position4, opt3.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);


        // Set the custom typeface to span over a section of the spannable object
        spannable5.setSpan(new CustomTypefaceSpan("sans-serif", typeface), position5, opt4.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        //spannable.setSpan( new CustomTypefaceSpan("sans-serif",SECOND_CUSTOM_TYPEFACE), firstWord.length(),
        // firstWord.length() + secondWord.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        // Set the text of a textView with the spannable object
        /////////////////////////////////////////////////////////////////////////////////
        binding.questionTv.setText(spannable1);
        binding.optARb.setText(spannable2);
        binding.optBRb.setText(spannable3);
        binding.optCRb.setText(spannable4);
        binding.optDRb.setText(spannable5);
        /////////////////////////////////////////////////////////////////////////////////
    }

    public int pos(String question) {

        return question.indexOf("*");
    }

    private void radioHideShow(int questionId)
    {
        DatabaseHandler databaseHandler=new DatabaseHandler(MainActivity.this);
        if(databaseHandler.getQuestionByID(questionId, GLOBAL.loginID).size()>0) {
            QuestionModel questionModel = databaseHandler.getQuestionByID(questionId, GLOBAL.loginID).get(0);
            ///////////////////////////////Making Radio Buttons invisible When Text is Not////////////////////////////////
            if (questionModel.getOptionA().replace("\n", "").equals("*")) {
                optARb.setVisibility(View.GONE);
            } else {
                optARb.setVisibility(View.VISIBLE);

            }
            if (questionModel.getOptionB().replace("\n", "").equals("*")) {
                optBRb.setVisibility(View.GONE);
            } else {
                optBRb.setVisibility(View.VISIBLE);

            }
            if (questionModel.getOptionC().replace("\n", "").equals("*")) {
                optCRb.setVisibility(View.GONE);
            } else {
                optCRb.setVisibility(View.VISIBLE);

            }
            if (questionModel.getOptionD().replace("\n", "").equals("*")) {
                optDRb.setVisibility(View.GONE);
            } else {
                optDRb.setVisibility(View.VISIBLE);

            }
            //////////////////////////////////////////////////////////////////////////////////////////////////////////
            databaseHandler.close();
        }
    }

}
