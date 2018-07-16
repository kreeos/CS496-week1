package com.example.q.cs496_week3;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CameraActivity extends AppCompatActivity {

    Button button;
//    final Camera[] mCamera = new Camera[1];
//    final CameraPreview[] mPreview = new CameraPreview[1];
    static final int REQUEST_IMAGE_CAPTURE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);



    dispatchTakePictureIntent();
//        mCamera[0] = getCameraInstance();
//        setCameraDisplayOrientation(this,getBackFacingCameraId() , mCamera[0]);
//        // Create our Preview view and set it as the content of our activity.
//        mCamera[0].startPreview();
//        mPreview[0] = new CameraPreview(CameraActivity.this, mCamera[0]);
//        Camera.Parameters params = mCamera[0].getParameters();
//        params.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
//        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
//        preview.addView(mPreview[0]);

    }
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
//            mImageView.setImageBitmap(imageBitmap);
            Log.d("Tagtest", String.valueOf(imageBitmap));
            Intent i = new Intent(getApplicationContext(), GalleryActivity.class);
            i.putExtra("chosen_pic", imageBitmap);
//            startActivity(i);
        }
    }

//    public static void setCameraDisplayOrientation(Activity activity,
//                                                   int cameraId, android.hardware.Camera camera) {
//        android.hardware.Camera.CameraInfo info =
//                new android.hardware.Camera.CameraInfo();
//        android.hardware.Camera.getCameraInfo(cameraId, info);
//        int rotation = activity.getWindowManager().getDefaultDisplay()
//                .getRotation();
//        int degrees = 0;
//        switch (rotation) {
////            case Surface.ROTATION_0: degrees = 0; break;
////            case Surface.ROTATION_90: degrees = 90; break;
////            case Surface.ROTATION_180: degrees = 180; break;
////            case Surface.ROTATION_270: degrees = 270; break;
////        }
////
////        int result;
////        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
//            result = (info.orientation + degrees) % 360;
//            result = (360 - result) % 360;  // compensate the mirror
//        } else {  // back-facing
//            result = (info.orientation - degrees + 360) % 360;
//        }
//        camera.setDisplayOrientation(result);
//    }
//
//    private int getBackFacingCameraId() {
//        int cameraId = -1;
//        // Search for the front facing camera
//        int numberOfCameras = Camera.getNumberOfCameras();
//        for (int i = 0; i < numberOfCameras; i++) {
//            Camera.CameraInfo info = new Camera.CameraInfo();
//            Camera.getCameraInfo(i, info);
//            if (info.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
//
//                cameraId = i;
//                break;
//            }
//        }
//        return cameraId;
//    }
//
//    public static Camera getCameraInstance() {
//        Camera c = null;
//        try {
//            c = Camera.open(); // attempt to get a Camera instance
//        }
//        catch (Exception e){
//            // Camera is not available (in use or does not exist)
//        }
//        return c;
//        // returns null if camera is unavailable
//    }




//    public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
//        private SurfaceHolder mHolder;
//        private Camera mCamera;
//
//
//        public CameraPreview(Context context, Camera camera) {
//            super(context);
//            mCamera = camera;
//
//            // Install a SurfaceHolder.Callback so we get notified when the
//            // underlying surface is created and destroyed.
//            mHolder = getHolder();
//            mHolder.addCallback(this);
//            // deprecated setting, but required on Android versions prior to 3.0
//            mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
//        }
//
//
//
//        public void surfaceCreated(SurfaceHolder holder) {
//            // The Surface has been created, now tell the camera where to draw the preview.
//            try {
//                mCamera.setPreviewDisplay(holder);
//                mCamera.startPreview();
//            } catch (IOException e) {
//                Log.d("TAG", "Error setting camera preview: " + e.getMessage());
//            }
//        }
//
//        public void surfaceDestroyed(SurfaceHolder holder) {
//            // empty. Take care of releasing the Camera preview in your activity.
//        }
//
//
//
//        public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
//            // If your preview can change or rotate, take care of those events here.
//            // Make sure to stop the preview before resizing or reformatting it.
//
//            if (mHolder.getSurface() == null){
//                // preview surface does not exist
//                return;
//            }
//
//            // stop preview before making changes
//            try {
//                mCamera.stopPreview();
//            } catch (Exception e){
//                // ignore: tried to stop a non-existent preview
//            }
//
//            // set preview size and make any resize, rotate or
//            // reformatting changes here
//
//            // start preview with new settings
//            try {
//                mCamera.setPreviewDisplay(mHolder);
//                mCamera.startPreview();
//
//            } catch (Exception e){
//                Log.d("TAG", "Error starting camera preview: " + e.getMessage());
//            }
//        }
//    }
}