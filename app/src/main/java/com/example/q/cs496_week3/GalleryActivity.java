package com.example.q.cs496_week3;

import android.app.Person;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
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
import java.io.InputStream;

public class GalleryActivity extends AppCompatActivity {
    int angle = 90;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        Button button = findViewById(R.id.randombt);
        FloatingActionButton rotate_left = findViewById(R.id.rotate_left);
        FloatingActionButton rotate_right = findViewById(R.id.rotate_right);

//        final ImageView preview = (ImageView) findViewById(R.id.gallery_preview);

        Intent i = getIntent();
//        Bitmap yourSelectedImage = (Bitmap) i.getParcelableExtra("chosen_pic");
//        String filePath2 = i.getStringExtra("chosen_pic");
        String filePath = i.getStringExtra("chosen_pic");
        final Bitmap yourSelectedImage = BitmapFactory.decodeFile(filePath);
//        Bitmap yourSelectedImage = BitmapFactory.decodeFile(filePath2);


        rotate_left.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.rotate_left));
        rotate_right.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.rotate_right));



//        byte[] decodedString = Base64.decode(pic.getPhoto(),Base64.NO_WRAP);
//        InputStream inputStream  = new ByteArrayInputStream(decodedString);
//        Bitmap bitmap  = BitmapFactory.decodeStream(inputStream);
//        if(yourSelectedImage!=null){
//            preview.setImageBitmap(yourSelectedImage);
//        }
//        if(yourSelectedImage2!=null){
//            preview.setImageBitmap(RotateBitmap(yourSelectedImage,90));
//        }

        final CropImageView cropImageView = findViewById(R.id.cropImageView);
        RotateBitmap(yourSelectedImage,angle,cropImageView);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap cropped = cropImageView.getCroppedImage();
//                preview.setImageBitmap(cropped);
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
