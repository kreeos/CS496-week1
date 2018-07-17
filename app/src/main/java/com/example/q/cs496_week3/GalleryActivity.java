package com.example.q.cs496_week3;

import android.app.Person;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.support.design.widget.FloatingActionButton;
import com.theartofdev.edmodo.cropper.CropImageView;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.q.cs496_week3.CameraActivity.REQUEST_IMAGE_CAPTURE;
import static com.example.q.cs496_week3.CameraActivity.REQUEST_TAKE_PHOTO;

public class GalleryActivity extends AppCompatActivity {
    int angle = 90;
    public static final int FEED_DIMENSION = 32;
    String mCurrentPhotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        Button button = findViewById(R.id.randombt);
        FloatingActionButton rotate_left = findViewById(R.id.rotate_left);
        FloatingActionButton rotate_right = findViewById(R.id.rotate_right);

        final ImageView cropped_image = findViewById(R.id.cropped_image);
        cropped_image.setAlpha(0);

        Intent i = getIntent();
        String filePath = i.getStringExtra("chosen_pic");
        final Bitmap yourSelectedImage = BitmapFactory.decodeFile(filePath);


        rotate_left.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.rotate_left));
        rotate_right.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.rotate_right));



        final CropImageView cropImageView = findViewById(R.id.cropImageView);
        RotateBitmap(yourSelectedImage,angle,cropImageView);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap cropped = cropImageView.getCroppedImage();
                Log.d("Tag",cropped.getWidth()+"   "+cropped.getHeight());
//                ByteArrayOutputStream bs = new ByteArrayOutputStream();
//                cropped.compress(Bitmap.CompressFormat.PNG, 100, bs);
//                byte[] byteArray = bs.toByteArray();
//                String encoded = Base64.encodeToString(byteArray, 0);
//
//                Intent takePictureIntent = new Intent(getApplicationContext(),MediaStore.);
//
//                File photoFile = null;
//                try {
//                    photoFile = createImageFile();
//                } catch (IOException ex) {
                    // Error occurred while creating the File
                Intent i = new Intent(getApplicationContext(), SwapActivity.class);
                ByteArrayOutputStream bs = new ByteArrayOutputStream();
                cropped.compress(Bitmap.CompressFormat.PNG, 50, bs);
                i.putExtra("byteArray", bs.toByteArray());
                startActivity(i);
//                }
                // Continue only if the File was successfully created
//                if (photoFile != null) {
//                    Uri photoURI = FileProvider.getUriForFile(GalleryActivity.this,
//                            "com.example.android.fileprovider",
//                            photoFile);
//                    takePictureIntent.putExtra(encoded, photoURI);
//                    startActivity(takePictureIntent);
//                }
//                ByteArrayOutputStream bs = new ByteArrayOutputStream();
//                bitmap2.compress(Bitmap.CompressFormat.PNG, 50, bs);
//                byte[] byteArray = bs.toByteArray();
//                String encoded = Base64.encodeToString(byteArray, 0);
//                intent.putExtra("byteArray",encoded);
//                startActivity(intent);
            }
        });

        rotate_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cropImageView.rotateImage(-2);
            }
        });

        rotate_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cropImageView.rotateImage(2);
            }
        });

    }


    public static void RotateBitmap(Bitmap source, float angle, CropImageView cropImageView)
    {
        int width = source.getWidth();
        int height = source.getHeight();
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        cropImageView.setImageBitmap( Bitmap.createBitmap(source, 0, 0, width, height, matrix, true));

    }




}
