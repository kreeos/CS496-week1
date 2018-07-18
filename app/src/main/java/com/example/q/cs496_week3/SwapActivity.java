package com.example.q.cs496_week3;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.q.cs496_week3.GalleryActivity.FEED_DIMENSION;


public class SwapActivity extends AppCompatActivity{

    private static final String LABEL_FILE = "2350-common-hangul.txt";
    private static final String MODEL_FILE = "optimized_hangul_tensorflow.pb";
    private static final String MODEL_FILE2 = "number_classifier.pb";
    private static final String LABEL_FILE2 = "number_label.txt";
    Bitmap[] bitmaps = new Bitmap[20];
    private HangulClassifier classifier;
    private NumberClassifier classifier2;
    String[] currentTopLabels;
    TextView resultText;
    Bitmap b,b2,b3;
    String result ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swap);
        final ImageView converted_id = findViewById(R.id.swap_id);
        converted_id.setMaxWidth((int) ScreenWidth());
//        final ImageView imageView = findViewById(R.id.student_id);
        converted_id.setAlpha(0);
//        Drawable test = getResources().getDrawable(R.drawable.test4);
//        resultText = findViewById(R.id.resultText);
//        final Bitmap bitmap2 = ((BitmapDrawable)test).getBitmap();
        Button bt_save = (Button) findViewById(R.id.bt_save);
        Button bt_back = (Button) findViewById(R.id.bt_back);

        if(getIntent().hasExtra("std_name")) {
            b = BitmapFactory.decodeByteArray(
                    getIntent().getByteArrayExtra("std_name"),0,getIntent().getByteArrayExtra("std_name").length);

        }
        if(getIntent().hasExtra("std_pic")) {
            b2 = BitmapFactory.decodeByteArray(
                    getIntent().getByteArrayExtra("std_pic"),0,getIntent().getByteArrayExtra("std_pic").length);
        }
        if(getIntent().hasExtra("std_num")) {
            b3 = BitmapFactory.decodeByteArray(
                    getIntent().getByteArrayExtra("std_num"),0,getIntent().getByteArrayExtra("std_num").length);
//            imageView.setImageBitmap(b3);
        }



        final Button bt_classify = findViewById(R.id.bt_classify);
        bt_classify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int width2 = b.getWidth();
                int height2 = b.getHeight();
                float new_width = (float) Math.ceil((float)width2/(float)height2);
                new_width = 3;
                int num = (int) Math.floor(width2/new_width);
                Log.d("tag1",width2+"   "+height2+"     "+new_width+"     "+num);
                for(int j = 0;j<new_width;j++){
                    if(j==0){
                        bitmaps[j] = Bitmap.createBitmap(b,(num*j),0,num+3,height2);
                    }
                    if(j>0 && j<(new_width-1)){
                        bitmaps[j] = Bitmap.createBitmap(b,(num*j)-3,0,num+3,height2);
                    }
                    if(j==(new_width-1)){
                        bitmaps[j] = Bitmap.createBitmap(b,(num*j)-3,0,num+3,height2);
                    }
                }
                for(int i =0;i<new_width;i++){
                    Log.d("Tag", String.valueOf(bitmaps[i]));
                    classify(getPixelData(bitmaps[i]));
                }

                Drawable drawable = getResources().getDrawable(R.drawable.korea_temp);
                Bitmap id_temp = ((BitmapDrawable)drawable).getBitmap();
                Bitmap mutableBitmap = id_temp.copy(Bitmap.Config.ARGB_8888, true);
                Log.d("Tagtest",mutableBitmap.getWidth()+"  "+mutableBitmap.getHeight());
                Canvas mCanvas = new Canvas(mutableBitmap);
                Paint paint = new Paint();
                mCanvas.drawBitmap(b2,890,240,null);
                paint.setTextSize(110);
                Typeface plain = Typeface.createFromAsset(getApplicationContext().getAssets(), "LexiGulim090423.ttf");
                paint.setColor(getResources().getColor(R.color.colorgrey));
                paint.setTypeface(plain.DEFAULT_BOLD);
                mCanvas.drawText(result,1600,525,paint);
                result = "";

                int width3 = b3.getWidth();
                int height3 = b3.getHeight();
                float new_width2 = (float) Math.ceil((float)width2/(float)height2);
                new_width2 = 8;
                int num2 = (int) Math.floor(width3/new_width2);
                Log.d("tag1",width3+"   "+height3+"     "+new_width2+"     "+num2);
                for(int j = 0;j<new_width2;j++){
                    if(j==0){
                        bitmaps[j] = Bitmap.createBitmap(b3,(num2*j),0,num2+1,height3);
                    }
                    if(j>0 && j<(new_width2-1)){
                        bitmaps[j] = Bitmap.createBitmap(b3,(num2*j)-2,0,num2+3,height3);
                    }
                    if(j==(new_width2-1)){
                        bitmaps[j] = Bitmap.createBitmap(b3,(num2*j)-1,0,num2+1,height3);
                    }
                }
                for(int i =0;i<new_width2;i++){
                    Log.d("Tag", String.valueOf(bitmaps[i]));
                    classify2(getPixelData(bitmaps[i]));
                }
                paint.setTextSize(80);
                paint.setColor(getResources().getColor(R.color.colorgrey));
                paint.setTypeface(plain.DEFAULT_BOLD);
                mCanvas.drawText(result,1600,875,paint);

                converted_id.setImageBitmap(mutableBitmap);
                converted_id.setAlpha(255);
                bt_classify.setEnabled(false);
            }
        });

        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        bt_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BitmapDrawable drawable = (BitmapDrawable) converted_id.getDrawable();
                Bitmap bitmap = drawable.getBitmap();
                MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "title", "descripton");
                Toast.makeText(SwapActivity.this, "사진이 저장되었습니다" , Toast.LENGTH_SHORT).show();
            }
        });

        loadModel();
        loadModeltwo();
    }

    void classify(float[] pixels) {
        currentTopLabels = classifier.classify(pixels);
//        resultText.append(currentTopLabels[0]);
        result=result+currentTopLabels[0];
    }

    void classify2(float[] pixels) {
        currentTopLabels = classifier2.classify(pixels);
//        resultText.append(currentTopLabels[0]);
        result=result+currentTopLabels[0];
    }

    public float[] getPixelData(Bitmap bitmap) {

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
    private void loadModeltwo() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    classifier2 = NumberClassifier.create(getAssets(),
                            MODEL_FILE2, LABEL_FILE2, 32,
                            "input", "keep_prob", "output");
                } catch (final Exception e) {
                    throw new RuntimeException("Error loading pre-trained model.", e);
                }
            }
        }).start();
    }
    //function getting screen width
    public float ScreenWidth(){
        Resources resources = getApplicationContext().getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.widthPixels;
    }
}
