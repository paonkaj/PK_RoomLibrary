package com.mda.roomlibrary;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class CameraActivity extends Activity {
    public final static String DEBUG_TAG = "MakePhotoActivity";
    private Camera camera;
    private int cameraId = 0;
    Button btn;
    private CameraPreview mPreview;

    // This tag is used for error or debug log.
    private static final String TAG_TAKE_PICTURE = "TAKE_PICTURE";

    // This is the request code when start camera activity use implicit intent.
    public static final int REQUEST_CODE_TAKE_PICTURE = 1;

    // This output image file uri is used by camera app to save taken picture.
    private Uri outputImgUri;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        btn = (Button)findViewById(R.id.captureFront);
        // Create an instance of Camera

        camera  = getCameraInstance();

        // Create our Preview view and set it as the content of our activity.
        mPreview = new CameraPreview(this, camera);
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        preview.addView(mPreview);



        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try{
                    // Startup camera app.
                    // Create an implicit intent which require take picture action..
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    // Specify the output image uri for the camera app to save taken picture.
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputImgUri);
                    // Start the camera activity with the request code and waiting for the app process result.
                    startActivityForResult(cameraIntent, REQUEST_CODE_TAKE_PICTURE);


                }catch (Exception e)
                {

                }

                camera.takePicture(null, null,
                        new PhotoHandler(getApplicationContext()));
            }
        });



        // do we have a camera?
//        if (!getPackageManager()
//                .hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
//            Toast.makeText(this, "No camera on this device", Toast.LENGTH_LONG)
//                    .show();
//        } else {
//            cameraId = findFrontFacingCamera();
//            if (cameraId < 0) {
//                Toast.makeText(this, "No front facing camera found.",
//                        Toast.LENGTH_LONG).show();
//            } else {
//                camera = Camera.open(cameraId);
//            }
//        }
    }

    public static Camera getCameraInstance(){
        Camera c = null;
        try {
            c = Camera.open(); // attempt to get a Camera instance
        }
        catch (Exception e){
            // Camera is not available (in use or does not exist)
        }
        return c; // returns null if camera is unavailable
    }
//    private int findFrontFacingCamera() {
//        int cameraId = -1;
//        // Search for the front facing camera
//        int numberOfCameras = Camera.getNumberOfCameras();
//        for (int i = 0; i < numberOfCameras; i++) {
//            Camera.CameraInfo info = new Camera.CameraInfo();
//            Camera.getCameraInfo(i, info);
//            if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
//                Log.d(DEBUG_TAG, "Camera found");
//                cameraId = i;
//                break;
//            }
//        }
//        return cameraId;
//    }

    @Override
    protected void onPause() {
        if (camera != null) {
            camera.release();
            camera = null;
        }
        super.onPause();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            // Process result for camera activity.
            if (requestCode == REQUEST_CODE_TAKE_PICTURE) {

                // If camera take picture success.
                if (resultCode == RESULT_OK) {

                    // Get content resolver.
                    ContentResolver contentResolver = getContentResolver();

                    // Use the content resolver to open camera taken image input stream through image uri.
                    InputStream inputStream = contentResolver.openInputStream(outputImgUri);

                    // Decode the image input stream to a bitmap use BitmapFactory.
                    Bitmap pictureBitmap = BitmapFactory.decodeStream(inputStream);

                    // Set the camera taken image bitmap in the image view component to display.
//                    takePictureImageView.setImageBitmap(pictureBitmap);
                }
            }
        }catch(FileNotFoundException ex)
        {
            Log.e(TAG_TAKE_PICTURE, ex.getMessage(), ex);
        }
    }
}




