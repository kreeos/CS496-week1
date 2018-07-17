package com.example.q.cs496_week3;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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
    TextView resultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swap);

        final ImageView imageView = findViewById(R.id.student_id);

        Drawable test = getResources().getDrawable(R.drawable.test4);
        resultText = findViewById(R.id.resultText);
        final Bitmap bitmap2 = ((BitmapDrawable)test).getBitmap();

        if(getIntent().hasExtra("byteArray")) {
            Bitmap b = BitmapFactory.decodeByteArray(
                    getIntent().getByteArrayExtra("byteArray"),0,getIntent().getByteArrayExtra("byteArray").length);
            imageView.setImageBitmap(b);
        }

//        Intent i = getIntent();
//        Bitmap filePath = i.getExtras("name");
//        String filePath = i.getStringExtra("cropped_pic");
//        final Bitmap yourSelectedImage = BitmapFactory.decodeFile(filePath);
//        imageView.setImageBitmap(yourSelectedImage);

        Button bt_classify = findViewById(R.id.bt_classify);
        bt_classify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int width2 = bitmap2.getWidth();
                int height2 = bitmap2.getHeight();
                float new_width = (float) Math.ceil((float)width2/(float)height2);
                int num = (int) Math.floor(width2/new_width);
                Log.d("tag1",width2+"   "+height2+"     "+new_width+"     "+num);
                for(int j = 0;j<new_width;j++){
                    if(j==0){
                        bitmaps[j] = Bitmap.createBitmap(bitmap2,(num*j),0,num+2,height2);
                    }
                    if(j>0 && j<(new_width-1)){
                        bitmaps[j] = Bitmap.createBitmap(bitmap2,(num*j)-2,0,num+4,height2);
                    }
                    if(j==(new_width-1)){
                        bitmaps[j] = Bitmap.createBitmap(bitmap2,(num*j)-2,0,num,height2);
                    }
                }
                for(int i =0;i<new_width;i++){
                    Log.d("Tag", String.valueOf(bitmaps[i]));
                    classify(getPixelData(bitmaps[i]));
                }
            }
        });
        loadModel();
    }

    void classify(float[] pixels) {
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
//            if(b>150){
                returnPixels[i] = (float) (b/255);
//            }
//            else{
//                returnPixels[i] = (float) 0;
//            }

        }
        return returnPixels;
    }

    private void loadModel() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    classifier = HangulClassifier.create(getAssets(),
                            MODEL_FILE, LABEL_FILE, 32,
                            "input", "keep_prob", "output");
                } catch (final Exception e) {
                    throw new RuntimeException("Error loading pre-trained model.", e);
                }
            }
        }).start();
    }

}
