package com.example.q.cs496_week3;

import android.app.Person;
import android.content.Intent;
import android.content.res.Resources;
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
import android.util.DisplayMetrics;
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
        FloatingActionButton rotate_left_ninty = findViewById(R.id.rotate_left_ninty);
        FloatingActionButton rotate_right_ninty = findViewById(R.id.rotate_right_ninty);

        final ImageView cropped_image = findViewById(R.id.cropped_image);
        cropped_image.setAlpha(0);

        Intent i = getIntent();
        String filePath = i.getStringExtra("chosen_pic");
        final Bitmap yourSelectedImage = BitmapFactory.decodeFile(filePath);


        rotate_left.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.rotate_left));
        rotate_right.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.rotate_right));
        rotate_left_ninty.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.rotate_left_ninty));
        rotate_right_ninty.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.rotate_right_ninty));



        final CropImageView cropImageView = findViewById(R.id.cropImageView);
        RotateBitmap(yourSelectedImage,angle,cropImageView);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap cropped = cropImageView.getCroppedImage();
                Log.d("Tag",cropped.getWidth()+"   "+cropped.getHeight());
                    // Error occurred while creating the File

                Bitmap std_id_cropped = Bitmap.createScaledBitmap(cropped,2460,1560,true);
                Bitmap std_pic = Bitmap.createBitmap(std_id_cropped, 1690,115,610,720);
                Bitmap std_name = Bitmap.createBitmap(std_id_cropped,920,240,420,130);
                Bitmap std_num = Bitmap.createBitmap(std_id_cropped,920,655,406,90);

                Intent i = new Intent(getApplicationContext(), SwapActivity.class);
                ByteArrayOutputStream bs = new ByteArrayOutputStream();
                std_name.compress(Bitmap.CompressFormat.PNG, 50, bs);
                i.putExtra("std_name", bs.toByteArray());

                ByteArrayOutputStream bs2 = new ByteArrayOutputStream();
                std_pic.compress(Bitmap.CompressFormat.PNG, 50, bs2);
                i.putExtra("std_pic", bs2.toByteArray());

                ByteArrayOutputStream bs3 = new ByteArrayOutputStream();
                std_num.compress(Bitmap.CompressFormat.PNG, 50, bs3);
                i.putExtra("std_num", bs3.toByteArray());

                startActivity(i);
            }
        });

        rotate_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cropImageView.rotateImage(-2);
            }
        });

        rotate_left_ninty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cropImageView.rotateImage(-90);
            }
        });

        rotate_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cropImageView.rotateImage(2);
            }
        });

        rotate_right_ninty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cropImageView.rotateImage(90);
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
