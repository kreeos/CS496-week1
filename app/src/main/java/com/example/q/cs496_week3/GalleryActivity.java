package com.example.q.cs496_week3;

import android.app.Person;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class GalleryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        Button button = findViewById(R.id.randombt);
//        ImageView preview = (ImageView) findViewById(R.id.gallery_preview);

        Intent i = getIntent();
//        Bitmap yourSelectedImage = (Bitmap) i.getParcelableExtra("chosen_pic");
//        String filePath2 = i.getStringExtra("chosen_pic");
        String filePath = i.getStringExtra("chosen_pic");
        Bitmap yourSelectedImage = BitmapFactory.decodeFile(filePath);
//        Bitmap yourSelectedImage = BitmapFactory.decodeFile(filePath2);


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
        cropImageView.setImageBitmap(RotateBitmap(yourSelectedImage,90));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap cropped = cropImageView.getCroppedImage();
                cropImageView.setImageBitmap(cropped);
            }
        });


    }

    public static Bitmap RotateBitmap(Bitmap source, float angle)
    {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }
}
