package com.example.mst.test;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn = (Button) findViewById(R.id.test_record);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strPath = Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_RINGTONES).getAbsolutePath()+"/audio.m";

                AudioRecordManager.getInstance().startRecord(strPath);
            }
        });


        Button btnStop = (Button) findViewById(R.id.stop_record);
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AudioRecordManager.getInstance().stopRecord();
            }
        });
    }
}