package com.example.q.cs496_week3;

import android.app.Person;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
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
import java.io.InputStream;

public class GalleryActivity extends AppCompatActivity {
    int angle = 90;
    public static final int FEED_DIMENSION = 32;

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

//                Bitmap std_id_cropped2 = Bitmap.createBitmap(cropped,0,0,2460,1560);
//                Bitmap std_id_cropped = Bitmap.createScaledBitmap(cropped,2460,1560,true);
//                Bitmap std_name = Bitmap.createBitmap(std_id_cropped,920,240,420,130);
                Intent intent = new Intent(getApplicationContext(),SwapActivity.class);
                ByteArrayOutputStream bs = new ByteArrayOutputStream();
                cropped.compress(Bitmap.CompressFormat.PNG, 100, bs);
                byte[] byteArray = bs.toByteArray();
                String encoded = Base64.encodeToString(byteArray, 0);
                intent.putExtra("byteArray",encoded);
                startActivity(intent);
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


    public float[] getPixelData(Bitmap bitmap) {
//        Drawable test = this.getResources().getDrawable(R.drawable.test_full6);
//        Bitmap bitmap2 = ((BitmapDrawable)test).getBitmap();
//        int width2 = bitmap2.getWidth();
//        int height2 = bitmap2.getHeight();
//        Bitmap test3 = Bitmap.createBitmap(bitmap2,0,0,(width2/3)+7,height2);

        Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, FEED_DIMENSION,
                FEED_DIMENSION, false);

        int width = FEED_DIMENSION;
        int height = FEED_DIMENSION;

        int[] pixels = new int[width * height];
        resizedBitmap.getPixels(pixels, 0, width, 0, 0, width, height);

        float[] returnPixels = new float[pixels.length];

        // Here we want to convert each pixel to a floating point number between 0.0 and 1.0 with
        // 1.0 being white and 0.0 being black.
        for (int i = 0; i < pixels.length; ++i) {
            int pix = pixels[i];
            int b = pix & 0xff;
            if(b>150){
                returnPixels[i] = (float) 1;
            }
            else{
                returnPixels[i] = (float) 0;
            }

        }
        return returnPixels;
    }



}
