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
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class GalleryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        ImageView preview = (ImageView) findViewById(R.id.gallery_preview);

        Intent i = getIntent();
        Bitmap yourSelectedImage = (Bitmap) i.getParcelableExtra("chosen_pic");
        String filePath = i.getStringExtra("chosen_pic2");
        Bitmap yourSelectedImage2 = BitmapFactory.decodeFile(filePath);


//        byte[] decodedString = Base64.decode(pic.getPhoto(),Base64.NO_WRAP);
//        InputStream inputStream  = new ByteArrayInputStream(decodedString);
//        Bitmap bitmap  = BitmapFactory.decodeStream(inputStream);
        if(yourSelectedImage!=null){
            preview.setImageBitmap(yourSelectedImage);
        }
        else{
            preview.setImageBitmap(RotateBitmap(yourSelectedImage2,90));
        }

    }

    public static Bitmap RotateBitmap(Bitmap source, float angle)
    {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }
}
