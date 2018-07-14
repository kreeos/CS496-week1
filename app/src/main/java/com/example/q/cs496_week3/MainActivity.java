package com.example.q.cs496_week3;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button bt_myschool = (Button) findViewById(R.id.bt_myschool);
        Button bt_otherschool = (Button) findViewById(R.id.bt_otherschool);
        Button bt_camera = (Button) findViewById(R.id.bt_camera);
        Button bt_gallery = (Button) findViewById(R.id.bt_gallery);

        bt_myschool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String[] items = new String[]{"KAIST"};
                final int[] selectedIndex = {0};


                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
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


                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
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
        public void onClick(View view) {
            
        }
    });

    }
}
