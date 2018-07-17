package com.example.q.cs496_week3;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayInputStream;

import static com.example.q.cs496_week3.GalleryActivity.FEED_DIMENSION;


public class SwapActivity extends AppCompatActivity{

    private static final String LABEL_FILE = "2350-common-hangul.txt";
    private static final String MODEL_FILE = "optimized_hangul_tensorflow.pb";
    Bitmap[] bitmaps = new Bitmap[20];
    private HangulClassifier classifier;
    String[] currentTopLabels;
    private TextView resultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swap);

        final ImageView imageView = findViewById(R.id.student_id);

        Intent i = new Intent();
        String b = i.getStringExtra("byteArray");
        byte[] bImage = Base64.decode(b, 0);
        ByteArrayInputStream bais = new ByteArrayInputStream(bImage);
        Bitmap bm = BitmapFactory.decodeStream(bais);
        imageView.setImageBitmap(bm);

//        Intent i = getIntent();
//        Bitmap filePath = i.getExtras("name");
//        imageView.setImageBitmap(filePath);

        Button bt_classify = findViewById(R.id.bt_classify);
        bt_classify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
                Bitmap bmap = drawable.getBitmap();
                int width2 = bmap.getWidth();
                int height2 = bmap.getHeight();
                float new_width = (float) Math.ceil(width2/height2);
                int num = (int) Math.floor(width2/new_width);
                for(int j = 0;j<new_width;j++){
                    if(j==0){
                        bitmaps[j] = Bitmap.createBitmap(bmap,(num*j),0,num+5,height2);
                    }
                    if(j>0 && j<(new_width-1)){
                        bitmaps[j] = Bitmap.createBitmap(bmap,(num*j)-5,0,num+10,height2);
                    }
                    if(j==(new_width-1)){
                        bitmaps[j] = Bitmap.createBitmap(bmap,(num*j)-5,0,num,height2);
                    }
                }
                for(int i =0;i<new_width;i++){
                    classify(getPixelData(bitmaps[i]));
                }

            }
        });
    }

    void classify(float pixels[]) {
        currentTopLabels = classifier.classify(pixels);
        resultText.append(currentTopLabels[0]);
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
