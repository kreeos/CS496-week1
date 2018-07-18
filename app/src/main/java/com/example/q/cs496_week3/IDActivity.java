package com.example.q.cs496_week3;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class IDActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_id);
        Button bt_myschool = (Button) findViewById(R.id.bt_myschool);
        Button bt_otherschool = (Button) findViewById(R.id.bt_otherschool);
        Button bt_camera = (Button) findViewById(R.id.bt_camera);
        Button bt_gallery = (Button) findViewById(R.id.bt_gallery);
        Button bt_start_write = (Button) findViewById(R.id.bt_start_write);


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE }, 0);
        }

        bt_myschool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String[] items = new String[]{"KAIST"};
                final int[] selectedIndex = {0};


                AlertDialog.Builder dialog = new AlertDialog.Builder(IDActivity.this);
                dialog .setTitle("본인의 학교를 선택하세요")
                        .setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                selectedIndex[0] = which;
                                //선택 시 스트링 내의 선택사항의 포지션을 지정
                            }
                        })

                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(selectedIndex[0] == 0){
                                }
                                if(selectedIndex[0] == 1){
                                }
                                if(selectedIndex[0] == 2){
                                }
                                if(selectedIndex[0] == 3){
                                }
                                if(selectedIndex[0] == 4){
                                }
                                if(selectedIndex[0] == 5){
                                }
                            }

                        }).create().show();




            }
        });

        bt_otherschool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String[] items = new String[]{"고려대학교"};
                final int[] selectedIndex = {0};


                AlertDialog.Builder dialog = new AlertDialog.Builder(IDActivity.this);
                dialog .setTitle("원하는 학교를 선택하세요")
                        .setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                selectedIndex[0] = which;
                                //선택 시 스트링 내의 선택사항의 포지션을 지정
                            }
                        })

                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(selectedIndex[0] == 0){
                                }
                                if(selectedIndex[0] == 1){
                                }
                                if(selectedIndex[0] == 2){
                                }
                                if(selectedIndex[0] == 3){
                                }
                                if(selectedIndex[0] == 4){
                                }
                                if(selectedIndex[0] == 5){
                                }
                            }

                        }).create().show();

            }
        });

        bt_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {// Create an instance of Camera
                Intent i = new Intent(getApplicationContext(), CameraActivity.class);
                startActivity(i);
            }
        });

        bt_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                final int ACTIVITY_SELECT_IMAGE = 1234;
                startActivityForResult(i, ACTIVITY_SELECT_IMAGE);
            }
        });

        bt_start_write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), WriteActivity.class);
                startActivity(i);
            }
        });

    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode) {
            case 1234:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String filePath = cursor.getString(columnIndex);
                    cursor.close();


                    /* Now you have choosen image in Bitmap format in object "yourSelectedImage". You can use it in way you want! */

                    Intent i = new Intent(getApplicationContext(), GalleryActivity.class);

                    i.putExtra("chosen_pic", filePath);

                    startActivity(i);
                }
        }

    }
}
