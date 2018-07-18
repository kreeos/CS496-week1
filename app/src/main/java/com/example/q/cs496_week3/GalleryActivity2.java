package com.example.q.cs496_week3;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.theartofdev.edmodo.cropper.CropImageView;

public class GalleryActivity2 extends AppCompatActivity {
    int angle = 90;
    public static final int FEED_DIMENSION = 32;
    String mCurrentPhotoPath;

    private static final String LABEL_FILE = "2350-common-hangul.txt";
    private static final String MODEL_FILE = "optimized_hangul_tensorflow.pb";
    Bitmap[] bitmaps = new Bitmap[20];
    private HangulClassifier classifier;
    String[] currentTopLabels;
    TextView resultText;
    TextView numText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery2);
        Button button = findViewById(R.id.randombt2);
        FloatingActionButton rotate_left = findViewById(R.id.rotate_left2);
        FloatingActionButton rotate_right = findViewById(R.id.rotate_right2);

        final ImageView cropped_image = findViewById(R.id.cropped_image2);
        cropped_image.setAlpha(0);

        Intent i = getIntent();
        String filePath = i.getStringExtra("chosen_pic");
        final Bitmap yourSelectedImage = BitmapFactory.decodeFile(filePath);


        rotate_left.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.rotate_left));
        rotate_right.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.rotate_right));

        resultText = findViewById(R.id.textView);
        numText = findViewById(R.id.textView2);

        final CropImageView cropImageView = findViewById(R.id.cropImageView2);
        RotateBitmap(yourSelectedImage,angle,cropImageView);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("tag1", numText.getText().toString());
                int char_num = Integer.parseInt(numText.getText().toString());
                Bitmap cropped = cropImageView.getCroppedImage();
                cropped = RemoveNoise(cropped);
                int width2 = cropped.getWidth();
                int height2 = cropped.getHeight();
                float new_width = char_num;
                int padding =0;
                if(char_num != 1){
                    padding = 4;
                }
                int num = (int) Math.floor(width2/new_width);
                Log.d("tag1",width2+"   "+height2+"     "+new_width+"     "+num);
                for(int j = 0;j<new_width;j++){
                    if(j==0){
                        bitmaps[j] = Bitmap.createBitmap(cropped,(num*j),0,num+padding,height2);
                    }
                    if(j>0 && j<(new_width-1)){
                        bitmaps[j] = Bitmap.createBitmap(cropped,(num*j)-padding,0,num+padding,height2);
                    }
                    if(j==(new_width-1)){
                        bitmaps[j] = Bitmap.createBitmap(cropped,(num*j)-padding,0,num,height2);
                    }
                }
                for(int i =0;i<new_width;i++){
                    Log.d("Tag", String.valueOf(bitmaps[i]));
                    classify(getPixelData(bitmaps[i]));
                }
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

        //start swap_activity

//        Intent i = getIntent();
//        Bitmap filePath = i.getExtras("name");
//        String filePath = i.getStringExtra("cropped_pic");
//        final Bitmap yourSelectedImage = BitmapFactory.decodeFile(filePath);
//        imageView.setImageBitmap(yourSelectedImage);

        loadModel();

    }


    public static void RotateBitmap(Bitmap source, float angle, CropImageView cropImageView)
    {
        int width = source.getWidth();
        int height = source.getHeight();
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        cropImageView.setImageBitmap( Bitmap.createBitmap(source, 0, 0, width, height, matrix, true));

    }

    void classify(float[] pixels) {
        currentTopLabels = classifier.classify(pixels);
        resultText.append(currentTopLabels[0]);
    }

    public float[] getPixelData(Bitmap bitmap) {
//        Drawable test = this.getResources().getDrawable(R.drawable.test_full2);
//        Bitmap bitmap2 = ((BitmapDrawable)test).getBitmap();
//        int width2 = bitmap2.getWidth();
//        int height2 = bitmap2.getHeight();
//        Bitmap test3 = Bitmap.createBitmap(bitmap2,0,0,(width2/3),height2);

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
            returnPixels[i] = (float) (b/255.0);
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
    public Bitmap RemoveNoise(Bitmap bmap) {
        for (int x = 0; x < bmap.getWidth(); x++) {
            for (int y = 0; y < bmap.getHeight(); y++) {
                int pixel = bmap.getPixel(x, y);
                int R = Color.red(pixel);
                int G = Color.green(pixel);
                int B = Color.blue(pixel);
                if (R < 162 && G < 162 && B < 162)
                    bmap.setPixel(x, y, Color.BLACK);
            }
        }
        for (int  x = 0; x < bmap.getWidth(); x++) {
            for (int y = 0; y < bmap.getHeight(); y++) {
                int pixel = bmap.getPixel(x, y);
                int R = Color.red(pixel);
                int G = Color.green(pixel);
                int B = Color.blue(pixel);
                if (R > 162 && G > 162 && B > 162)
                    bmap.setPixel(x, y, Color.WHITE);
            }
        }
        return bmap;
    }

    public static Bitmap grayScaleImage(Bitmap src) {
        // constant factors
        final double GS_RED = 0.299;
        final double GS_GREEN = 0.587;
        final double GS_BLUE = 0.114;

        // create output bitmap
        Bitmap bmOut = Bitmap.createBitmap(src.getWidth(), src.getHeight(), src.getConfig());
        // pixel information
        int A, R, G, B;
        int pixel;

        // get image size
        int width = src.getWidth();
        int height = src.getHeight();

        // scan through every single pixel
        for(int x = 0; x < width; ++x) {
            for(int y = 0; y < height; ++y) {
                // get one pixel color
                pixel = src.getPixel(x, y);
                // retrieve color of all channels
                A = Color.alpha(pixel);
                R = Color.red(pixel);
                G = Color.green(pixel);
                B = Color.blue(pixel);
                // take conversion up to one single value
                R = G = B = (int)(GS_RED * R + GS_GREEN * G + GS_BLUE * B);
                // set new pixel color to output bitmap
                bmOut.setPixel(x, y, Color.argb(A, R, G, B));
            }
        }

        // return final image
        return bmOut;
    }

}
